package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Class sourced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Including unmodified method testReaderNonExistentFile()

class  JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            model.System system = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadingEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            System system = reader.read();
            assertEquals(1, system.getLibraries().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReadingGeneralLibrary() {
        JsonReader reader = new JsonReader("./data/generalLibraries.json");
        try {
            System system = reader.read();
            assertEquals(6, system.getLibraries().size());
            List<Library> libraries = system.getLibraries();
            assertEquals(6, libraries.size());
            assertEquals("Asian Library", libraries.get(0).getName());
            assertEquals("NaN", libraries.get(0).getRatingDisplayed());

            assertEquals(0, libraries.get(0).getListOfComments().getComments().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
