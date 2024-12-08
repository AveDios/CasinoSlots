package SevenSlotsGame;

import ISlots.ISlots;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SevenSlots implements ISlots {
    private static double totalWinnings = 0;

    static {
        // Klucz: symbol, Wartość: tablica [wartość, waga]
        symbolData.put("🍒", new double[]{5, 35.0});   // 35% - często się pojawia, mała wygrana
        symbolData.put("🍋", new double[]{10, 25.0});  // 25% - dość częsty
        symbolData.put("🍊", new double[]{15, 17.0});  // 17% - średnia częstość
        symbolData.put("🍉", new double[]{20, 12.0});  // 12% - rzadszy
        symbolData.put("🔔", new double[]{30, 6.5});   // 6.5% - jeszcze rzadszy
        symbolData.put("⭐", new double[]{50, 2.5});    // 2.5% - rzadki symbol
        symbolData.put("💎", new double[]{75, 1.3});   // 1.3% - bardzo rzadki
        symbolData.put("🍀", new double[]{100, 0.7});  // 0.7% - najrzadszy, najwyższa nagroda
    }
    private static final Random random = new Random();

//    private static final String[] symbols = { "🍒", "🍋", "🍊", "🍉", "🔔", "⭐", "💎", "🍀" };
//    private static final int[] winPrice = { 5, 10, 15, 20, 30, 50, 75, 100 };



    public void setSlotSize(int value) {
        slotSize[0] = value;
    }
    public void game() {
        Scanner scanner = new Scanner(System.in);
        int size = slotSize[0];

        while(true) {
            System.out.println("Press the ENTER key to play or 'exit' to leave");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("99% of gamblers give up before winning big");
                System.out.println("Are you sure to leave us and our awesome machines (Y/ N)");
                String ynGambling = scanner.nextLine();
                if (ynGambling.equalsIgnoreCase("y")) {
                    System.out.println("Your total winnings before leaving is: " + String.format("%.2f",totalWinnings));
                    break;
                } else {
                    continue;
                }
            }

            // Tworzenie symboli planszy do gry
            String[] symbol = new String[size];
            Map<String, Integer> symbolCount = new HashMap<>(); // Dodajemy liczenie symboli w trakcie losowania

            for (int i = 0; i < size; i++) {
                symbol[i] = getRandomSymbol(symbolCount); // Losujemy z uwzględnieniem dotychczasowych symboli
                symbolCount.put(symbol[i], symbolCount.getOrDefault(symbol[i], 0) + 1); // Aktualizujemy liczbę wystąpień
            }

            System.out.println("777: " + String.join(" ", symbol));

            boolean hasSmallWin = false;
            boolean hasMediumWin = false;
            boolean hasMegaWin = false;
            String winningSymbol = null;
            int maxCount = 0;

            // Sprawdzenie, który symbol wygrał
            for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
                int count = entry.getValue();
                if (count > maxCount) {
                    maxCount = count;
                    winningSymbol = entry.getKey();
                }
            }

            if (maxCount >= 3) {
                hasSmallWin = true;
            }
            if (maxCount >= 4) {
                hasMediumWin = true;
            }
            if (maxCount == 5) {
                hasMegaWin = true;
            }

            // Obliczanie wygranej
            if (hasMegaWin) {
                double megaWinAmount = symbolData.get(winningSymbol)[0] * 5;
                totalWinnings += megaWinAmount;
                System.out.println("Congratulations! You are the 'mega big win'! Your win is " + String.format("%.2f", megaWinAmount)) ;
            } else if (hasMediumWin) {
                double mediumWinAmount = symbolData.get(winningSymbol)[0] * 4;
                totalWinnings += mediumWinAmount;
                System.out.println("Congratulations! You have a medium win! Your win is " + String.format("%.2f", mediumWinAmount)) ;
            } else if (hasSmallWin) {
                double smallWInAmount = symbolData.get(winningSymbol)[0] * 3 * 1.1;
                totalWinnings += smallWInAmount;
                System.out.println("Congratulations! You have a small win! Your win is " + String.format("%.2f", smallWInAmount)) ;
            } else {
                System.out.println("Zagraj ponownie");
            }
        }
    }

    private static String getRandomSymbol(Map<String, Integer> symbolCount) {
        // Losowanie symbolu na podstawie wag, z uwzględnieniem liczby wystąpień symbolu
        double totalWeight = 0;
        for (Map.Entry<String, double[]> entry : symbolData.entrySet()) {
            String symbol = entry.getKey();
            int count = symbolCount.getOrDefault(symbol, 0);

            // Redukcja wagi dla symboli, które pojawiły się 2 razy, aby zapobiec wygranym
            double weight = entry.getValue()[1];
            if (count == 2) {
                weight /= 3; // Redukujemy wagę 10-krotnie, jeśli symbol pojawił się 2 razy
            }

            totalWeight += weight;
        }

        double randomValue = random.nextDouble(totalWeight);
        for (Map.Entry<String, double[]> entry : symbolData.entrySet()) {
            String symbol = entry.getKey();
            double weight = entry.getValue()[1];

            // Redukcja wagi dla symboli, które pojawiły się 2 razy
            int count = symbolCount.getOrDefault(symbol, 0);
            if (count == 2) {
                weight /= 3;
            }

            if (randomValue < weight) {
                return symbol;
            }
            randomValue -= weight;
        }
        return ""; // Domyślny zwrot, jeśli coś poszło nie tak
    }
}
