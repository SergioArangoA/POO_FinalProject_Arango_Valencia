package domain;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.Serializable;
/**
 * A vehicle instance represents a vehicle that can be parked inside a parking spot.
 *
 * <p><b>Attributes:</b></p>
 * <ul>
 *     <li>{@code LocalDateTime ARRIVAL_TIME}: the date and time when the vehicle entered the parking lot.</li>
 *     <li>{@code String LICENSE_PLATE}: the identifier of the vehicle.</li>
 *     <li>{@code String VEHICLE_TYPE}: the type of the vehicle ("Car" or "Motorcycle").</li>
 *     <li>{@code float space}: amount of parking units the vehicle occupies.</li>
 *     <li>{@code int TICKET_PRICE}: the fee associated with the vehicle based on its type.</li>
 * </ul>
 *  * <p><b>Methods:</b></p>
 * <ul>
 *     <li>getTicketPrice</li>
 *     <li>getTicket</li>
 *     <li>getSpace</li>
 *     <li>setSpace</li>
 *     <li>getLicensePlate</li>
 *     <li>getArrivalTime</li>
 *     <li>getMinutesParked</li>
 *     <li>getHoursParked</li>
 *     <li>getVehicleType</li>
 * </ul>
 */
public class Vehicle implements Serializable{
    private  final LocalDateTime ARRIVAL_TIME;
    private final String LICENSE_PLATE;
    private final String VEHICLE_TYPE;
    private float space;
    private final int TICKET_PRICE;
    /**
     * The constructor of the Vehicle class.
     * @param LicensePlate A string with the vehicle's identifier/license plate.
     * @param VehicleType A char, either m or c, representing if it's a car or a motorcycle.
     * @param motorcyclePerSpotAmount An int, that contains the amount of motorcycles that can fit a spot.
     * @param motorcycleCostPerHour An int, that contains the motorcycle fee each hour.
     * @param carCostPerHour An int, that contains the car fee each hour
     * 
     * @throws IllegalArgumentException If the vehicle type char is different from 'c' and 'm'
     *
     */
    public Vehicle(String licensePlate, char vehicleType, int motorcyclePerSpotAmount,int motorcycleCostPerHour, int carCostPerHour){
        this.ARRIVAL_TIME = LocalDateTime.now();
        this.LICENSE_PLATE = licensePlate;
        if (vehicleType == 'm' || vehicleType == 'M'){
            this.VEHICLE_TYPE = "Motorcycle";
            this.space = 1f/motorcyclePerSpotAmount;
            this.TICKET_PRICE = motorcycleCostPerHour;
        }
        else if(vehicleType == 'c' || vehicleType == 'C'){
            this.VEHICLE_TYPE = "Car";
            this.space = (float) 1;
            this.TICKET_PRICE = carCostPerHour;
        }
        else{
            throw new IllegalArgumentException("Invalid vehicle type, it must be either 'c' or 'm'");
        }
    }
    /**
     * @return The ticket fee per hour
     */
    public int getTicketPrice(){
        return TICKET_PRICE;
    }
    /**
     * 
     * @return An int with the total ticket price
     */
    public int getTicket(){
        int ParkedTime = (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toHours());
        return TICKET_PRICE * (ParkedTime+1);
    }
    /**
     * @return The space that the vehicle occupies inside the parking spot
     */
    public float getSpace(){
        return space;
    }
    /**
     * A method used to change the amount of motorcycles that can be placed inside a spot, this method changes the total space that a motorcycle occupies
     * @param newSpace An int with the new amount of motorcycles that fit in one spot
     */
    public void setSpace(int newSpace){
        this.space = (float) 1f/newSpace;
    }
    /**
     * 
     * @return The vehicle's license plate
     */
    public String getLicensePlate(){
        return LICENSE_PLATE;
    }
    /**
     * @return The vehicle's arrival time
     */
    public LocalDateTime getArrivalTime(){
        return ARRIVAL_TIME;
    }
    /**
     * @return An int with the amount of minutes that the vehicle has been parked inside the spot
     */
    public int getMinutesParked(){
        return (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toMinutes());
    }
    /**
     * @return An  int with the amount of hours that the vehicle has been parked inside the spot
     */
    public int getHoursParked(){
        return (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toHours());
    }
    /**
     * @return A string with the vehicle type, either "Motorcycle" or "Car"
     */
    public String getVehicleType(){
        return VEHICLE_TYPE;
    }

}
