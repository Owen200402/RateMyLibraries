package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    Library library;

    @BeforeEach
    public void setUp() {
        Comment c = new Comment("Great Environment", 3, "owen04", new Date());
        Comments comments = new Comments();
        comments.add(c);
        library = new Library(comments, "Koerner", 4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Koerner", library.getName());
        assertEquals(4, library.getNumber());
        assertEquals(0, library.getOverallRating());
        assertNull(library.getOverallRatingDisplayed());
        assertNotNull(library.getListOfComments());
    }

    @Test
    public void testCalculateOverallRating() {
        assertEquals(0, library.getOverallRating());
        library.calculateOverallRating();
        assertEquals(3, library.getOverallRating());
        library.getListOfComments().add(new Comment("Hey", 2.6, "owen04", new Date()));
        library.calculateOverallRating();
        assertEquals(2.8, library.getOverallRating());
        assertEquals("2.8", library.getRating());
    }

}
