package BlackJack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

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

    public boolean isBlackjack() {
        return cards.size() == 2 && getTotalValue() == 21;
    }
}
