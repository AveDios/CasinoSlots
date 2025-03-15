import JDBC.ConnectionInit;
import JDBC.User.UserRegisterJDBC;
import Slots.SevenSlotsGame.SevenSlots;
import Slots.SevenSlotsGame.SevenSlotsView;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsView;
import UserLoginRegister.LoginView;
import UserLoginRegister.RegisterView;
import UserLoginRegister.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = ConnectionInit.getConnection();
//
        try {
            ConnectionInit.init();

            SwingUtilities.invokeLater(RegisterView::new);
//            User testUser = new User("test1", "test");
//
//            UserRegisterJDBC.insertUserData(ConnectionInit.getConnection(), testUser.getUsername(), testUser.getHashedPassword(), 100);
//
//            System.out.println(testUser.getHashedPassword());

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    ConnectionInit.close();
                    System.out.println("Połączenie zostało zamknięte.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }







//        User user1 = new User("Name", "haslo");
//
//        System.out.println("Hashed Password: " + user1.getHashedPassword());
//
//        System.out.println("Password Verified: " + user1.verifyPassword("Secure@1234"));
//        System.out.println("Password Verified: " + user1.verifyPassword("haslo"));



//        SevenSlots sevenSlots = new SevenSlots();
//        sevenSlots.setSlotSize(5);
//        sevenSlots.game();
    }
}