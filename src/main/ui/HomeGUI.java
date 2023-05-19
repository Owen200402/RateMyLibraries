package ui;

import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Represents the Home Page where users can select libraries of their choices
public class HomeGUI extends SharedResources {
    private static JFrame frame;
    private static JLabel heading;
    private static JPanel libraryList;
    private static JLabel imageLabel;
    private static JFrame logOutFrame;

    // EFFECTS: constructs and shows the window of Home Page of Rate My Library
    public HomeGUI() {
        frame = new JFrame("Rate My Libraries - Home");
        imageLabel = new JLabel();
        setFrame();
        setHeading();
        setLibrariesGrid();
        setDescription();
        insertLibraryList();
        setIcon();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the frame
    private void setFrame() {
        frame.setSize(new Dimension(1140, 500));
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(204, 87, 87));
        frame.setMaximumSize(new Dimension(1140, 500));
        frame.setMinimumSize(new Dimension(1000, 470));
        frame.setLocationRelativeTo(null);
        if (LoginGUI.loggedIn) {
            addWelcomeMessage(frame);
        }
        logEvents();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: sets the login icon
    private void setIcon() {
        ImageIcon imageIcon = new ImageIcon("image/icon.png");
        Image image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        imageLabel.setIcon(inserted);
        imageLabel.setBounds(200, 20, 20, 30);
        frame.add(imageLabel, BorderLayout.AFTER_LINE_ENDS);
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (LoginGUI.loggedIn) {
                    setLogOutConfirmWindow();
                } else {
                    new LoginGUI();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Asking user whether they want to save their changes and direct to Home page
    private void setLogOutConfirmWindow() {
        logOutFrame = new JFrame();
        logOutFrame.setSize(new Dimension(450, 100));
        logOutFrame.setVisible(true);
        logOutFrame.setLocationRelativeTo(null);

        JButton button1 = new JButton("Yes");
        button1.setBounds(10,20, 30, 30);
        JButton button2 = new JButton("No");
        button2.setBounds(40,20, 30, 30);
        JLabel label = new JLabel("<html>Proceed to log out?</html>");
        label.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        logOutFrame.add(button1, BorderLayout.WEST);
        logOutFrame.add(button2, BorderLayout.EAST);
        logOutFrame.add(label, BorderLayout.NORTH);

        button1.addActionListener(e -> {
            LoginGUI.loggedIn = false;
            new HomeGUI();
        });

        button2.addActionListener(e -> {
            logOutFrame.dispose();
        });
    }

    // MODIFIES: this
    // EFFECTS: sets the heading with handwritten font
    private void setHeading() {
        heading = new JLabel();

        ImageIcon imageIcon = new ImageIcon("image/RateMyLib.png");
        Image image = imageIcon.getImage().getScaledInstance(500, 120, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        heading.setIcon(inserted);
        heading.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.add(heading, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets the layout to GridLayout and adjusts color
    private void setLibrariesGrid() {
        libraryList = new JPanel();
        libraryList.setSize(new Dimension(400, 300));
        libraryList.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 200));
        libraryList.setLayout(new GridLayout(2, 3, 0, 20));
        libraryList.setBackground(new Color(204, 87, 87));

        frame.add(libraryList, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets the description/purpose of the application
    private void setDescription() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(154, 61, 41));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));

        JLabel label = new JLabel("<html><div style=\"text-align: center;\"><b>Our Purposes:</b> New students to UBC would "
                + "nonetheless worry about their study space, "
                + "hardware, their distances to each building etc. Therefore we need your voice "
                + "to help make their transition to UBC smoother!<br><i>@ratemylibraries &copy; Owen Zheng </i></div></html>");
        label.setHorizontalAlignment(0);
        label.setForeground(new Color(234, 214, 93));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5,5, 5));
        panel.add(label);

        frame.add(panel, BorderLayout.SOUTH);
    }

    // EFFECTS: calls the helper createPanelForIndividualLib() and passes in the paths of the pictures
    // for scaling and the name of the library associated with them
    private void insertLibraryList() {
        createPanelForIndividualLib("image/Asian_Centre.jpg", "Asian Centre");
        createPanelForIndividualLib("image/Ikblearningcentre.jpg", "Irving K. Barber Learning Centre");
        createPanelForIndividualLib("image/Koerner_Library.jpg", "Koerner Library");
        createPanelForIndividualLib("image/Law_library.jpg", "Law Library");
        createPanelForIndividualLib("image/Woodward_Library.jpg", "Woodward Library");
        createPanelForIndividualLib("image/Biomed_Library.jpg", "BioMedical Branch Library");
    }

    // MODIFIES: this
    // EFFECTS: adds panels with pictures and titles into the grid and sets the cursor to hand shape when hovered
    private void createPanelForIndividualLib(String path, String name) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 200));
        panel.setBackground(new Color(204, 87, 87));
        panel.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        imageLabel.setIcon(inserted);
        imageLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        panel.add(imageLabel, BorderLayout.NORTH);

        JLabel libLabel = new JLabel(name);
        libLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(libLabel, BorderLayout.CENTER);
        libraryList.add(panel);

        directToOtherPages(panel, name);
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // MODIFIES: this
    // EFFECTS: directs the user to the correct library pages of their choices
    private void directToOtherPages(JPanel panel, String name) {
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (name.equals("Asian Centre")) {
                    new AsianGUI();
                    frame.dispose();
                } else if (name.equals("Irving K. Barber Learning Centre")) {
                    new IkbGUI();
                    frame.dispose();
                } else if (name.equals("Koerner Library")) {
                    new KoernerGUI();
                    frame.dispose();
                } else if (name.equals("Law Library")) {
                    new LawGUI();
                    frame.dispose();
                } else if (name.equals("Woodward Library")) {
                    new WoodwardGUI();
                    frame.dispose();
                } else if (name.equals("BioMedical Branch Library")) {
                    new BiomedicalBranchGUI();
                    frame.dispose();
                }
            }
        });
    }

    // EFFECTS: logs events on the console after user closes the Home Window
    private void logEvents() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (model.Event event : EventLog.getInstance()) {
                    java.lang.System.out.println(event.getDate());
                    java.lang.System.out.println(event.getDescription());
                    java.lang.System.out.println();
                }
            }
        });
    }

    public static JFrame getNewFrame() {
        return frame;
    }

    @Override
    public void addWelcomeMessage(Frame frame) {
        JLabel label = new JLabel("Welcome " + LoginGUI.displayedName + "!");
        label.setForeground(Color.LIGHT_GRAY);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        label.setBounds(frame.getWidth() - width, 5, width + 5, 20);
        frame.add(label);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                label.setBounds(frame.getWidth() - label.getWidth(), 5, width + 5, 20);
            }
        });
    }
}
