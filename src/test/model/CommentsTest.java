package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentsTest {
    Comments comments;

    @BeforeEach
    public void setUp() {
        comments = new Comments();
        Library library = new Library(comments, "Koerner", 4);

        comments.setLibrary(library);
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<Comment>(), comments.getComments());
    }

    @Test
    public void testAdd() {
        assertEquals(0, comments.getComments().size());
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date().toString());
        comments.addToSystem(comment);

        assertEquals(1, comments.getComments().size());
        assertEquals(1, comment.getNumberBeingAdded());
    }

    @Test
    public void testRemove() {
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date().toString());
        comments.addToSystem(comment);

        assertFalse(comments.removeFromSystem(1, "gregor"));
        assertEquals(1, comments.getComments().size());

        assertFalse(comments.removeFromSystem(0, "gregor"));
        assertEquals(1, comments.getComments().size());

        assertTrue(comments.removeFromSystem(1, "owen04"));
        assertEquals(0, comments.getComments().size());
    }

    @Test
    public void testToJson() {
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date().toString());
        comments.addToSystem(comment);

        JSONObject jsonObject = comments.toJson();
        assertEquals(comments.commentsToJson().toString(), jsonObject.get("comments").toString());
    }

    @Test
    public void testToJsonAddMultipleComments() {
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date().toString());
        Comment comment2 = new Comment("Nice", 5, "Daniel", new Date().toString());
        comments.addToSystem(comment);
        comments.addToSystem(comment2);

        JSONObject jsonObject = comments.toJson();
        assertEquals(comments.commentsToJson().toString(), jsonObject.get("comments").toString());
    }
}
