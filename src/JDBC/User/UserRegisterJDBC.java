package JDBC.User;
import java.sql.*;

public class UserRegisterJDBC {
    public static void insertUserData(Connection connection, String login, String password, int balance) {
        // Zapytanie SQL do wstawienia danych
        String insertUserSQL = "INSERT INTO users (login, password) VALUES (?, ?);";
        String insertBalanceSQL = "INSERT INTO balance (user_id, balance) VALUES (?, ?);";

        try(
            PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement balanceStmt = connection.prepareStatement(insertBalanceSQL)
        ) {
            connection.setAutoCommit(false); // Start transaction

            // Adding user
            userStmt.setString(1, login);
            userStmt.setString(2, password);
            userStmt.executeUpdate();

            // Fetching generated user_id
            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                balanceStmt.setInt(1, userId);
                balanceStmt.setInt(2, balance);
                balanceStmt.executeUpdate();
            }

            connection.commit();
            System.out.println("User and saldo has been inserted!");

        } catch (SQLException e) {
            try {
                connection.rollback(); // Cofnięcie zmian w razie błędu
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Przywrócenie domyślnego trybu autocommit
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static boolean isLoginExist(Connection connection, String login){
        String selectLoginSQL = "SELECT login FROM users WHERE login = ?;";
        try (PreparedStatement loginStmt = connection.prepareStatement(selectLoginSQL)) {
            loginStmt.setString(1, login);

            try (ResultSet rs = loginStmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
