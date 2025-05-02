package Slots.TwoDimensionalSlots;

import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinType;

import javax.swing.*;

/**
 * Provides logic for calculating the win value in the Two Dimensional Slots game.
 * Determines the payout based on the winning symbol and the type of win (row, diagonal, etc.).
 */
public class TwoDimensionalSlotsWinPriceLogic {

    /** Base value for a cherry symbol win. */
    private static final double CHERRY_VALUE = 15.0;

    /** Base value for a lemon symbol win. */
    private static final double LEMON_VALUE = 20.0;

    /** Base value for an orange symbol win. */
    private static final double ORANGE_VALUE = 25.0;

    /**
     * Calculates the win value based on the provided WinInfo.
     *
     * @param winInfo the WinInfo object containing details about the win, including the winning symbol and win type
     * @return the calculated win value, or 0.0 if no win is provided
     */
    public static double getWinValue(WinInfo winInfo) {
        if (winInfo == null) return 0.0;
        double baseValue = getBaseValue(winInfo.getWinningSymbol());
        return applyMultiplier(baseValue, winInfo.getWinType());
    }

    /**
     * Determines the base value for a given symbol based on its description.
     *
     * @param symbol the ImageIcon representing the winning symbol
     * @return the base value for the symbol, or 0.0 if the symbol is null or unrecognized
     */
    private static double getBaseValue(ImageIcon symbol) {
        if (symbol == null) return 0.0;

        String description = symbol.getDescription();
        if (description == null) return 0.0;

        return switch (description.toLowerCase()) {
            case "cherry" -> CHERRY_VALUE;
            case "lemon" -> LEMON_VALUE;
            case "orange" -> ORANGE_VALUE;
            default -> 0.0;
        };
    }

    /**
     * Applies a multiplier to the base value based on the type of win.
     *
     * @param baseValue the base value of the winning symbol
     * @param winType   the type of win (e.g., ROW, MAIN_DIAGONAL, MULTI_DIAGONAL)
     * @return the final win value after applying the multiplier
     */
    private static double applyMultiplier(double baseValue, WinType winType) {
        return switch (winType) {
            case ROW -> baseValue;
            case MAIN_DIAGONAL, ANTI_DIAGONAL -> baseValue * 1.5;
            case MULTI_DIAGONAL -> baseValue * 2.0;
            case REVERSE_MAIN_DIAGONAL, REVERSE_ANTI_DIAGONAL -> baseValue * 1.75;
            case REVERSE_MULTI_DIAGONAL -> baseValue * 2.5;
        };
    }
}
