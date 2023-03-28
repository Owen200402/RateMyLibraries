package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    Comment comment;
    Date newDate;

    @BeforeEach
    public void setUp() {
        newDate = new Date();
        comment = new Comment("Great Environment", 1, "owen04", newDate.toString());
    }

    @Test
    public void testConstructor() {
        assertEquals(comment.getMessage(), "Great Environment");
        assertEquals(comment.getRating(), 1);
        assertEquals(comment.getPassword(), "owen04");
        assertEquals(comment.getDate(), newDate.toString());
        assertEquals(comment.getNumberBeingAdded(), 0);
    }

    @Test
    public void testToString() {
        comment.setNumberBeingAdded(1);
        assertEquals("<html><b>User " + 1 + "</b>: " + "Rating = " + 1.0 + "\n"
                + " - commented at " + newDate + " : " + "Great Environment</html>"
                , comment.toString());
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = comment.toJson();
        assertEquals("Great Environment", jsonObject.get("message"));
        assertEquals(1.0, jsonObject.get("rating"));
        assertEquals("owen04", jsonObject.get("password"));
        assertEquals(newDate.toString(), jsonObject.get("date").toString());
    }
}