import java.util.ArrayList;
public class ParkingSpot {
    private ArrayList<Vehicle> parkedVehicles = new ArrayList<Vehicle>();
    private String spotName;
    public ParkingSpot(String spotName){
        this.spotName = spotName;
    }
    public float getRemainingSpace(){
        int totalSpace = 1;
        for (Vehicle vehicle : parkedVehicles){
            totalSpace -= vehicle.getSpace();
        }
        return totalSpace;
    }
    public void addVehicle(Vehicle vehicle){
        if (getRemainingSpace()+vehicle.getSpace() <= 1){
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
