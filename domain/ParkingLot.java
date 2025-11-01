import java.util.ArrayList;
public class ParkingLot {
    private ArrayList<ParkingSpot> spotList = new ArrayList<ParkingSpot>();
    private int motorcycleCostPerHour;
    private int motorcyclePerSpotAmount;
    private int carCostPerHour;
    
    public ArrayList<ParkingSpot> getAvailableSpots(float spaceNeeded){
        ArrayList<ParkingSpot> availableSpotsList = new ArrayList<ParkingSpot>();
        for (ParkingSpot spot : spotList){
            if (spot.getRemainingSpace() >= spaceNeeded){
                availableSpotsList.add(spot);
            }
        }
        return availableSpotsList;
    }
    public int getAvailableSpotsAmount(){
        int availableSpotsAmount = 0;
        for (ParkingSpot spot : spotList){
            if (spot.getRemainingSpace()<1){
                availableSpotsAmount++;
            }
        }
        return availableSpotsAmount;
    }
    public int getOccupiedSpotsAmount(){
        int occupiedSpotsAmount = 0;
        for (ParkingSpot spot : spotList){
            if (spot.getRemainingSpace()==1){
                occupiedSpotsAmount++;
            }
        }
        return occupiedSpotsAmount;
    }
    public void setCarCostPerHour(int cost){
        this.carCostPerHour = cost;
    }
    public int getCarCostPerHour(){
        return carCostPerHour;
    }
    public void setMotorcycleCostPerHour(int cost){
        this.motorcycleCostPerHour = cost;
    }
    public int getMotorcycleCostPerHour(){
        return motorcycleCostPerHour;
    }
    public void setMotorcyclePerSpotAmount(int amount){
        this.motorcyclePerSpotAmount = amount;
    }
    public int getMotorcyclePerSpotAmount(){
        return motorcyclePerSpotAmount;
    }
}
