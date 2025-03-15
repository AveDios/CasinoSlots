package UserLoginRegister;

import Roulette.Bet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

@Data @ToString
public class User {
    private int userID;
    private String username;
    private String hashedPassword;
    private int level;
    private int experience;
    @Getter
    private int balance = 1000;

    public User(String username, String password) {
        if (!isValidPassword(password) && !isValidUserName(username)) {
            throw new IllegalArgumentException("Invalid Username or Password");
        }
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.level = 1;
        this.experience = 0;
    }

    public void addExperience(int experienceToAdd) {
        this.experience += experienceToAdd;
        updateLevel();
    }

    public int getExperienceToNextLevel() {
        int experienceNeeded = calculateExperienceToNextLevel(this.level) - this.experience;
        return Math.max(0, experienceNeeded); // Zwracamy 0, jeśli użytkownik już przekroczył próg
    }

    private void updateLevel() {
        int experienceToNextLevel = calculateExperienceToNextLevel(this.level);

        while (this.experience >= experienceToNextLevel) {
            this.level++;
            this.experience -= experienceToNextLevel;
            experienceToNextLevel = calculateExperienceToNextLevel(this.level);
            System.out.println("Użytkownik " + this.username + " awansował na poziom " + this.level + "!");
        }
    }

    public int calculateExperienceToNextLevel(int level) {
        return 50 * level + 50;
    }


    private boolean isValidUserName(String userName) {
        String userRegex = "^[a-zA-Z0-9_-]{3,16}$";
        return Pattern.compile(userRegex).matcher(userName).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    public static String hashPassword(String password) {
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

    public boolean verifyPassword(String password) {
        return this.hashedPassword.equals(hashPassword(password));
    }



    // Adds winnings to the player's balance
    public void addWinnings(int amount) {
        balance += amount;
    }
}
