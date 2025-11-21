package ui;

// Import the Back-End "Facade" (our stub)
import domain.ParkingLot;

/**
 - Main class for the User Interface (Front-End).
 - This class organizated the menu and user interactions.
 */
public class ParkingMenu {

    // Atributos dependecy:
    // 1. Dependency on the I/O Abstraction 
    private final IOUser io;
    
    // 2. Dependency on the Back-End Facade 
    private final ParkingLot parkingLot;

    //Constructor:

    /**
     - Creates the menu by the dependencies.
     - @param io The I/O implementation to use (Console or swing)
     - @param parkingLot The back-end logic controller
     */
    public ParkingMenu(IOUser io, ParkingLot parkingLot) {
        this.io = io;
        this.parkingLot = parkingLot;
    }

    //Main Method=The Application Loop

    /**
     - Displays the main menu and handles user navigation.
     - This is the primary loop of the application.
     */
    public void showMainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            io.writeLine("--- SMART PARKING SYSTEM ---");
            io.writeLine("1. Add Vehicle");
            io.writeLine("2. Remove Vehicle");
            io.writeLine("3. Search Vehicle");
            io.writeLine("4. Show Occupation and Available Spots");
            io.writeLine("5. Configuration ");
            io.writeLine("6. Import Data ");
            io.writeLine("7. Exit");
            
            // Use the robust 'readInt' method
            int option = io.readInt("Select an option (1-7):");

            // Route to the correct helper method
            switch (option) {
                case 1:
                    handleAddVehicle();
                    break;
                case 2:
                    handleRemoveVehicle();
                    break;
                case 3:
                    handleSearchVehicle();
                    break;
                case 4:
                    handleShowOccupation();
                    break;
                case 5:
                    handleConfiguration();
                    break;
                case 6:
                    handleImportData();
                    break;
                case 7:
                    isRunning = false; // This will exit the while loop
                    break;
                default:
                    io.writeLine("Error: Invalid option. Please try again.");
            }
            io.writeLine(""); // Add a blank line for readability
        }
        io.writeLine("Thank you for using the system. Goodbye!");
    }

    // Private Helper Methods = Logic for each menu option

    /**
     - Handles the UI flow for adding a vehicle.
     */
    private void handleAddVehicle() {
        io.writeLine("--- 1. Add Vehicle ---");
        // 1. Get data from user (UI)
        String plate = io.readLine("Enter vehicle license plate:");
        String type = io.readLine("Enter vehicle type:");
        
        // 2. Send data to Back-End (ParkingLot)
        String result = parkingLot.addVehicle(plate, type);
        
        // 3. Show result to user (UI)
        io.writeLine(result);
    }

    /**
     - Handles the UI flow for removing a vehicle.
     */
    private void handleRemoveVehicle() {
        io.writeLine("--- 2. Remove Vehicle ---");
        String plate = io.readLine("Enter license plate of vehicle to remove:");
        String result = parkingLot.removeVehicle(plate);
        io.writeLine(result);
    }

    /**
     - Handles the UI flow for searching for a vehicle.
     */
    private void handleSearchVehicle() {
        io.writeLine("--- 3. Search Vehicle ---");
        String plate = io.readLine("Enter license plate to search:");
        String result = parkingLot.searchVehicle(plate);
        io.writeLine(result);
    }

    /**
     - Handles the UI flow for showing parking lot occupation.
     */
    private void handleShowOccupation() {
        io.writeLine("--- 4. Occupation Report ---");
        
        // 1. Get all data from the Back-End
        int available = parkingLot.getAvailableSpotsAmount();
        int occupied = parkingLot.getOccupiedSpotsAmount();
        String availableNames = parkingLot.getAvailableSpotsNames();
        
        // 2. Display formatted data (UI)
        io.writeLine("Occupation Summary:");
        io.writeLine("- Available Spots: " + available);
        io.writeLine("- Occupied Spots: "f + occupied);
        io.writeLine("\nList of Available Spots:");
        io.writeLine(availableNames);
    }

    /**
     - Handles the UI flow for the configuration sub-menu.
     */
    private void handleConfiguration() {
        io.writeLine("--- 5. Configuration ---");
        io.writeLine("1. Modify Car Rate");
        io.writeLine("2. Modify Motorcycle Rate");
        io.writeLine("3. Back to Main Menu");
        
        int option = io.readInt("Select option:");

        switch (option) {
            case 1:
                double carRate = io.readDouble("Enter new hourly rate for Cars:");
                parkingLot.setCarCostPerHour(carRate);
                io.writeLine("Car rate updated.");
                break;
            case 2:
                double motoRate = io.readDouble("Enter new hourly rate for Motorcycles:");
                parkingLot.setMotorcycleCostPerHour(motoRate);
                io.writeLine("Motorcycle rate updated.");
                break;
            case 3:
                io.writeLine("Returning to main menu...");
                break;
            default:
                io.writeLine("Invalid configuration option.");
        }
    }

    /**
     - Handles the UI flow for importing data.
     */
    private void handleImportData() {
        io.writeLine("--- 6. Import Data from CSV ---");
        String path = io.readLine("Enter the full file path of the .csv file:");
        String result = parkingLot.importData(path);
        io.writeLine(result);
    }
}