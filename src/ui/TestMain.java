package ui;

import domain.ParkingLot;


public class TestMain {

    public static void main(String[] args) {
        
        // Injection Dependency

        // Choose your UI implementation.
        IOUser io = new IOUserConsole();
        // IOUser io = new IOUserSwing();

        // Create the Back-End object.
        ParkingLot parkingLot = new ParkingLot();
        
        // Create the Menu and add the UI and Domain objects.
        ParkingMenu menu = new ParkingMenu(io, parkingLot);
        
        //  Start the main menu loop.
        menu.showMainMenu();
    }
}