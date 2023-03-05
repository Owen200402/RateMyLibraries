package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.WriteEnable;

import java.text.DecimalFormat;
import java.util.List;

// Represents a library system with comments associated

public class Library implements WriteEnable {
    private Comments comments;
    private double overallRatingForCalculation;
    private String overallRatingDisplayed;
    private String name;
    private double number;

    // EFFECTS: construct and initialize a library object
    public Library(Comments comments, String name, double number) {
        this.comments = comments;
        this.name = name;
        this.number = number;
    }

    // MODIFIES: this
    // EFFECTS: calculate the average/overall rating of the library
    public void calculateOverallRating() {
        List<Comment> commentList = comments.getComments();
        double count = 0;

        for (Comment c : commentList) {
            count += c.getRating();
        }

        this.overallRatingForCalculation = count / (double) commentList.size();
    }

    public String getRating() {
        DecimalFormat df = new DecimalFormat("#.#");
        this.overallRatingDisplayed = df.format(overallRatingForCalculation);

        return overallRatingDisplayed;
    }

    public double getNumber() {
        return number;
    }

    public double getOverallRating() {
        return overallRatingForCalculation;
    }

    public String getOverallRatingDisplayed() {
        return overallRatingDisplayed;
    }

    public Comments getListOfComments() {
        return comments;
    }

    public String getName() {
        return name;
    }


    // EFFECTS: returns a JSONObject with name library and rating in it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("library", libraryInfoToJson());
        calculateOverallRating();
        json.put("rating", getRating());
        return json;
    }

    // EFFECTS: returns things in this library as a JSON array
    private JSONArray libraryInfoToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Comment c : comments.getComments()) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
