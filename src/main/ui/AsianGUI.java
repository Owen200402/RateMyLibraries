package ui;

// Represents a GUI for Asian library

// Reference:
// image/Asian_Centre.jpg: https://www.rjc.ca/project-details/ubc-asian-centre-roof-replacement.html

public class AsianGUI extends LibraryGUI {
    public AsianGUI() {
        super("Asian Centre", 0,
                "<html><u>Description</u>: <br>"
                        + "Surrounded by peaceful and serene forest that could turn"
                        + "everything into nothingness, with one of the libraries Asian learners "
                        + "are proud of! In here, there is also a gigantic study area for quiet learning. "
                        + "Check it out!</html>", "image/Asian_Centre.jpg");
    }
}
