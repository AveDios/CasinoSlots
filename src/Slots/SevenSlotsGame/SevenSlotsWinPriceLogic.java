package Slots.SevenSlotsGame;

import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinPossibilities;

import javax.swing.*;

/**
 * Provides logic for calculating the win value in the Seven Slots game.
 * Determines the payout based on the winning symbol and the number of matching symbols.
 */
public class SevenSlotsWinPriceLogic {

    /** Base value for a cherry symbol win. */
    private static final double CHERRY_VALUE = 15.0;

    /** Base value for a lemon symbol win. */
    private static final double LEMON_VALUE = 20.0;

    /** Base value for an orange symbol win. */
    private static final double ORANGE_VALUE = 25.0;

    /**
     * Calculates the win value based on the provided WinInfo.
     *
     * @param winInfo the WinInfo object containing details about the win, including the winning symbol and win possibilities
     * @return the calculated win value, or 0.0 if no win is provided
     */
    public static double getWinValue(WinInfo winInfo) {
        if (winInfo == null) return 0.0;
        double baseValue = getBaseValue(winInfo.getWinningSymbol());
        return applyMultiplier(baseValue, winInfo.getWinPossibilities());
    }

    /**
     * Determines the base value for a given symbol based on its description.
     *
     * @param symbol the ImageIcon representing the winning symbol
     * @return the base value for the symbol, or 0.0 if the symbol is null or unrecognized
     */
    private static double getBaseValue(ImageIcon symbol) {
        if (symbol == null) return 0.0;

        String desc = symbol.getDescription();
        if (desc == null) return 0.0;

        return switch (desc.toLowerCase()) {
            case "cherry" -> CHERRY_VALUE;
            case "lemon" -> LEMON_VALUE;
            case "orange" -> ORANGE_VALUE;
            default -> 0.0;
        };
    }

    /**
     * Applies a multiplier to the base value based on the number of matching symbols.
     *
     * @param baseValue       the base value of the winning symbol
     * @param winPossibilities the number of matching symbols (THREE, FOUR, or FIVE)
     * @return the final win value after applying the multiplier
     */
    private static double applyMultiplier(double baseValue, WinPossibilities winPossibilities) {
        return switch (winPossibilities) {
            case THREE -> baseValue * 1.5;
            case FOUR -> baseValue * 1.75;
            case FIVE -> baseValue * 2.0;
        };
    }
}