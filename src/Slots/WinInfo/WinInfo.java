package Slots.WinInfo;

import lombok.Getter;

import javax.swing.*;
import java.util.List;

public class WinInfo {

    @Getter
    private WinGameName winGameName;
    @Getter
    private WinType winType;
    @Getter
    private ImageIcon winningSymbol;
    @Getter
    private WinPossibilities winPossibilities;
    @Getter
    private List<int[]> winningFields;

    public WinInfo(WinGameName winGameName, WinType winType, ImageIcon winningSymbol, List<int[]> winningFields) {
        this.winGameName = winGameName;
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winningFields = winningFields;
    }

    public WinInfo(WinGameName winGameName, WinType winType, ImageIcon winningSymbol, WinPossibilities winPossibilities) {
        this.winGameName = winGameName;
        this.winType = winType;
        this.winningSymbol = winningSymbol;
        this.winPossibilities = winPossibilities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WinGameName: ").append(winGameName).append("\n");
        sb.append("Typ wygranej: ").append(winType).append("\n");
        sb.append("Symbol wygrywający: ").append(winningSymbol).append("\n");

        if (winPossibilities != null) {
            sb.append("Wygrana Seven Slots ilość pól: ").append(winPossibilities);
        }

        if (winningFields != null) {
            sb.append("Pola wygranej: ");
            for (int[] pos : winningFields) {
                sb.append("[").append(pos[0]).append(",").append(pos[1]).append("] ");
            }
        }

        return sb.toString();
    }
}
