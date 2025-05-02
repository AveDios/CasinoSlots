package BlackJack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards in a game of Blackjack.
 * A hand can hold multiple cards, calculate their total value, and determine if it is a Blackjack.
 */
@Getter
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds a card to the hand.
     *
     * @param card the card to be added to the hand
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Calculates the total value of the hand.
     * Aces are counted as 11 by default, but their value is reduced to 1 if the total exceeds 21.
     *
     * @return the total value of the cards in the hand
     */
    public int getTotalValue() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : cards) {
            sum += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }

    /**
     * Checks if the hand is a Blackjack.
     * A Blackjack occurs when the hand consists of exactly two cards with a total value of 21.
     *
     * @return true if the hand is a Blackjack, false otherwise
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getTotalValue() == 21;
    }
}
