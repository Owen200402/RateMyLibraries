package ui;

// Represents a GUI for Irving K. Barber Learning Centre

// Reference:
// image/Ikblearningcentre.jpg: https://en.wikipedia.org/wiki/Irving_K._Barber_Learning_Centre

public class IkbGUI extends LibraryGUI {
    // EFFECTS: calls the super constructor and pass in the library's title, description and its image
    public IkbGUI() {
        super("Irving K. Barber Learning Centre", 1,
                "<html><u>Description</u>: <br>"
                        + "The gigantic tower well-known for its gorgeous looking "
                        + "and the packed studying areas with great hardware. It is "
                        + "one of the libraries that have the most books stored! Also, lots of room-booking "
                        + "Services! </html>", "image/Ikblearningcentre.jpg");
    }
}
