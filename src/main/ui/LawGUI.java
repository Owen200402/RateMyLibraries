package ui;

// Represents a GUI for Law Library


// Reference:
// image/Law_Library.jpg: https://news.ubc.ca/2014/01/28/ubc-law-asks-b-c-s-law-society-to-consider-impact-of-trinity-westerns-covenant-on-lgbt-community/

public class LawGUI extends LibraryGUI {
    public LawGUI() {
        super("Law Library", 3,
                "<html><u>Description</u>: <br>"
                        + "Want to talking to leaders of tomorrow in faculty of law?"
                        + "Come here and have a coffee chat with them! School of Law has a great environment"
                        + "for anyone whether to sit quiet or to do group discussion! </html>",
                "image/Law_Library.jpg");
    }
}
