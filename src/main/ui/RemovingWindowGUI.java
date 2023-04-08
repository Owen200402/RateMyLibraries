package ui;

import model.System;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Separate Window for removing comments linked with password
public class RemovingWindowGUI implements ActionListener {
    private static int HGAP = 20;
    private static int VGAP = 10;
    private static JFrame frame;
    private static JPanel panel;
    private static JPanel container;
    private static JLabel prompt;
    private static JLabel userLabel;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton button;
    private static JButton clearAll;
    private static JLabel success;
    private static ButtonGroup buttonGroup;
    private static JRadioButton radioButton;
    private static JPanel radioContainer;
    private int userNum;

    private JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private JsonWriter jsonWriter = new JsonWriter("./data/libraries.json");

    private LibraryGUI libraryGUI;
    private int libraryNum;
    private String imagePath;

    // REQUIRES: libraryNum to save all the content to jsonReader
    // EFFECT: Constructs the AddingWindowGUI
    public RemovingWindowGUI(int libraryNum, LibraryGUI libraryGUI, String imagePath) {
        this.libraryGUI = libraryGUI;
        this.libraryNum = libraryNum;
        this.imagePath = imagePath;
    }

    // EFFECTS: sets up the window with frames and all components
    public void setUpWindow() {
        setFrame();
        setPromptAndRadioButtons();
        setRatingPasswordAndSaveButton();
        setColorToGray();
        setClearAllButton();

        container.add(userLabel);
        container.add(passwordLabel);
        container.add(passwordText);

        panel.add(container, BorderLayout.CENTER);

        success = new JLabel();
        success.setBounds(20, 160 + VGAP + VGAP / 2, 300, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("SansSerif", 0, 12));

        container.add(success);
        frame.setBackground(new Color(5, 63, 190));
        panel.setBackground(new Color(236, 106, 144));
        container.setBackground(new Color(236, 106, 144));
        radioContainer.setBackground(new Color(236, 106, 144));
        frame.add(panel);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame and panel
    private void setFrame() {
        frame = new JFrame();
        panel = new JPanel(new BorderLayout());

        frame.setSize(400, 300);
        frame.setMaximumSize(new Dimension(400, 300));
        frame.setMinimumSize(new Dimension(400, 300));

        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: sets up the prompt asking users to remove their comments and radio buttons
    private void setPromptAndRadioButtons() {
        prompt = new JLabel("<html><h2>Removing Comment</h2><html>");
        prompt.setSize(new Dimension(350, 50));
        prompt.setBackground(Color.BLUE);
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(prompt, BorderLayout.NORTH);

        container = new JPanel(null);

        setRadioButton();
    }

    // MODIFIES: this
    // EFFECTS: sets colors to gray for all input panels
    private void setColorToGray() {
        passwordText.setBackground(new Color(222, 221, 213));
    }

    // MODIFIES: this
    // EFFECTS: sets the radio button to allow only single choice and its container has flexible grid layout
    //          depending on number of inputs
    private void setRadioButton() {
        userLabel = new JLabel("Select a user comment you want to remove: ");
        userLabel.setBounds(20,0,300,25);


        try {
            System system = jsonReader.read();
            int userNumber = system.getLibraries().get(libraryNum).getListOfComments().getComments().size();
            radioContainer = new JPanel(new GridLayout((2 + userNumber) / 3, 3));
            radioContainer.setBounds(50, 20, 300, 100);
            setButton(userNumber);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        container.add(radioContainer, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets the affect for the radio button and save the data as to which one is clicked
    private void setButton(int userNumber) {
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < userNumber; i++) {
            radioButton = new JRadioButton("User: " + (i + 1));
            radioButton.setSize(new Dimension(20,20));
            int finalI = i;
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userNum = finalI + 1;
                }
            });
            radioContainer.add(radioButton);
            buttonGroup.add(radioButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the password prompt and allows removal
    private void setRatingPasswordAndSaveButton() {
        passwordLabel = new JLabel("<html>Password: </html>");
        passwordLabel.setBounds(20, 110 + VGAP, 400 - HGAP - HGAP, 35);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(20, 145 + VGAP, 200, 25);

        button = new JButton("Remove");
        button.setBounds(400 - HGAP * 9 - 5, 145 + VGAP, 80, 25);
        button.addActionListener(this);
        container.add(button);
    }

    // MODIFIES: this
    // EFFECTS: sets the button for clearing all the text user has answered
    private void setClearAllButton() {
        clearAll = new JButton("Clear");
        clearAll.setBounds(400 - HGAP * 5 - 10, 145 + VGAP, 100, 25);
        addActionForClearAll();
        container.add(clearAll);
    }

    // MODIFIES: this
    // EFFECTS: sets action for clearing texts in all fields when the user clicks on the "Clear All button"
    private void addActionForClearAll() {
        clearAll.addActionListener(e -> {
            passwordText.setText("");
            buttonGroup.clearSelection();
        });
    }


    // MODIFIES: this
    // EFFECTS: action event that validates user's inputs, switch screen back to Home frame with initial
    //          set up after the refresh and write the changes into ./data/libraries.json file
    @Override
    public void actionPerformed(ActionEvent e) {
        String password = passwordText.getText();

        if (password.isEmpty()) {
            success.setText("Password Field Cannot Be Empty!");
            return;
        }
        if (userNum == 0) {
            success.setText("Please Make a Choice!");
            return;
        }

        try {
            System system = jsonReader.read();
            if (!system.getLibraries().get(libraryNum).getListOfComments().removeFromSystem(userNum, password)) {
                success.setText("Password is incorrect!");
                passwordText.setText("");
                return;
            }
            jsonWriter.open();
            jsonWriter.write(system);
            jsonWriter.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        switchScreen();
    }

    // MODIFIES: this
    // EFFECT: Switch Back to the Main Screen
    private void switchScreen() {
        JFrame oldFrame = LibraryGUI.getFrame();
        new LibraryGUI(libraryGUI.getTitle(), libraryGUI.getLibraryNum(), libraryGUI.getDescriptionText(), imagePath);
        frame.dispose();
        oldFrame.dispose();
    }
}

