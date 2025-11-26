package domain;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 * Represents a parking lot that contains multiple parking spots.
 * Each parking spot can hold one car or multiple motorcycles.
 * The parking lot also stores pricing information for vehicles
 * and configuration parameters.
 *
 * <p><b>Attributes:</b></p>
 * <ul>
 *     <li>{@code ArrayList<ParkingSpot> spotList}: list of all parking spots in the lot.</li>
 *     <li>{@code int motorcycleCostPerHour}: hourly fee for motorcycles.</li>
 *     <li>{@code int motorcyclePerSpotAmount}: number of motorcycles that can fit in one spot.</li>
 *     <li>{@code int carCostPerHour}: hourly fee for cars.</li>
 *     <li>{@code String csvFilename}: name of the CSV file used for saving/loading parking lot data.</li>
 * </ul>
 *  * <p><b>Methods:</b></p>
 * <ul>
 *     <li>getAvailableSpots</li>
 *     <li>getAvailableSpotsAmount</li>
 *     <li>getAvailableSpotsNames</li>
 *     <li>getRecommendedSpot</li>
 *     <li>getOccupiedSpotsAmount</li>
 *     <li>searchVehicleSpot</li>
 *     <li>removeSpot/li>
 *     <li>createSpot</li>
 *     <li>setCarCostPerHour</li>
 *     <li>getCarCostPerHour</li>
 *     <li>setMotorcycleCostPerHour</li>
 *     <li>getMotorcycleCostPerHour</li>
 *     <li>setMotorcyclePerSpotAmount</li>
 *     <li>getMotorcyclePerSpotAmount</li>
 *     <li>getMinutesParked</li>
 *     <li>setCSVFileName</li>
 *     <li>getCSVFileName</li>
 *      
 * </ul>
 */
public class ParkingLot implements Serializable {
    private ArrayList<ParkingSpot> spotList;
    private int motorcycleCostPerHour;
    private int motorcyclePerSpotAmount;
    private int carCostPerHour;
    private String csvFilename;
    /**
     * The ParkingLot instance constructor
     * @param motorcyclePerSpotAmount contains an int with the amount of motorcycles allowed per spot
     * @param motorcycleCostPerHour contains an int with the motorcycle fee per hour
     * @param carCostPerHour contains an int with the car fee per hour
     * @param csvFilename contains the String of the name of the CSV file where the payment's history will be saved
     * 
     * @throws IllegalArgumentException if the motorcyclePerSpot, motorcycleCostPerHour or the carCostPerHour amounts are negative
     */
    public ParkingLot(int motorcyclePerSpotAmount, int motorcycleCostPerHour, int carCostPerHour,String csvFilename){
        if (motorcyclePerSpotAmount >= 0 && motorcycleCostPerHour >= 0 && carCostPerHour >= 0){//Checks if the amounts are positive
            this.motorcyclePerSpotAmount = motorcyclePerSpotAmount;
            this.motorcycleCostPerHour = motorcycleCostPerHour;
            this.carCostPerHour = carCostPerHour;
        }
        else{
            throw new IllegalArgumentException("The motorcycle per spot amount, car and motorcycle cost per hour must be positive or equal to zero");
        }
        if (!csvFilename.matches("[\\w\\- ]+")) {//Checks if the filename is valid, no special characters allowed
            throw new IllegalArgumentException("Invalid filename with special characters");
        }
        this.csvFilename = csvFilename;
        this.spotList = new ArrayList<ParkingSpot>();
    }
    /**
     * @param vehicle The vehicle that's looking for a spot
     * @return A list of spots in which the vehicle can fit
     */
    public ArrayList<ParkingSpot> getAvailableSpots(Vehicle vehicle){
        float spaceNeeded = vehicle.getSpace(); //gets the space that the vehicle needs
        ArrayList<ParkingSpot> availableSpotsList = new ArrayList<ParkingSpot>();
        for (ParkingSpot spot : spotList){ //goes through every spot checking if they have enough space to fit the vehicle
            if (spot.getRemainingSpace() >= spaceNeeded){
                availableSpotsList.add(spot);//adds the spot to the list if the vehicle fits in it
            }
        }
        return availableSpotsList;
    }
    /**
     * @return The amount of unfilled spots
     */
    public int getAvailableSpotsAmount(){
        int availableSpotsAmount = 0;
        for (ParkingSpot spot : spotList){
            if (spot.getRemainingSpace()>0){//Checks if the spot is not filled
                availableSpotsAmount++;
            }
        }
        return availableSpotsAmount;
    }
    /**
     * @return An ArrayList of strings with the available spots's names
     */
    public ArrayList<String> getAvailableSpotsNames(){
        ArrayList<String> nameList = new ArrayList<String>();
        for (ParkingSpot spot: spotList){
            if (spot.getRemainingSpace()>0){
                nameList.add(spot.getSpotName());
            }
        }
        return nameList;
    }
    /**
     * @param vehicleType A char with the vehicle type
     * @return the first spot found that can fit the vehicle type
     * @throws NoSuchElementException if there isn't any spot left that can fit the vehicle type
     */
    public ParkingSpot getRecommendedSpot(char vehicleType){
        if (vehicleType == 'm' || vehicleType == 'M'){
            for (ParkingSpot spot: spotList){
                if (spot.getRemainingSpace() > 0 && spot.getRemainingSpace()>= 1/motorcyclePerSpotAmount){ //looks for a spot that can fit a motorcycle
                    return spot;
                }
            }
        }
        else{
            for(ParkingSpot spot: spotList){
                if (spot.getRemainingSpace()==1){ //looks for a spot that can fit a car
                    return spot;
                }
            }
        }
        throw new NoSuchElementException("Couldn't locate a spot with enough space for that vehicle type");
    }
    /**
     * @return an int with the amount of filled spots
     */
    public int getOccupiedSpotsAmount(){
        int occupiedSpotsAmount = 0;
        for (ParkingSpot spot : spotList){
            if (spot.getRemainingSpace()==0){
                occupiedSpotsAmount++;
            }
        }
        return occupiedSpotsAmount;
    }
    /**
     * @return A list with all the spots in the parkinglot
     */
    public ArrayList<ParkingSpot> getSpotList(){
        return spotList;
    }
    /**
     * Searchs for the spot in which a vehicle is located
     * @param licensePlate the license plate of the searched vehicle
     * @return the spot in which the car is located
     * @throws NoSuchElementException if the car is not found
    */
    public ParkingSpot searchVehicleSpot(String licensePlate){
        for (ParkingSpot spot : spotList){
            for(Vehicle vehicle : spot.getVehicleList()){
                if (licensePlate.equalsIgnoreCase(vehicle.getLicensePlate())){
                    return spot;
                }
            }
        }
        throw new NoSuchElementException("Couldn't locate a car with that license plate");
    }
    /**
     * Removes a spot from the parkinglot
     * @param spotName a string with the name of the spot
     */
    public void removeSpot(String spotName){
        for (ParkingSpot spot: spotList){
            if (spot.getSpotName().equalsIgnoreCase(spotName)){
                spotList.remove(spot);
            }
        }
    }
    /**
     * Creates a spot in the parkinglot
     * @param spotName a string with the name of the spot
     */
    public void createSpot(String spotName){
        spotList.add(new ParkingSpot(spotName));
    }
    /**
     * Searchs for a spot and returns it
     * @param spotName the name of the spot
     * @return the spot instance
     * @throws NoSuchElementException if the spot isn't found
     */
    public ParkingSpot searchSpot(String spotName){
        for (ParkingSpot spot:spotList){
            if (spot.getSpotName().equalsIgnoreCase(spotName)){
                return spot;
            }
        }
        throw new NoSuchElementException("Couldn't locate a parking spot with that name");
    }
    /**
     * @param cost the new cost
     * @throws InvalidArgumentException if the new cost is negative
     */
    public void setCarCostPerHour(int cost){
        if (cost >= 0){
            this.carCostPerHour = cost;    
        }
        else{
            throw new IllegalArgumentException("The new cost can't be negative");
        }
    }
    /**
     * @return the car fee per hour
     */
    public int getCarCostPerHour(){
        return this.carCostPerHour;
    }
    /**
     * @param cost the new cost
     * @throws IllegalArgumentException if the new cost is negative
     */
    public void setMotorcycleCostPerHour(int cost){
        if (cost>=0){
        this.motorcycleCostPerHour = cost;   
        }
        else{
            throw new IllegalArgumentException("The new cost can't be negative");
        }
    }
    /**
     * @return the motorcycle fee per hour
     */
    public int getMotorcycleCostPerHour(){
        return this.motorcycleCostPerHour;
    }
    /**
     * @param amount the new amount of motorcycles allowed per spot
     * @throws IllegalArgumentException if the amount isn't positive
     */
    public void setMotorcyclePerSpotAmount(int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("The amount must be positive");
        }
        this.motorcyclePerSpotAmount = amount;
        for (ParkingSpot spot: spotList){
            for(Vehicle vehicle: spot.getVehicleList()){//modifies the taken space atribute of each motorcycle
                if (vehicle.getVehicleType().equalsIgnoreCase("Motorcycle")){
                    vehicle.setSpace(amount);
                }
            }
        }
    }
    /**
     * @return the amount of motorcycles that fit in a spot
     */
    public int getMotorcyclePerSpotAmount(){
        return this.motorcyclePerSpotAmount;
    }
    /**
     * @param name the new csv file name
     * @throws IllegalArgumentException if the filename contains special characters
     */
    public void setCSVFileName(String name){
        if (!csvFilename.matches("[\\w\\- ]+")) {//Checks if the filename is valid, no special characters allowed
            throw new IllegalArgumentException("Invalid filename with special characters");
        }
        this.csvFilename = name;
    }
    /**
     * @return the csv file name
     */
    public String getCSVFileName(){
        return csvFilename;
    }
}
