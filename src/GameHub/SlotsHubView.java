package GameHub;

import Slots.SevenSlotsGame.SevenSlotsView;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SlotsHubView extends JFrame{
    public SlotsHubView() {
        super("Slot Hub");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Choose Slot Game");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(messageLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(1, 2,5,0));
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.add(createGameSection("SevenSlots", "src/Assets/HubBackground/sevenSlots.png", "SevenSlots"));
        gamePanel.add(createGameSection("TwoDimensionalSlots", "src/Assets/HubBackground/twoDimensionalSlots.png", "TwoDimensionalSlots"));

        setVisible(true);
    }

    private JPanel createGameSection(String gameName, String backgroundImage, String gameType) {
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
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    startGame(gameType); // Uruchomienie odpowiedniej gry po kliknięciu
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(playButton, BorderLayout.SOUTH);

        return panel;
    }

    private void startGame(String gameName) throws SQLException {
        switch(gameName) {
            case "SevenSlots":
                new SevenSlotsView();
                break;
            case "TwoDimensionalSlots":
                new TwoDimensionalSlotsView();
                break;

        }
    }
}
