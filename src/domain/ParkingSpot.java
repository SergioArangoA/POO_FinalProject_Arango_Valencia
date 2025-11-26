package domain;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.Serializable;
/**
 * A ParkingSpot instance represents one spot in the parking lot, where one car
 * or multiple motorcycles can park, depending on the parking lot configuration.
 *
 * <p><b>Attributes:</b></p>
 * <ul>
 *     <li>{@code ArrayList<Vehicle>}; parkedVehicles: list of vehicles parked inside.</li>
 *     <li>{@code String spotName}: identifier of the spot inside the parking lot.</li>
 * </ul>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *     <li>getRemainingSpace</li>
 *     <li>addVehicle</li>
 *     <li>removeVehicle</li>
 *     <li>getVehicleList</li>
 *     <li>setSpotName</li>
 *     <li>getSpotName</li>
 *     <li>searchVehicle</li>
 * </ul>
 */
public class ParkingSpot implements Serializable {
    /**
    * The list of vehicles parked inside the spot.
    */
    private ArrayList<Vehicle> parkedVehicles = new ArrayList<Vehicle>();
    /**
    * Identifier of the parking spot inside the parking lot.
    */
    private String spotName;
    public ParkingSpot(String spotName){
        this.spotName = spotName;
    }
    /**
     * @return The remaining space in the spot
     */
    public float getRemainingSpace(){
        float totalSpace = 1;
        for (Vehicle vehicle : parkedVehicles){
            totalSpace -= vehicle.getSpace(); //For every vehicle inside the parkingspot, substracts to the total space the vehicle's occupied space
        }
        return totalSpace;
    }
    /**
     * Method used to add a vehicle to a parking <spot
     * @param vehicle the vehicle that's going to get added to the spot
     * @throws IllegalStateException if there's not enough space left in the parkingspot for the vehicle to get added.
     */
    public void addVehicle(Vehicle vehicle){
        if (getRemainingSpace()-vehicle.getSpace() >= 0){//First checks that the vehicle fits inside the spot, comparing the remaining space with the vehicle's space that it occupies.
            parkedVehicles.add(vehicle); //If there's enough space, the vehicle gets added
        }
        else{
            throw new IllegalStateException("Not enough space left in the parking spot");//If there isn't enough space, the vehicle doesn't get added and an exception is thrown
        }
    }
    /**
     * Method used to remove a vehicle from the parkingspot
     * @param vehicle the vehicle that's going to be removed of the spot
     * @return
     */
    public Vehicle removeVehicle(Vehicle vehicle){
        parkedVehicles.remove(vehicle);
        return vehicle;
    }
    /**
     * Method used to get the list of vehicles inside the parkingspot
     * @return the vehicle list
     */
    public ArrayList<Vehicle> getVehicleList(){
        return parkedVehicles;
    }
    /**
     * Method used to change the name of a spot
     * @param name the new name of the parkingspot
     */
    public void setSpotName(String name){
        this.spotName = name;
    }
    /**
     * Method used to get the parkingspot's name
     * @return the spot's name
     */
    public String getSpotName(){
        return spotName;
    }
    /**
     * Method used to search for a vehicle inside the parkingspot
     * @param licensePlate the license plate of the searched vehicle 
     * @return vehicle
     * @throws NoSuchElementException if the vehicle wasn't located in the spot
     */
    public Vehicle searchVehicle(String licensePlate){
        for (Vehicle vehicle: parkedVehicles){ //Looks for every vehicle inside the spot
            if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)){ //When the vehicle's plate matches the one searched, returns the vehicle
                return vehicle;
            }
        }
        throw new NoSuchElementException("Couldn't locate a car with that license plate in that spot"); //If the vehicle isn't located, throws an exception.
    }
}
