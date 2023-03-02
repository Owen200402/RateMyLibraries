package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.WriteEnable;

// Represents user's comments, enabling users to write or remove them

public class Comments implements WriteEnable {
    private List<Comment> comments;

    // EFFECTS: Constructs a list of comments to store individual comments from the user
    public Comments() {
        comments = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add comment to the list of comments we have so far
    //      and record the number of that comment being added in a queue
    public void add(Comment comment) {
        comments.add(comment);
        comment.setNumberBeingAdded(comments.size());
    }

    // REQUIRES: 0 < num <= number of comments displayed specifically to the user
    // MODIFIES: this
    // EFFECTS:  remove the specified item of choice based on the password associated
    //      with the same comment
    public boolean remove(int num, String password) {
        for (int i = 0; i < comments.size(); i++) {
            if (i == num - 1) {
                // Verify the password for now to make use of this argument
                if (comments.get(i).getPassword().equals(password)) {
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
    private JSONArray commentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Comment c : comments) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
