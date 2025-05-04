import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutputWriter {
    public void write(String filename, Map<String, Object> data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
            System.out.println("Wrote output to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }
}