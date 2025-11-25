package ui;

import domain.*;
import data.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 - Controller for the User Interface and The logic between User and Domain 
 */
public class ParkingMenu {

    private final IOUser io;
    private final ParkingLot parkingLot;

    public ParkingMenu(IOUser io, ParkingLot parkingLot) {
        this.io = io;
        this.parkingLot = parkingLot;
    }

    /**
     * Displays the main menu loop and handles user navigation.
     */
    public void showMainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            io.writeLine("\n--- SMART PARKING SYSTEM ---");
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
                // Catch-all for unexpected errors 
                io.writeLine("Ocurrió un error inesperado en el menú: " + e.getMessage());
            }
        }
        io.writeLine("Sistema cerrado. ¡Hasta luego!");
    }

    //VEHICLE MANAGEMENT METHODS 

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

    private void addVehicleFlow() {
        io.writeLine("--- Ingreso de Vehículo ---");
        try {
            // Get data from user 
            String plate = io.readLine("Ingrese Placa:");
            char typeChar = io.readChar("Tipo de Vehículo (c = Carro, m = Moto):");
            
           
            typeChar = Character.toLowerCase(typeChar);

            // Create Vehicle object passing current rates 
            Vehicle newVehicle = new Vehicle(
                plate, 
                typeChar, 
                parkingLot.getMotorcyclePerSpotAmount(), 
                parkingLot.getMotorcycleCostPerHour(), 
                parkingLot.getCarCostPerHour()
            );

            // Get recommended spot from logic 
            ParkingSpot spot = parkingLot.getRecommendedSpot(typeChar);

            // Add vehicle to the spot 
            spot.addVehicle(newVehicle);

            io.writeLine("ÉXITO: Vehículo agregado en el espacio: " + spot.getSpotName());

            // Save changes / 
            saveState();

        } catch (IllegalArgumentException e) {
            // Invalid character or data  
            io.writeLine("ERROR DE DATOS: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Spot is full  
            io.writeLine("ERROR DE ESPACIO: " + e.getMessage());
        } catch (NoSuchElementException e) {
            // No spots available
            io.writeLine("LO SENTIMOS: No hay espacios disponibles para este tipo de vehículo.");
        }
    }

    private void removeVehicleFlow() {
        io.writeLine("--- Retiro de Vehículo ---");
        String plate = io.readLine("Ingrese la placa del vehículo a retirar:");

        try {
            // Find the spot containing the vehicle 
            ParkingSpot spot = parkingLot.searchVehicleSpot(plate);
            
            // Retrieve the vehicle object 
            Vehicle vehicle = spot.searchVehicle(plate);

            // Calculate ticket price 
            int price = vehicle.getTicket();
            io.writeLine("Tiempo parqueado: " + vehicle.getHoursParked() + " horas.");
            io.writeLine("TOTAL A PAGAR: $" + price);

            // Save ticket to CSV 
            DataManager.saveTicket(vehicle, parkingLot.getCSVFileName());

            // Remove vehicle from memory 
            spot.removeVehicle(vehicle);
            io.writeLine("Vehículo retirado y ticket guardado.");

            // Save system state 
            saveState();

        } catch (NoSuchElementException e) {
            io.writeLine("ERROR: El vehículo no se encuentra en el parqueadero."); 
        }
    }

    private void searchVehicleFlow() {
        io.writeLine("--- Búsqueda de Vehículo ---");
        String plate = io.readLine("Ingrese la placa a buscar:");

        try {
            // Find spot and vehicle 
            ParkingSpot spot = parkingLot.searchVehicleSpot(plate);
            Vehicle vehicle = spot.searchVehicle(plate);

            // Display details 
            io.writeLine("\n--- Vehículo Encontrado ---");
            io.writeLine("Ubicación (Espacio): " + spot.getSpotName());
            io.writeLine("Placa: " + vehicle.getLicensePlate());
            io.writeLine("Tipo: " + vehicle.getVehicleType());
            io.writeLine("Hora de Llegada: " + vehicle.getArrivalTime());
            io.writeLine("Costo acumulado actual: $" + vehicle.getTicket());

        } catch (NoSuchElementException e) {
            io.writeLine("El vehículo no se encuentra en el parqueadero.");
        }
    }

    //SPOT MANAGEMENT METHODS 

    private void handleSpotManagement() {
        io.writeLine("\n--- GESTIÓN DE ESPACIOS ---");
        io.writeLine("1. Ver espacios disponibles");
        io.writeLine("2. Ver ocupación general");
        io.writeLine("3. Agregar un nuevo espacio (Construir)");
        io.writeLine("4. Eliminar un espacio");
        io.writeLine("5. Volver");

        int option = io.readInt("Seleccione:");

        switch (option) {
            case 1:
                // Receive ArrayList<String> from Domain 
                ArrayList<String> available = parkingLot.getAvailableSpotsNames();
                io.writeLine("Espacios con disponibilidad: " + available);
                break;
            case 2:
                io.writeLine("Cantidad de espacios TOTALMENTE llenos: " + parkingLot.getOccupiedSpotsAmount());
                break;
            case 3:
                String newName = io.readLine("Nombre del nuevo espacio (ej: B05):");
                parkingLot.createSpot(newName);
                io.writeLine("Espacio creado exitosamente.");
                saveState();
                break;
            case 4:
                String delName = io.readLine("Nombre del espacio a eliminar:");
                parkingLot.removeSpot(delName);
                io.writeLine("Espacio eliminado (si existía).");
                saveState();
                break;
            case 5: 
                return;
        }
    }

    //REPORTS AND CSV METHODS 

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
                        io.writeLine("No se pudo leer el archivo (quizás aun no hay ventas): " + e.getMessage());
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
            io.writeLine("Error en reportes: " + e.getMessage());
        }
    }

    //CONFIGURATION METHODS 

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
                    // This updates config and existing vehicles / Esto actualiza configuración y vehículos existentes
                    parkingLot.setMotorcyclePerSpotAmount(newCapacity); 
                    break;
                case 4:
                    return;
            }
            io.writeLine("Configuración actualizada.");
            saveState(); 
        } catch (IllegalArgumentException e) {
            io.writeLine("Error: Valor inválido (debe ser positivo).");
        }
    }

    //HELPER TO SAVE STATE 
    
    private void saveState() {
        try {
            DataManager.saveParkingLot(parkingLot);
             io.writeLine("(Autoguardado exitoso)");
        } catch (IOException e) {
            io.writeLine("¡ALERTA! No se pudo guardar el estado del sistema: " + e.getMessage());
        }
    }
}