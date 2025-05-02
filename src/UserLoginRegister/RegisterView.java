package UserLoginRegister;

import GameHub.GameHubView;
import JDBC.ConnectionInit;
import JDBC.User.UserLoginJDBC;
import JDBC.User.UserRegisterJDBC;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static JDBC.ConnectionInit.connection;
import static UserLoginRegister.LoginView.userBalance;
import static UserLoginRegister.LoginView.userId;

/**
 * Represents the graphical user interface for user registration.
 * Provides fields for entering a username, password, and re-entering the password, a register button,
 * and a button to navigate to the login view. Handles user registration and redirects to the game hub upon success.
 */
public class RegisterView extends JFrame {

    /** Text field for entering the username. */
    private final JTextField loginText;

    /** Password field for entering the password. */
    private final JPasswordField passwordText;

    /** Password field for re-entering the password to confirm it. */
    private final JPasswordField reEnterPassword;

    /**
     * Constructs a new RegisterView, initializing the GUI components and setting up event listeners.
     */
    public RegisterView() {
        super("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        String helloLabelText = "Welcome to my application.\nEnter login and password to continue";
        String helloLabelTextToHTML = "<html><div style='text-align: center;'>" + helloLabelText.replace("\n", "<br>") + "</div></html>";
        JLabel helloLabel = new JLabel(helloLabelTextToHTML);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(helloLabel, gbc);

        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(loginLabel, gbc);

        loginText = new JTextField(15);
        gbc.gridx = 1;
        add(loginText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordText = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordText, gbc);

        JLabel emailLabel = new JLabel("Re enter password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);

        reEnterPassword = new JPasswordField(15);
        gbc.gridx = 1;
        add(reEnterPassword, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        JButton loginButton = new JButton("Already have an account? Login into it.");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        getRootPane().setDefaultButton(registerButton);

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

    /**
     * Performs the registration process by validating the entered username and passwords.
     * Checks if the passwords match and if the username is unique, then registers the user in the database.
     * On success, sets the user ID and balance, closes the registration window, and opens the game hub.
     * Displays an error message if the passwords do not match or the username already exists.
     *
     * @throws SQLException if a database error occurs during registration or data retrieval
     */
    private void performRegister() throws SQLException {
        String login = loginText.getText().trim();
        char[] password = passwordText.getPassword();
        String passwordStr = new String(password);
        if (!passwordStr.equals(reEnterPassword.getText())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
        } else {
            if (UserRegisterJDBC.isLoginExist(connection, login)) {
                JOptionPane.showMessageDialog(this, "Login already exist");
            } else {
                User user = new User(login, passwordStr);
                UserRegisterJDBC.insertUserData(connection, user.getUsername(), user.getHashedPassword(), 100);
                JOptionPane.showMessageDialog(this, "Welcome to casino");
                userId = UserLoginJDBC.userID(connection, login, passwordStr);
                userBalance = UserLoginJDBC.userBalance(connection, userId);
                dispose();
                new GameHubView();
            }
        }
    }
}