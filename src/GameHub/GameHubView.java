package GameHub;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameHubView extends JFrame {

    public GameHubView() {
        setTitle("Casino Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 Górny pasek z wiadomością
        JLabel messageLabel = new JLabel("Welcome to the Casino!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(messageLabel, BorderLayout.NORTH);

        // 🔹 Panel z 3 sekcjami
        JPanel gamePanel = new JPanel(new GridLayout(1, 3, 5, 0)); // 1 rząd, 3 kolumny
        add(gamePanel, BorderLayout.CENTER);

        // 🔹 Dodanie 3 sekcji
        gamePanel.add(createGameSection("Roulette", "src/Assets/HubBackground/roulette_background.png"));
        gamePanel.add(createGameSection("Slots", "src/Assets/HubBackground/slots_background.png"));
        gamePanel.add(createGameSection("BlackJack", "src/Assets/HubBackground/blackjack_background.png"));

        setVisible(true);
    }

    // 📌 Metoda do tworzenia sekcji gry
    private JPanel createGameSection(String gameName, String backgroundImage) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Załaduj obrazek tła
                    Image bgImage = ImageIO.read(new File(backgroundImage));
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Obrys sekcji

        JLabel nameLabel = new JLabel(gameName, SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        JButton playButton = new JButton("PLAY");

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(playButton, BorderLayout.SOUTH);

        return panel;
    }
}

