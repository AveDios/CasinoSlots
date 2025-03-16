package Slots.SevenSlotsGame;

import JDBC.ConnectionInit;
import JDBC.Slots.BalanceChanger;
import JDBC.Slots.SevenSlots.DataGathering;
import JDBC.User.UserLoginJDBC;
import Slots.WinInfo.WinInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.userId;

public class SevenSlotsView extends JFrame {
    private SevenSlots sevenSlots;
    private JButton spinButton;
    private JLabel[] slotLabel = new JLabel[5];
    public static double userBalance;

    public SevenSlotsView() {
        sevenSlots = new SevenSlots();

        setTitle("Seven Slots Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10)); // 1 wiersz, 5 kolumn
        for (int i = 0; i < 5; i++) {
            slotLabel[i] = new JLabel("", SwingConstants.CENTER);
            slotLabel[i].setFont(new Font("Arial", Font.BOLD, 32));
            slotLabel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            slotLabel[i].setPreferredSize(new Dimension(64, 64)); // Sloty większe niż ikony, by były wyśrodkowane
            slotLabel[i].setOpaque(true);
            slotLabel[i].setBackground(new Color(200, 255, 200)); // Zielone tło dla kontrastu

            panel.add(slotLabel[i]);
        }



        spinButton = new JButton("Spin");
        spinButton.addActionListener(new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    spin();
                } catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void spin() throws SQLException {
        double gameCost = 10.0;
        userBalance = UserLoginJDBC.userBalance(connection, userId);

        if (userBalance < gameCost) {
            JOptionPane.showMessageDialog(null,"Insufficient balance! You need at least " + gameCost + " to play.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        sevenSlots.makeBoard();
        updateBoard();

        WinInfo winPossibilitiesInfo = sevenSlots.getWinInfo();
        double winValueInfo = SevenSlotsWinPriceLogic.getWinValue(winPossibilitiesInfo);

        if (winPossibilitiesInfo != null) {
            System.out.println(winPossibilitiesInfo);
            System.out.println(winValueInfo);

            double valueReturnAfterSpin = winValueInfo - gameCost;
            System.out.println("Win value after: (winValue - gameCost): " + valueReturnAfterSpin);

            BalanceChanger.changeBalance(ConnectionInit.getConnection(), userId,userBalance + valueReturnAfterSpin);

            DataGathering.insertSlotsData(connection, userId, winPossibilitiesInfo.getWinType(), winPossibilitiesInfo.getWinningSymbol().toString(), winPossibilitiesInfo.getWinPossibilities(), winValueInfo, true);
            JOptionPane.showMessageDialog(null,"You Win!", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            BalanceChanger.changeBalance(connection, userId, userBalance - gameCost);
            DataGathering.insertSlotsData(connection, userId, null, null, null, 0.0, false);
        }
        Thread.yield();
    }

    private void updateBoard() {
        ImageIcon[] board = sevenSlots.getBoard();
        for (int i = 0; i < board.length; i++) {
            slotLabel[i].setHorizontalAlignment(SwingConstants.CENTER); // Wyśrodkowanie poziome
            slotLabel[i].setVerticalAlignment(SwingConstants.CENTER);   // Wyśrodkowanie pionowe

            slotLabel[i].setIcon(getScaledIcon(board[i], 32, 32));
            slotLabel[i].setBackground(new Color(152, 255, 152));
        }
    }


    private ImageIcon getScaledIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) return null;

        int targetSize = (width + height) / 2; // Ustalony rozmiar ikon 32x32
        Image img = icon.getImage().getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }



//    private ImageIcon getScaledIcon(ImageIcon icon, int width, int height) {
//        if (icon == null || icon.getImage() == null) return null;
//        Image img = icon.getImage();
//
//        double aspectRatio = (double) img.getWidth(null) / (double) img.getHeight(null);
//
//        int newWidth = width;
//        int newHeight = (int) (height / aspectRatio);
//        if (newHeight > height) {
//            newHeight = height;
//            newWidth = (int) (width * aspectRatio);
//        }
//
//        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//        return new ImageIcon(scaledImg);
//    }

}
