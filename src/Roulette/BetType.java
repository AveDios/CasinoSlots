package Roulette;

// Enum representing different types of bets in roulette
public enum BetType {
    NUMBER,   // Single number (payout 35:1)
    COLOR,    // Red or Black (payout 1:1)
    EVEN_ODD, // Even or Odd (payout 1:1)
    HIGH_LOW, // Low (1-18) or High (19-36) (payout 1:1)
    COLUMN,   // Column bet (payout 2:1)
    DOZEN     // Dozen bet (1-12, 13-24, 25-36) (payout 2:1)
}