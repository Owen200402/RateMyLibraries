package ui;

import model.System;
import persistance.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// GUI Class Representing the Rate My Libraries
public class Home implements ActionListener {
    // Constants:
    private static final int IMAGE_WIDTH = 180;
    private static final int COMMENT_GAP = 20;
    private static final JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private static int trackCommentY = 20;

    // Components:
    private static System loadedInfo;
    private static JFrame frame;
    private static AddingWindow addingWindow;
    private static RemovingWindow removingWindow;
    private static JPanel panelL;
    private static JPanel panelR;
    private static JPanel addOrRemovePanel;
    private static JPanel titleAndDescriptionPanel;
    private static JPanel subTitlePanel;
    private static JPanel promptPanel;
    private static JLabel imageLabel;
    private static JLabel ubcLogo;
    private static JLabel textLabel;
    private static JButton clickHereToView;
    private static JButton buttonAdd;
    private static JButton buttonRemove;
    private static JScrollPane scrollPaneR;
    private static JSplitPane sl;
    private static ComponentAdapter adapter;

    // EFFECTS: sets the main display screen with components added into the parent frame window
    //         that users can interact with
    public Home() {
        setFrame();
        setPanel();
        setScrollPane();
        setRatingDisplayed();
        setImageDisplayed();
        setTextDescription();
        setSubtitlePanel();
        setNumberOfComments();
        setSplitScreen();
        setClickHereToViewButton();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the frame (pop-up window) with minimum size so that users cannot shrink it
    private void setFrame() {
        frame = new JFrame();

        frame.setSize(1140, 500); // 20px Border everywhere
        frame.setMaximumSize(new Dimension(1140, 500));
        frame.setMinimumSize(new Dimension(1000, 450));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Rate My UBC Libraries");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        panelL.setPreferredSize(new Dimension(550, 500));
        panelL.setMinimumSize(new Dimension(550, 500));
    }

    // MODIFIES: this
    // EFFECTS: sets the panel representing right half of the screen with gif and logos
    private void setRightPanel() {
        panelR = new JPanel();
        panelR.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelR.setBackground(Color.GRAY);

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
                panelR.setMinimumSize(new Dimension(550, 500));

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
        titleAndDescriptionPanel.setBackground(Color.pink);
        titleAndDescriptionPanel.setPreferredSize(new Dimension(500, 300));
        titleAndDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelL.add(titleAndDescriptionPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up text description inside titleAndDescriptionPanel
    private void setTextDescription() {
        JLabel label = new JLabel();
        label.setText(
                "<html><u>Description</u>: <br>"
                        + "The gigantic tower well-known for its gorgeous looking "
                        + "and the packed studying areas with great hardware. It is "
                        + "one of the libraries that have the most books stored!</html>");
        label.setPreferredSize(new Dimension(220, 50)); // to avoid overflow

        Font fantasyFont = new Font("Comic Sans MS", Font.PLAIN, 14);
        label.setFont(fantasyFont);

        JPanel panelForLibDescription = new JPanel(new BorderLayout());
        panelForLibDescription.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelForLibDescription.add(label, BorderLayout.LINE_START);
        panelForLibDescription.setBounds(220, 75, 250, 180);
        panelForLibDescription.setBackground(Color.yellow);
        titleAndDescriptionPanel.add(panelForLibDescription);
    }

    // MODIFIES: this
    // EFFECTS: sets the panel containing buttons "Add" and "Remove"
    private void setPromptPanel() {
        promptPanel = new JPanel();
        promptPanel.setBackground(Color.ORANGE);
        promptPanel.setLayout(new BorderLayout());
        promptPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        panelL.add(promptPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets the rating displayed according to what is saved in jsonReader
    private void setRatingDisplayed() {
        try {
            JLabel library = new JLabel("Irving K Barber Learning Centre");
            loadedInfo = jsonReader.read();
            JLabel rating = new JLabel("-- " + loadedInfo.getLibraries().get(1).getRatingDisplayed() + " / 5.0");
            library.setBounds(30, 20, 450, 40);
            rating.setBounds(450, 50, 100, 20);

            // Set the font to bold
            Font boldFont = new Font(library.getFont().getName(), Font.BOLD, 25);
            library.setFont(boldFont);

            // Set font for rating to Comic Sans MS
            Font fantasyFont = new Font("Comic Sans MS", Font.PLAIN, 15);
            rating.setFont(fantasyFont);

            titleAndDescriptionPanel.add(library);
            titleAndDescriptionPanel.add(rating);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the subtitle panel containing "Click here to view" button
    private void setSubtitlePanel() {
        subTitlePanel = new JPanel();
        subTitlePanel.setBackground(Color.cyan);
        subTitlePanel.setBounds(30, 210, IMAGE_WIDTH, 50);
        subTitlePanel.setLayout(new BorderLayout());
        titleAndDescriptionPanel.add(subTitlePanel);
    }

    // MODIFIES: this
    // EFFECTS: displays the image with smooth scaling
    private void setImageDisplayed() {
        // create a new JLabel component
        JLabel imageLabel = new JLabel();

        // create an ImageIcon from a file path or URL
        ImageIcon imageIcon = new ImageIcon("image/Ikblearningcentre.jpg");
        Image image = imageIcon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        // set the imageIcon as the label's icon
        imageLabel.setIcon(inserted);
        imageLabel.setBounds(30, 40, IMAGE_WIDTH, 200);

        // add panelLeft to the frame
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

        Font font = new Font("Roboto", Font.BOLD, 12);
        textLabel.setFont(font);
        textLabel.setBounds(30, 200, IMAGE_WIDTH, 30);
        textLabel.setBorder(BorderFactory.createSoftBevelBorder(1));
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
        return loadedInfo.getLibraries().get(1).getListOfComments().getComments().size();
    }

    // MODIFIES: this
    // EFFECTS: gets comments and display them on the right-hand screen;
    //          mouse event highlights every comment of our choice.
    private void retrieveComments(System loadedInfo) {
        for (int i = 0; i < getNumberOfComments(); i++) {
            String ratingMessage = "<html>"
                    + loadedInfo.getLibraries().get(1).getListOfComments().getComments().get(i).toString() + "</html>";
            JPanel commentPanel = new JPanel(new BorderLayout());
            commentPanel.setBackground(Color.cyan);
            commentPanel.setBounds(10, trackCommentY, 500, 60);
            commentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel label = new JLabel(ratingMessage);
            label.setPreferredSize(new Dimension(500, 60));


            commentPanel.add(label);
            panelR.add(commentPanel);
            trackCommentY = trackCommentY + 60 + COMMENT_GAP;
            commentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    commentPanel.setBackground(Color.YELLOW);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    commentPanel.setBackground(Color.cyan);
                }
            });
        }
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
        addOrRemovePanel.setBackground(Color.ORANGE);

        description.setPreferredSize(new Dimension(30, 90));
        description.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        promptPanel.add(description, BorderLayout.NORTH);
        promptPanel.add(addOrRemovePanel, BorderLayout.CENTER);
        setAddAndRemovePanel();
    }

    // MODIFIES: this
    // EFFECTS: sets Add and Remove buttons and panels that wraps these buttons
    private void setAddAndRemovePanel() {
        buttonAdd = new JButton("Add");
        buttonRemove = new JButton("Remove");
        buttonAdd.setForeground(Color.magenta);
        buttonRemove.setForeground(Color.magenta);
        addOrRemovePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        addOrRemovePanel.add(buttonAdd);
        addOrRemovePanel.add(buttonRemove);

        buttonAdd.addActionListener(e -> {
            addingWindow = new AddingWindow();
            addingWindow.setUpWindow();
        });

        buttonRemove.addActionListener(e -> {
            removingWindow = new RemovingWindow();
            removingWindow.setUpWindow();
        });
    }

    // MODIFIES: this
    // EFFECTS: switch screen from gif and logo to a screen with only comments; also
    //          disables the "Click here to view" button
    @Override
    public void actionPerformed(ActionEvent e) {
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
        // Add everything for this library
        retrieveComments(loadedInfo);

        sl.setDividerLocation(0.5);
        panelL.setMinimumSize(new Dimension(550, 500));
        panelR.setPreferredSize(new Dimension(550, trackCommentY - 20 - COMMENT_GAP));
        clickHereToView.setEnabled(false);
    }

    // EFFECTS: gets the parent frame
    public static JFrame getFrame() {
        return frame;
    }
}