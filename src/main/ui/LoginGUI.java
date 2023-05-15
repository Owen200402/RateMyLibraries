package ui;

import model.Client;
import model.Clients;
import persistance.LoginReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Separate Window for adding comments linked with password
public class LoginGUI {
    private static final int VGAP = 10;
    private static JFrame frame;
    private final  GradientPanel panel = new GradientPanel(new Color(109, 213, 237), new Color(33, 147, 176));
    private static JPanel container;
    private static JLabel prompt;
    private static JLabel userLabel;
    private static JTextArea textArea1;
    private static JButton button;
    private static JLabel success;
    private static JLabel passwordLabel;
    private static JPasswordField textArea2;
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:SuppressWarnings"})
    public static boolean loggedIn;
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:SuppressWarnings"})
    public static String displayedName;

    private LoginReader loginReader = new LoginReader("./data/loginProfile.json");

    private static int TEXTAREA_HEIGHT = 18;

    public LoginGUI() {
        loggedIn = false;
        setUpWindow();
    }

    // EFFECTS: sets up the window with frames and all components
    public void setUpWindow() {
        setFrame();
        setPromptAndTextArea();
        addTextAreaToContainer();
        setPasswordConfirmLabel();
        setFootNote();

        container.add(userLabel);
        container.add(passwordLabel);

        container.setOpaque(false);

        success = new JLabel();
        success.setBounds(75, 160 + VGAP + VGAP / 2, 270, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("SansSerif", 1, 12));

        container.add(success, BorderLayout.NORTH);
        frame.add(panel);
        frame.setVisible(true);

        panel.add(container, BorderLayout.CENTER);

    }


    // MODIFIES: this
    // EFFECTS: sets up the frame and panel
    private void setFrame() {
        frame = new JFrame();
        panel.setLayout(new BorderLayout());

        frame.setSize(400, 300);
        frame.setMaximumSize(new Dimension(400, 300));
        frame.setMinimumSize(new Dimension(400, 300));

        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: displays the question prompt and text areas for users to input their comments
    private void setPromptAndTextArea() {
        prompt = new JLabel("<html><h1>Rate My libraries Login</h1><html>");
        prompt.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        prompt.setSize(new Dimension(350, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(prompt, BorderLayout.NORTH);

        container = new JPanel(null);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(70,5,100,25);
        userLabel.setFont(new Font("SansSerif", 0, 14));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(70, 65, 100, 25);
        passwordLabel.setFont(new Font("SansSerif", 0, 14));

        textArea1 = new JTextArea();
        textArea1.setBounds(70, 35, 400 - 70 * 2, TEXTAREA_HEIGHT);

        textArea2 = new JPasswordField();
        textArea2.setBounds(70, 95, 400 - 70 * 2, TEXTAREA_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: adds text area to the container
    private void addTextAreaToContainer() {
        container.add(textArea1, BorderLayout.CENTER);
        container.add(textArea2, BorderLayout.CENTER);
        setTextAreaBorder();
    }

    // MODIFIES: this
    // EFFECTS: sets text area's border
    private void setTextAreaBorder() {
        textArea1.setBorder(BorderFactory.createLineBorder(Color.gray));
        textArea2.setBorder(BorderFactory.createLineBorder(Color.gray));
    }


    // MODIFIES: this
    // EFFECTS: sets prompt and allows users to save their rating with a password of their choice;
    //          the "Save" button has action event to allows savings
    private void setPasswordConfirmLabel() {
        button = new JButton("Login");
        button.setBounds(70, 125, 400 - 70 * 2, TEXTAREA_HEIGHT + 2);

        container.add(button, BorderLayout.CENTER);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setButton(button);
    }

    // MODIFIES: this
    // EFFECTS: sets clicking events for the button
    private void setButton(JButton button) {
        button.addActionListener(e -> {
            if (matches()) {
                switchScreen();
            } else {
                setEmptyTextArea();
                success.setText("Your username or password is incorrect!");
            }
        });
    }

    // EFFECTS: sets text area to be empty
    private void setEmptyTextArea() {
        textArea1.setText("");
        textArea2.setText("");
    }

    // EFFECTS: checks weather an account has been created with the same username and password
    private boolean matches() {
        try {
            Clients clients = loginReader.read();
            for (Client client : clients) {
                String str = new String(textArea2.getPassword());
                if (client.getUserName().equals(textArea1.getText())
                        && client.getPassword().equals(str)) {
                    loggedIn = true;
                    displayedName = client.getUserName();
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: sets the link for directing to the Login page
    private void setFootNote() {
        JButton label = new JButton("<html><u>"
                + "No account? Click here to sign it up!</u></html>");
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setForeground(Color.BLUE);
        label.setBounds(400 / 2 - 250 / 2, 150, 250,30);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addActionListener(e -> {
            new SignUpGUI();
            frame.dispose();
        });
        container.add(label);
    }

    // MODIFIES: this
    // EFFECT: Switch Back to the Main Screen
    private void switchScreen() {
        JFrame oldFrame = HomeGUI.getFrame();
        new HomeGUI();
        frame.dispose();
        oldFrame.dispose();
    }
}

