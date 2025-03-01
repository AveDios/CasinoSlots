package TwoDimensionalSlots;

import lombok.Getter;

import javax.swing.*;
import java.util.List;

public class WinInfo {
    public enum WinType {
        ROW,
        MAIN_DIAGONAL,
        ANTI_DIAGONAL,
        MULTI_DIAGONAL,
        REVERSE_MAIN_DIAGONAL,
        REVERSE_ANTI_DIAGONAL,
        REVERSE_MULTI_DIAGONAL,
    }
    public enum WinGameName {
        TWO_DIMENSIONAL_SLOTS,
        ONE_DIMENSIONAL_SLOTS
    }

    @Getter
    private WinGameName winGameName;
    @Getter
    private WinType winType;
    @Getter
    private ImageIcon winningSymbol;
    @Getter
    private List<int[]> winningFields;

    public WinInfo(WinGameName winGameName, WinType winType, ImageIcon winningSymbol, List<int[]> winningFields) {
        this.winGameName = winGameName;
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winningFields = winningFields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WinGameName: ").append(winGameName).append("\n");
        sb.append("Typ wygranej: ").append(winType).append("\n");
        sb.append("Symbol wygrywający: ").append(winningSymbol).append("\n");
        sb.append("Pola wygranej: ");
        for (int[] pos : winningFields) {
            sb.append("[").append(pos[0]).append(",").append(pos[1]).append("] ");
        }
        return sb.toString();
    }
}
