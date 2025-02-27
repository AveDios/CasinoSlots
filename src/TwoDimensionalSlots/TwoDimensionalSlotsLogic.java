package TwoDimensionalSlots;

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
        symbols[0] = new ImageIcon("src/symbols/cherry.png");
        symbols[1] = new ImageIcon("src/symbols/lemon.png");
        symbols[2] = new ImageIcon("src/symbols/orange.png");
    }

    public void makeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int index = rand.nextInt(symbols.length);
                board[i][j] = symbols[index];
            }
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < board.length; i++) {
            if(checkRowWin(board[i])){
                return true;
            }
        }

        return checkDiagonalWin();
    }

    public WinInfo getWinInfo() {
        // Sprawdzenie wygranej poziomej (row win)
        for (int i = 0; i < board.length; i++) {
            if (checkRowWin(board[i])) {
                List<int[]> fields = new ArrayList<>();
                for (int j = 0; j < board[i].length; j++) {
                    fields.add(new int[]{i, j});
                }
                return new WinInfo(WinInfo.WinType.ROW, board[i][0], fields);
            }
        }

        // Sprawdzenie wygranej po przekątnej głównej (main diagonal win)
        if (checkMainDiagonalWin()) {
            List<int[]> fields = new ArrayList<>();
            int minLength = Math.min(board.length, board[0].length);
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, i});
            }
            return new WinInfo(WinInfo.WinType.MAIN_DIAGONAL, board[0][0], fields);
        }

        // Sprawdzenie wygranej po przekątnej pobocznej (anti-diagonal win)
        if (checkAntiDiagonalWin()) {
            List<int[]> fields = new ArrayList<>();
            int cols = board[0].length;
            int minLength = Math.min(board.length, board[0].length);
            for (int i = 0; i < minLength; i++) {
                fields.add(new int[]{i, cols - 1 - i});
            }
            return new WinInfo(WinInfo.WinType.ANTI_DIAGONAL, board[0][cols - 1], fields);
        }

        // Brak wygranej
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

    boolean checkDiagonalWin() {
        return checkMainDiagonalWin() || checkAntiDiagonalWin();
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
            if(!board[i][cols - 1].equals(firstSymbol)){
                return false;
            }
        }
        return true;
    }

    public List<int[]> getWinningFields() {
        List<int[]> winningFields = new ArrayList<>();

        // Check for horizontal wins
        for (int i = 0; i < board.length; i++) {
            if (checkRowWin(board[i])) {
                for (int j = 0; j < board[i].length; j++) {
                    winningFields.add(new int[]{i, j});
                }
            }
        }

        // Check for diagonal wins
        if (checkMainDiagonalWin()) {
            for (int i = 0; i < Math.min(board.length, board[0].length); i++) {
                winningFields.add(new int[]{i, i});
            }
        }

        if (checkAntiDiagonalWin()) {
            for (int i = 0; i < Math.min(board.length, board[0].length); i++) {
                winningFields.add(new int[]{i, board[0].length - 1 - i});
            }
        }

        return winningFields;
    }
}
