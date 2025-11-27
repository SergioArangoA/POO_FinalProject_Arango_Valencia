package ui;

import domain.*;
import data.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 - Controller class for the User Interface.
 - This class acts as the bridge between the user (UI) and the business logic (Domain).

 */
public class ParkingMenu {

    private final IOUser io;
    private final ParkingLot parkingLot;

    /**
     - Constructor using Dependency.
     - @param io The input/output handler 
     - @param parkingLot The main system logic 
     */
    public ParkingMenu(IOUser io, ParkingLot parkingLot) {
        this.io = io;
        this.parkingLot = parkingLot;
    }

    /**
     - Displays the main menu loop and handles user navigation.
     */
    public void showMainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            io.writeLine("\n--- SMART PARKING SYSTEM ---");
    
            int ocupados = parkingLot.getOccupiedSpotsAmount();
            int disponibles = parkingLot.getAvailableSpotsAmount();
            io.writeLine("Estado: " + ocupados + " Ocupados | " + disponibles + " Disponibles");
            io.writeLine("------------------------------");
            io.writeLine("1. Gestión de Vehículos (Entrada/Salida/Buscar)");
            io.writeLine("2. Gestión de Espacios (Crear/Eliminar/Listar)");
            io.writeLine("3. Reportes y Ganancias (CSV)");
            io.writeLine("4. Configuración del Sistema");
            io.writeLine("5. Salir");

            int option = io.readInt("Seleccione una opción:");

            try {
                switch (option) {
                    case 1:
                        handleVehicleManagement();
                        break;
                    case 2:
                        handleSpotManagement();
                        break;
                    case 3:
                        handleReports();
                        break;
                    case 4:
                        handleConfiguration();
                        break;
                    case 5:
                        isRunning = false;
                        saveState(); 
                        break;
                    default:
                        io.writeLine("Opción no válida.");
                }
            } catch (Exception e) {
                io.writeLine("Ocurrió un error inesperado en el menú. Por favor intente de nuevo.");
            }
        }
        io.writeLine("Sistema cerrado. ¡Hasta luego!");
    }

    // VEHICLE MANAGEMENT METHODS 

    /**
     - Sub-menu for vehicle operations.
     */
    private void handleVehicleManagement() {
        io.writeLine("\n--- GESTIÓN DE VEHÍCULOS ---");
        io.writeLine("1. Ingresar Vehículo");
        io.writeLine("2. Retirar Vehículo");
        io.writeLine("3. Buscar Vehículo");
        io.writeLine("4. Volver");

        int option = io.readInt("Seleccione:");

        switch (option) {
            case 1:
                addVehicleFlow();
                break;
            case 2:
                removeVehicleFlow();
                break;
            case 3:
                searchVehicleFlow();
                break;
            case 4:
                return;
            default:
                io.writeLine("Opción inválida.");
        }
    }

    /**
     - Handles the logic to add a vehicle.
     */
    private void addVehicleFlow() {
        io.writeLine("--- Ingreso de Vehículo ---");
        try {
            // Get data from user 
            String plate = io.readLine("Ingrese Placa:");
            char typeChar = io.readChar("Tipo de Vehículo (c = Carro, m = Moto):");
            
            typeChar = Character.toLowerCase(typeChar);

            // Create Vehicle object
            Vehicle newVehicle = new Vehicle(
                plate, 
                typeChar, 
                parkingLot.getMotorcyclePerSpotAmount(), 
                parkingLot.getMotorcycleCostPerHour(), 
                parkingLot.getCarCostPerHour()
            );

            // Get recommended spot 
            ParkingSpot spot = parkingLot.getRecommendedSpot(typeChar);

            // Add vehicle to the spot 
            spot.addVehicle(newVehicle);

            io.writeLine("ÉXITO: Vehículo agregado en el espacio: " + spot.getSpotName());

            saveState();

        } catch (IllegalArgumentException e) {
            io.writeLine("Error: Tipo de vehículo inválido o datos incorrectos. Use 'c' o 'm'.");
        } catch (IllegalStateException e) {
            io.writeLine("Error: No hay espacio suficiente en el parqueadero para este vehículo.");
        } catch (NoSuchElementException e) {
            io.writeLine("Lo sentimos: No se encontraron espacios disponibles.");
        }
    }

    /**
     - Handles logic to remove a vehicle and generate the ticket.
     */
    private void removeVehicleFlow() {
        io.writeLine("--- Retiro de Vehículo ---");
        String plate = io.readLine("Ingrese la placa del vehículo a retirar:");

        try {
            // Find spot and vehicle 
            ParkingSpot spot = parkingLot.searchVehicleSpot(plate);
            Vehicle vehicle = spot.searchVehicle(plate);

            // Calculate ticket price 
            int price = vehicle.getTicket();
            io.writeLine("Tiempo parqueado: " + vehicle.getHoursParked() + " horas.");
            io.writeLine("TOTAL A PAGAR: $" + price);

            // Save ticket to CSV 
            DataManager.saveTicket(vehicle, parkingLot.getCSVFileName());

            // Remove from memory 
            spot.removeVehicle(vehicle);
            io.writeLine("Vehículo retirado y ticket guardado.");

            saveState();

        } catch (NoSuchElementException e) {
             io.writeLine("Error: El vehículo con esa placa no se encuentra en el parqueadero."); 
        } catch (IOException e) { 
            io.writeLine("Error Crítico: No se pudo guardar el ticket en el archivo CSV. Verifique permisos.");
        }
    }

    /**
     - Handles searching for a vehicle.
     */
    private void searchVehicleFlow() {
        io.writeLine("--- Búsqueda de Vehículo ---");
        String plate = io.readLine("Ingrese la placa a buscar:");

        try {
            ParkingSpot spot = parkingLot.searchVehicleSpot(plate);
            Vehicle vehicle = spot.searchVehicle(plate);

            io.writeLine("\n--- Vehículo Encontrado ---");
            io.writeLine("Ubicación (Espacio): " + spot.getSpotName());
            io.writeLine("Placa: " + vehicle.getLicensePlate());
            io.writeLine("Tipo: " + vehicle.getVehicleType());
            io.writeLine("Hora de Llegada: " + vehicle.getArrivalTime());
            io.writeLine("Costo acumulado actual: $" + vehicle.getTicket());

        } catch (NoSuchElementException e) {
            io.writeLine("Búsqueda fallida: No existe ningún vehículo con esa placa.");
        }
    }

    // SPOT MANAGEMENT METHODS 

    private void handleSpotManagement() {
        io.writeLine("\n--- GESTIÓN DE ESPACIOS ---");
        io.writeLine("1. Ver espacios disponibles");
        io.writeLine("2. Ver ocupación general");
        io.writeLine("3. Listar espacios del parqueadero(Espacios y Placas)");
        io.writeLine("4. Agregar un nuevo espacio (Construir)");
        io.writeLine("5. Eliminar un espacio");
        io.writeLine("6. Volver");

        int option = io.readInt("Seleccione:");

        switch (option) {
            case 1:
                ArrayList<String> available = parkingLot.getAvailableSpotsNames();
                io.writeLine("Espacios con disponibilidad: " + available);
                break;
            case 2:
                io.writeLine("Cantidad de espacios TOTALMENTE llenos: " + parkingLot.getOccupiedSpotsAmount());
                break;
            case 3:
                io.writeLine("--- Mapa del Parqueadero ---");
                // iterate through spots and vehicles to print the full map
                for (domain.ParkingSpot spot : parkingLot.getSpotList()) {
                    io.writeLine("Espacio [" + spot.getSpotName() + "]:");
                    ArrayList<domain.Vehicle> vehicles = spot.getVehicleList();
                    if (vehicles.isEmpty()) {
                        io.writeLine("   -> (Vacío)");
                    } else {
                        for (domain.Vehicle v : vehicles) {
                            io.writeLine("   -> Vehículo: " + v.getLicensePlate() + " (" + v.getVehicleType() + ")");
                        }
                    }
                }
                break;
            case 4:
                String newName = io.readLine("Nombre del nuevo espacio (ej: B05):");
                parkingLot.createSpot(newName);
                io.writeLine("Espacio creado exitosamente.");
                saveState();
                break;
            case 5:
                String delName = io.readLine("Nombre del espacio a eliminar:");
                try {
                    parkingLot.removeSpot(delName);
                    io.writeLine("Espacio eliminado.");
                    saveState();
                }
                catch (NoSuchElementException e){
                    io.writeLine("No se encontró el espacio");
                }
                break;
            case 6: 
                return;
        }
    }

    // REPORTS AND CSV METHODS 

    private void handleReports() {
        io.writeLine("\n--- REPORTES Y CSV ---");
        io.writeLine("Archivo CSV actual: " + parkingLot.getCSVFileName() + ".csv");
        io.writeLine("1. Ver ganancias totales (Leer CSV)");
        io.writeLine("2. Cambiar nombre del archivo CSV");
        io.writeLine("3. Volver");

        int option = io.readInt("Seleccione:");

        try {
            switch (option) {
                case 1:
                    String csvName = parkingLot.getCSVFileName() + ".csv";
                    try {
                        int total = DataManager.getTotalEarnings(csvName);
                        io.writeLine("Ganancias históricas totales: $" + total);
                    } catch (IOException e) {
                        io.writeLine("Aviso: No se pudo leer el archivo de ganancias. Quizás aún no hay ventas.");
                    }
                    break;
                case 2:
                    String newName = io.readLine("Nuevo nombre para el archivo CSV (sin .csv):");
                    parkingLot.setCSVFileName(newName);
                    io.writeLine("Nombre actualizado. Los futuros tickets se guardarán en: " + newName + ".csv");
                    saveState();
                    break;
                case 3:
                    return;
            }
        } catch (Exception e) {
            io.writeLine("Error al generar reportes. Verifique los datos.");
        }
    }

    // CONFIGURATION METHODS

    private void handleConfiguration() {
        io.writeLine("\n--- CONFIGURACIÓN ---");
        io.writeLine("1. Modificar Tarifa Carro (Actual: " + parkingLot.getCarCostPerHour() + ")");
        io.writeLine("2. Modificar Tarifa Moto (Actual: " + parkingLot.getMotorcycleCostPerHour() + ")");
        io.writeLine("3. Modificar Capacidad Motos por Espacio (Actual: " + parkingLot.getMotorcyclePerSpotAmount() + ")");
        io.writeLine("4. Volver");

        int option = io.readInt("Seleccione:");

        try {
            switch (option) {
                case 1:
                    int newCarCost = io.readInt("Nuevo costo hora Carro:");
                    parkingLot.setCarCostPerHour(newCarCost);
                    break;
                case 2:
                    int newMotoCost = io.readInt("Nuevo costo hora Moto:");
                    parkingLot.setMotorcycleCostPerHour(newMotoCost);
                    break;
                case 3:
                    int newCapacity = io.readInt("Nueva cantidad de motos por espacio:");
                    parkingLot.setMotorcyclePerSpotAmount(newCapacity); 
                    break;
                case 4:
                    return;
            }
            io.writeLine("Configuración actualizada.");
            saveState(); 
        } catch (IllegalArgumentException e) {
            io.writeLine("Error: Los valores de costo deben ser positivos.");
        }
    }

    /**
     - Helper method to save the system state.
     */
    private void saveState() {
        try {
            DataManager.saveParkingLot(parkingLot);
             io.writeLine("(Autoguardado exitoso)");
        } catch (IOException e) {
            io.writeLine("¡ALERTA! No se pudo guardar el estado del sistema. Verifique espacio en disco.");
        }
    }
}