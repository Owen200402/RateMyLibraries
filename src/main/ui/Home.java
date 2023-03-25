package ui;

import model.System;
import persistance.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Home implements ActionListener {
    // Constants:
    private static final int IMAGE_WIDTH = 180;
    private static final int COMMENT_GAP = 20;
    private final JsonReader jsonReader = new JsonReader("./data/libraries.json");
    private int trackCommentY = 20;
    private JFrame frame;
    private JPanel panelL;
    private JPanel panelR;
    private JScrollPane scrollPaneR;
    private JPanel titleAndDescriptionPanel;
    private JPanel subTitlePanel;
    private JPanel promptPanel;
    private JLabel imageLabel;
    private JButton clickHereToView;
    private JSplitPane sl;

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

    private void setFrame() {
        frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(1140, 500); // 20px Border everywhere
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setPanel() {
        setLeftPanel();
        setRightPanel();
        setDescriptionPanel();
        setPromptPanel();
    }

    private void setLeftPanel() {
        panelL = new JPanel();
        panelL.setLayout(new BorderLayout());
        panelL.setPreferredSize(new Dimension(550, 500));
        panelL.setMinimumSize(new Dimension(550, 500));
    }

    private void setRightPanel() {
        panelR = new JPanel();
        panelR.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelR.setBackground(Color.GRAY);

        panelR.setPreferredSize(new Dimension(550, 500));

        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./image/vancouver-vancouver-marina.gif");

        Image image = imageIcon.getImage().getScaledInstance(550, 400,
                Image.SCALE_DEFAULT);
        ImageIcon inserted = new ImageIcon(image);

        imageLabel.setIcon(inserted);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelR.add(imageLabel, BorderLayout.CENTER);
    }

    private void setScrollPane() {
        scrollPaneR = new JScrollPane(panelR);
        scrollPaneR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPaneR);
    }

    private void setDescriptionPanel() {
        titleAndDescriptionPanel = new JPanel();
        titleAndDescriptionPanel.setLayout(null);
        titleAndDescriptionPanel.setBackground(Color.pink);
        titleAndDescriptionPanel.setPreferredSize(new Dimension(500, 300));
        titleAndDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelL.add(titleAndDescriptionPanel, BorderLayout.NORTH);
    }

    private void setPromptPanel() {
        promptPanel = new JPanel();
        promptPanel.setBackground(Color.ORANGE);
        promptPanel.setLayout(new GridLayout());
        panelL.add(promptPanel, BorderLayout.CENTER);
    }

    private void setRatingDisplayed() {
        try {
            JLabel library = new JLabel("Irving K Barber Learning Centre");
            System loadedInfo = jsonReader.read();
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

    private void setSubtitlePanel() {
        subTitlePanel = new JPanel();
        subTitlePanel.setBackground(Color.cyan);
        subTitlePanel.setBounds(30, 210, IMAGE_WIDTH, 50);
        subTitlePanel.setLayout(new BorderLayout());
        titleAndDescriptionPanel.add(subTitlePanel);
    }

    private void setImageDisplayed() {
        // create a new JLabel component
        JLabel imageLabel = new JLabel();

        // create an ImageIcon from a file path or URL
        ImageIcon imageIcon = new ImageIcon("./image/Ikblearningcentre.jpg");
        Image image = imageIcon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        // set the imageIcon as the label's icon
        imageLabel.setIcon(inserted);
        imageLabel.setBounds(30, 40, IMAGE_WIDTH, 200);

        // add panelLeft to the frame
        titleAndDescriptionPanel.add(imageLabel);
    }

    public void setNumberOfComments() {
        JLabel textLabel = new JLabel();
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

    private void setClickHereToViewButton() {
        clickHereToView = new JButton("Click here to view");
        clickHereToView.setHorizontalAlignment(SwingConstants.CENTER);
        subTitlePanel.add(clickHereToView, BorderLayout.SOUTH);

        clickHereToView.addActionListener(this);
    }

    private void setTextDescription() {
        JLabel label = new JLabel();
        label.setText(
                "<html><u>Description</u>: <br>"
                        + "The gigantic tower well-known for its gorgeous looking"
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


    private void setSplitScreen() {
        sl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelL, scrollPaneR);
        sl.setDividerLocation(0.5);
        sl.setDividerSize(8);
        frame.add(sl);
    }

    private int getNumberOfComments() {
        System loadedInfo;
        try {
            loadedInfo = jsonReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedInfo.getLibraries().get(1).getListOfComments().getComments().size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System loadedInfo;
        try {
            loadedInfo = jsonReader.read();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        panelR.remove(imageLabel);
        panelR.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Add everything for this library
        retrieveComments(loadedInfo);

        sl.setDividerLocation(0.5);
        panelL.setMinimumSize(new Dimension(550, 500));
        panelR.setPreferredSize(new Dimension(550, trackCommentY - 20 - COMMENT_GAP));
    }

    private void retrieveComments(System loadedInfo) {
        for (int i = 0; i < getNumberOfComments(); i++) {
            String ratingMessage = "<html>"
                    + loadedInfo.getLibraries().get(1).getListOfComments().getComments().get(i).toString()
                    + "</html>";
            JPanel commentPanel = new JPanel(new BorderLayout());
            commentPanel.setBackground(Color.cyan);
            commentPanel.setBounds(10, trackCommentY, 500, 60);
            commentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel label = new JLabel(ratingMessage);
            label.setPreferredSize(new Dimension(500, 60));

            commentPanel.add(label);
            panelR.add(commentPanel);
            trackCommentY = trackCommentY + 60 + COMMENT_GAP;
        }
    }
}