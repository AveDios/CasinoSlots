package BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackJackGUI extends JFrame {
    private final BlackJackGame game;
    private final JTextArea gameStateArea;
    private final JButton hitButton;
    private final JButton standButton;
    private final JButton newGameButton;

    public BlackJackGUI() {
        game = new BlackJackGame();

        setTitle("Blackjack Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        gameStateArea = new JTextArea();
        gameStateArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameStateArea);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.playerHit();
                updateGameState();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.playerStand();
                updateGameState();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startNewGame();
                updateGameState();
            }
        });

        updateGameState();
        setVisible(true);
    }

    private void updateGameState() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dealer's Hand:").append(System.lineSeparator());
        for (Card card : game.getDealerHand().getCards()) {
            sb.append(card).append(System.lineSeparator());
        }
        sb.append("Total: ").append(game.getDealerHand().getTotalValue()).append(System.lineSeparator());

        sb.append(System.lineSeparator());

        sb.append("Player's Hand:").append(System.lineSeparator());
        for (Card card : game.getPlayerHand().getCards()) {
            sb.append(card).append(System.lineSeparator());
        }
        sb.append("Total: ").append(game.getPlayerHand().getTotalValue()).append(System.lineSeparator());

        sb.append(System.lineSeparator());

        if (game.isGameOver()) {
            sb.append("Result: ").append(game.getResult()).append(System.lineSeparator());
        } else if (game.isPlayerTurn()) {
            sb.append("Player's Turn").append(System.lineSeparator());
        } else {
            sb.append("Dealer's Turn").append(System.lineSeparator());
        }

        gameStateArea.setText(sb.toString());

        hitButton.setEnabled(game.isPlayerTurn() && !game.isGameOver());
        standButton.setEnabled(game.isPlayerTurn() && !game.isGameOver());
    }
}
