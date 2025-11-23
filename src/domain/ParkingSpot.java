package domain;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.Serializable;
public class ParkingSpot implements Serializable {
    private ArrayList<Vehicle> parkedVehicles = new ArrayList<Vehicle>();
    private String spotName;
    public ParkingSpot(String spotName){
        this.spotName = spotName;
    }
    public float getRemainingSpace(){
        float totalSpace = 1;
        for (Vehicle vehicle : parkedVehicles){
            totalSpace -= vehicle.getSpace();
        }
        return totalSpace;
    }
    public void addVehicle(Vehicle vehicle){
        if (getRemainingSpace()-vehicle.getSpace() >= 0){
            parkedVehicles.add(vehicle);
        }
        else{
            throw new IllegalStateException("Not enough space left in the parkingSpot");
        }
    }
    public Vehicle removeVehicle(Vehicle vehicle){
        parkedVehicles.remove(vehicle);
        return vehicle;
    }
    public ArrayList<Vehicle> getVehicleList(){
        return parkedVehicles;
    }
    public void setSpotName(String name){
        this.spotName = name;
    }
    public String getSpotName(){
        return spotName;
    }
    public Vehicle searchVehicle(String licensePlate){
        for (Vehicle vehicle: parkedVehicles){
            if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)){
                return vehicle;
            }
        }
        throw new NoSuchElementException("Couldn't locate a car with that license plate in that spot");
    }
}
