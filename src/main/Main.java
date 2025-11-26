package main;
import ui.*;
import domain.*;
import data.*;
import java.io.IOException;

/**
 - Main entry point of the application 
 - Handles initial setup and data loading 
 */
public class Main {

    public static void main(String[] args) {
        // Initialize UI  
        IOUser io = new IOUserConsole();
        ParkingLot parkingLot = null;

        // Try to load existing data 
        try {
            parkingLot = DataManager.loadParkingLot();
            io.writeLine("--- Bienvenido de nuevo. Datos cargados correctamente. ---");
        } catch (IOException e) {
            // If loading fails, it's the first run or file is missing
            io.writeLine("--- Primera ejecución detectada (o archivo de datos no encontrado) ---");
            io.writeLine("Por favor, configure los datos iniciales del sistema:");

            // Ask for initial configuration 
            String csvName = io.readLine("Ingrese el nombre para el archivo de reportes CSV (ej: 'ganancias'):");
            
            // Loop to ensure valid numeric input 
            while(true) {
                try {
                    int motoPerSpot = io.readInt("Ingrese cantidad de motos por espacio (ej: 5):");
                    int motoCost = io.readInt("Ingrese costo por hora para Motos:");
                    int carCost = io.readInt("Ingrese costo por hora para Carros:");
                    
                    // Create the ParkingLot instance 
                    parkingLot = new ParkingLot(motoPerSpot, motoCost, carCost, csvName);
                    
                    // Save immediately to create the file 
                    DataManager.saveParkingLot(parkingLot);
                    io.writeLine("Sistema configurado y guardado exitosamente.");
                    break;
                } catch (IllegalArgumentException ex) {
                    // Handle invalid arguments from Domain 
                    io.writeLine("Error en los datos: " + ex.getMessage());
                    io.writeLine("Intente nuevamente con valores positivos.");
                } catch (IOException ioEx) {
                    io.writeLine("Error crítico guardando el archivo inicial: " + ioEx.getMessage());
                    break; 
                }
            }
        }

        // Start the Menu only if we have a valid object 
        if (parkingLot != null) {
            ParkingMenu menu = new ParkingMenu(io, parkingLot);
            menu.showMainMenu();
        } else {
            io.writeLine("Error crítico: No se pudo iniciar el sistema.");
        }
    }
}