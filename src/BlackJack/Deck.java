package BlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a standard deck of 52 playing cards used in a game of Blackjack.
 * The deck can be shuffled and cards can be drawn from it.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Constructs a new deck of 52 cards, consisting of all combinations of suits and ranks.
     * The deck is automatically shuffled upon creation.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the cards in the deck using a random permutation.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws and removes the top card from the deck.
     *
     * @return the card drawn from the deck
     * @throws IllegalStateException if the deck is empty
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }
}

