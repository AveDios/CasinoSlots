package MenuMain;

import JDBC.Slots.BalanceChanger;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.*;

public class AddUserBalanceView extends JFrame {
    public AddUserBalanceView() {
        super("Add User Balance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Zamyka tylko to okno, a nie całą aplikację
        setSize(300, 200); // Ustawienie rozmiaru okna
        setLocationRelativeTo(null); // Wyśrodkowanie okna
        setLayout(new GridBagLayout()); // Użycie GridBagLayout dla lepszej kontroli

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Odstępy między elementami
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etykieta z nazwą użytkownika
        JLabel usernameLabel = new JLabel("User: " + username, SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        // Pole tekstowe do wpisania salda
        JTextField balanceField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(balanceField, gbc);

        // Przycisk "OK"
        JButton okButton = new JButton("OK");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(okButton, gbc);

        // Akcja przycisku "OK"
        okButton.addActionListener(e -> {
            try {
                // Pobieranie wartości z pola tekstowego w momencie kliknięcia
                String balanceText = balanceField.getText().trim();
                if (balanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a balance amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double balance = Double.parseDouble(balanceText);
                if (balance <= 0) {
                    JOptionPane.showMessageDialog(this, "Balance must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Zmiana salda w bazie danych
                BalanceChanger.changeBalance(connection, userId, userBalance + balance);
                JOptionPane.showMessageDialog(this, "Balance updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Zamknięcie bieżącego okna i otwarcie MenuMainView
                dispose();
                MenuMainView menuMainView = new MenuMainView();
                menuMainView.setVisible(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
