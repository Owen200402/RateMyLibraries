package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Class sourced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Including unmodified method testWriterInvalidFile()

class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            model.System system = new System();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            System system = new System();
            Date date = new Date(20);
            Comment c = new Comment("Great Environment", 3, "owen04", date.toString());
            Comments comments = new Comments();
            comments.add(c);
            Library library = new Library(comments, "Koerner", 4);
            system.add(library);

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(system);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            system = reader.read();
            assertEquals(1, system.getLibraries().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            System system = new System();
            Date date = new Date(20);
            Comment c = new Comment("Great Environment", 3, "owen04", date.toString());
            Comments comments = new Comments();
            comments.add(c);
            Library library = new Library(comments, "Koerner", 4);
            system.add(library);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(system);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            system = reader.read();
            assertEquals(1, system.getLibraries().size());
            List<Library> libraries = system.getLibraries();
            assertEquals(1, libraries.size());
            assertEquals("Koerner", libraries.get(0).getName());
            assertEquals("3", libraries.get(0).getRatingDisplayed());

            assertEquals(1, libraries.get(0).getListOfComments().getComments().size());
            assertEquals("Great Environment",
                    libraries.get(0).getListOfComments().getComments().get(0).getMessage());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
