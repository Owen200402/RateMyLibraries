package persistance;

import org.json.JSONObject;

// Class sourced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents an interface that returns a json object for regular classes
public interface WriteEnable {
    JSONObject toJson();
}
