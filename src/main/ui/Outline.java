package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Outline implements ActionListener {
    private JFrame frame; // top-level container
    private JPanel panel; // light-weight container
    private JButton button;
    private JLabel label;
    private int count = 0;

    public Outline() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.button = new JButton("Click me");
        this.label = new JLabel("Number of clicks: 0");

        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Rate My Classroom");

        addBackgroundToFrame();

        // adding elements into frame
        setRightPanel();
        frame.add(setTop(), BorderLayout.NORTH);
        frame.add(panel, BorderLayout.LINE_END);
        addImageToFrame();

        frame.setVisible(true);
        button.addActionListener(this);
    }

    // Panel uses BoxLayout
    public void setRightPanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(button);
        panel.add(label);
//        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    // Title
    public JLabel setTop() {
        JLabel label = new JLabel();
        label.setText("Rate My UBC Libraries");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.darkGray);
        return label;
    }

    // need to create a panel to contain that image
    public void addImageToFrame() {
        // create a JPanel component
        JPanel panelLeft = new JPanel();

        // create a new JLabel component
        JLabel imageLabel = new JLabel();

        // create an ImageIcon from a file path or URL
        ImageIcon imageIcon = new ImageIcon("./image/Ikblearningcentre.jpg");
        Image image = imageIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);

        ImageIcon inserted = new ImageIcon(image);

        // set the imageIcon as the label's icon
        imageLabel.setIcon(inserted);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // set layout for panelLeft
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.X_AXIS));
        panelLeft.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        // add widgets to the panel Left
        panelLeft.add(imageLabel);
        JLabel textLabel = new JLabel("Hello World");
        panelLeft.add(textLabel);

        // add panelLeft to the frame
        frame.add(panelLeft, BorderLayout.LINE_START);
    }

    // Set the background
    public void addBackgroundToFrame() {
        frame.getContentPane().setBackground(new Color(238, 255, 238));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
    }
}
