package model;

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
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<Comment>(), comments.getComments());
    }

    @Test
    public void testAdd() {
        assertEquals(0, comments.getComments().size());
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date());
        comments.add(comment);

        assertEquals(1, comments.getComments().size());
        assertEquals(1, comment.getNumberBeingAdded());
    }

    @Test
    public void testRemove() {
        Comment comment = new Comment("Great Environment", 1, "owen04", new Date());
        comments.add(comment);

        assertFalse(comments.remove(1, "gregor"));
        assertEquals(1, comments.getComments().size());

        assertTrue(comments.remove(1, "owen04"));
        assertEquals(0, comments.getComments().size());
    }
}
