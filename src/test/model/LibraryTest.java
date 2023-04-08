package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    Library library;
    Comments comments;
    Date date = new Date(20);

    @BeforeEach
    public void setUp() {
        Comment c = new Comment("Great Environment", 3, "owen04", date.toString());
        comments = new Comments();
        comments.addToSystem(c);
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
        library.getListOfComments().addToSystem(new Comment("Hey", 2.6, "owen04",
                new Date().toString()));
        library.calculateOverallRating();
        assertEquals(2.8, library.getOverallRating());
        assertEquals("2.8", library.getRatingDisplayed());
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = library.toJson();
        assertEquals(3.0, library.getOverallRating());
        assertEquals("Koerner", jsonObject.get("name"));
        assertEquals(library.getRatingDisplayed(), jsonObject.get("rating"));
        assertEquals(library.libraryInfoToJson().toString(), jsonObject.get("library").toString());
    }

    @Test
    public void testToJsonWithMultipleLibraries() {
        Comment newComment = new Comment("Nice!", 5, "daniel", date.toString());
        comments.addToSystem(newComment);

        JSONObject jsonObject = library.toJson();

        assertEquals(comments.commentsToJson().toString(), jsonObject.get("library").toString());
    }

}
