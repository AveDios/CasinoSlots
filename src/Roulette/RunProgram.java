package Roulette;

import UserLoginRegister.User;

import java.util.Scanner;

public class RunProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        Player User = new Player(1000);
        RouletteTable table = new RouletteTable();

        User user = new User("kutas", "kutas");

        System.out.println("Witaj w ruletce! Twój balans: " + user.getBalance());

        while (user.getBalance() > 0) {
            System.out.println("\nWybierz typ zakładu:");
            System.out.println("1 - Numer (stawka 35:1)");
            System.out.println("2 - Kolor (czerwony/czarny) (stawka 1:1)");
            System.out.println("3 - Parzyste/Nieparzyste (stawka 1:1)");
            System.out.println("4 - Niskie/Wysokie (1:18 / 19:36) (stawka 1:1)");
            System.out.println("5 - Kolumna (stawka 2:1)");
            System.out.println("6 - Tuzin (stawka 2:1)");
            System.out.print("Wybór: ");
            int choice = scanner.nextInt();

            BetType betType;
            String value;
            switch (choice) {
                case 1:
                    betType = BetType.NUMBER;
                    System.out.print("Podaj numer (0-36): ");
                    value = scanner.next();
                    break;
                case 2:
                    betType = BetType.COLOR;
                    System.out.print("Kolor (RED/BLACK): ");
                    value = scanner.next().toUpperCase();
                    break;
                case 3:
                    betType = BetType.EVEN_ODD;
                    System.out.print("Parzyste/Nieparzyste (EVEN/ODD): ");
                    value = scanner.next().toUpperCase();
                    break;
                case 4:
                    betType = BetType.HIGH_LOW;
                    System.out.print("Niskie/Wysokie (LOW/HIGH): ");
                    value = scanner.next().toUpperCase();
                    break;
                case 5:
                    betType = BetType.COLUMN;
                    System.out.print("Kolumna (1, 2, 3): ");
                    value = scanner.next();
                    break;
                case 6:
                    betType = BetType.DOZEN;
                    System.out.print("Tuzin (1, 2, 3): ");
                    value = scanner.next();
                    break;
                default:
                    System.out.println("Niepoprawny wybór.");
                    continue;
            }

            System.out.print("Podaj kwotę zakładu: ");
            int amount = scanner.nextInt();

            Bet bet = new Bet(betType, value, amount);
            if (!placeBet(bet, user)) continue;

            String[] result = table.getRandomNumber();
            System.out.println("Wylosowano: " + result[0] + " (" + result[1] + ")");

            if (RouletteGame.checkWin(bet, result)) {
                int winnings = RouletteGame.calculatePayout(bet);
                user.getBalance();
                System.out.println("Gratulacje! Wygrałeś " + winnings + ". Nowy balans: " + user.getBalance());
            } else {
                System.out.println("Przegrana. Twój balans: " + user.getBalance());
            }

            System.out.print("Czy chcesz grać dalej? (tak/nie): ");
            String cont = scanner.next().toLowerCase();
            if (!cont.equals("tak")) break;
        }

        System.out.println("Koniec gry. Twój końcowy balans: " + user.getBalance());
        scanner.close();
    }
    public static boolean placeBet(Bet bet, User user) {
        int balance = user.getBalance();
        if (bet.getAmount() > balance) {
            System.out.println("Not enough funds to place this bet!");
            return false;
        } else {
            balance -= bet.getAmount();
        }

        return true;
    }
}
