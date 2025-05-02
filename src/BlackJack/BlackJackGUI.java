package BlackJack;

import JDBC.Slots.BalanceChanger;
import JDBC.User.UserLoginJDBC;
import MenuMain.MenuMainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.userId;

public class BlackJackGUI extends JFrame {
    private final BlackJackGame game;
    private final JLabel statusLabel;
    private final JPanel dealerPanel;
    private final JPanel playerPanel;
    private final JButton hitButton;
    private final JButton standButton;
    private final JButton newGameButton;
    private final JLabel userInfo;
    private final Map<String, ImageIcon> cardImages;
    private final ImageIcon cardBack;
    private static double userBalance;
    private static String username;
    private static final double BET_AMOUNT = 100;

    public BlackJackGUI() {
        game = new BlackJackGame();
        cardImages = generateCardImages();
        cardBack = generateCardBack();

        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top panel with GridLayout (1 row, 3 columns)
        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.setBackground(new Color(34, 139, 34));
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            dispose();
            new MenuMainView();
        });
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userInfo = new JLabel("", SwingConstants.CENTER);
        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(new Font("Arial", Font.BOLD, 14));

        try {
            updateUser();
        } catch (SQLException e) {
            userInfo.setText("Error loading user data");
        }

        infoPanel.add(backButton);
        infoPanel.add(statusLabel);
        infoPanel.add(userInfo);
        add(infoPanel, BorderLayout.NORTH);

        // Dealer and player panels
        dealerPanel = new JPanel(new GridBagLayout());
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer's Hand"));
        dealerPanel.setBackground(new Color(34, 139, 34));
        playerPanel = new JPanel(new GridBagLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player's Hand"));
        playerPanel.setBackground(new Color(34, 139, 34));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(dealerPanel);
        centerPanel.add(playerPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Button panel
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        styleButton(hitButton);
        styleButton(standButton);
        styleButton(newGameButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(34, 139, 34));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        hitButton.addActionListener(e -> {
            game.playerHit();
            updateGameState();
        });

        standButton.addActionListener(e -> {
            game.playerStand();
            updateGameState();
        });

        newGameButton.addActionListener(e -> {
            if (userBalance >= BET_AMOUNT) {
                try {
                    userBalance -= BET_AMOUNT;
                    BalanceChanger.changeBalance(connection, userId, userBalance);
                    game.startNewGame();
                    updateGameState();
                    updateUser();
                } catch (SQLException ex) {
                    statusLabel.setText("Error placing bet");
                }
            } else {
                statusLabel.setText("Insufficient balance!");
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                newGameButton.setEnabled(false);
            }
        });

        updateGameState();
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void updateUser() throws SQLException {
        userBalance = UserLoginJDBC.userBalance(connection, userId);
        username = UserLoginJDBC.getUserName(connection, userId);
        userInfo.setText("Player: " + username + " | Balance: $" + String.format("%.2f", userBalance));
    }

    private Map<String, ImageIcon> generateCardImages() {
        Map<String, ImageIcon> images = new HashMap<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suitSymbols = {"♥", "♦", "♣", "♠"};

        for (int s = 0; s < suits.length; s++) {
            String suit = suits[s];
            String symbol = suitSymbols[s];
            Color color = (suit.equals("Hearts") || suit.equals("Diamonds")) ? Color.RED : Color.BLACK;

            for (String rank : ranks) {
                BufferedImage image = new BufferedImage(100, 140, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(5, 5, 90, 130, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(5, 5, 90, 130, 20, 20);

                g2d.setColor(color);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.drawString(rank, 15, 30);
                g2d.drawString(symbol, 15, 50);
                g2d.rotate(Math.PI, 50, 70);
                g2d.drawString(rank, -85, -110);
                g2d.drawString(symbol, -85, -90);

                g2d.dispose();
                images.put(rank + " of " + suit, new ImageIcon(image));
            }
        }
        return images;
    }

    private ImageIcon generateCardBack() {
        BufferedImage image = new BufferedImage(100, 140, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 139));
        g2d.fillRoundRect(5, 5, 90, 130, 20, 20);
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("BACK", 25, 80);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private void updateGameState() {
        // Update dealer panel
        dealerPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < game.getDealerHand().getCards().size(); i++) {
            Card card = game.getDealerHand().getCards().get(i);
            JLabel cardLabel;
            if (i == 0 && !game.isGameOver()) {
                cardLabel = new JLabel(cardBack);
            } else {
                cardLabel = new JLabel(cardImages.get(card.toString()));
            }
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dealerPanel.add(cardLabel, gbc);
            gbc.gridx++;
        }
        JLabel dealerTotal = new JLabel("Total: " + (game.isGameOver() ? game.getDealerHand().getTotalValue() : "?"));
        dealerTotal.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        dealerPanel.add(dealerTotal, gbc);

        // Update player panel
        playerPanel.removeAll();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        for (Card card : game.getPlayerHand().getCards()) {
            JLabel cardLabel = new JLabel(cardImages.get(card.toString()));
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            playerPanel.add(cardLabel, gbc);
            gbc.gridx++;
        }
        JLabel playerTotal = new JLabel("Total: " + game.getPlayerHand().getTotalValue());
        playerTotal.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        playerPanel.add(playerTotal, gbc);

        // Update status, balance, and buttons
        if (game.isGameOver()) {
            String result = game.getResult();
            statusLabel.setText(result);
            updateBalance(result);
            try {
                updateUser();
            } catch (SQLException e) {
                userInfo.setText("Error updating user data");
            }
        } else if (game.isPlayerTurn()) {
            statusLabel.setText("Your Turn");
        } else {
            statusLabel.setText("Dealer's Turn");
        }

        hitButton.setEnabled(game.isPlayerTurn() && !game.isGameOver());
        standButton.setEnabled(game.isPlayerTurn() && !game.isGameOver());
        newGameButton.setEnabled(userBalance >= BET_AMOUNT);

        // Refresh panels
        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void updateBalance(String result) {
        try {
            if (result.contains("Player wins")) {
                userBalance += 200; // Win: +200
                BalanceChanger.changeBalance(connection, userId, userBalance);
            } else if (result.contains("Push")) {
                userBalance += 100; // Push: +100
                BalanceChanger.changeBalance(connection, userId, userBalance);
            } // Loss: no change (bet already deducted)
        } catch (SQLException e) {
            statusLabel.setText("Error updating balance");
        }
    }
}
