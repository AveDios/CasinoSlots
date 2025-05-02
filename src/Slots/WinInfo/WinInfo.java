package Slots.WinInfo;

import lombok.Getter;

import javax.swing.*;
import java.util.List;

/**
 * Represents information about a win in a slot machine game.
 * Stores details such as the game type, win type, winning symbol, and either the number of matching symbols
 * (for one-dimensional slots) or the winning fields (for two-dimensional slots).
 */
public class WinInfo {

    /** The name of the slot game where the win occurred. */
    @Getter
    private WinGameName winGameName;

    /** The type of win (e.g., row, diagonal). */
    @Getter
    private WinType winType;

    /** The symbol that triggered the win. */
    @Getter
    private ImageIcon winningSymbol;

    /** The number of matching symbols for one-dimensional slot wins. */
    @Getter
    private WinPossibilities winPossibilities;

    /** The list of winning field coordinates for two-dimensional slot wins. */
    @Getter
    private List<int[]> winningFields;

    /**
     * Constructs a WinInfo object for a two-dimensional slot game win.
     *
     * @param winGameName   the name of the slot game (e.g., TWO_DIMENSIONAL_SLOTS)
     * @param winType       the type of win (e.g., ROW, MAIN_DIAGONAL)
     * @param winningSymbol the symbol that triggered the win
     * @param winningFields the list of field coordinates involved in the win
     */
    public WinInfo(WinGameName winGameName, WinType winType, ImageIcon winningSymbol, List<int[]> winningFields) {
        this.winGameName = winGameName;
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winningFields = winningFields;
    }

    /**
     * Constructs a WinInfo object for a one-dimensional slot game win.
     *
     * @param winGameName    the name of the slot game (e.g., ONE_DIMENSIONAL_SLOTS)
     * @param winType        the type of win (e.g., ROW)
     * @param winningSymbol  the symbol that triggered the win
     * @param winPossibilities the number of matching symbols (e.g., THREE, FOUR, FIVE)
     */
    public WinInfo(WinGameName winGameName, WinType winType, ImageIcon winningSymbol, WinPossibilities winPossibilities) {
        this.winGameName = winGameName;
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winPossibilities = winPossibilities;
    }

    /**
     * Returns a string representation of the win information, including the game name, win type,
     * winning symbol, and either the number of matching symbols or the winning field coordinates.
     *
     * @return a string describing the win details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WinGameName: ").append(winGameName).append("\n");
        sb.append("Typ wygranej: ").append(winType).append("\n");
        sb.append("Symbol wygrywający: ").append(winningSymbol).append("\n");

        if (winPossibilities != null) {
            sb.append("Wygrana Seven Slots ilość pól: ").append(winPossibilities);
        }

        if (winningFields != null) {
            sb.append("Pola wygranej: ");
            for (int[] pos : winningFields) {
                sb.append("[").append(pos[0]).append(",").append(pos[1]).append("] ");
            }
        }

        return sb.toString();
    }
}