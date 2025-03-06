package Roulette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class RouletteView extends JPanel {
    private static final Map<Integer, String> NUMBER_COLORS = new HashMap<>();

    static {                                                                                                                                                                                                                                                                                                                // A ku ku, tu cie mam XDD   po co tak daleko patrztsz      A chuj cie to !!!!! co to znaczy, działa działa i w czym problem <33
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int i = 1; i <= 36; i++) {
            NUMBER_COLORS.put(i, "BLACK");
        }
        for (int red : redNumbers) {
            NUMBER_COLORS.put(red, "RED");
        }
        NUMBER_COLORS.put(0, "GREEN");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RouletteView::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Add zero on the left, occupying the height of 3 rows
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel zeroLabel = createLabel("0", "GREEN");
        tablePanel.add(zeroLabel, gbc);

        // Reset gridheight for the following elements
        gbc.gridheight = 1;

        // Add numbers 1-36 in 3 rows and 12 columns
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 12; col++) {
                int number = col * 3 + (3 - row); // Calculate the roulette number
                gbc.gridx = col + 1;
                gbc.gridy = row;
                JLabel label = createLabel(String.valueOf(number), NUMBER_COLORS.get(number));
                tablePanel.add(label, gbc);
            }
        }

        // Add column for betting on rows
        for (int i = 0; i < 3; i++) {
            gbc.gridx = 13;
            gbc.gridy = i;
            JLabel rowBetLabel = createLabel("Row " + (i + 1), "GRAY");
            tablePanel.add(rowBetLabel, gbc);
        }

        // Panel for betting on dozens, color, half, and even/odd
        JPanel bottomPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        bottomPanel.setBackground(Color.DARK_GRAY);

        bottomPanel.add(createLabel("1st 12", "GRAY"));
        bottomPanel.add(createLabel("2nd 12", "GRAY"));
        bottomPanel.add(createLabel("3rd 12", "GRAY"));
        bottomPanel.add(createLabel("1-18", "GRAY"));
        bottomPanel.add(createLabel("EVEN", "GRAY"));
        bottomPanel.add(createLabel("RED", "RED"));
        bottomPanel.add(createLabel("BLACK", "BLACK"));

        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JLabel createLabel(String text, String color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(50, 50));
        label.setForeground(Color.WHITE);

        switch (color) {
            case "RED" -> label.setBackground(Color.RED);
            case "BLACK" -> label.setBackground(Color.BLACK);
            case "GREEN" -> label.setBackground(Color.GREEN);
            default -> label.setBackground(Color.GRAY);
        }

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Bet on: " + label.getText());
            }
        });
        return label;
    }
}
