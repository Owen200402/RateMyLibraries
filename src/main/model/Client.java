package model;

import org.json.JSONObject;
import persistance.WriteEnable;

public class Client implements WriteEnable {
    private String userName;
    private String password;

    // EFFECTS: constructs a client object storing both its userName and password
    public Client(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);

        return jsonObject;
    }
}
