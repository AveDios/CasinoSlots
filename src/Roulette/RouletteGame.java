package Roulette;

// Class that handles game logic, including checking bet results and calculating payouts
public class RouletteGame {
    public static boolean checkWin(Bet bet, String[] result) {
        String number = result[0]; // Extract the drawn number
        String color = result[1];  // Extract the drawn color

        switch (bet.getType()) {
            case NUMBER:
                return bet.getValue().equals(number); // Win if number matches

            case COLOR:
                return bet.getValue().equals(color); // Win if color matches

            case EVEN_ODD:
                if (number.equals("0")) return false; // Zero is neither even nor odd
                boolean isEven = Integer.parseInt(number) % 2 == 0;
                return (bet.getValue().equals("EVEN") && isEven) || (bet.getValue().equals("ODD") && !isEven);

            case HIGH_LOW:
                if (number.equals("0")) return false; // Zero is not part of high/low bets
                int num = Integer.parseInt(number);
                return (bet.getValue().equals("LOW") && num <= 18) || (bet.getValue().equals("HIGH") && num >= 19);

            case COLUMN:
                int colIndex = (Integer.parseInt(number) - 1) % 3;
                return bet.getValue().equals(String.valueOf(colIndex + 1)); // Column 1, 2, or 3

            case DOZEN:
                int dozen = (Integer.parseInt(number) - 1) / 12 + 1;
                return bet.getValue().equals(String.valueOf(dozen)); // Dozen 1, 2, or 3

            default:
                return false;
        }
    }

    // Calculates payout based on the bet type
    public static int calculatePayout(Bet bet) {
        switch (bet.getType()) {
            case NUMBER: return bet.getAmount() * 35;
            case COLOR:
            case EVEN_ODD:
            case HIGH_LOW: return bet.getAmount() * 2;
            case COLUMN:
            case DOZEN: return bet.getAmount() * 3;
            default: return 0;
        }
    }
}
