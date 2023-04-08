package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class SystemTest {
    System system;
    Date date = new Date(20);
    Library library;
    Comments comments;

    @BeforeEach
    public void setUp() {
        system = new System();
        Comment c = new Comment("Great Environment", 3, "owen04", date.toString());
        comments = new Comments();

        library = new Library(comments, "Koerner", 4);
        comments.setLibrary(library);
        comments.addToSystem(c);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, system.getLibraries().size());
    }

    @Test
    public void addLibrary() {
        system.add(library);
        assertEquals(1, system.getLibraries().size());
    }

    @Test
    public void testToJson() {
        system.add(library);
        JSONObject jsonObject = system.toJson();
        assertEquals(system.libraryInfoToJson().toString(), jsonObject.get("Rate My Library").toString());
    }



}
