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
    private static JPanel panelLeftBottom;
    private static JPanel panelRightBottom;
    private static JLabel prompt;
    private static JLabel userLabel;
    private static JTextArea textArea1;
    private static JButton button;
    private static JLabel success;
    private static JLabel passwordLabel;
    private static JPasswordField textArea2;
    private LibraryGUI currentGUI;
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:SuppressWarnings"})
    public static boolean loggedIn;
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:SuppressWarnings"})
    public static String displayedName;

    // Data Preserving:
    private LoginReader loginReader = new LoginReader("./data/loginProfile.json");

    // CONSTANTS:
    private static int TEXTAREA_HEIGHT = 18;

    // EFFECTS: constructs a LoginGUI
    public LoginGUI() {
        loggedIn = false;
        setUpWindow();
    }

    // EFFECTS: constructs a LoginGUI with current frame passed in for screen switching
    public LoginGUI(LibraryGUI currentGUI) {
        loggedIn = false;
        this.currentGUI = currentGUI;
        setUpWindow();
    }

    // EFFECTS: sets up the window with frames and all components
    private void setUpWindow() {
        setFrame();
        setPromptAndTextArea();
        addTextAreaToContainer();
        setPasswordConfirmLabel();
        setFootNote();

        panelLeftBottom.add(userLabel);
        panelLeftBottom.add(passwordLabel);

        success = new JLabel();
        success.setBounds(75, 160 + VGAP + VGAP / 2, 270, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("SansSerif", 1, 12));

        panelLeftBottom.add(success, BorderLayout.NORTH);
        frame.add(panel);
        frame.setVisible(true);

        panel.add(container, BorderLayout.CENTER);
    }


    // MODIFIES: this
    // EFFECTS: sets up the frame and panel
    private void setFrame() {
        frame = new JFrame();
        panel.setLayout(new BorderLayout());

        frame.setSize(600, 300);
        frame.setMaximumSize(new Dimension(600, 300));
        frame.setMinimumSize(new Dimension(600, 300));

        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: displays the question prompt and text areas for users to input their comments
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setPromptAndTextArea() {
        prompt = new JLabel("<html><h1>Rate My libraries Login</h1><html>");
        prompt.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        prompt.setSize(new Dimension(350, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(prompt, BorderLayout.NORTH);

        container = new JPanel(new BorderLayout());
        container.setOpaque(false);

        panelLeftBottom = new JPanel(null);
        panelLeftBottom.setPreferredSize(new Dimension(300, 150));
        panelLeftBottom.setOpaque(false);

        panelRightBottom = new JPanel(new BorderLayout());
        panelRightBottom.setPreferredSize(new Dimension(300, 150));
        panelRightBottom.setOpaque(false);
        insertToPanelRight();

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
    // EFFECTS: inserts login logo to the right panel
    private void insertToPanelRight() {
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("image/th.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        imageLabel.setIcon(inserted);
        imageLabel.setSize(100, 100);

        panelRightBottom.add(imageLabel, BorderLayout.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(panelRightBottom, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: adds text area to the container
    private void addTextAreaToContainer() {
        panelLeftBottom.add(textArea1);
        panelLeftBottom.add(textArea2);
        setTextAreaBorder();
        container.add(panelLeftBottom, BorderLayout.WEST);
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

        panelLeftBottom.add(button, BorderLayout.CENTER);

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
                + "No account? Click here to sign up!</u></html>");
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setForeground(Color.BLUE);
        label.setBounds(370 / 2 - 250 / 2, 150, 250,30);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addActionListener(e -> {
            new SignUpGUI();
            frame.dispose();
        });
        panelLeftBottom.add(label);
    }

    // MODIFIES: this
    // EFFECT: Switch Back to the Main Screen
    private void switchScreen() {
        if (currentGUI != null) {
            currentGUI.reload();
            frame.dispose();
            return;
        } else {
            JFrame oldFrame = HomeGUI.getNewFrame();
            new HomeGUI();
            frame.dispose();
            oldFrame.dispose();
        }
    }
}

