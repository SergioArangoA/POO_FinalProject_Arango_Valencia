package data;

import domain.Vehicle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVManagement {
    private static final String CSV_FILE = "tickets.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Método para guardar un ticket de un vehículo
    public static void saveTicket(Vehicle vehicle) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Si el archivo está vacío, escribir encabezado
            if (new java.io.File(CSV_FILE).length() == 0) {
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
    
    public static int getTotalEarnings() {
        int totalEarnings = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
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
            e.printStackTrace();
        }

        return totalEarnings;
    }


    public static void main(String[] args) {
        int ganancias = getTotalEarnings();
        System.out.println("Ganancias totales: $" + ganancias);
    }
}