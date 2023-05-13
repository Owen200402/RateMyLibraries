package ui;

import model.EventLog;
import model.System;
import org.json.JSONObject;
import persistance.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// REFERENCE:
// image/vancouver-vancouver-marine.gif: https://tenor.com/view/vancouver-vancouver-marina-british-columbia-victoria-harbour-front-gif-18735026
// image/UBC-Logo.png: https://institute.ctlt.ubc.ca/ubc-logo-2/
// image/home-icon.png: https://www.flaticon.com/search?word=Home

// GUI Class Representing the Rate My Libraries
public class LibraryGUI implements ActionListener {
    // Constants:
    private static final int IMAGE_WIDTH = 180;
    private static final int COMMENT_GAP = 20;
    private static final JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private int trackCommentY = 20;

    // Components:
    private static System loadedInfo;
    private static JFrame frame;
    private static JFrame frameClosing;
    private static AddingWindowGUI addingWindowGUI;
    private static RemovingWindowGUI removingWindowGUI;
    private static JPanel panelL;
    private static JPanel panelR;
    private static JPanel addOrRemovePanel;
    private static JPanel titleAndDescriptionPanel;
    private static JPanel subTitlePanel;
    private static JPanel promptPanel;
    private static JLabel imageLabel;
    private static JLabel ubcLogo;
    private static JLabel textLabel;
    private static JLabel homeIcon;
    private static JButton clickHereToView;
    private static JButton buttonAdd;
    private static JButton buttonRemove;
    private static JScrollPane scrollPaneR;
    private static JSplitPane sl;
    private static ComponentAdapter adapter;

    // Inherited Fields
    private String title;
    private int libraryNum;
    private String descriptionText;
    private String imagePath;

    // EFFECTS: sets the main display screen with components added into the parent frame window
    //         that users can interact with
    public LibraryGUI() {
        run();
    }

    // REQUIRES: title for the current library, the id of a library
    // EFFECTS: sets the main display screen with components added into the parent frame window
    //         that users can interact with
    public LibraryGUI(String title, int libraryNum, String descriptionText, String imagePath) {
        this.title = title;
        this.libraryNum = libraryNum;
        this.descriptionText = descriptionText;
        this.imagePath = imagePath;
        run();
    }

    // EFFECTS: runs the GUI
    protected void run() {
        setFrame();
        setPanel();
        setScrollPane();
        setRatingDisplayed();
        setImageDisplayed();
        setTextDescription();
        setSubtitlePanel();
        setReturnIcon();
        setNumberOfComments();
        setSplitScreen();
        setClickHereToViewButton();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the frame (pop-up window) with minimum size so that users cannot shrink it
    private void setFrame() {
        frame = new JFrame();
        frame.setSize(1140, 550); // 20px Border everywhere
        frame.setMaximumSize(new Dimension(1140, 550));
        frame.setMinimumSize(new Dimension(1000, 500));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Rate My UBC Libraries");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Asking user whether they want to save their changes or not
    private void close() {
        frameClosing = new JFrame();
        frameClosing.setSize(new Dimension(450, 100));
        frameClosing.setVisible(true);
        frameClosing.setLocationRelativeTo(null);

        JButton button1 = new JButton("Save");
        button1.setBounds(10,20, 50, 30);
        JButton button2 = new JButton("No Save");
        button2.setBounds(40,20, 30, 30);
        JLabel label = new JLabel("<html>Save Your Changes to this Library?</html>");
        label.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        frameClosing.add(button1, BorderLayout.WEST);
        frameClosing.add(button2, BorderLayout.EAST);
        frameClosing.add(label, BorderLayout.NORTH);

        button1.addActionListener(e -> {
            setConsoleLogs();
            saveHistory();
        });

        button2.addActionListener(e -> {
            setConsoleLogs();
            noSaveHistory();
        });
    }

    // EFFECTS: logs all events on the console
    private void setConsoleLogs() {
        frameClosing.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                for (model.Event event : EventLog.getInstance()) {
                    java.lang.System.out.println(event.getDate());
                    java.lang.System.out.println(event.getDescription());
                    java.lang.System.out.println();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Asking user whether they want to save their changes and direct to Home page
    private void closeAndDirectHome() {
        frameClosing = new JFrame();
        frameClosing.setSize(new Dimension(450, 100));
        frameClosing.setVisible(true);
        frameClosing.setLocationRelativeTo(null);

        JButton button1 = new JButton("Save");
        button1.setBounds(10,20, 50, 30);
        JButton button2 = new JButton("No Save");
        button2.setBounds(40,20, 30, 30);
        JLabel label = new JLabel("<html>Save Your Changes to This Library?</html>");
        label.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        frameClosing.add(button1, BorderLayout.WEST);
        frameClosing.add(button2, BorderLayout.EAST);
        frameClosing.add(label, BorderLayout.NORTH);

        button1.addActionListener(e -> {
            saveHistory();
            new HomeGUI();
        });

        button2.addActionListener(e -> {
            noSaveHistory();
            new HomeGUI();
        });
    }

    // MODIFIES: this
    // EFFECTS: saves history for this library
    public void saveHistory() {
        try {
            save();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // MODIFIES: this
    // EFFECTS: doesn't save history for this library
    public void noSaveHistory() {
        try {
            noSave();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // MODIFIES: this
    // EFFECTS: synchronizes librariesTemp.json and libraries.json and closes the frame
    private void save() throws IOException {
        File source = new File("./data/libraries.json");
        File toFile = new File("./data/librariesTemp.json");

        BufferedReader sourceReader = new BufferedReader(new FileReader(source));
        StringBuilder sourceJsonStringBuilder = new StringBuilder();
        String line;
        while ((line = sourceReader.readLine()) != null) {
            sourceJsonStringBuilder.append(line);
        }
        sourceReader.close();

        JSONObject sourceJsonObject = new JSONObject(sourceJsonStringBuilder.toString());

        FileWriter destinationWriter = new FileWriter(toFile);
        destinationWriter.write(sourceJsonObject.toString());
        destinationWriter.close();

        frameClosing.dispose();
        frame.dispose();
    }

    // MODIFIES: this
    // EFFECTS: removes the changes to jsonReader and closes the frame
    private void noSave() throws IOException {
        File source = new File("./data/librariesTemp.json");
        File toFile = new File("./data/libraries.json");

        BufferedReader sourceReader = new BufferedReader(new FileReader(source));
        StringBuilder sourceJsonStringBuilder = new StringBuilder();
        String line;
        while ((line = sourceReader.readLine()) != null) {
            sourceJsonStringBuilder.append(line);
        }
        sourceReader.close();

        JSONObject sourceJsonObject = new JSONObject(sourceJsonStringBuilder.toString());

        FileWriter destinationWriter = new FileWriter(toFile);
        destinationWriter.write(sourceJsonObject.toString());
        destinationWriter.close();

        frameClosing.dispose();
        frame.dispose();
    }

    // MODIFIES: this
    // EFFECTS: sets left, right panels as well as their children panels
    private void setPanel() {
        setLeftPanel();
        setRightPanel();
        setDescriptionPanel();
        setPromptPanel();
        setAddCommentsLabel();
    }

    // MODIFIES: this
    // EFFECTS: sets the panel representing left half of the screen
    private void setLeftPanel() {
        panelL = new JPanel();
        panelL.setLayout(new BorderLayout());
        panelL.setPreferredSize(new Dimension(550, 530));
        panelL.setMinimumSize(new Dimension(550, 530));
        panelL.setBorder(BorderFactory.createEmptyBorder(5, 0,0,0));
        panelL.setBackground(new Color(59, 229, 234));
    }

    // MODIFIES: this
    // EFFECTS: sets the panel representing right half of the screen with gif and logos
    private void setRightPanel() {
        panelR = new JPanel();
        panelR.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelR.setBackground(new Color(242, 245, 196));

        panelR.setPreferredSize(new Dimension(550, 500));

        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("image/vancouver-vancouver-marina.gif");
        Image image = imageIcon.getImage().getScaledInstance(550, 430,
                Image.SCALE_DEFAULT);
        ImageIcon inserted = new ImageIcon(image);


        imageLabel.setIcon(inserted);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelR.add(imageLabel, BorderLayout.CENTER);

        ubcLogo = new JLabel();
        ImageIcon imageIcon2 = new ImageIcon("image/UBC-Logo.png");
        Image image2 = imageIcon2.getImage().getScaledInstance(300, 50,
                Image.SCALE_SMOOTH);
        ImageIcon inserted2 = new ImageIcon(image2);
        ubcLogo.setIcon(inserted2);
        ubcLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ubcLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelR.add(ubcLogo, BorderLayout.SOUTH);
        panelR.setMinimumSize(new Dimension(550, 500));
    }

    // MODIFIES: this
    // EFFECTS: sets the scroll pane to the right half of the screen
    private void setScrollPane() {
        scrollPaneR = new JScrollPane(panelR);
        scrollPaneR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollBar verticalScrollBar = scrollPaneR.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(8);
        frame.add(scrollPaneR);

        setAdapter();

        scrollPaneR.addComponentListener(adapter);
    }

    // MODIFIES: this
    // EFFECTS: a helper method for setScrollPane() maintaining the layout and interactive size of components in
    //          the right panel when the window resizes
    private void setAdapter() {
        adapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension scrollPaneSize = scrollPaneR.getSize();

                panelR.removeAll();

                ImageIcon imageIcon = new ImageIcon("image/vancouver-vancouver-marina.gif");
                Image image = imageIcon.getImage().getScaledInstance(scrollPaneSize.width - 20,
                        scrollPaneSize.height - 50 - 20, Image.SCALE_DEFAULT);
                ImageIcon inserted = new ImageIcon(image);


                imageLabel.setIcon(inserted);

                panelR.setPreferredSize(new Dimension(scrollPaneSize.width - 20, scrollPaneSize.height - 20));
                panelR.add(imageLabel, BorderLayout.CENTER);


                ImageIcon imageIcon2 = new ImageIcon("image/UBC-Logo.png");
                Image image2 = imageIcon2.getImage().getScaledInstance(300, 50, Image.SCALE_SMOOTH);
                ImageIcon inserted2 = new ImageIcon(image2);
                ubcLogo.setIcon(inserted2);
                panelR.add(ubcLogo, BorderLayout.SOUTH);
                panelR.setMinimumSize(new Dimension(550, 510));

                panelR.revalidate();
                panelR.repaint();
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: sets the panel wrapping around the title, image and description of the library
    private void setDescriptionPanel() {
        titleAndDescriptionPanel = new JPanel();
        titleAndDescriptionPanel.setLayout(null);
        titleAndDescriptionPanel.setBackground(new Color(59, 229, 234));
        titleAndDescriptionPanel.setPreferredSize(new Dimension(550, 310));
        titleAndDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panelL.add(titleAndDescriptionPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up text description inside titleAndDescriptionPanel
    private void setTextDescription() {
        JLabel label = new JLabel();
        label.setText(descriptionText);
        label.setPreferredSize(new Dimension(220, 50)); // to avoid overflow

        Font fantasyFont = new Font("Serif", Font.PLAIN, 14);
        label.setFont(fantasyFont);

        JPanel panelForLibDescription = new JPanel(new BorderLayout());
        panelForLibDescription.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelForLibDescription.add(label, BorderLayout.LINE_START);
        panelForLibDescription.setBounds(250, 95, 250, 180);
        panelForLibDescription.setBackground(new Color(132, 204, 250));
        titleAndDescriptionPanel.add(panelForLibDescription);
    }

    // MODIFIES: this
    // EFFECTS: sets the panel containing buttons "Add" and "Remove"
    private void setPromptPanel() {
        promptPanel = new JPanel();
        promptPanel.setBackground(new Color(242, 245, 196));
        promptPanel.setLayout(new BorderLayout());
        promptPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        panelL.add(promptPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets the rating displayed according to what is saved in jsonReader
    private void setRatingDisplayed() {
        try {
            JLabel libraryTitle = new JLabel(title);
            loadedInfo = jsonReader.read();
            JLabel rating = new JLabel("-- " + loadedInfo.getLibraries().get(libraryNum)
                    .getRatingDisplayed() + " / 5.0");
            libraryTitle.setSize(new Dimension(550, 40));
            libraryTitle.setHorizontalAlignment(SwingConstants.CENTER);
            rating.setBounds(450, 60, 100, 20);

            // Set the font to bold
            Font boldFont = new Font(libraryTitle.getFont().getName(), Font.BOLD, 25);
            libraryTitle.setFont(boldFont);

            // Set font for rating to Comic Sans MS
            Font fantasyFont = new Font("Comic Sans MS", Font.PLAIN, 16);
            rating.setFont(fantasyFont);

            titleAndDescriptionPanel.add(libraryTitle, BorderLayout.SOUTH);
            titleAndDescriptionPanel.add(rating);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the subtitle panel containing "Click here to view" button
    private void setSubtitlePanel() {
        subTitlePanel = new JPanel();
        subTitlePanel.setBackground(new Color(96, 243, 231, 229));
        subTitlePanel.setBounds(50, 230, IMAGE_WIDTH, 50);
        subTitlePanel.setLayout(new BorderLayout());
        titleAndDescriptionPanel.add(subTitlePanel);
    }

    // MODIFIES: this
    // EFFECTS: displays the image with smooth scaling
    private void setImageDisplayed() {
        JLabel imageLabel = new JLabel();

        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        imageLabel.setIcon(inserted);
        imageLabel.setBounds(50, 60, IMAGE_WIDTH, 200);

        titleAndDescriptionPanel.add(imageLabel);
    }

    // MODIFIES: this
    // EFFECTS: links to jsonReader to keep track of numbers of comments made so far for this library
    public void setNumberOfComments() {
        textLabel = new JLabel();
        String concat = ""
                + getNumberOfComments()
                + " comments in history";

        textLabel.setText(concat);

        Font font = new Font("SansSerif", Font.BOLD, 12);
        textLabel.setFont(font);
        textLabel.setBounds(30, 200, IMAGE_WIDTH, 30);
        textLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subTitlePanel.add(textLabel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up the "Click here to view" Button and adds an event listener for switching to a screen
    //          with comments only
    private void setClickHereToViewButton() {
        clickHereToView = new JButton("Click here to view");

        clickHereToView.setHorizontalAlignment(SwingConstants.CENTER);
        subTitlePanel.add(clickHereToView, BorderLayout.SOUTH);

        clickHereToView.addActionListener(this);
    }


    // MODIFIES: this
    // EFFECTS: adds a split screen to split left and right panels up
    private void setSplitScreen() {
        sl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelL, scrollPaneR);
        sl.setDividerLocation(0.5);
        sl.setEnabled(false);
        frame.add(sl);

    }

    // EFFECTS: returns number of comments for this library from json
    private int getNumberOfComments() {
        System loadedInfo;
        try {
            loadedInfo = jsonReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedInfo.getLibraries().get(libraryNum).getListOfComments().getComments().size();
    }

    // MODIFIES: this
    // EFFECTS: gets comments and display them on the right-hand screen;
    //          mouse event highlights every comment of our choice.
    private void retrieveComments(System loadedInfo) {
        for (int i = 0; i < getNumberOfComments(); i++) {
            String ratingMessage = "<html>" + loadedInfo.getLibraries().get(libraryNum)
                    .getListOfComments().getComments().get(i).toString() + "</html>";

            JPanel commentPanel = new JPanel(new BorderLayout());
            setCommentPanel(commentPanel);

            JLabel label = new JLabel(ratingMessage);
            label.setPreferredSize(new Dimension(500, 60));

            commentPanel.add(label);
            panelR.add(commentPanel);
            trackCommentY = trackCommentY + 60 + COMMENT_GAP;
            commentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    commentPanel.setBackground(new Color(95, 98, 200, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    commentPanel.setBackground(new Color(125, 149, 180));
                }
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the comment panel
    private void setCommentPanel(JPanel panel) {
        panel.setBackground(new Color(125, 149, 180));
        panel.setBounds(10, trackCommentY, 500, 60);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    // MODIFIES: this
    // EFFECTS: sets labels for AddComment section (PromptPanel)
    private void setAddCommentsLabel() {
        JLabel description = new JLabel("<html><h3>Wonder to give insights to new students about this library?"
                + "<br></h3> We welcome you to"
                + " leave your footprint by adding your comments to share your unique voices!<br>"
                + " Click on <b>Add</b> to add your comments or <b>Remove</b> to remove "
                + "it and follow the prompt. </html>");
        addOrRemovePanel = new JPanel(new GridLayout(1, 2));
        addOrRemovePanel.setBackground(new Color(242, 245, 196));

        description.setPreferredSize(new Dimension(30, 120));
        description.setBorder(BorderFactory.createEmptyBorder(1, 8, 2, 2));
        promptPanel.add(description, BorderLayout.NORTH);
        promptPanel.add(addOrRemovePanel, BorderLayout.CENTER);
        setAddAndRemovePanel();
    }

    // MODIFIES: this
    // EFFECTS: sets Add and Remove buttons and panels that wraps these buttons
    private void setAddAndRemovePanel() {
        buttonAdd = new JButton("Add");
        buttonRemove = new JButton("Remove");
        buttonAdd.setForeground(new Color(28, 96, 43));
        buttonRemove.setForeground(new Color(28, 96, 43));
        addOrRemovePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        addOrRemovePanel.add(buttonAdd);
        addOrRemovePanel.add(buttonRemove);

        buttonAdd.addActionListener(e -> {
            addingWindowGUI = new AddingWindowGUI(libraryNum, this, imagePath);
            addingWindowGUI.setUpWindow();
        });

        buttonRemove.addActionListener(e -> {
            removingWindowGUI = new RemovingWindowGUI(libraryNum, this, imagePath);
            removingWindowGUI.setUpWindow();
        });
    }

    // MODIFIES: this
    // EFFECTS: switch screen from gif and logo to a screen with only comments; also
    //          disables the "Click here to view" button
    @Override
    public void actionPerformed(ActionEvent e) {
        addLoad();
    }

    // MODIFIES: this
    // EFFECTS: adds and loads the changes or no
    public void addLoad() {
        show();
    }

    // MODIFIES: this
    // EFFECTS: shows comments
    public void show() {
        scrollPaneR.removeComponentListener(adapter);
        System loadedInfo;
        try {
            loadedInfo = jsonReader.read();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        panelR.remove(imageLabel);
        panelR.remove(ubcLogo);
        panelR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        retrieveComments(loadedInfo);
        panelL.setMinimumSize(new Dimension(550, 510));
        panelR.setPreferredSize(new Dimension(550, trackCommentY - 20 - COMMENT_GAP));
        clickHereToView.setEnabled(false);

    }

    // MODIFIES: this
    // EFFECTS: sets the return icon
    public void setReturnIcon() {
        JPanel container = new JPanel();
        container.setBounds(45,55,30,30);

        homeIcon = new JLabel();
        ImageIcon imageIcon = new ImageIcon("image/home-icon.png");
        Image image = imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);
        homeIcon.setIcon(inserted);

        container.add(homeIcon);
        container.setBackground(new Color(59, 229, 234));
        frame.add(container);

        setMouseEventForReturningHomePage(container);
        homeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // EFFECTS: activates the homeIcon
    private void setMouseEventForReturningHomePage(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeAndDirectHome();
            }
        });
    }

    // EFFECTS: gets the parent frame
    public static JFrame getFrame() {
        return frame;
    }

    // EFFECTS: gets the title of this library
    public String getTitle() {
        return title;
    }

    // EFFECTS; gets the id of this library
    public int getLibraryNum() {
        return libraryNum;
    }

    // EFFECTS: gets the description text for this library
    public String getDescriptionText() {
        return descriptionText;
    }


}