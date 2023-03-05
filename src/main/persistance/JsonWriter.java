package persistance;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Class sourced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Including methods open(), write(), close(), and saveToFile()

// Represents a writer that writes JSON representation of system to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String to;

    // EFFECTS: constructs writer to write to "to" file
    public JsonWriter(String to) {
        this.to = to;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if the "to" file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(to));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of library to file
    public void write(model.System system) {
        JSONObject json = system.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
