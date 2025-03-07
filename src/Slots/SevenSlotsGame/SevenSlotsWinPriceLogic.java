package Slots.SevenSlotsGame;

import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinPossibilities;

import javax.swing.*;

public class SevenSlotsWinPriceLogic {
    private static final double CHERRY_VALUE = 15.0;
    private static final double LEMON_VALUE = 20.0;
    private static final double ORANGE_VALUE = 25.0;

    public static double getWinValue(WinInfo winInfo) {
        if (winInfo == null) return 0.0;
        double baseValue = getBaseValue(winInfo.getWinningSymbol());
        return applyMultiplier(baseValue, winInfo.getWinPossibilities());
    }

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

    private static double applyMultiplier(double baseValue, WinPossibilities winPossibilities) {
        return switch (winPossibilities) {
            case THREE -> baseValue * 1.5;
            case FOUR -> baseValue * 1.75;
            case FIVE -> baseValue * 2.0;
        };
    }

}
