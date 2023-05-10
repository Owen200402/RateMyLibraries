package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.WriteEnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clients implements WriteEnable, Iterable<Client> {
    private static List<Client> clients;

    public Clients() {
        clients = new ArrayList<>();
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Clients", clientInfoToJson());
        return json;
    }

    public JSONArray clientInfoToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Client c : clients) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    @Override
    public Iterator<Client> iterator() {
        return clients.iterator();
    }
}
