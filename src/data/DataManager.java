package data;
import java.io.*;
import domain.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DataManager{
     private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static void saveParkingLot(ParkingLot parking) throws IOException {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("ParkingData.data"))) {
            save.writeObject(parking);
        } catch (IOException e) {
            throw new IOException("There was an error during the saving process, couldn't save properly the changes made");
        }
    }

    public static ParkingLot loadParkingLot() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("ParkingData.data"))) {
            return (ParkingLot) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Couldn't find the parkinglot file");
        }
    }
    // Método para guardar un ticket de un vehículo
    public static void saveTicket(Vehicle vehicle, String csvFileName) {
        csvFileName += ".csv";
        LocalDateTime exitTime = LocalDateTime.now(); // Momento de salida
        int totalPrice = vehicle.getTicket();
        int hoursParked = vehicle.getHoursParked(); // Horas totales de parqueo

        // Formatear fecha y hora
        String arrival = vehicle.getArrivalTime().format(formatter);
        String departure = exitTime.format(formatter);

        // Crear línea CSV
        String csvLine = String.join(",",
                vehicle.getVehicleType(),
                vehicle.getLicensePlate(),
                String.valueOf(totalPrice),
                arrival,
                departure,
                String.valueOf(hoursParked)
        );

        // Escribir en archivo CSV (agrega si ya existe)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName, true))) {
            // Si el archivo está vacío, escribir encabezado
            if (new java.io.File(csvFileName).length() == 0) {
                bw.write("VehicleType,LicensePlate,TotalPrice,ArrivalTime,DepartureTime,HoursParked");
                bw.newLine();
            }
            bw.write(csvLine);
            bw.newLine();
            System.out.println("Ticket guardado correctamente!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int getTotalEarnings(String csvFileName) throws FileNotFoundException {
        int totalEarnings = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    // Saltar encabezado
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // TotalPrice está en la columna 3 (índice 2)
                    totalEarnings += Integer.parseInt(parts[2]);
                }
            }

        } catch (IOException e) {
            throw new FileNotFoundException("Couldn't find the csv file that you're trying to read");
        }

        return totalEarnings;
    }


}