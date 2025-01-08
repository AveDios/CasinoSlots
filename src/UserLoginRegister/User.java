package UserLoginRegister;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User {
    private int userID;
    private String username;
    private String hashedPassword;

    User(int userID, String username, String password) {
        if (isValidPassword(hashedPassword) && isValidPassword(username)) {
            throw new IllegalArgumentException("Invalid Username or Password");
        }
        this.userID = userID;
        this.username = username;
        this.hashedPassword = hashPassword(password);
    }


    private boolean isValidUserName(String userName) {
        String userRegex = "^[a-zA-Z0-9_-]{3,16}$";
        return Pattern.compile(userRegex).matcher(userName).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public boolean verifyPassword(String password) {
        return this.hashedPassword.equals(hashPassword(password));
    }
}
