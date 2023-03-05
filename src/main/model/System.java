package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.WriteEnable;

import java.util.ArrayList;
import java.util.List;

public class System implements WriteEnable {
    private List<Library> libraries;

    public System() {
        libraries = new ArrayList<>();
    }

    // EFFECTS: adds libraries into the system
    public void add(Library lib) {
        libraries.add(lib);
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    // EFFECTS: returns the parental level of JSONObject that is titled as "Rate My Library"
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Rate My Library", libraryInfoToJson());
        return json;
    }

    // EFFECTS: returns library in this system as a JSON array
    private JSONArray libraryInfoToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Library l : libraries) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }
}
