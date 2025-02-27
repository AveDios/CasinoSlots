package TwoDimensionalSlots;

import JDBC.ConnectionInit;
import JDBC.DataGathering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TwoDimensionalSlotsView extends JFrame {

    /**
     * Ustawia zmienne pomocnicze do obsłygi gry
     * @param twoDimensionalSlotsGameLogic odwołanie do klasy w której jest logika całej gry
     * @param labels
     **/
    private TwoDimensionalSlotsLogic twoDimensionalSlotsGameLogic;
    private JLabel[][] labels = new JLabel[3][5];
    private JButton spinButton;
    private JLabel winnerInfo;

    /**
     *
     */
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
        twoDimensionalSlotsGameLogic.makeBoard();
        updateBoard();

        List<int[]> winningFields = twoDimensionalSlotsGameLogic.getWinningFields();
        if (twoDimensionalSlotsGameLogic.checkWin()){
            highlightWinningFields(winningFields);
            winnerInfo.setText("You win!");
            System.out.println("You win!");
            System.out.println(twoDimensionalSlotsGameLogic.getWinInfo());
//            DataGathering.insertWinData(ConnectionInit.getConnection(),"Two Diementional Slots Game", price, true);
        } else {
            winnerInfo.setText("You lost!");
            System.out.println("You lost!");
//            DataGathering.insertWinData(ConnectionInit.getConnection(),"Two Diementional Slots Game", 0, false);
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

    private void highlightWinningFields(List<int[]> winningFields) {
        for (int[] pos: winningFields){
            int row = pos[0];
            int col = pos[1];
            labels[row][col].setOpaque(true);
            labels[row][col].setBackground(Color.ORANGE);
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
