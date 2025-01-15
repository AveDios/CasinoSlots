package JDBC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionInit {
    private static Connection connection;

    public static void init() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("src/JDBC/databaseAssets.properties")) {
                props.load(fis);
            }

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password", "");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection with database has been established.");
        }
    }

    public static void close() throws SQLException {
        if (connection != null || !connection.isClosed()) {
            try {
                connection.close();
                System.out.println("Connection with database has been closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
