package JDBC.User;

import UserLoginRegister.User;

import java.sql.*;

import static JDBC.ConnectionInit.connection;

public class UserLoginJDBC {

    // sprawdza czy podane dane są zgodne z istniejącymi w bazie dancych
    public static boolean isCorrectLogin(String login, String password) {
        String sql = "SELECT login, password FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            password = User.hashPassword(password);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                // Jeśli wynik zapytania zawiera dane (tzn. użytkownik istnieje)
                return rs.next(); // rs.next() zwróci true, jeśli wynik zapytania ma przynajmniej jeden wiersz
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int userID(Connection connection, String login, String password) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            password = User.hashPassword(password);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Jeśli dane logowania są poprawne, zwróć user_id
                    return rs.getInt("user_id");
                } else {
                    // Jeśli dane logowania są niepoprawne, zwróć -1
                    return -1;  // Możesz tu zwrócić kod błędu lub rzucić wyjątek
                }
            }
        }
    }
}
