package ui;

import javax.swing.*;
import java.awt.*;

// Represents a Gradiant Background created by 2D graphics

public class GradientPanel extends JPanel {
    public GradientPanel() {
        setBackground(Color.WHITE); // Default Background
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D object
        Graphics2D g2d = (Graphics2D) g;

        // Define the gradient colors and their positions
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(255, 110, 127), 0, getHeight(), new Color(191, 233, 255));

        // Set the gradient as the paint for the background
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
