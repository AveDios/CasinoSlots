package TwoDimensionalSlots;

import lombok.Getter;

import javax.swing.*;
import java.util.List;

class WinInfo {
    public enum WinType {
        ROW,
        MAIN_DIAGONAL,
        ANTI_DIAGONAL
    }

    @Getter
    private WinType winType;
    @Getter
    private ImageIcon winningSymbol;
    @Getter
    private List<int[]> winningFields;

    public WinInfo(WinType winType, ImageIcon winningSymbol, List<int[]> winningFields) {
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winningFields = winningFields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Typ wygranej: ").append(winType).append("\n");
        sb.append("Symbol wygrywający: ").append(winningSymbol).append("\n");
        sb.append("Pola wygranej: ");
        for (int[] pos : winningFields) {
            sb.append("[").append(pos[0]).append(",").append(pos[1]).append("] ");
        }
        return sb.toString();
    }
}
