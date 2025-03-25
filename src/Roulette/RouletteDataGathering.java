package Roulette;

import JDBC.Slots.BalanceChanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RouletteDataGathering {
    public static void saveSpin(Connection connection, int userId, Bet bet, String[] result, int winValue, boolean isWin) throws SQLException {
        String sql = "INSERT INTO roulette_spins (user_id, bet_type, bet_value, result_number, result_color, win_value, is_win, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, bet.getType().toString());
            stmt.setString(3, bet.getValue());
            stmt.setString(4, result[0]);
            stmt.setString(5, result[1]);
            stmt.setDouble(6, winValue);
            stmt.setBoolean(7, isWin);
            stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Roulette spin data saved to database.");
        }
    }

    public static void updateBalance(Connection connection, int userId, double newBalance) throws SQLException {
        BalanceChanger.changeBalance(connection, userId, newBalance);
        System.out.println("Balance updated in database: " + newBalance);
    }
}