package ui;

import model.Client;
import model.Clients;
import persistance.LoginReader;
import persistance.LoginWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Separate Window for adding comments linked with password
public class SignUpGUI {
    private static int HGAP = 20;
    private static int VGAP = 10;
    private static JFrame frame;
    private static GradientPanel panel = new GradientPanel();
    private static JPanel container;
    private static JLabel prompt;
    private static JLabel userLabel;
    private static JTextArea textArea1;
    private static JButton button;
    private static JLabel success;
    private static JLabel passwordText;
    private static JLabel passwordLabel;
    private static JLabel passwordConfirmLabel;
    private static JTextArea textArea2;
    private static JTextArea textArea3;

    private LoginReader loginReader = new LoginReader("./data/loginProfile.json");
    private LoginWriter loginWriter = new LoginWriter("./data/loginProfile.json");

    private static int TEXTAREA_HEIGHT = 18;

    public SignUpGUI() {
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
        container.add(passwordConfirmLabel);

        panel.add(container, BorderLayout.CENTER);
        container.setOpaque(false);

        success = new JLabel();
        success.setBounds(75, 160 + VGAP + VGAP / 2, 270, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("SansSerif", 1, 14));

        container.add(success, BorderLayout.NORTH);
        frame.add(panel);
        frame.setVisible(true);
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
        prompt = new JLabel("<html><h2>Rate My Libraries Account Sign-Up</h2><html>");
        prompt.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        prompt.setSize(new Dimension(350, 50));
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(prompt, BorderLayout.NORTH);

        container = new JPanel(null);

        userLabel = new JLabel("Create a username:");
        userLabel.setBounds(75,10,150,25);

        passwordLabel = new JLabel("Create a password:");
        passwordLabel.setBounds(75, 40, 150, 25);

        passwordConfirmLabel = new JLabel("Confirm password:");
        passwordConfirmLabel.setBounds(75, 70, 150, 25);

        textArea1 = new JTextArea();
        textArea1.setBounds(215, 10, 100, TEXTAREA_HEIGHT);

        textArea2 = new JTextArea();
        textArea2.setBounds(215, 40, 150, TEXTAREA_HEIGHT);

        textArea3 = new JTextArea();
        textArea3.setBounds(215, 70, 150, TEXTAREA_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: adds text area to the container
    private void addTextAreaToContainer() {
        container.add(textArea1, BorderLayout.CENTER);
        container.add(textArea2, BorderLayout.CENTER);
        container.add(textArea3, BorderLayout.CENTER);
        setTextAreaBorder();
    }

    // MODIFIES: this
    // EFFECTS: sets text area's border
    private void setTextAreaBorder() {
        textArea1.setBorder(BorderFactory.createLineBorder(Color.gray));
        textArea2.setBorder(BorderFactory.createLineBorder(Color.gray));
        textArea3.setBorder(BorderFactory.createLineBorder(Color.gray));
    }


    // MODIFIES: this
    // EFFECTS: sets prompt and allows users to save their rating with a password of their choice;
    //          the "Save" button has action event to allows savings
    private void setPasswordConfirmLabel() {
        button = new JButton("Create an Account");
        button.setBounds(400 / 2 - 150 / 2, 120, 150, 25);
        container.add(button);
        button.addActionListener(e -> {
            if (textArea1.getText().length() == 0 | textArea2.getText().length() == 0) {
                success.setForeground(Color.RED);
                success.setText("User name or password cannot be empty!");
                setEmptyTextArea();
            } else if (!textArea2.getText().equals(textArea3.getText())) {
                success.setForeground(Color.RED);
                success.setText("Passwords do not match! Please try again!");
                setEmptyTextArea();
            } else if (accountExist()) {
                success.setForeground(Color.RED);
                success.setText("Username already exists! Please login!");
                setEmptyTextArea();
            } else {
                setJson();
                success.setForeground(new Color(19, 171, 116));
                success.setText("You've successfully signed up your account!");
                setEmptyTextArea();
            }
        });
    }

    // EFFECTS: sets text area to be empty
    private void setEmptyTextArea() {
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
    }

    // EFFECTS: checks weather an account has been created with the same username and password
    private boolean accountExist() {
        try {
            Clients clients = loginReader.read();
            for (Client client : clients) {
                if (client.getUserName().equals(textArea1.getText())) {
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
        JLabel label = new JLabel("<html><a href=\"\\ui\\LoginGUI\"> "
                + "Account exists already? Click here to login</a></html>");
        label.setBounds(400 / 2 - 270 / 2, 150, 270,30);
        container.add(label);

        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // TODO
//            }
//        });
    }

    // MODIFIES: this
    // EFFECTS: writes the new info into ./data/loginProfile.json file
    private void setJson() {
        String userName = textArea1.getText();
        String password = textArea2.getText();
        try {
            Clients clients = loginReader.read();
            clients.addClient(new Client(userName, password));

            loginWriter.open();
            loginWriter.write(clients);
            loginWriter.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
