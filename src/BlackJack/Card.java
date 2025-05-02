package BlackJack;

import lombok.Getter;

/**
 * Represents a single playing card in a game of Blackjack.
 * Each card has a suit, rank, and numerical value.
 */
@Getter
public class Card {
    private final String suit;
    private final String rank;
    private final int value;

    /**
     * Constructs a new card with the specified suit, rank, and value.
     *
     * @param suit  the suit of the card (e.g., "Hearts", "Diamonds", "Clubs", "Spades")
     * @param rank  the rank of the card (e.g., "2", "3", ..., "10", "Jack", "Queen", "King", "Ace")
     * @param value the numerical value of the card (e.g., 2-10 for numbered cards, 10 for face cards, 11 for Ace)
     */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Returns a string representation of the card in the format "rank of suit".
     *
     * @return a string describing the card (e.g., "Ace of Spades")
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
