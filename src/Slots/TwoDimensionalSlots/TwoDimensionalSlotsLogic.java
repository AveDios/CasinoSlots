package Slots.TwoDimensionalSlots;

import Slots.WinInfo.WinGameName;
import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinType;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoDimensionalSlotsLogic {
    static Random rand = new Random();
    ImageIcon[] symbols;

    @Getter
    private ImageIcon[][] board = new ImageIcon[3][5];

    public TwoDimensionalSlotsLogic() {
        loadSymbols();
    }

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

    public void makeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int index = rand.nextInt(symbols.length);
                board[i][j] = symbols[index];
            }
        }
    }

    public WinInfo getWinInfo() {
        // Sprawdzenie wygranej poziomej (row win)
        List<int[]> fields = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (checkRowWin(board[i])) {
//                List<int[]> fields = new ArrayList<>();
                for (int j = 0; j < board[i].length; j++) {
                    fields.add(new int[]{i, j});
                }
                return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.ROW, board[i][0], fields);
            }
        }

        int rows = board.length;
        int cols = board[0].length;
        int minLength = Math.min(rows, cols);

        // Sprawdzenie, czy obie przekątne są wygrywające
        if (checkMainDiagonalWin() && checkAntiDiagonalWin()) {
            // Dodaj pola głównej przekątnej w kolejności naturalnej
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, i});
            }
            // Dodaj pola przekątnej pobocznej w kolejności odwrotnej
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
            // Ustalamy symbol wygrywający – można tu dodać dodatkową logikę, jeśli potrzebna
            ImageIcon winningSymbol = board[0][0];
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS ,WinType.MULTI_DIAGONAL, winningSymbol, fields);
        }

        if (checkReverseMainDiagonalWin() && checkReverseAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, cols - 1 - i}); // Odwrócona główna
            }
            for (int i = minLength - 1; i >= 0; i--) {
                int[] coordinate = new int[]{rows - 1 - i, i};
                boolean exists = false;
                for (int[] field : fields) {
                    if (field[0] == coordinate[0] && field[1] == coordinate[1]){
                        exists = true;
                        break;
                    }// Odwrócona antyprzekątna
                }
                if (!exists) {
                    fields.add(coordinate);
                }
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_MULTI_DIAGONAL, board[rows - 1][cols - 1], fields);
        }

        // Sprawdzenie przekątnej głównej (main diagonal win)
        if (checkMainDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.MAIN_DIAGONAL, board[0][0], fields);
        }

        // Sprawdzenie przekątnej pobocznej (anti-diagonal win)
        if (checkAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, cols - 1 - i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.ANTI_DIAGONAL, board[0][cols - 1], fields);
        }

        if (checkReverseMainDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, cols - 1 - i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_MAIN_DIAGONAL, board[0][0], fields);
        }

        if (checkReverseAntiDiagonalWin()) {
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{rows - 1 - i, i});
            }
            return new WinInfo(WinGameName.TWO_DIMENSIONAL_SLOTS, WinType.REVERSE_ANTI_DIAGONAL, board[rows - 1][0], fields);
        }

        return null;
    }

    boolean checkRowWin(ImageIcon[] row) {
        ImageIcon firstSymbol = row[0];
        for (int i = 1; i < row.length; i++) {
            if (!row[i].equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }

    boolean checkMainDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[0][0];
        for (int i = 1; i < Math.min(rows,cols); i++) {
            if(!board[i][i].equals(firstSymbol)){
                return false;
            }
        }
        return true;
    }

    boolean checkAntiDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[0][cols - 1];
        for (int i = 1; i < Math.min(rows,cols); i++) {
            if(!board[i][cols - 1 - i].equals(firstSymbol)){
                return false;
            }
        }
        return true;
    }

    boolean checkReverseMainDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[rows - 1][cols - 1];
        for (int i = 1; i < Math.min(rows,cols); i++) {
            if(!board[rows - 1 - i][cols - 1 - i].equals(firstSymbol)){
                return false;
            }
        }
        return true;
    }

    boolean checkReverseAntiDiagonalWin() {
        int rows = board.length;
        int cols = board[0].length;
        if (rows < 3 || cols < 3) return false;

        ImageIcon firstSymbol = board[rows - 1][0];
        for (int i = 1; i < Math.min(rows,cols); i++) {
            if(!board[rows - 1 - i][i].equals(firstSymbol)){
                return false;
            }
        }
        return true;
    }
}
