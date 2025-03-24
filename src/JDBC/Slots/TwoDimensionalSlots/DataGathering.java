package JDBC.Slots.TwoDimensionalSlots;

import JDBC.ConnectionInit;
import JDBC.Slots.BalanceChanger;
import JDBC.User.UserLoginJDBC;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsLogic;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsWinPriceLogic;
import Slots.WinInfo.WinInfo;
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

    // Metoda do wstawienia 100k rekordów
    public Long generateAndInsertMassData(int totalRecords, int userId) throws SQLException {
        List<Object[]> slotsData = new ArrayList<>();
        Connection connection = ConnectionInit.getConnection();
        String username = UserLoginJDBC.getUserName(connection, userId);
        int batchSize = 1000;

        TwoDimensionalSlotsLogic twoDimensionalSlotsGameLogic = new TwoDimensionalSlotsLogic();

        // Pomiar czasu rozpoczęcia
        long startTime = System.currentTimeMillis();

        connection.setAutoCommit(false);
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO twoDimensionalSlots (user_id, win_type, win_symbol, winning_fields, win_value, is_win, date) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            for (int i = 0; i < totalRecords; i++) {
                twoDimensionalSlotsGameLogic.makeBoard();
                WinInfo winInfo = twoDimensionalSlotsGameLogic.getWinInfo();
                double winValue = TwoDimensionalSlotsWinPriceLogic.getWinValue(winInfo);

                WinType winType = (winInfo != null && winInfo.getWinType() != null)
                        ? WinType.valueOf(winInfo.getWinType().toString())
                        : null;
                String winTypeStr = (winType != null) ? winType.name() : "NO_WIN";
                String winSymbol = (winInfo != null && winInfo.getWinningSymbol() != null)
                        ? winInfo.getWinningSymbol().toString()
                        : "NO_SYMBOL";
                List<int[]> winningFields = (winInfo != null) ? winInfo.getWinningFields() : new ArrayList<>();
                String coordinates = convertWinningFieldsToString(winningFields);

                slotsData.add(new Object[]{userId, winTypeStr, winSymbol, coordinates, winValue, winInfo != null});

                if (slotsData.size() >= batchSize || i == totalRecords - 1) {
                    for (Object[] data : slotsData) {
                        ps.setInt(1, (Integer) data[0]);
                        ps.setString(2, (String) data[1]);
                        ps.setString(3, (String) data[2]);
                        ps.setString(4, (String) data[3]);
                        ps.setDouble(5, (Double) data[4]);
                        ps.setBoolean(6, (Boolean) data[5]);
                        ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    slotsData.clear();
                    System.out.println("Wstawiono " + (i + 1) + " rekordów");
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

        // Pomiar czasu zakończenia i obliczenie różnicy
        // Około 3 sek = 100_000 rekordów
        long endTime = System.currentTimeMillis();
        return endTime - startTime; // Zwracamy Long (autoboxing z long na Long)
    }
}
