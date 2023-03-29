package ui;

import model.Comment;
import model.System;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

// Separate Window for adding comments linked with password
public class AddingWindow implements ActionListener {
    private static int HGAP = 20;
    private static int VGAP = 10;
    private static JFrame frame;
    private static JPanel panel;
    private static JPanel container;
    private static JLabel prompt;
    private static JLabel userLabel;
    private static JTextArea textArea;
    private static JLabel passwordLabel;
    private static JLabel ratingLabel;
    private static JTextField ratingText;
    private static JPasswordField passwordText;
    private static JButton button;
    private static JButton clearAll;
    private static JLabel success;

    private JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private JsonWriter jsonWriter = new JsonWriter("./data/libraries.json");

    // EFFECTS: sets up the window with frames and all components
    public void setUpWindow() {
        setFrame();
        setPromptAndTextArea();
        setScrollPane();
        setRatingPasswordAndSaveButton();
        setClearAllButton();

        container.add(userLabel);
        container.add(ratingLabel);
        container.add(ratingText);
        container.add(passwordLabel);
        container.add(passwordText);


        panel.add(container, BorderLayout.CENTER);

        success = new JLabel();
        success.setBounds(20, 160 + VGAP + VGAP / 2, 300, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("Roboto", 0, 12));

        container.add(success);
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
    // EFFECTS: sets scroll pane for the text area
    private void setScrollPane() {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20,30,400 - HGAP - HGAP, 40);
        container.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: displays the question prompt and text areas for users to input their comments
    private void setPromptAndTextArea() {
        prompt = new JLabel("<html><h2>Adding Comment</h2><html>");
        prompt.setSize(new Dimension(350, 50));
        prompt.setBackground(Color.BLUE);
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(prompt, BorderLayout.NORTH);

        container = new JPanel(null);

        userLabel = new JLabel("Comment:");
        userLabel.setBounds(20,0,100,25);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(20, 30, 400 - HGAP - HGAP, 40);
    }

    // MODIFIES: this
    // EFFECTS: sets prompt and allows users to save their rating with a password of their choice;
    //          the "Save" button has action event to allows savings
    private void setRatingPasswordAndSaveButton() {
        ratingLabel = new JLabel("Give it a rate! (out of 5)");
        ratingLabel.setBounds(20, 80, 150, 30);
        ratingText = new JTextField();
        ratingText.setBounds(150 + HGAP, 80, 40, 30);


        passwordLabel = new JLabel("<html>Password: <I>(Ensuring your comment cannot be modified"
                + "by others)</I></html>");
        passwordLabel.setBounds(20, 110 + VGAP, 400 - HGAP - HGAP, 35);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(20, 145 + VGAP, 200, 25);

        button = new JButton("Save");
        button.setBounds(400 - HGAP * 9 - 5, 145 + VGAP, 80, 25);
        button.addActionListener(this);
        container.add(button);
    }

    // MODIFIES: this
    // EFFECTS: sets the button for clearing all the text user has answered
    private void setClearAllButton() {
        clearAll = new JButton("Clear All");
        clearAll.setBounds(400 - HGAP * 5 - 10, 145 + VGAP, 100, 25);
        addActionForClearAll();
        container.add(clearAll);
    }

    // MODIFIES: this
    // EFFECTS: sets action for clearing texts in all fields when the user clicks on the "Clear All button"
    private void addActionForClearAll() {
        clearAll.addActionListener(e -> {
            textArea.setText("");
            passwordText.setText("");
            ratingText.setText("");
        });
    }

    // MODIFIES: this
    // EFFECTS: action event that validates user's inputs and switch screen back to Home frame with initial
    //          set up after the refresh.
    @Override
    public void actionPerformed(ActionEvent e) {
        String comment = textArea.getText();
        String password = passwordText.getText();
        String rating = ratingText.getText();

        if (password.isEmpty() | comment.isEmpty() | rating.isEmpty()) {
            success.setText("Fields Cannot Be Empty!");
            return;
        }
        if (password.length() <= 5) {
            success.setText("Unsafe Password. Please Enter A Longer Password.");
            passwordText.setText("");
            return;
        }
        if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
            success.setText("Please Enter A Number Between 0 and 5.");
            return;
        }
        setText();
        switchScreen();
    }

    // MODIFIES: this
    // EFFECTS: writes the new info into ./data/libraries.json file
    private void setText() {
        String comment = textArea.getText();
        String password = passwordText.getText();
        String rating = ratingText.getText();
        try {
            System system = jsonReader.read();
            system.getLibraries().get(1).getListOfComments().add(new Comment(comment,
                    Double.parseDouble(rating), password, new Date().toString()));
            jsonWriter.open();
            jsonWriter.write(system);
            jsonWriter.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // MODIFIES: this
    // EFFECT: Switch Back to the Main Screen
    private void switchScreen() {
        JFrame oldFrame = Home.getFrame();
        new Home();
        frame.dispose();
        oldFrame.dispose();
    }
}