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
        comments.addToSystem(c);
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library(comments, "Asian Library", 1));
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
