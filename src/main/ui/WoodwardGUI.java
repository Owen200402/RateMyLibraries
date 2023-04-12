package ui;

// Represents a GUI for Woodward Library

// Reference:
// Woodward_Library.jpg: https://infrastructuredevelopment.ubc.ca/projects/woodward-library-entrance-and-study-space/

public class WoodwardGUI extends LibraryGUI {
    // EFFECTS: calls the super constructor and pass in the library's title, description and its image
    public WoodwardGUI() {
        super("Woodward Library", 4,
                "<html><u>Description</u>: <br>"
                        + "Woodward Library is one of the biggest libraries in UBC with combination "
                        + "of two gigantic lecture halls and a library! Arts students who commute between "
                        + "IRC and Buchanan would often choose this library to study. </html>",
                "image/Woodward_Library.jpg");
    }
}
