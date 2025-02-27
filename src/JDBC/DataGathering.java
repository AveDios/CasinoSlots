package JDBC;
import TwoDimensionalSlots.TwoDimensionalSlotsLogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataGathering {
    public static void insertWinData(Connection connection, String game_name, double win, boolean isWin) throws SQLException {

        // Zapytanie SQL do wstawienia danych
        String insertGameSQL = "INSERT INTO wszystkieGry (id_user, game_name, win_value, isWin) VALUES (?, ?, ?, ?);";
        String insertWinSQL = "INSERT INTO wygraneGry (id_user, game_name, win_value, isWin) VALUES (?, ?, ?, ?);";

        int idUser = 1;//placeholder

        if (isWin) {

            try (PreparedStatement preparedStatementGame = connection.prepareStatement(insertGameSQL);
                 PreparedStatement preparedStatementWin = connection.prepareStatement(insertWinSQL)) {

                // Ustawianie parametrów zapytania
                preparedStatementGame.setInt(1, idUser);
                preparedStatementGame.setString(2, game_name);
                preparedStatementGame.setDouble(3, win);
                preparedStatementGame.setBoolean(4, isWin);

                preparedStatementWin.setInt(1, idUser);
                preparedStatementWin.setString(2, game_name);
                preparedStatementWin.setDouble(3, win);
                preparedStatementWin.setBoolean(4, isWin);


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
