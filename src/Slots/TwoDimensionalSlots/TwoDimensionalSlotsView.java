package Slots.TwoDimensionalSlots;

import Assets.TwoDimensionalSlotsColors;
import GameHub.GameHubView;
import JDBC.Slots.BalanceChanger;
import JDBC.Slots.TwoDimensionalSlots.DataGathering;
import JDBC.User.UserLoginJDBC;
import MenuMain.MenuMainView;
import Slots.WinInfo.WinInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.List;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.*;

/**
 * Represents the graphical user interface for the Two Dimensional Slots game.
 * Displays a 3x5 slot machine board, a spin button, and user information.
 * Handles game logic, user balance updates, and database interactions.
 */
public class TwoDimensionalSlotsView extends JFrame {

    /**
     * The game logic instance for the Two Dimensional Slots game.
     */
    private TwoDimensionalSlotsLogic twoDimensionalSlotsGameLogic;

    /**
     * 2D array of labels displaying the slot machine symbols.
     */
    private JLabel[][] labels = new JLabel[3][5];

    /**
     * Button to initiate a spin of the slot machine.
     */
    private JButton spinButton;

    /**
     * Label displaying the win or loss result.
     */
    private JLabel winnerInfo;

    /**
     * Label displaying the user's username and balance.
     */
    private JLabel userInfo;

    /**
     * The current balance of the user.
     */
    private static double userBalance;

    /**
     * The username of the logged-in user.
     */
    private static String username;

    /**
     * Button to return to the main menu.
     */
    private JButton backButton;

    /**
     * Constructs a new TwoDimensionalSlotsView, initializing the GUI and game logic.
     *
     * @throws SQLException if a database error occurs during initialization
     */
    public TwoDimensionalSlotsView() throws SQLException {
        twoDimensionalSlotsGameLogic = new TwoDimensionalSlotsLogic();

        setTitle("Two Dimensional Slots");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

        JPanel boardPanel = new JPanel(new GridLayout(3, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j] = new JLabel("", SwingConstants.CENTER);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                labels[i][j].setBackground(new Color(152, 255, 152));
                labels[i][j].setOpaque(true);
                boardPanel.add(labels[i][j]);
            }
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

//        spinButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SwingWorker<Long, Void> worker = new SwingWorker<>() {
//                    @Override
//                    protected Long doInBackground() throws Exception {
//                        DataGathering dbManager = new DataGathering();
//                        dbManager.generateAndInsertMassData(100_000, userId); // 100k rekordów
//                        return null;
//                    }
//
//                    @Override
//                    protected void done() {
//                        try {
//                            long duration = get(); // Pobieramy czas z wyniku
//                            double seconds = duration / 1000.0; // Konwersja na sekundy
//                            JOptionPane.showMessageDialog(null,
//                                    String.format("Wstawiono 100 000 rekordów!\nCzas operacji: %.2f sekund", seconds));
//                        } catch (Exception ex) {
//                            JOptionPane.showMessageDialog(null, "Błąd: " + ex.getMessage());
//                        }
//                    }
//                };
//                worker.execute();
//            }
//        });

        add(infoPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoard();
            }
        });

        setSize(500, 350);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Performs a spin of the slot machine, updates the board, and processes the game outcome.
     * Checks the user's balance, deducts the game cost, and updates the balance based on the win or loss.
     * Highlights winning fields, displays the result, and logs the game data to the database.
     *
     * @throws SQLException if a database error occurs during balance updates or data logging
     */
    private void spin() throws SQLException {
        double gameCost = 25.0;
        updateUser();

        if (userBalance < gameCost) {
            JOptionPane.showMessageDialog(null, "Insufficient balance! You need at least " + gameCost + " to play.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        twoDimensionalSlotsGameLogic.makeBoard();
        updateBoard();

        WinInfo winInfo = twoDimensionalSlotsGameLogic.getWinInfo();
        double winValue = TwoDimensionalSlotsWinPriceLogic.getWinValue(winInfo);

        if (winInfo != null) {
            highlightWinningFields(winInfo);
            winnerInfo.setText("You win! (" + winInfo.getWinType() + ")");
            userInfo.setText("Balance: $" + userBalance);
            System.out.println("You win!");
            System.out.println(winInfo);

            double valueReturnAfterGame = winValue - gameCost;
            System.out.println("Win value after: (winValue - gameCost): " + valueReturnAfterGame);

            BalanceChanger.changeBalance(connection, userId, userBalance + valueReturnAfterGame);

            DataGathering.insertSlotsData(connection, userId, winInfo.getWinType(), winInfo.getWinningSymbol().toString(), winInfo.getWinningFields(), winValue, true);

            JOptionPane.showMessageDialog(null, "You win! Your price is: " + winValue, "High Score", JOptionPane.INFORMATION_MESSAGE);
            updateUser();
        } else {
            BalanceChanger.changeBalance(connection, userId, userBalance - gameCost);
            DataGathering.insertSlotsData(connection, userId, null, null, null, 0.0, false);
            winnerInfo.setText("You lost!");
            updateUser();
            System.out.println("You lost!");
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
        userInfo.setText("Balance: $" + userBalance);
    }

    /**
     * Updates the slot machine board display with the current symbols from the TwoDimensionalSlotsLogic instance.
     * Scales the icons to fit the label dimensions.
     */
    private void updateBoard() {
        ImageIcon[][] board = twoDimensionalSlotsGameLogic.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int width = labels[i][j].getWidth() - 10;
                int height = labels[i][j].getHeight() - 10;

                if (width > 0 && height > 0) {
                    ImageIcon scaledIcon = getScaledIcon(board[i][j], width, height);
                    labels[i][j].setIcon(scaledIcon);
                } else {
                    labels[i][j].setIcon(board[i][j]);
                }

                labels[i][j].setBackground(new Color(152, 255, 152));
            }
        }
    }

    /**
     * Highlights the winning fields on the board based on the WinInfo provided.
     * Applies a specific color to the winning fields depending on the win type.
     *
     * @param winInfo the WinInfo object containing the winning fields and win type
     */
    private void highlightWinningFields(WinInfo winInfo) {
        List<int[]> winningFields = winInfo.getWinningFields();

        Color highlightColor = switch (winInfo.getWinType()) {
            case ROW -> TwoDimensionalSlotsColors.ROW_COLOR_ORANGE;
            case MAIN_DIAGONAL -> TwoDimensionalSlotsColors.MAIN_DIAGONAL_COLOR_YELLOW;
            case ANTI_DIAGONAL -> TwoDimensionalSlotsColors.ANTI_DIAGONAL_COLOR_CYAN;
            case MULTI_DIAGONAL -> TwoDimensionalSlotsColors.MULTI_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_MAIN_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_MAIN_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_ANTI_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_ANTI_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_MULTI_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_MULTI_DIAGONAL_COLOR_MAGENTA;
        };

        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j].setBackground(new Color(152, 255, 152));
            }
        }

        for (int[] pos : winningFields) {
            int row = pos[0];
            int col = pos[1];
            labels[row][col].setOpaque(true);
            labels[row][col].setBackground(highlightColor);
        }
    }

    /**
     * Scales an ImageIcon to the specified width and height while maintaining the aspect ratio.
     *
     * @param icon   the ImageIcon to scale
     * @param width  the target width of the scaled icon
     * @param height the target height of the scaled icon
     * @return a new scaled ImageIcon, or null if the input icon or its image is null
     */
    private ImageIcon getScaledIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) return null;
        Image img = icon.getImage();

        double aspectRatio = (double) img.getWidth(null) / (double) img.getHeight(null);

        int newWidth = width;
        int newHeight = (int) (height / aspectRatio);
        if (newHeight > height) {
            newHeight = height;
            newWidth = (int) (width * aspectRatio);
        }

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}