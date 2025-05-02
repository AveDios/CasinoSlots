package Slots.WinInfo;

/**
 * Represents the types of winning combinations in slot machine games.
 * Used to identify the pattern of a win, such as rows or diagonals, in one- or two-dimensional slot games.
 */
public enum WinType {

    /**
     * Represents a win formed by a horizontal row of matching symbols.
     */
    ROW,

    /**
     * Represents a win formed by matching symbols along the main diagonal (top-left to bottom-right).
     */
    MAIN_DIAGONAL,

    /**
     * Represents a win formed by matching symbols along the anti-diagonal (top-right to bottom-left).
     */
    ANTI_DIAGONAL,

    /**
     * Represents a win formed by matching symbols along both the main and anti-diagonals.
     */
    MULTI_DIAGONAL,

    /**
     * Represents a win formed by matching symbols along the reverse main diagonal (bottom-right to top-left).
     */
    REVERSE_MAIN_DIAGONAL,

    /**
     * Represents a win formed by matching symbols along the reverse anti-diagonal (bottom-left to top-right).
     */
    REVERSE_ANTI_DIAGONAL,

    /**
     * Represents a win formed by matching symbols along both the reverse main and reverse anti-diagonals.
     */
    REVERSE_MULTI_DIAGONAL
}
