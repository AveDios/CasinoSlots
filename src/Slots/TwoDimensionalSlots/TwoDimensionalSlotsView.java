package Slots.TwoDimensionalSlots;

import Assets.TwoDimensionalSlotsColors;
import JDBC.ConnectionInit;
import JDBC.Slots.BalanceChanger;
import JDBC.Slots.TwoDimensionalSlots.DataGathering;
import JDBC.User.UserLoginJDBC;
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
import static UserLoginRegister.LoginView.userId;

public class TwoDimensionalSlotsView extends JFrame {
    private TwoDimensionalSlotsLogic twoDimensionalSlotsGameLogic;
    private JLabel[][] labels = new JLabel[3][5];
    private JButton spinButton;
    private JLabel winnerInfo;
    public static double userBalance;

    public TwoDimensionalSlotsView() {
        twoDimensionalSlotsGameLogic = new TwoDimensionalSlotsLogic();

        setTitle("Two Dimensional Slots");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        winnerInfo = new JLabel("", JLabel.CENTER);

        JPanel boardPanel = new JPanel(new GridLayout(3,5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j] = new JLabel("", SwingConstants.CENTER);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                labels[i][j].setBackground(new Color(152,255,152));
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

        add(winnerInfo, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoard();
            }
        });

        setSize(500,350);
        setResizable(false);
        setVisible(true);
    }

    private void spin() throws SQLException {
        double gameCost = 25.0;
        userBalance = UserLoginJDBC.userBalance(connection,userId);

        if (userBalance < gameCost) {
            JOptionPane.showMessageDialog(null,"Insufficient balance! You need at least " + gameCost + " to play.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        twoDimensionalSlotsGameLogic.makeBoard();
        updateBoard();

        // Pobieramy informacje o wygranej – metoda getWinInfo() już ustala priorytet (ROW ma najwyższy priorytet)
        WinInfo winInfo = twoDimensionalSlotsGameLogic.getWinInfo();
        double winValue = TwoDimensionalSlotsWinPriceLogic.getWinValue(winInfo);

        if (winInfo != null) {
            // Podświetlamy tylko pola odpowiadające typowi wygranej, który ma najwyższy priorytet
            highlightWinningFields(winInfo);
            winnerInfo.setText("You win! (" + winInfo.getWinType() + ")");
            System.out.println("You win!");
            System.out.println(winInfo);


            double valueReturnAfterGame = winValue - gameCost;
            System.out.println("Win value after: (winValue - gameCost): " + valueReturnAfterGame);

            //Aktualizacja balansu gracza
            BalanceChanger.changeBalance(connection, userId, userBalance + valueReturnAfterGame);

            //Zapis wyników do bazy danych
            DataGathering.insertSlotsData(connection, userId, winInfo.getWinType(),winInfo.getWinningSymbol().toString(),winInfo.getWinningFields(),winValue,true);

            JOptionPane.showMessageDialog(null, "You win! Your price is: " + winValue, "High Score", JOptionPane.INFORMATION_MESSAGE);
        } else {
            BalanceChanger.changeBalance(connection, userId, userBalance - gameCost);
            DataGathering.insertSlotsData(connection, userId, null, null, null, 0.0, false);
            winnerInfo.setText("You lost!");
            System.out.println("You lost!");
        }
        Thread.yield();
    }

    private void updateBoard() {
        ImageIcon[][] board = twoDimensionalSlotsGameLogic.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int width =  labels[i][j].getWidth() - 10;
                int height = labels[i][j].getHeight() - 10;

                if (width > 0 && height > 0) {
                    ImageIcon scaledIcon = getScaledIcon(board[i][j], width, height);
                    labels[i][j].setIcon(scaledIcon);
                } else {
                    labels[i][j].setIcon(board[i][j]);
                }

                labels[i][j].setBackground(new Color(152,255,152));
            }
        }
    }

    private void highlightWinningFields(WinInfo winInfo) {
        List<int[]> winningFields = winInfo.getWinningFields();

        // Zmiana kolorów tła w zależności od wylosowanej wygranej
        Color highlightColor = switch (winInfo.getWinType()) {
            case ROW -> TwoDimensionalSlotsColors.ROW_COLOR_ORANGE;
            case MAIN_DIAGONAL -> TwoDimensionalSlotsColors.MAIN_DIAGONAL_COLOR_YELLOW;
            case ANTI_DIAGONAL -> TwoDimensionalSlotsColors.ANTI_DIAGONAL_COLOR_CYAN;
            case MULTI_DIAGONAL -> TwoDimensionalSlotsColors.MULTI_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_MAIN_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_MAIN_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_ANTI_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_ANTI_DIAGONAL_COLOR_MAGENTA;
            case REVERSE_MULTI_DIAGONAL -> TwoDimensionalSlotsColors.REVERSE_MULTI_DIAGONAL_COLOR_MAGENTA;
        };

        // Opcjonalnie: najpierw przywróć domyślne tło dla całej planszy
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j].setBackground(new Color(152,255,152));
            }
        }

        // Podświetlenie tylko pól wygranej zgodnie z priorytetem (winInfo zawiera tylko te pola, które odpowiadają najwyższemu priorytetowi)
        for (int[] pos : winningFields) {
            int row = pos[0];
            int col = pos[1];
            labels[row][col].setOpaque(true);
            labels[row][col].setBackground(highlightColor);
        }
    }

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
