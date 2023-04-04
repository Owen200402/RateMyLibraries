package ui;

// Represents a GUI for Biomedical Branch Library

// Reference:
// image/Biomed_Library.jpg: https://nicelocal.ca/vancouver/cultural_places/ubc_biomedical_branch_library/

public class BiomedicalBranchGUI extends LibraryGUI {
    public BiomedicalBranchGUI() {
        super("Biomedical Branch Library", 5,
                "<html><u>Description</u>: <br>"
                        + "One of the newest Libraries that lots of old UBCers haven't gone for a visit!"
                        + "Without spoiling too much about it, come here and enjoy your sunny study session! </html>",
                "image/Biomed_Library.jpg");
    }
}
