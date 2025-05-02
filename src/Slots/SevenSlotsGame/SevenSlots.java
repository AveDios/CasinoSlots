package Slots.SevenSlotsGame;

import Slots.WinInfo.WinGameName;
import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinPossibilities;
import Slots.WinInfo.WinType;
import lombok.Getter;

import javax.swing.*;
import java.util.Random;

/**
 * Represents a one-dimensional slot machine game with a board of five slots.
 * The game uses a set of symbols (e.g., cherry, lemon) and determines wins based on consecutive matching symbols.
 */
public class SevenSlots {

    /** Random number generator used for selecting symbols. */
    static Random rand = new Random();

    /** Array of available symbols that can appear on the board. */
    ImageIcon[] symbols;

    /** The current state of the slot machine board, containing five symbols. */
    @Getter
    private ImageIcon[] board = new ImageIcon[5];

    /**
     * Constructs a new SevenSlots game and initializes the symbols.
     */
    public SevenSlots() {
        loadSymbols();
    }

    /**
     * Loads the symbols (cherry and lemon) into the symbols array.
     * Each symbol is represented by an ImageIcon with a corresponding description.
     */
    private void loadSymbols() {
        symbols = new ImageIcon[2];
        String cherry = "src/Assets/symbols/cherry.png";
        String lemon = "src/Assets/symbols/lemon.png";
        symbols[0] = new ImageIcon(cherry);
        symbols[0].setDescription("cherry");
        symbols[1] = new ImageIcon(lemon);
        symbols[1].setDescription("lemon");
    }

    /**
     * Generates a new board by randomly selecting symbols for each of the five slots.
     */
    public void makeBoard() {
        for (int i = 0; i < board.length; i++) {
            int index = rand.nextInt(symbols.length);
            board[i] = symbols[index];
        }
    }

    /**
     * Checks the board for a winning combination of consecutive matching symbols.
     * Returns a WinInfo object if a win is found (three, four, or five matching symbols),
     * or null if no win is found.
     *
     * @return a WinInfo object describing the win, or null if there is no win
     */
    public WinInfo getWinInfo() {
        if (checkRowWin(board, 5)) {
            return new WinInfo(WinGameName.ONE_DIMENSIONAL_SLOTS, WinType.ROW, board[0], WinPossibilities.FIVE);
        }
        if (checkRowWin(board, 4)) {
            return new WinInfo(WinGameName.ONE_DIMENSIONAL_SLOTS, WinType.ROW, board[0], WinPossibilities.FOUR);
        }
        if (checkRowWin(board, 3)) {
            return new WinInfo(WinGameName.ONE_DIMENSIONAL_SLOTS, WinType.ROW, board[0], WinPossibilities.THREE);
        }
        return null;
    }

    /**
     * Checks if a row contains a sequence of matching symbols that satisfies the win condition.
     *
     * @param row          the array of symbols to check
     * @param winCondition the number of consecutive matching symbols required for a win
     * @return true if a winning sequence is found, false otherwise
     */
    private boolean checkRowWin(ImageIcon[] row, int winCondition) {
        if (row.length < winCondition) {
            return false; // Cannot win if the row is shorter than the win condition
        }

        for (int i = 0; i <= row.length - winCondition; i++) {
            ImageIcon firstSymbol = row[i];
            if (firstSymbol == null) continue; // Skip empty slots

            boolean win = true;
            for (int j = 1; j < winCondition; j++) {
                if (!row[i + j].equals(firstSymbol)) {
                    win = false;
                    break;
                }
            }
            if (win) return true; // Return true if a sequence is found
        }
        return false;
    }












//    private static double totalWinnings = 0;
//
//    static {
//        // Klucz: symbol, Wartość: tablica [wartość, waga]
//        symbolData.put("🍒", new double[]{5, 35.0});   // 35% - często się pojawia, mała wygrana
//        symbolData.put("🍋", new double[]{10, 25.0});  // 25% - dość częsty
//        symbolData.put("🍊", new double[]{15, 17.0});  // 17% - średnia częstość
//        symbolData.put("🍉", new double[]{20, 12.0});  // 12% - rzadszy
//        symbolData.put("🔔", new double[]{30, 6.5});   // 6.5% - jeszcze rzadszy
//        symbolData.put("⭐", new double[]{50, 2.5});    // 2.5% - rzadki symbol
//        symbolData.put("💎", new double[]{75, 1.3});   // 1.3% - bardzo rzadki
//        symbolData.put("🍀", new double[]{100, 0.7});  // 0.7% - najrzadszy, najwyższa nagroda
//    }
//    private static final Random random = new Random();
//
////    private static final String[] Assets.symbols = { "🍒", "🍋", "🍊", "🍉", "🔔", "⭐", "💎", "🍀" };
////    private static final int[] winPrice = { 5, 10, 15, 20, 30, 50, 75, 100 };
//
//
//
//    @Override
//    public void setSlotSize(int value) {
//        slotSize[0] = value;
//    }
//
//    @Override
//    public void game() {
//        Scanner scanner = new Scanner(System.in);
//        int size = slotSize[0];
//
//        while(true) {
//            System.out.println("Press the ENTER key to play or 'exit' to leave");
//            String input = scanner.nextLine();
//            if (input.equalsIgnoreCase("exit")) {
//                System.out.println("99% of gamblers give up before winning big");
//                System.out.println("Are you sure to leave us and our awesome machines (Y/ N)");
//                String ynGambling = scanner.nextLine();
//                if (ynGambling.equalsIgnoreCase("y")) {
//                    System.out.println("Your total winnings before leaving is: " + String.format("%.2f",totalWinnings));
//                    break;
//                } else {
//                    continue;
//                }
//            }
//
//            // Tworzenie symboli planszy do gry
//            String[] symbol = new String[size];
//            Map<String, Integer> symbolCount = new HashMap<>(); // Dodajemy liczenie symboli w trakcie losowania
//
//            for (int i = 0; i < size; i++) {
//                symbol[i] = getRandomSymbol(symbolCount); // Losujemy z uwzględnieniem dotychczasowych symboli
//                symbolCount.put(symbol[i], symbolCount.getOrDefault(symbol[i], 0) + 1); // Aktualizujemy liczbę wystąpień
//            }
//
//            System.out.println("777: " + String.join(" ", symbol));
//
//            boolean hasSmallWin = false;
//            boolean hasMediumWin = false;
//            boolean hasMegaWin = false;
//            String winningSymbol = null;
//            int maxCount = 0;
//
//            // Sprawdzenie, który symbol wygrał
//            for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
//                int count = entry.getValue();
//                if (count > maxCount) {
//                    maxCount = count;
//                    winningSymbol = entry.getKey();
//                }
//            }
//
//            if (maxCount >= 3) {
//                hasSmallWin = true;
//            }
//            if (maxCount >= 4) {
//                hasMediumWin = true;
//            }
//            if (maxCount == 5) {
//                hasMegaWin = true;
//            }
//
//            // Obliczanie wygranej
//            if (hasMegaWin) {
//                double megaWinAmount = symbolData.get(winningSymbol)[0] * 5;
//                totalWinnings += megaWinAmount;
//                System.out.println("Congratulations! You are the 'mega big win'! Your win is " + String.format("%.2f", megaWinAmount)) ;
//            } else if (hasMediumWin) {
//                double mediumWinAmount = symbolData.get(winningSymbol)[0] * 4;
//                totalWinnings += mediumWinAmount;
//                System.out.println("Congratulations! You have a medium win! Your win is " + String.format("%.2f", mediumWinAmount)) ;
//            } else if (hasSmallWin) {
//                double smallWInAmount = symbolData.get(winningSymbol)[0] * 3 * 1.1;
//                totalWinnings += smallWInAmount;
//                System.out.println("Congratulations! You have a small win! Your win is " + String.format("%.2f", smallWInAmount)) ;
//            } else {
//                System.out.println("Zagraj ponownie");
//            }
//        }
//    }
//
//    private static String getRandomSymbol(Map<String, Integer> symbolCount) {
//        double totalWeight = 0;
//        double[] cumulativeWeights = new double[symbolData.size()];
//        String[] symbolsArray = new String[symbolData.size()];
//
//        int i = 0;
//        for (Map.Entry<String, double[]> entry : symbolData.entrySet()) {
//            String symbol = entry.getKey();
//            double adjustedWeight = getAdjustedWeight(symbol, symbolCount.getOrDefault(symbol, 0));
//
//            totalWeight += adjustedWeight;
//            cumulativeWeights[i] = totalWeight;
//            symbolsArray[i] = symbol;
//
//            i++;
//        }
//
//        double randomValue = random.nextDouble() * totalWeight;
//
//        for (int j = 0; j < symbolsArray.length; j++) {
//            if (randomValue < cumulativeWeights[j]) {
//                return symbolsArray[j];
//            }
//        }
//        return ""; // Domyślny zwrot dla nieprzewidzianych sytuacji
//    }
//
//    private static double getAdjustedWeight(String symbol, int count) {
//        double baseWeight = symbolData.get(symbol)[1];
//        // Redukuj wagę, jeśli symbol pojawił się 2 razy
//        return count == 2 ? baseWeight / 3 : baseWeight;
//    }
}
