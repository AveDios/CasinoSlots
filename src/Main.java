import SevenSlotsGame.SevenSlots;
import UserLoginRegister.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView();
            }
        });

//        SevenSlots sevenSlots = new SevenSlots();
//        sevenSlots.setSlotSize(5);
//        sevenSlots.game();
    }
}