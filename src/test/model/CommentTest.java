package model;

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
        comment = new Comment("Great Environment", 1, "owen04", newDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(comment.getMessage(), "Great Environment");
        assertEquals(comment.getRating(), 1);
        assertEquals(comment.getPassword(), "owen04");
        assertEquals(comment.getDate(), newDate);
        assertEquals(comment.getNumberBeingAdded(), 0);
    }

    @Test
    public void testToString() {
        comment.setNumberBeingAdded(1);
        assertEquals("User " + 1 + ". " + "Rating = " + 1.0 + "\n"
                + " - Commented at " + newDate + " : " + "Great Environment"
                , comment.toString());
    }
}