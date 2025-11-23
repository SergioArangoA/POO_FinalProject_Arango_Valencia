package domain;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.Serializable;
public class Vehicle implements Serializable{
    private  final LocalDateTime ARRIVAL_TIME;
    private final String LICENSE_PLATE;
    private final String VEHICLE_TYPE;
    private float space;
    private final int TICKET_PRICE;
    public Vehicle(String LicensePlate, char VehicleType, int motorcyclePerSpotAmount,int motorcycleCostPerHour, int carCostPerHour){
        this.ARRIVAL_TIME = LocalDateTime.now();
        this.LICENSE_PLATE = LicensePlate;
        if (VehicleType == 'm'){
            this.VEHICLE_TYPE = "Motorcycle";
            this.space = 1f/motorcyclePerSpotAmount;
            this.TICKET_PRICE = motorcycleCostPerHour;
        }
        else if(VehicleType == 'c'){
            this.VEHICLE_TYPE = "Car";
            this.space = (float) 1;
            this.TICKET_PRICE = carCostPerHour;
        }
        else{
            throw new IllegalArgumentException("Invalid vehicle type, it must be either 'c' or 'm");
        }
    }
    public int getTicketPrice(){
        return TICKET_PRICE;
    }
    public int getTicket(){
        int ParkedTime = (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toHours());
        return TICKET_PRICE * (ParkedTime+1);
    }
    public float getSpace(){
        return space;
    }
    public void setSpace(int newSpace){
        this.space = (float) 1f/newSpace;
    }
    public String getLicensePlate(){
        return LICENSE_PLATE;
    }
    public LocalDateTime getArrivalTime(){
        return ARRIVAL_TIME;
    }
    public int getMinutesParked(){
        return (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toMinutes());
    }
    public int getHoursParked(){
        return (int)(Duration.between(ARRIVAL_TIME, LocalDateTime.now()).toHours());
    }
    public String getVehicleType(){
        return VEHICLE_TYPE;
    }

}
