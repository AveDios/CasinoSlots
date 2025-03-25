package Roulette;

import UserLoginRegister.LoginView;

public class RouletteGame {
    private final RouletteTable table;
    private double currentBalance;

    public RouletteGame() {
        this.table = new RouletteTable();
        this.currentBalance = LoginView.userBalance; // Początkowy balans z LoginView
    }

    public SpinResult spin(Bet bet) {
        if (bet.getAmount() > currentBalance) {
            return new SpinResult(false, "Not enough balance!", null, 0, currentBalance);
        }

        String[] result = table.getRandomNumber();
        boolean win = checkWin(bet, result);
        int payout = win ? calculatePayout(bet) : 0;
        currentBalance = currentBalance - bet.getAmount() + payout;

        String message = "Result: " + result[0] + " (" + result[1] + ")\n" +
                (win ? "You won " + payout + "!" : "You lost " + bet.getAmount() + ".");
        return new SpinResult(true, message, result, payout, currentBalance);
    }

    // Metody checkWin i calculatePayout bez zmian
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

    public static class SpinResult {
        private final boolean success;
        private final String message;
        private final String[] result;
        private final int payout;
        private final double newBalance;

        public SpinResult(boolean success, String message, String[] result, int payout, double newBalance) {
            this.success = success;
            this.message = message;
            this.result = result;
            this.payout = payout;
            this.newBalance = newBalance;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String[] getResult() { return result; }
        public int getPayout() { return payout; }
        public double getNewBalance() { return newBalance; }
    }
}