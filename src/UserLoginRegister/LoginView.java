package UserLoginRegister;

import GameHub.GameHubView;
import JDBC.ConnectionInit;
import JDBC.User.UserLoginJDBC;
import MenuMain.MenuMainView;
import Slots.TwoDimensionalSlots.TwoDimensionalSlotsView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

import static JDBC.ConnectionInit.connection;
import static JDBC.User.UserLoginJDBC.isCorrectLogin;

/**
 * Represents the graphical user interface for user login.
 * Provides fields for entering a username and password, a login button, and a button to navigate to the registration view.
 * Handles user authentication and redirects to the main menu upon successful login.
 */
public class LoginView extends JFrame {

    /** Text field for entering the username. */
    private final JTextField loginText;

    /** Password field for entering the password. */
    private final JPasswordField passwordText;

    /** The ID of the logged-in user. */
    public static int userId;

    /** The username of the logged-in user. */
    public static String username;

    /** The current balance of the logged-in user. */
    public static double userBalance;

    /**
     * Constructs a new LoginView, initializing the GUI components and setting up event listeners.
     */
    public LoginView() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
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

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        JButton createAccountButton = new JButton("Register if you dont have account: Create Account");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(createAccountButton, gbc);

        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> {
            try {
                performLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        passwordText.addActionListener(e -> {
            try {
                performLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginText.addActionListener(e -> {
            try {
                performLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        createAccountButton.addActionListener(e -> {
            dispose();
            new RegisterView();
        });

        setVisible(true);
    }

    /**
     * Performs the login process by validating the entered username and password against the database.
     * On successful login, retrieves the user ID, username, and balance, closes the login window,
     * and opens the main menu. Displays an error message if the login fails.
     *
     * @throws SQLException if a database error occurs during authentication or data retrieval
     */
    private void performLogin() throws SQLException {
        String login = loginText.getText().trim();
        char[] password = passwordText.getPassword();
        String passwordStr = new String(password);

        if (isCorrectLogin(login, passwordStr)) {
            userId = UserLoginJDBC.userID(connection, login, passwordStr);
            username = UserLoginJDBC.getUserName(connection, userId);
            userBalance = UserLoginJDBC.userBalance(connection, userId);
            dispose();
            new MenuMainView();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong login or password", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Arrays.fill(password, '\0');
    }
}