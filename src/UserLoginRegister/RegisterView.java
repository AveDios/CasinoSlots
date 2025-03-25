package UserLoginRegister;

import GameHub.GameHubView;
import JDBC.ConnectionInit;
import JDBC.User.UserLoginJDBC;
import JDBC.User.UserRegisterJDBC;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.userBalance;
import static UserLoginRegister.LoginView.userId;

public class RegisterView extends JFrame {
    private final JTextField loginText;
    private final JPasswordField passwordText;
    private final JTextField reEnterPassword;  // Dodane pole tekstowe

    public  RegisterView() {
        super("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350); // Zwiększamy rozmiar okna, aby pomieścić nowe pole
        setLocationRelativeTo(null);
        setResizable(false);

        // Ustawiamy layout na GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Odstępy między komponentami

        // Etykieta powitalna
        String helloLabelText = "Welcome to my application.\nEnter login and password to continue";
        String helloLabelTextToHTML = "<html><div style='text-align: center;'>" + helloLabelText.replace("\n", "<br>") + "</div></html>";
        JLabel helloLabel = new JLabel(helloLabelTextToHTML);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Dodajemy etykietę powitalną na górze
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Etykieta zajmuje dwie kolumny
        add(helloLabel, gbc);

        // Etykieta i pole tekstowe dla nazwy użytkownika
        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Etykieta zajmuje jedną kolumnę
        add(loginLabel, gbc);

        loginText = new JTextField(15);
        gbc.gridx = 1;
        add(loginText, gbc);

        // Etykieta i pole tekstowe dla hasła
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordText = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordText, gbc);

        // Etykieta i pole tekstowe dla e-maila
        JLabel emailLabel = new JLabel("Re enter password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);

        reEnterPassword = new JPasswordField(15);  // Nowe pole tekstowe dla emaila
        gbc.gridx = 1;
        add(reEnterPassword, gbc);

        // Przycisk rejestracji
        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Przycisk zajmuje dwie kolumny
        add(registerButton, gbc);

        JButton loginButton = new JButton("Already have an account? Login into it.");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        getRootPane().setDefaultButton(registerButton); // Obsługa Entera

        registerButton.addActionListener(e -> {
            try {
                performRegister();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        passwordText.addActionListener(e -> {
            try {
                performRegister();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginText.addActionListener(e -> {
            try {
                performRegister();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        reEnterPassword.addActionListener(e -> {
            try {
                performRegister();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        loginButton.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        setVisible(true);
    }

    private void performRegister() throws SQLException {
        // Logika rejestracji
        String login = loginText.getText().trim();
        char[] password = passwordText.getPassword();
        String passwordStr = new String(password); // Konwertowanie char[] na String
        if (!passwordStr.equals(reEnterPassword.getText())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
        } else {
            if (UserRegisterJDBC.isLoginExist(connection, login)) {
                JOptionPane.showMessageDialog(this, "Login already exist");
            }else {
                User user = new User(login, passwordStr);
                UserRegisterJDBC.insertUserData(connection, user.getUsername(), user.getHashedPassword(), 100);
                JOptionPane.showMessageDialog(this, "Welcome to casino");
                userId = UserLoginJDBC.userID(connection, login,passwordStr);
                userBalance = UserLoginJDBC.userBalance(connection,userId);
                dispose();
                new GameHubView();
            }

        }
    }
}
