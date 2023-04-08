package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A User who can view list of comments they made
public class User {
    private List<Library> libraryList;

    // EFFECTS: construct a user with all libraries public to them
    public User(List<Library> libraries) {
        this.libraryList = libraries;
    }

    // REQUIRES: 5 < password <= 20 characters
    // EFFECTS: view the comments this particular user has commented on
    public Map<Comment, String> viewPreviousCommented(String password) {
        Map<Comment, String> commentMap = new HashMap<>();
        int count = 0;

        for (Library l : libraryList) {
            for (Comment c : l.getListOfComments().getComments()) {
                if (c.getPassword().equals(password)) {
                    commentMap.put(c, l.getName());
                    count++;
                }
            }
        }

        if (count == 0) {
            return null;
        }
        return commentMap;
    }

    public List<Library> getLibraryList() {
        return libraryList;
    }
}
