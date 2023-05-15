package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// Represents shared functionalities between HomeGUI and LibraryGUI
public abstract class SharedResources {
    // MODIFIES: this
    // EFFECTS: adds welcome message in the top-right corner if the user has logged in successfully
    public abstract void addWelcomeMessage(Frame frame);
}
