package BlackJack;

import lombok.Getter;

public class BlackJackGame {
    private final Deck deck;
    @Getter
    private final Hand playerHand;
    @Getter
    private final Hand dealerHand;
    @Getter
    private boolean playerTurn;
    @Getter
    private boolean gameOver;

    public BlackJackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        startNewGame();
    }

    public void startNewGame() {
        playerHand.getCards().clear();
        dealerHand.getCards().clear();
        deck.shuffle();

        playerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());

        playerTurn = true;
        gameOver = false;

        if (playerHand.isBlackjack() || dealerHand.isBlackjack()) {
            playerTurn = false;
            gameOver = true;
        }
    }

    public void playerHit() {
        if (!playerTurn || gameOver) return;
        playerHand.addCard(deck.draw());
        if (playerHand.getTotalValue() > 21) {
            playerTurn = false;
            gameOver = true;
        }
    }

    public void playerStand() {
        if (!playerTurn || gameOver) return;
        playerTurn = false;
        dealerPlay();
        gameOver = true;
    }

    private void dealerPlay() {
        while (dealerHand.getTotalValue() < 17) {
            dealerHand.addCard(deck.draw());
        }
    }

    public String getResult() {
        int playerTotal = playerHand.getTotalValue();
        int dealerTotal = dealerHand.getTotalValue();

        if (playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            return "Both have Blackjack! Push (draw)!";
        } else if (playerHand.isBlackjack()) {
            return "Player has Blackjack! Player wins!";
        } else if (dealerHand.isBlackjack()) {
            return "Dealer has Blackjack! Dealer wins!";
        }

        if (playerTotal > 21) {
            return "Player busts! Dealer wins.";
        } else if (dealerTotal > 21) {
            return "Dealer busts! Player wins.";
        } else if (playerTotal > dealerTotal) {
            return "Player wins!";
        } else if (dealerTotal > playerTotal) {
            return "Dealer wins!";
        } else {
            return "Push (draw)!";
        }
    }

}
