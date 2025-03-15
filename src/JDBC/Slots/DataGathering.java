package JDBC.Slots;

import Slots.WinInfo.WinInfo;
import Slots.WinInfo.WinType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


public class DataGathering {
    public static void insertSlotsData(Connection connection, int user_id, WinType win_type, String winSymbol,
                                       List<int[]> winningFields, double winValue, boolean isWin) throws SQLException {
        String coordinates = convertWinningFieldsToString(winningFields);

        String winTypeString = win_type.name();
        // Zapytanie SQL do wstawienia danych
        String sql = "INSERT INTO twoDimensionalSlots (user_id, win_type, win_symbol, winning_fields, win_value, is_win, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, user_id);
            stmt.setString(2, String.valueOf(winTypeString));
            stmt.setString(3, String.valueOf(winSymbol));
            if (coordinates == null) {
                stmt.setString(4, null);
            } else {
                stmt.setString(4, coordinates);
            }
            stmt.setDouble(5, winValue);
            stmt.setBoolean(6, isWin);
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Dane zostały zapisane do bazy.");
        }
    }

    private static String convertWinningFieldsToString(List<int[]> winningFields) {
        StringBuilder sb = new StringBuilder();
        for (int[] fields : winningFields) {
            sb.append("[")
                .append(fields[0])
                .append(", ")
                .append(fields[1])
                .append("],");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
