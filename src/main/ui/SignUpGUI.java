package ui;

import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;

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
    private static JButton clearAll;
    private static JLabel success;
    private static JLabel passwordText;
    private static JLabel passwordLabel;
    private static JLabel passwordConfirmLabel;
    private static JTextArea textArea2;
    private static JTextArea textArea3;

    private JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private JsonWriter jsonWriter = new JsonWriter("./data/libraries.json");

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
        success.setBounds(20, 160 + VGAP + VGAP / 2, 300, 25);
        success.setForeground(Color.RED);
        success.setFont(new Font("SansSerif", 0, 12));

        container.add(success);
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
}
