package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.WriteEnable;

// Represents user's comments, enabling users to write or remove them
public class Comments implements WriteEnable {
    private List<Comment> comments;
    private Library library;
    private boolean isReadingFromJson = false;

    // EFFECTS: constructs a list of comments to store individual comments from the user
    public Comments() {
        comments = new ArrayList<>();
    }

    // EFFECTS: constructs a list of comments and sets up the library field

    // EFFECTS: associates Comments with its Libraries
    public void setLibrary(Library library) {
        this.library = library;
    }

    // REQUIRES: comment from the user
    // MODIFIES: this
    // EFFECTS: adds comment to the list of comments we have so far
    //      and record the number of that comment being added in a queue
    public void addToSystem(Comment comment) {
        comments.add(comment);
        comment.setNumberBeingAdded(comments.size());
        if (!isReadingFromJson) {
            EventLog.getInstance().logEvent(new Event("A new comment is added to " + library.getName()));
        }
    }

    // REQUIRES: 0 < num <= number of comments displayed specifically to the user
    // MODIFIES: this
    // EFFECTS:  removes the specified item of choice based on the password associated
    //      with the same comment
    public boolean removeFromSystem(int num, String password) {
        for (int i = 0; i < comments.size(); i++) {
            if (i == num - 1) {
                if (comments.get(i).getPassword().equals(password)) {
                    if (!isReadingFromJson) {
                        EventLog.getInstance().logEvent(new Event("A comment is removed from " + library.getName()));
                    }
                    comments.remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("comments", commentsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    public JSONArray commentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Comment c : comments) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setIsReadingFromJson(boolean yesOrNo) {
        isReadingFromJson = yesOrNo;
    }
}
