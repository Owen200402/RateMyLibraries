package ui;

import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Represents the Home Page where users can select libraries of their choices
public class HomeGUI {
    private static JFrame frame;
    private static JLabel heading;
    private static JPanel libraryList;

    // EFFECTS: constructs and shows the window of Home Page of Rate My Library
    public HomeGUI() {
        frame = new JFrame("Rate My Libraries - Home");
        setFrame();
        setHeading();
        setGrid();
        insertLibraryList();
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
        logEvents();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: sets the hand-written heading
    private void setHeading() {
        heading = new JLabel();

        ImageIcon imageIcon = new ImageIcon("image/RateMyLib.png");
        Image image = imageIcon.getImage().getScaledInstance(500, 130, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        heading.setIcon(inserted);
        heading.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.add(heading, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets the layout to GridLayout and adjusts color
    private void setGrid() {
        libraryList = new JPanel();
        libraryList.setPreferredSize(new Dimension(400, 500));
        libraryList.setBorder(BorderFactory.createEmptyBorder(20,200,20,200));
        libraryList.setLayout(new GridLayout(2, 3, 0, 20));
        libraryList.setBackground(new Color(204, 87, 87));
        frame.add(libraryList);
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

    private void logEvents() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (model.Event event : EventLog.getInstance()) {
                    java.lang.System.out.println(event.getDescription());
                }
            }
        });
    }
}
