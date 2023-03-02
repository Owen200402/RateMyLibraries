package model;

import org.json.JSONObject;
import persistance.WriteEnable;

import java.util.Date;

// Represents statistics of individual comments made by users

public class Comment implements WriteEnable {
    private String message;
    private double rating;
    private String password;
    private Date date;
    private int numberBeingAdded;

    // EFFECTS: write default messages into the comments, and set rating
    public Comment(String message, double rating, String password, Date date) {
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

    public Date getDate() {
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
