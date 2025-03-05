package Roulette;

// Class representing a bet placed by the player
public class Bet {
    private final BetType type;  // Type of bet
    private final String value;  // Bet selection (e.g., "RED", "17", "EVEN")
    private final int amount;    // Amount wagered

    public Bet(BetType type, String value, int amount) {
        this.type = type;
        this.value = value;
        this.amount = amount;
    }

    public BetType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }
}
