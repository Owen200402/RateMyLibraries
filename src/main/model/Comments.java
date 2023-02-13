package model;

import java.util.ArrayList;
import java.util.List;

public class Comments {
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

    public List<Comment> getComments() {
        return comments;
    }
}
