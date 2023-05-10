package persistance;

import model.Clients;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LoginWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String to;

    // EFFECTS: constructs writer to write to "to" file
    public LoginWriter(String to) {
        this.to = to;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if the "to" file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(to));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of library to file
    public void write(Clients client) {
        JSONObject json = client.toJson();
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
