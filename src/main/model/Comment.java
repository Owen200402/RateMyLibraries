package model;

import org.json.JSONObject;
import persistance.WriteEnable;

// Represents statistics of individual comments made by users

public class Comment implements WriteEnable {
    private String message;
    private double rating;
    private String password;
    private String date;
    private int numberBeingAdded;

    // EFFECTS: write default messages into the comments, and set rating
    public Comment(String message, double rating, String password, String date) {
        this.message = message;
        this.rating = rating;
        this.password = password;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public double getRating() {
        return rating;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }

    public void setNumberBeingAdded(int num) {
        numberBeingAdded = num;
    }

    public int getNumberBeingAdded() {
        return numberBeingAdded;
    }

    @Override
    public String toString() {
        return "User " + numberBeingAdded + ". " + "Rating = " + rating + "\n"
                + " - Commented at " + date + " : " + message;
    }

    // EFFECTS: returns a JSONObject that contains message, rating, password and date in it
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        jsonObject.put("rating", rating);
        jsonObject.put("password", password);
        jsonObject.put("date", date);
        return jsonObject;
    }
}
