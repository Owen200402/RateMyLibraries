package persistance;

import model.Library;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public model.System read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private model.System parseLibrary(JSONObject jsonObject) {

    }

    // MODIFIES: wr
    // EFFECTS: parses libraries from JSON object and adds them to workroom
    private void addLibraries(System sys, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addThingy(sys, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses comments from JSON object and adds it to library
    private void addThingy(System sys, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Library lib = Library.valueOf(jsonObject.getString("category"));
        Thingy thingy = new Thingy(name, lib);
        sys.addThingy(thingy);
    }
}
