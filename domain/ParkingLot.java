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
    public ArrayList<String> getAvailableSpotsNames(){
        ArrayList<String> nameList = new ArrayList<String>();
        for (ParkingSpot spot: spotList){
            if (spot.getRemainingSpace()<1){
                nameList.add(spot.getSpotName());
            }
        }
        return nameList;
    }
    public ParkingSpot getRecommendedSpot(char vehicleType){
        if (vehicleType == 'm' || vehicleType == 'M'){
            for (ParkingSpot spot: spotList){
                if (spot.getRemainingSpace() > 0 && spot.getRemainingSpace()>= 1/motorcyclePerSpotAmount){
                    return spot;
                }
            }
        }
        else{
            for(ParkingSpot spot: spotList){
                if (spot.getRemainingSpace()==1){
                    return spot;
                }
            }
        }
        return null;
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
    public ArrayList<ParkingSpot> getSpotList(){
        return spotList;
    }
    public Vehicle searchVehicle(String licensePlate){
        for (ParkingSpot spot : spotList){
            for(Vehicle vehicle : spot.getVehicleList()){
                if (licensePlate.equalsIgnoreCase(vehicle.getLicensePlate())){
                    return vehicle;
                }
            }
        }
        return null;
    }

    public void removeSpot(String spotName){
        for (ParkingSpot spot: spotList){
            if (spot.getSpotName().equalsIgnoreCase(spotName)){
                spotList.remove(spot);
            }
        }
    }

    public void createSpot(String spotName){
        spotList.add(new ParkingSpot(spotName));
    }

    public ParkingSpot searchSpot(String spotName){
        for (ParkingSpot spot:spotList){
            if (spot.getSpotName().equalsIgnoreCase(spotName)){
                return spot;
            }
        }
        return null;
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
