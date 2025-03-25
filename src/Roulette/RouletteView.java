package Roulette;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class RouletteView extends JPanel {
    private static final Map<Integer, String> NUMBER_COLORS = new HashMap<>();
    private BetType selectedBetType;
    private String selectedValue;
    private JTextField betValueField;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JLabel selectedLabel;
    private final Consumer<Bet> onSpin;
    private final RouletteGame game;
    private final Border cyanBorder = BorderFactory.createLineBorder(Color.CYAN, 2); // Cyjanowa obramówka UwU
    private final Border emptyBorder = BorderFactory.createEmptyBorder();

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

    public RouletteView(String username, double initialBalance, Consumer<Bet> onSpin) {
        this.onSpin = onSpin;
        this.game = new RouletteGame();

        JFrame frame = new JFrame("Roulette - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Zero
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        tablePanel.add(createLabel("0", "GREEN", BetType.NUMBER, "0"), gbc);
        gbc.gridheight = 1;

        // Numery 1-36
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 12; col++) {
                int number = col * 3 + (3 - row);
                gbc.gridx = col + 1;
                gbc.gridy = row;
                tablePanel.add(createLabel(String.valueOf(number), NUMBER_COLORS.get(number), BetType.NUMBER, String.valueOf(number)), gbc);
            }
        }

        // Kolumna do obstawiania wierszy
        for (int i = 0; i < 3; i++) {
            gbc.gridx = 13;
            gbc.gridy = i;
            String columnText = (i == 0) ? "1st row" : (i == 1) ? "2nd row" : "3rd row";
            tablePanel.add(createLabel(columnText, "GRAY", BetType.COLUMN, String.valueOf(i + 1)), gbc);
        }

        // Panel dolny
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 52, 0, 52));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Tuziny
        gbc.gridwidth = 4;
        gbc.gridy = 0;
        gbc.gridx = 0;
        bottomPanel.add(createLabel("1st 12", "GRAY", BetType.DOZEN, "1"), gbc);
        gbc.gridx = 4;
        bottomPanel.add(createLabel("2nd 12", "GRAY", BetType.DOZEN, "2"), gbc);
        gbc.gridx = 8;
        bottomPanel.add(createLabel("3rd 12", "GRAY", BetType.DOZEN, "3"), gbc);

        // Zakłady dolne
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        gbc.gridx = 0;
        bottomPanel.add(createLabel("1-18", "GRAY", BetType.HIGH_LOW, "LOW"), gbc);
        gbc.gridx = 2;
        bottomPanel.add(createLabel("EVEN", "GRAY", BetType.EVEN_ODD, "EVEN"), gbc);
        gbc.gridx = 4;
        bottomPanel.add(createLabel("RED", "RED", BetType.COLOR, "RED"), gbc);
        gbc.gridx = 6;
        bottomPanel.add(createLabel("BLACK", "BLACK", BetType.COLOR, "BLACK"), gbc);
        gbc.gridx = 8;
        bottomPanel.add(createLabel("ODD", "GRAY", BetType.EVEN_ODD, "ODD"), gbc);
        gbc.gridx = 10;
        bottomPanel.add(createLabel("19-36", "GRAY", BetType.HIGH_LOW, "HIGH"), gbc);

        // Spin, kwota, balans i wynik
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        bottomPanel.add(new JLabel("Bet Amount:"), gbc);

        betValueField = new JTextField(10);
        gbc.gridx = 1;
        bottomPanel.add(betValueField, gbc);

        gbc.gridx = 2;
        bottomPanel.add(createSpinLabel(frame), gbc);

        balanceLabel = new JLabel("Balance: " + initialBalance);
        gbc.gridx = 3;
        bottomPanel.add(balanceLabel, gbc);

        resultLabel = new JLabel("-", SwingConstants.CENTER);
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.GRAY);
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setPreferredSize(new Dimension(50, 50));
        gbc.gridx = 4;
        bottomPanel.add(resultLabel, gbc);

        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel createLabel(String text, String color, BetType type, String value) {
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
                selectedBetType = type;
                selectedValue = value;
                System.out.println("Selected: " + type + " - " + value);

                // Usuwamy obramówkę z poprzedniego labela, jeśli istnieje
                if (selectedLabel != null) {
                    selectedLabel.setBorder(emptyBorder);
                }
                // Ustawiamy nową obramówkę na aktualnym labelu
                selectedLabel = label;
                selectedLabel.setBorder(cyanBorder);
            }
        });
        return label;
    }

    private JLabel createSpinLabel(JFrame frame) {
        JLabel spinLabel = new JLabel("Spin", SwingConstants.CENTER);
        spinLabel.setOpaque(true);
        spinLabel.setPreferredSize(new Dimension(50, 50));
        spinLabel.setBackground(Color.GRAY);
        spinLabel.setForeground(Color.WHITE);

        spinLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int betValue = Integer.parseInt(betValueField.getText());
                    if (selectedBetType == null || selectedValue == null || betValue <= 0) {
                        JOptionPane.showMessageDialog(frame, "Select a bet type and enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Bet bet = new Bet(selectedBetType, selectedValue, betValue);
                    RouletteGame.SpinResult spinResult = game.spin(bet);

                    if (spinResult.isSuccess()) {
                        simulateSpinAnimation(frame, bet, spinResult);
                    } else {
                        JOptionPane.showMessageDialog(frame, spinResult.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Enter a valid number for the bet amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return spinLabel;
    }

    private void simulateSpinAnimation(JFrame frame, Bet bet, RouletteGame.SpinResult spinResult) {
        Random random = new Random();
        Timer timer = new Timer(50, null);
        final int[] iteration = {0};
        final int totalIterations = 30;
        final int[] delay = {50};

        timer.addActionListener(e -> {
            iteration[0]++;
            if (iteration[0] <= totalIterations) {
                int randomNum = random.nextInt(37);
                String color = NUMBER_COLORS.getOrDefault(randomNum, "GREEN");
                resultLabel.setText(String.valueOf(randomNum));
                setResultLabelBackground(color);
                delay[0] += 10;
                timer.setDelay(delay[0]);
            } else {
                resultLabel.setText(spinResult.getResult()[0]);
                setResultLabelBackground(spinResult.getResult()[1]);
                timer.stop();

                try {
                    RouletteDataGathering.saveSpin(JDBC.ConnectionInit.getConnection(),
                            UserLoginRegister.LoginView.userId,
                            bet, spinResult.getResult(),
                            spinResult.getPayout(),
                            spinResult.getPayout() > 0);
                    RouletteDataGathering.updateBalance(JDBC.ConnectionInit.getConnection(),
                            UserLoginRegister.LoginView.userId,
                            spinResult.getNewBalance());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                updateBalance(spinResult.getNewBalance());
                JOptionPane.showMessageDialog(frame, spinResult.getMessage(), "Spin Result", JOptionPane.INFORMATION_MESSAGE);
                onSpin.accept(bet);
            }
        });
        timer.start();
    }

    private void setResultLabelBackground(String color) {
        switch (color) {
            case "RED" -> resultLabel.setBackground(Color.RED);
            case "BLACK" -> resultLabel.setBackground(Color.BLACK);
            case "GREEN" -> resultLabel.setBackground(Color.GREEN);
            default -> resultLabel.setBackground(Color.GRAY);
        }
    }

    public void updateBalance(double newBalance) {
        balanceLabel.setText("Balance: " + newBalance);
    }
}