package persistance;

import model.Comment;
import model.Comments;
import model.Library;
import model.System;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Class sourced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Including methods: read(), readFile();
// and adapted methods: parseLibrary(), addSystem(), addLibraries(), addComments()

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

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses system from JSON object and returns it
    private model.System parseLibrary(JSONObject jsonObject) {
        System sys = new System();
        addSystem(sys, jsonObject);
        return sys;
    }

    // MODIFIES: sys
    // EFFECTS: parses libraries from JSON object and adds them to system
    private void addSystem(System sys, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Rate My Library");

        for (Object json : jsonArray) {
            JSONObject jsonComment = (JSONObject) json;
            Library obj = addLibraries(jsonComment);
            obj.calculateOverallRating();
            sys.add(obj);
        }
    }

    // EFFECTS: Parses and returns every library from jsonObject
    private Library addLibraries(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("library");
        String name = jsonObject.getString("name");
        String number = jsonObject.getString("rating");
        Comments comments = new Comments();

        for (Object json: jsonArray) {
            comments.add(addComments((JSONObject) json));
        }

        return new Library(comments, name, Double.parseDouble(number));
    }

    // EFFECTS: Parses and returns every comment from jsonObject
    private Comment addComments(JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        String password = jsonObject.getString("password");
        double rating = jsonObject.getDouble("rating");
        String message = jsonObject.getString("message");
        return new Comment(message, rating, password, date);
    }
}
