package domain;
import java.util.ArrayList;
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
    }
    public int removeVehicle(Vehicle vehicle){
        parkedVehicles.remove(vehicle);
        return vehicle.getTicket();
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

    
}
