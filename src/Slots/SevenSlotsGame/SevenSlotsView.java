package Slots.SevenSlotsGame;

import GameHub.GameHubView;
import JDBC.ConnectionInit;
import JDBC.Slots.BalanceChanger;
import JDBC.Slots.SevenSlots.DataGathering;
import JDBC.User.UserLoginJDBC;
import MenuMain.MenuMainView;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsView;
import Slots.WinInfo.WinInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.*;

/**
 * Represents the graphical user interface for the Seven Slots game.
 * Displays a one-dimensional slot machine with five slots, a spin button, and user information.
 * Handles game logic, user balance updates, and database interactions.
 */
public class SevenSlotsView extends JFrame {

    /** The SevenSlots game logic instance. */
    private SevenSlots sevenSlots;

    /** Button to initiate a spin of the slot machine. */
    private JButton spinButton;

    /** Array of labels displaying the slot machine symbols. */
    private JLabel[] slotLabel = new JLabel[5];

    /** Label displaying the win or loss result. */
    private JLabel winnerInfo;

    /** Label displaying the user's username and balance. */
    private JLabel userInfo;

    /** The current balance of the user. */
    public static double userBalance;

    /** The username of the logged-in user. */
    private static String username;

    /** Button to return to the main menu. */
    private JButton backButton;

    /**
     * Constructs a new SevenSlotsView, initializing the GUI and game logic.
     *
     * @throws SQLException if a database error occurs during initialization
     */
    public SevenSlotsView() throws SQLException {
        sevenSlots = new SevenSlots();

        setTitle("Seven Slots Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuMainView();
            }
        });
        winnerInfo = new JLabel("Spin to win!", SwingConstants.CENTER);
        userInfo = new JLabel("", SwingConstants.CENTER);

        updateUser();

        infoPanel.add(backButton);
        infoPanel.add(winnerInfo);
        infoPanel.add(userInfo);

        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        for (int i = 0; i < 5; i++) {
            slotLabel[i] = new JLabel("", SwingConstants.CENTER);
            slotLabel[i].setFont(new Font("Arial", Font.BOLD, 32));
            slotLabel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            slotLabel[i].setPreferredSize(new Dimension(64, 64));
            slotLabel[i].setOpaque(true);
            slotLabel[i].setBackground(new Color(200, 255, 200));

            panel.add(slotLabel[i]);
        }

        spinButton = new JButton("Spin");
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    spin();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(infoPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Performs a spin of the slot machine, updates the board, and processes the game outcome.
     * Checks the user's balance, deducts the game cost, and updates the balance based on the win or loss.
     * Displays the result and logs the game data to the database.
     *
     * @throws SQLException if a database error occurs during balance updates or data logging
     */
    private void spin() throws SQLException {
        double gameCost = 10.0;
        updateUser();

        userBalance = UserLoginJDBC.userBalance(connection, userId);

        if (userBalance < gameCost) {
            JOptionPane.showMessageDialog(null, "Insufficient balance! You need at least " + gameCost + " to play.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sevenSlots.makeBoard();
        updateBoard();

        WinInfo winPossibilitiesInfo = sevenSlots.getWinInfo();
        double winValueInfo = SevenSlotsWinPriceLogic.getWinValue(winPossibilitiesInfo);

        if (winPossibilitiesInfo != null) {
            System.out.println(winPossibilitiesInfo);
            System.out.println(winValueInfo);
            winnerInfo.setText("You win! (" + winPossibilitiesInfo.getWinType() + ")");
            userInfo.setText("User: " + username + " | Balance: $" + userBalance);

            double valueReturnAfterSpin = winValueInfo - gameCost;
            System.out.println("Win value after: (winValue - gameCost): " + valueReturnAfterSpin);

            BalanceChanger.changeBalance(ConnectionInit.getConnection(), userId, userBalance + valueReturnAfterSpin);

            DataGathering.insertSlotsData(connection, userId, winPossibilitiesInfo.getWinType(), winPossibilitiesInfo.getWinningSymbol().toString(), winPossibilitiesInfo.getWinPossibilities(), winValueInfo, true);
            JOptionPane.showMessageDialog(null, "You Win!", null, JOptionPane.INFORMATION_MESSAGE);
            updateUser();
        } else {
            BalanceChanger.changeBalance(connection, userId, userBalance - gameCost);
            DataGathering.insertSlotsData(connection, userId, null, null, null, 0.0, false);
            winnerInfo.setText("You lost!");
            System.out.println("You lost!");
            updateUser();
        }
        Thread.yield();
    }

    /**
     * Updates the user's balance and username displayed in the userInfo label.
     *
     * @throws SQLException if a database error occurs while retrieving user information
     */
    private void updateUser() throws SQLException {
        userBalance = UserLoginJDBC.userBalance(connection, userId);
        username = UserLoginJDBC.getUserName(connection, userId);
        userInfo.setText("User: " + username + " | Balance: $" + userBalance);
    }

    /**
     * Updates the slot machine board display with the current symbols from the SevenSlots instance.
     */
    private void updateBoard() {
        ImageIcon[] board = sevenSlots.getBoard();
        for (int i = 0; i < board.length; i++) {
            slotLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
            slotLabel[i].setVerticalAlignment(SwingConstants.CENTER);

            slotLabel[i].setIcon(getScaledIcon(board[i], 32, 32));
            slotLabel[i].setBackground(new Color(152, 255, 152));
        }
    }

    /**
     * Scales an ImageIcon to the specified width and height while maintaining a smooth appearance.
     *
     * @param icon   the ImageIcon to scale
     * @param width  the target width of the scaled icon
     * @param height the target height of the scaled icon
     * @return a new scaled ImageIcon, or null if the input icon or its image is null
     */
    private ImageIcon getScaledIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) return null;

        int targetSize = (width + height) / 2;
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
