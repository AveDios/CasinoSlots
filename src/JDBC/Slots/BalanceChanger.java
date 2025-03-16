package JDBC.Slots;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalanceChanger {

    // zapytanie zmienia wartosc kolumny balance w tabeli balance
    public static void changeBalance(Connection connection, int user_id, double balanceValue) throws SQLException {

        String changeBalanceSQL = "UPDATE balance SET balance = ? WHERE user_id = ?";

        try (PreparedStatement changeBalanceStatement = connection.prepareStatement(changeBalanceSQL)) {
            changeBalanceStatement.setDouble(1, balanceValue);
            changeBalanceStatement.setInt(2, user_id);

            changeBalanceStatement.executeUpdate();
        }
    }
}
