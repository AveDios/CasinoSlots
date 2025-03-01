package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataGathering {
    public static void insertWinData(Connection connection, String game_name, double win, boolean isWin, String win_type) throws SQLException {

        // Zapytanie SQL do wstawienia danych
        String insertGameSQL = "INSERT INTO wszystkieGry (id_user, game_name, win_value, isWin, win_type) VALUES (?, ?, ?, ?, ?);";
        String insertWinSQL = "INSERT INTO wygraneGry (id_user, game_name, win_value, isWin, win_type) VALUES (?, ?, ?, ?, ?);";

        int idUser = 1;
        int win_value = 1;


        if (isWin) {

            try (PreparedStatement preparedStatementGame = connection.prepareStatement(insertGameSQL);
                 PreparedStatement preparedStatementWin = connection.prepareStatement(insertWinSQL)) {

                // Ustawianie parametrów zapytania
                preparedStatementGame.setInt(1, idUser);
                preparedStatementGame.setString(2, game_name);
                preparedStatementGame.setDouble(3, win);
                preparedStatementGame.setBoolean(4, isWin);
                preparedStatementGame.setString(5, win_type);

                preparedStatementWin.setInt(1, idUser);
                preparedStatementWin.setString(2, game_name);
                preparedStatementWin.setDouble(3, win);
                preparedStatementWin.setBoolean(4, isWin);
                preparedStatementGame.setString(5, win_type);


                // Wykonanie zapytania
                int rowsInsertedGame = preparedStatementGame.executeUpdate();
                if (rowsInsertedGame > 0) {
                    System.out.println("Dane gry zostały wstawione");
                }

                int rowsInsertedWin = preparedStatementWin.executeUpdate();
                if (rowsInsertedWin > 0) {
                    System.out.println("Dane dane wygranej zostały wstawione");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // (id_game, id_user, game_name, win_value, isWin)
        } else {
            try (PreparedStatement preparedStatementWin = connection.prepareStatement(insertGameSQL)) {

                preparedStatementWin.setInt(1, idUser);
                preparedStatementWin.setString(2, game_name);
                preparedStatementWin.setDouble(3, win);
                preparedStatementWin.setBoolean(4, isWin);

                int rowsInsertedGame = preparedStatementWin.executeUpdate();
                if (rowsInsertedGame > 0) {
                    System.out.println("Dane zostały wstawione tylko do tabeli wszystkie spiny");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
}
