package JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserRegisterJDBC {
    public static void insertUserData(Connection connection, String username, String password, int level, int experience, int balance) {
        // Zapytanie SQL do wstawienia danych
        String insertUserSQL = "INSERT INTO users (username, password, level, experience) VALUES (?, ?, ?, ?);";
        String insertBalanceSQL = "INSERT INTO balance (balance) VALUES (?);";

        try (PreparedStatement preparedStatementUser = connection.prepareStatement(insertUserSQL);
        PreparedStatement preparedStatementBalance = connection.prepareStatement(insertBalanceSQL)) {
            // Ustawianie parametrów zapytania
            preparedStatementUser.setString(1, username);
            preparedStatementUser.setString(2, password);
            preparedStatementUser.setInt(3, level);
            preparedStatementUser.setInt(4, experience);

            preparedStatementBalance.setInt(1, balance);


            // Wykonanie zapytania
            int rowsInsertedUser = preparedStatementUser.executeUpdate();
            if (rowsInsertedUser > 0) {
                System.out.println("Dane użytkownika zostały pomyślnie wstawione!");
            }

            int rowsInsertedBalance = preparedStatementBalance.executeUpdate();
            if (rowsInsertedBalance > 0) {
                System.out.println("Dane balansu użytkownika zostały pomyślnie dodane");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
