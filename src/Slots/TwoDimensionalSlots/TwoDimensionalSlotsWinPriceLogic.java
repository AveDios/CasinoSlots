package Slots.TwoDimensionalSlots;

import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinType;

import javax.swing.*;

public class TwoDimensionalSlotsWinPriceLogic {
    private static final double CHERRY_VALUE = 15.0;
    private static final double LEMON_VALUE = 20.0;
    private static final double ORANGE_VALUE = 25.0;

    public static double getWinValue(WinInfo winInfo) {
        if (winInfo == null) return 0.0;
        double baseValue = getBaseValue(winInfo.getWinningSymbol());
        return applyMultiplier(baseValue, winInfo.getWinType());
    }

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

    private static double applyMultiplier(double baseValue, WinType winType) {
        return switch (winType) {
            case ROW ->  baseValue;
            case MAIN_DIAGONAL, ANTI_DIAGONAL -> baseValue * 1.5;
            case MULTI_DIAGONAL -> baseValue * 2.0;
            case REVERSE_MAIN_DIAGONAL, REVERSE_ANTI_DIAGONAL -> baseValue * 1.75;
            case REVERSE_MULTI_DIAGONAL -> baseValue * 2.5;
        };
    }
}
