import JDBC.ConnectionInit;
import SevenSlotsGame.SevenSlots;
import UserLoginRegister.LoginView;
import UserLoginRegister.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new LoginView();
//            }
//        });

        try {
            ConnectionInit.init();

            ConnectionInit.close();
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