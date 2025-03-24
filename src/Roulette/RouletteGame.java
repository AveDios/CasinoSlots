package Roulette;

import java.util.ArrayList;
import java.util.List;

public class RouletteGame {
    private final RouletteTable table;

    public RouletteGame() {
        this.table = new RouletteTable();
    }

    public String[] spin() {
        return table.getRandomNumber();
    }

    public static boolean checkWin(Bet bet, String[] result) {
        String number = result[0];
        String color = result[1];

        switch (bet.getType()) {
            case NUMBER:
                return bet.getValue().equals(number);
            case COLOR:
                return bet.getValue().equals(color);
            case EVEN_ODD:
                if (number.equals("0")) return false;
                boolean isEven = Integer.parseInt(number) % 2 == 0;
                return (bet.getValue().equals("EVEN") && isEven) || (bet.getValue().equals("ODD") && !isEven);
            case HIGH_LOW:
                if (number.equals("0")) return false;
                int num = Integer.parseInt(number);
                return (bet.getValue().equals("LOW") && num <= 18) || (bet.getValue().equals("HIGH") && num >= 19);
            case COLUMN:
                int colIndex = (Integer.parseInt(number) - 1) % 3;
                return bet.getValue().equals(String.valueOf(colIndex + 1));
            case DOZEN:
                int dozen = (Integer.parseInt(number) - 1) / 12 + 1;
                return bet.getValue().equals(String.valueOf(dozen));
            default:
                return false;
        }
    }

    public static int calculatePayout(Bet bet) {
        return switch (bet.getType()) {
            case NUMBER -> bet.getAmount() * 35;
            case COLOR, EVEN_ODD, HIGH_LOW -> bet.getAmount() * 2;
            case COLUMN, DOZEN -> bet.getAmount() * 3;
            default -> 0;
        };
    }
}