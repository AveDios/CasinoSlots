package JDBC.Slots.SevenSlots;

import Slots.WinInfo.WinPossibilities;
import Slots.WinInfo.WinType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DataGathering {
        public static void insertSlotsData(Connection connection, int user_id, WinType win_type, String winSymbol,
                                           WinPossibilities winPossibilities, double winValue, boolean isWin) throws SQLException {

            String winTypeStr = (win_type != null) ? win_type.toString() : "NO_WIN";

            String winPossStr = (winPossibilities != null) ? winPossibilities.toString() : "NO_WIN";

            String sql = "INSERT INTO sevenSlots (user_id, win_type, win_symbol, win_poss, win_value, is_win, date) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
                insertStmt.setInt(1, user_id);
                insertStmt.setString(2, winTypeStr);
                insertStmt.setString(3, winSymbol != null ? winSymbol : "NO_SYMBOL");
                insertStmt.setString(4, winPossStr);
                insertStmt.setDouble(5, winValue);
                insertStmt.setBoolean(6, isWin);
                insertStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

                insertStmt.executeUpdate();
                System.out.println("Dane zostały zapisane do bazy.");
            }
        }
}
