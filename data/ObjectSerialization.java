Package data;
import java.io.*;
import domain.*;
public class ObjectSerialization{

    public static void saveParkingLot(ParkingLot parking, String filename) {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(filename))) {
            save.writeObject(parking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ParkingLot loadParkingLot(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ParkingLot) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

