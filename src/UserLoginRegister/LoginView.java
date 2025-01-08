package UserLoginRegister;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    public  LoginView() {
        super("LoginView");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setVisible(true);

        addGuiComponent();
    }

    private void addGuiComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel("Username:"));
        JTextField username = new JTextField(10);
        panel.add(username);
        panel.add(new JLabel("Password:"));
        JPasswordField password = new JPasswordField(10);
        panel.add(password);

        getContentPane().add(panel);
    }
}
