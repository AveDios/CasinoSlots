package Roulette;

import java.util.Random;

// Class representing the roulette table with predefined numbers and colors
public class RouletteTable {
    private final String[][][] table = {
            {{"3", "RED"}, {"6", "BLACK"}, {"9", "RED"}, {"12", "BLACK"}, {"15", "RED"}, {"18", "BLACK"},
                    {"21", "RED"}, {"24", "BLACK"}, {"27", "RED"}, {"30", "BLACK"}, {"33", "RED"}, {"36", "BLACK"}},
            {{"2", "BLACK"}, {"5", "RED"}, {"8", "BLACK"}, {"11", "BLACK"}, {"14", "RED"}, {"17", "BLACK"},
                    {"20", "BLACK"}, {"23", "RED"}, {"26", "BLACK"}, {"29", "RED"}, {"32", "BLACK"}, {"35", "RED"}},
            {{"1", "RED"}, {"4", "BLACK"}, {"7", "RED"}, {"10", "BLACK"}, {"13", "BLACK"}, {"16", "RED"},
                    {"19", "RED"}, {"22", "BLACK"}, {"25", "RED"}, {"28", "BLACK"}, {"31", "RED"}, {"34", "BLACK"}}
    };
    private final String[] zero = {"0", "GREEN"}; // Zero is green

    // Generates a random roulette spin result
    public String[] getRandomNumber() {
        Random random = new Random();
        if (random.nextDouble() < (1.0 / 37.0)) { // 1/37 probability for zero
            return zero;
        }
        int row = random.nextInt(3);
        int col = random.nextInt(12);
        return table[row][col];
    }
}
