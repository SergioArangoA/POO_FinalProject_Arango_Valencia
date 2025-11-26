package data;
import java.io.*;
import domain.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An instance of the DataManager allows to serialize (save) the whole parkinglot and save the ticket history into a CSV file
 * </ul>
 * <p><b>Methods:</b></p>
 * <ul>
 *     <li>saveParkingLot</li>
 *     <li>loadParkingLot</li>
 *     <li>saveTicket</li>
 *     <li>getTotalEarnings</li>
 * </ul>
 */
public class DataManager{
    private static final String DIRECTORY = "src/data/"; //file path
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //The format in which the dates and times will be saved
    /**
     * @param parking The parkinglot instance that will be saved
     * @throws IOException if the file couldn't be saved
     */
    public static void saveParkingLot(ParkingLot parking) throws IOException {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(DIRECTORY + "ParkingData.data"))) {
            save.writeObject(parking);
        } catch (IOException e) {
            throw new IOException("There was an error during the saving process, couldn't save properly the changes made");
        }
    }
    /**
     * Loads the saved parkinglot instance
     * @return the parkinglot instance
     * @throws IOException if the file that contains the parkinglot instance wasn't located or if the class wasn't found
     */
    public static ParkingLot loadParkingLot() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DIRECTORY +"ParkingData.data"))) {
            return (ParkingLot) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Couldn't find the parkinglot file");
        }
    }
    /**
     * Saves a ticket a CSV file
     * @param vehicle the vehicle instance from which the ticket will be saved
     * @param csvFileName the name of the file in which the ticket will be saved
     */
    public static void saveTicket(Vehicle vehicle, String csvFileName) throws IOException {
        csvFileName += ".csv";
        LocalDateTime exitTime = LocalDateTime.now(); //The time in which the vehicle is leaving
        int totalPrice = vehicle.getTicket();
        int hoursParked = vehicle.getHoursParked(); //The amount of hours parked
        int fee = vehicle.getTicketPrice();

        //formats the time of arrival and departure
        String arrival = vehicle.getArrivalTime().format(formatter);
        String departure = exitTime.format(formatter);

        // creates a line of the CSV
        String csvLine = String.join(",",
                vehicle.getVehicleType(),
                vehicle.getLicensePlate(),
                String.valueOf(totalPrice),
                String.valueOf(hoursParked),
                String.valueOf(fee),
                arrival,
                departure
        );

        // writes in the CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORY + csvFileName, true))) {
            //If the file is empty, creates the header
            if (new java.io.File(DIRECTORY + csvFileName).length() == 0) {
                bw.write("Vehicle type,License plate,Total price,Hours parked,Fee,Arrival time,Departure time");
                bw.newLine();
            }
            bw.write(csvLine);//writes the line that contains the ticket of the vehicle
            bw.newLine();//creates a new line for the next line that will be written
        } catch (IOException e) {
            throw new IOException("The file couldn't be saved");
        }
    }
    /**
     * Reads a CSV file earnings column and returns it
     * @param csvFileName The name of the file you're trying to read
     * @return the total earnings
     * @throws FileNotFoundException if the file you're looking for doesn't exist
     */
    public static int getTotalEarnings(String csvFileName) throws FileNotFoundException {
        int totalEarnings = 0;
        csvFileName += ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(DIRECTORY + csvFileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    //doesn't count the file header
                    firstLine = false;//It won't ever get back in this if
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // The earnings are in the third column (index 2)
                    totalEarnings += Integer.parseInt(parts[2]);
                }
            }

        } catch (IOException e) {
            throw new FileNotFoundException("Couldn't find the csv file that you're trying to read");
        }
        return totalEarnings;
    }


}