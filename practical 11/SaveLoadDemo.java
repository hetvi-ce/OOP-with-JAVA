import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoadDemo {
    public static void main(String[] args) throws Exception {
        String fileName = "train_passes_105.ser";

        TrainPass[] originalPasses = {
            new TrainPass("TP-1051", "Hetvi Sangani", "Anand -> Ahmedabad", 480.0, "8492"),
            new TrainPass("TP-1052", "Nidhi Desai", "Vadodara -> Surat", 620.0, "3170"),
            new TrainPass("TP-1053", "Rohan Mehta", "Nadiad -> Mumbai", 1450.0, "5904")
        };

        System.out.println("Saving TrainPass[] to '" + fileName + "':");
        for (TrainPass pass : originalPasses) {
            System.out.println("  Before Save -> " + pass);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(originalPasses);
        }

        TrainPass[] restoredPasses;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            restoredPasses = (TrainPass[]) in.readObject();
        }

        System.out.println("\nDeserialized TrainPass[] into fresh objects:");
        for (TrainPass pass : restoredPasses) {
            System.out.println("  After Load  -> " + pass);
        }
    }
}
