package Slots.TwoDimensionalSlots;

import Slots.WinInfo.WinGameName;
import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinType;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the logic for a two-dimensional slot machine game with a 3x5 board.
 * Handles symbol generation, board creation, and win condition checks for rows and diagonals.
 */
public class TwoDimensionalSlotsLogic {

    /** Random number generator used for selecting symbols. */
    static Random rand = new Random();

    /** Array of available symbols that can appear on the board. */
    ImageIcon[] symbols;

    /** The current state of the slot machine board, represented as a 3x5 grid of symbols. */
    @Getter
    private ImageIcon[][] board = new ImageIcon[3][5];

    /**
     * Constructs a new TwoDimensionalSlotsLogic instance and initializes the symbols.
     */
    public TwoDimensionalSlotsLogic() {
        loadSymbols();
    }

    /**
     * Loads the symbols (cherry, lemon, orange) into the symbols array.
     * Each symbol is represented by an ImageIcon with a corresponding description.
     */
    private void loadSymbols() {
        symbols = new ImageIcon[3];
        String cherry = "src/Assets/symbols/cherry.png";
        String lemon = "src/Assets/symbols/lemon.png";
        String orange = "src/Assets/symbols/orange.png";
        symbols[0] = new ImageIcon(cherry);
        symbols[0].setDescription("cherry");
        symbols[1] = new ImageIcon(lemon);
        symbols[1].setDescription("lemon");
        symbols[2] = new ImageIcon(orange);
        symbols[2].setDescription("orange");
    }

    /**
     * Generates a new board by randomly selecting symbols for each position in the 3x5 grid.
     */
    public void makeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int index = rand.nextInt(symbols.length);
                board[i][j] = symbols[index];
            }
        }
    }

    /**
     * Checks the board for winning combinations, including rows, main diagonal, anti-diagonal,
     * reverse main diagonal, reverse anti-diagonal, or combinations of diagonals.
     *
     * @return a WinInfo object describing the win, or null if no win is found
     */
    public WinInfo getWinInfo() {
        List<int[]> fields = new ArrayList<>();

        // Check for row wins
        for (int i = 0; i < board.length; i++) {
            if (checkRowWin(board[i])) {
                for (int j = 0; j < board[i].length; j++) {
                    fields.add(new int[]{i, j});
                }
                return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.ROW, board[i][0], fields);
            }
        }

        int rows = board.length;
        int cols = board[0].length;
        int minLength = Math.min(rows, cols);

        // Check for both main and anti-diagonal wins
        if (checkMainDiagonalWin() && checkAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, i});
            }
            for (int i = minLength - 1; i >= 0; i--) {
                int[] coordinate = new int[]{i, cols - 1 - i};
                boolean exists = false;
                for (int[] field : fields) {
                    if (field[0] == coordinate[0] && field[1] == coordinate[1]) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    fields.add(coordinate);
                }
            }
            ImageIcon winningSymbol = board[0][0];
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.MULTI_DIAGONAL, winningSymbol, fields);
        }

        // Check for both reverse main and reverse anti-diagonal wins
        if (checkReverseMainDiagonalWin() && checkReverseAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, cols - 1 - i});
            }
            for (int i = minLength - 1; i >= 0; i--) {
                int[] coordinate = new int[]{rows - 1 - i, i};
                boolean exists = false;
                for (int[] field : fields) {
                    if (field[0] == coordinate[0] && field[1] == coordinate[1]) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    fields.add(coordinate);
                }
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_MULTI_DIAGONAL, board[rows - 1][cols - 1], fields);
        }

        // Check for main diagonal win
        if (checkMainDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.MAIN_DIAGONAL, board[0][0], fields);
        }

        // Check for anti-diagonal win
        if (checkAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, cols - 1 - i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.ANTI_DIAGONAL, board[0][cols - 1], fields);
        }

        // Check for reverse main diagonal win
        if (checkReverseMainDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, cols - 1 - i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_MAIN_DIAGONAL, board[0][0], fields);
        }

        // Check for reverse anti-diagonal win
        if (checkReverseAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_ANTI_DIAGONAL, board[rows - 1][0], fields);
        }

        return null;
    }

    /**
     * Checks if a row contains five identical symbols.
     *
     * @param row the row of symbols to check
     * @return true if all symbols in the row are identical, false otherwise
     */
    boolean checkRowWin(ImageIcon[] row) {
        ImageIcon firstSymbol = row[0];
        for (int i = 1; i < row.length; i++) {
            if (!row[i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the main diagonal (top-left to bottom-right) contains identical symbols.
     *
     * @return true if all symbols in the main diagonal are identical, false otherwise
     */
    boolean checkMainDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[0][0];
        for (int i = 1; i < Math.min(rows, cols); i++) {
            if (!board[i][i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the anti-diagonal (top-right to bottom-left) contains identical symbols.
     *
     * @return true if all symbols in the anti-diagonal are identical, false otherwise
     */
    boolean checkAntiDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[0][cols - 1];
        for (int i = 1; i < Math.min(rows, cols); i++) {
            if (!board[i][cols - 1 - i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the reverse main diagonal (bottom-right to top-left) contains identical symbols.
     *
     * @return true if all symbols in the reverse main diagonal are identical, false otherwise
     */
    boolean checkReverseMainDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[rows - 1][cols - 1];
        for (int i = 1; i < Math.min(rows, cols); i++) {
            if (!board[rows - 1 - i][cols - 1 - i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the reverse anti-diagonal (bottom-left to top-right) contains identical symbols.
     *
     * @return true if all symbols in the reverse anti-diagonal are identical, false otherwise
     */
    boolean checkReverseAntiDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[rows - 1][0];
        for (int i = 1; i < Math.min(rows, cols); i++) {
            if (!board[rows - 1 - i][i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }
}