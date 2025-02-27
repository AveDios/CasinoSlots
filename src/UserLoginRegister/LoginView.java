package UserLoginRegister;

import TwoDimensionalSlots.TwoDimensionalSlotsView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginView extends JFrame {
    private final JTextField loginText;
    private final JPasswordField passwordText;

    public  LoginView() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
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

        // Przycisk logowania
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Przycisk zajmuje dwie kolumny
        add(loginButton, gbc);

        getRootPane().setDefaultButton(loginButton); // Obsługa Entera

        loginButton.addActionListener(e -> performLogin());
        passwordText.addActionListener(e -> performLogin());
        loginText.addActionListener(e -> performLogin());

        setVisible(true);
    }

    private void performLogin() {
        String login = loginText.getText().trim();
        char[] password = passwordText.getPassword();

        if (login.equals("admin") && Arrays.equals(password, "admin".toCharArray())) {
            dispose();
            new TwoDimensionalSlotsView();
        }else {
            JOptionPane.showMessageDialog(this, "Wrong login or password", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Arrays.fill(password, '\0');
    }
}
