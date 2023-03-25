package ui;

import javax.swing.*;
import java.awt.*;

public class TextOverflowLearning {
    public TextOverflowLearning() {
        JFrame frame = new JFrame();
        frame.setLayout(null);

        frame.setSize(1100, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.gray);
        panel.setBounds(10, 10, 200, 500);

        JLabel label = new JLabel("<html>Some long text that may overflow and that's correct long long long</html>");
        label.setPreferredSize(new Dimension(200, 100));
        label.setBackground(Color.GRAY);

        panel.add(label);
        frame.add(panel);

        frame.setPreferredSize(new Dimension(220, 70));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TextOverflowLearning();
    }
}
