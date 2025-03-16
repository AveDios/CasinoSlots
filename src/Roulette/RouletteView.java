package Roulette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class RouletteView extends JPanel {
    private static final Map<Integer, String> NUMBER_COLORS = new HashMap<>();
    private static double betValue = 0; // Zmienna do przechowywania wartości zakładu

    static {
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int i = 1; i <= 36; i++) {
            NUMBER_COLORS.put(i, "BLACK");
        }
        for (int red : redNumbers) {
            NUMBER_COLORS.put(red, "RED");
        }
        NUMBER_COLORS.put(0, "GREEN");
    }

    public RouletteView() {
        JFrame frame = new JFrame("Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Zero na lewo, zajmujące wysokość 3 rzędów
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel zeroLabel = createLabel("0", "GREEN");
        tablePanel.add(zeroLabel, gbc);
        gbc.gridheight = 1;

        // Numery 1-36 w 3 rzędach po 12 kolumn
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 12; col++) {
                int number = col * 3 + (3 - row);
                gbc.gridx = col + 1;
                gbc.gridy = row;
                JLabel label = createLabel(String.valueOf(number), NUMBER_COLORS.get(number));
                tablePanel.add(label, gbc);
            }
        }

        // Kolumna do obstawiania wierszy
        for (int i = 0; i < 3; i++) {
            gbc.gridx = 13; // Ustaw kolumnę na 13
            gbc.gridy = i; // Ustaw wiersz
            JLabel rowBetLabel = createLabel((i + 1) + "st row", "GRAY"); // Zmiana etykiety
            if (i == 1) {
                rowBetLabel.setText("2nd row"); // Dla drugiego wiersza
            } else if (i == 2) {
                rowBetLabel.setText("3rd row"); // Dla trzeciego wiersza
            }
            tablePanel.add(rowBetLabel, gbc);
        }

        // Panel dolny z opcjami zakładów
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 52, 0, 52)); // Przesunięcie o szerokość pola "0"

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Zakłady tuzinowe (3 pola, każde zajmuje 4 kolumny, żeby zmieściło się w 12 kolumnach)
        gbc.gridwidth = 4;
        gbc.gridy = 0;
        gbc.gridx = 0;
        bottomPanel.add(createLabel("1st 12", "GRAY"), gbc);
        gbc.gridx = 4;
        bottomPanel.add(createLabel("2nd 12", "GRAY"), gbc);
        gbc.gridx = 8;
        bottomPanel.add(createLabel("3rd 12", "GRAY"), gbc);

        // Zakłady dolne (6 pól, każde zajmuje 2 kolumny, żeby zmieściło się w 12 kolumnach)
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        gbc.gridx = 0;
        bottomPanel.add(createLabel("1-18", "GRAY"), gbc);
        gbc.gridx = 2;
        bottomPanel.add(createLabel("EVEN", "GRAY"), gbc);
        gbc.gridx = 4;
        bottomPanel.add(createLabel("RED", "RED"), gbc);
        gbc.gridx = 6;
        bottomPanel.add(createLabel("BLACK", "BLACK"), gbc);
        gbc.gridx = 8;
        bottomPanel.add(createLabel("ODD", "GRAY"), gbc);
        gbc.gridx = 10;
        bottomPanel.add(createLabel("19-36", "GRAY"), gbc);

        // Dodanie etykiety "Spin" i pola do wpisania wartości zakładu
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        bottomPanel.add(createLabel("Spin", "GRAY"), gbc);

        JTextField betValueField = new JTextField(10);
        gbc.gridx = 1;
        bottomPanel.add(betValueField, gbc);

        // Dodanie MouseListener na label "Spin"
        JLabel spinLabel = createLabel("Spin", "GRAY");
        spinLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Odczytujemy wartość z pola tekstowego i zapisujemy do zmiennej
                    betValue = Double.parseDouble(betValueField.getText());
                    System.out.println("Bet value set to: " + betValue);
                } catch (NumberFormatException ex) {
                    // Obsługa błędu, jeśli użytkownik wprowadził coś niepoprawnego
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number for the bet value.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 2;
        bottomPanel.add(spinLabel, gbc);

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
