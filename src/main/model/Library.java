package model;

import java.text.DecimalFormat;
import java.util.List;

public class Library {
    private Comments comments;
    private double overallRatingForCalculation;
    private String overallRatingDisplayed;
    private String name;
    private int number;

    // EFFECTS: construct and initialize a library object
    public Library(Comments comments, String name, int number) {
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

    public int getNumber() {
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
}
