package MenuMain;

import GameHub.GameHubView;
import UserLoginRegister.LoginView;

import javax.swing.*;
import java.awt.*;

import static UserLoginRegister.LoginView.*;

public class MenuMainView extends JFrame {
    public MenuMainView() {
        // Ustawienia podstawowe okna
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Rozmiar okna
        setLocationRelativeTo(null); // Wyśrodkowanie okna na ekranie

        // Ustawienie menedżera układu
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Odstępy między elementami

        // Przycisk "Logout" w prawym górnym rogu
        JButton logoutButton = new JButton("Logout");
        gbc.gridx = 1; // Kolumna 1
        gbc.gridy = 0; // Wiersz 0
        gbc.anchor = GridBagConstraints.NORTHEAST; // Wyrównanie do prawego górnego rogu
        add(logoutButton, gbc);

        // Etykieta powitalna "HELLO username"
        JLabel welcomeLabel = new JLabel("HELLO " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Większa czcionka
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 1; // Wiersz 1
        gbc.gridwidth = 2; // Zajmuje 2 kolumny
        gbc.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie
        add(welcomeLabel, gbc);

        // Przycisk "Play"
        JButton playButton = new JButton("Play");
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 2; // Wiersz 2
        gbc.gridwidth = 2; // Zajmuje 2 kolumny
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rozciąga przycisk w poziomie
        add(playButton, gbc);

        // Przycisk "Add balance"
        JButton addBalanceButton = new JButton("Add balance");
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 3; // Wiersz 3
        gbc.gridwidth = 2; // Zajmuje 2 kolumny
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rozciąga przycisk w poziomie
        add(addBalanceButton, gbc);

        // Akcje przycisków (opcjonalne)
        logoutButton.addActionListener(e -> {
            // Tutaj kod do wylogowania, np. zamknięcie okna
            dispose(); // Zamyka okno
            username = null;
            userId = 0;
            new LoginView();
        });

        playButton.addActionListener(e -> {
            dispose();
            new GameHubView();
        });

        addBalanceButton.addActionListener(e -> {
            dispose();
            new AddUserBalanceView();
        });

        setVisible(true);
    }
}
