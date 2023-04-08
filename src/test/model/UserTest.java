package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user;

    @BeforeEach
    public void setUp() {
        Comment c = new Comment("Great Environment", 1, "owen04", new Date().toString());
        Comments comments = new Comments();
        List<Library> libraries = new ArrayList<>();
        Library asianL = new Library(comments, "Asian Library", 1);
        libraries.add(asianL);

        comments.setLibrary(asianL);
        comments.addToSystem(c);
        user = new User(libraries);
    }

    @Test
    public void constructorTest() {
        assertEquals(1, user.getLibraryList().size());
    }

    @Test
    public void viewPreviousCommentedTest() {
        assertNull(user.viewPreviousCommented("askdjhask"));
        assertEquals(1, user.viewPreviousCommented("owen04").size());
    }
}
