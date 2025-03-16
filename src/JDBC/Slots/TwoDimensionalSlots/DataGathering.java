package JDBC.Slots.TwoDimensionalSlots;

import Slots.WinInfo.WinType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class DataGathering {
    public static void insertSlotsData(Connection connection, int user_id, WinType win_type, String winSymbol,
                                       List<int[]> winningFields, double winValue, boolean isWin) throws SQLException {

        if (winningFields == null) {
            winningFields = new ArrayList<>();
        }

        String coordinates = convertWinningFieldsToString(winningFields);

        String winTypeStr = (win_type != null) ? win_type.name() : "NO_WIN";


        // Zapytanie SQL do wstawienia danych
        String sql = "INSERT INTO twoDimensionalSlots (user_id, win_type, win_symbol, winning_fields, win_value, is_win, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, user_id);
            stmt.setString(2, winTypeStr);
            stmt.setString(3,  winSymbol != null ? winSymbol : "NO_SYMBOL");
            stmt.setString(4, coordinates);
            stmt.setDouble(5, winValue);
            stmt.setBoolean(6, isWin);
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Dane zostały zapisane do bazy.");
        }
    }

    private static String convertWinningFieldsToString(List<int[]> winningFields) {
        if (winningFields == null || winningFields.isEmpty()) {
            return "[]";  // Zwracamy pustą listę zamiast null
        }
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
