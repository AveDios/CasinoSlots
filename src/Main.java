import GameHub.GameHubView;
import GameHub.SlotsHubView;
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

        // TODO: zrobić okno do dodawania balansu, dodać w kazdej grze w prawym górnym login i aktualny stan balansu

//
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ConnectionInit.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        try {
            ConnectionInit.init();
            SwingUtilities.invokeLater(LoginView::new);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
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