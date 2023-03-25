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
    private JFrame frame;
    private JPanel panelL;
    private JPanel panelR;
    private JScrollPane scrollPaneR;
    private JPanel titleAndDescriptionPanel;
    private JPanel subTitlePanel;
    private JPanel promptPanel;
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
        frame.setSize(1100, 500);
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
    }

    private void setRightPanel() {
        panelR = new JPanel();
        panelR.setLayout(null);
        panelR.setBackground(Color.GRAY);
        panelR.setPreferredSize(new Dimension(500, 500));
    }

    private void setScrollPane() {
        scrollPaneR = new JScrollPane(panelR);
        scrollPaneR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
        sl.setDividerSize(10);
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

        int trackCommentY = 10;

        // Add everything for this library
        for (int i = 0; i < getNumberOfComments(); i++) {
            String ratingMessage = "<html>"
                    + loadedInfo.getLibraries().get(1).getListOfComments().getComments().get(i).toString()
                    + "</html>";
            JPanel ratingPanel = new JPanel(new BorderLayout());
            ratingPanel.setBackground(Color.cyan);
            ratingPanel.setBounds(10, trackCommentY, 500, 80);
            ratingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel label = new JLabel(ratingMessage);
            label.setPreferredSize(new Dimension(500, 80));

            ratingPanel.add(label);
            panelR.add(ratingPanel);
            trackCommentY = trackCommentY + 80 + COMMENT_GAP;
        }

        // Adding multiple Comments?
        for (int i = 0; i < getNumberOfComments(); i++) {
            String ratingMessage = "<html>"
                    + loadedInfo.getLibraries().get(1).getListOfComments().getComments().get(i).toString()
                    + "</html>";
            JPanel ratingPanel = new JPanel(new BorderLayout());
            ratingPanel.setBackground(Color.cyan);
            ratingPanel.setBounds(10, trackCommentY, 500, 80);
            ratingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel label = new JLabel(ratingMessage);
            label.setPreferredSize(new Dimension(500, 80));

            ratingPanel.add(label);
            panelR.add(ratingPanel);
            trackCommentY = trackCommentY + 80 + COMMENT_GAP;
        }

        sl.setDividerLocation(0.5);
    }
}