package ui;

// Represents a GUI for Koerner Library

// Reference:
// image/Koerner_Library.jpg: https://commons.wikimedia.org/wiki/File:Koerner_library_ubc.jpg

public class KoernerGUI extends LibraryGUI {
    // EFFECTS: calls the super constructor and pass in the library's title, description and its image
    public KoernerGUI() {
        super("Koerner Library", 2,
                "<html><u>Description</u>: <br>"
                        + "Located near the Sauder School of business, as one of the most beautiful"
                        + "architectures in UBC -- an open book symbolizing knowledge and potential!"
                        + "There are lots of learning spaces around especially underground. Highly recommend "
                        + "new students to check out. Believe me! You will love it! </html>",
                "image/Koerner_Library.jpg");
    }
}
