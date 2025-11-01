import java.time.Duration;
import java.time.LocalDateTime;
public class Vehicle{
    private  final LocalDateTime ARRIVAL_TIME;
    private final String LICENSE_PLATE;
    private final String VEHICLE_TYPE;
    private final float SPACE;
    private final int TICKET_PRICE;
    public Vehicle(String LicensePlate, char VehicleType){
        this.ARRIVAL_TIME = LocalDateTime.now();
        this.LICENSE_PLATE = LicensePlate;
        if (VehicleType == 'm'){
            this.VEHICLE_TYPE = "Motorcycle";
            this.SPACE = 1/3; //Change to customizable later
            this.TICKET_PRICE = 1; //Change to customizable later
        }
        else if(VehicleType == 'c'){
            this.VEHICLE_TYPE = "Car";
            this.SPACE = 1;
            this.TICKET_PRICE = 2; //Change to customizable later
        }
        else{ //This one shouldn't be ever activated. Must validate that it's a valid vehicle type beforehand
            this.VEHICLE_TYPE = "Unknown"; 
            this.SPACE = 1;
            this.TICKET_PRICE = 2;
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
        return SPACE;
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
