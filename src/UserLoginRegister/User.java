package UserLoginRegister;

import Roulette.Bet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Represents a user in the system, storing their identification, credentials, level, experience, and balance.
 * Provides methods for validating and hashing passwords, as well as managing user data.
 */
@Data
@ToString
public class User {

    /** The unique identifier for the user. */
    private int userID;

    /** The username chosen by the user. */
    private String username;

    /** The hashed password for secure storage. */
    private String hashedPassword;

    /** The user's current level in the system. */
    private int level;

    /** The user's accumulated experience points. */
    private int experience;

    /** The user's current balance in the system. */
    @Getter
    private int balance = 1000;

    /**
     * Constructs a new User with the specified username and password.
     * Validates the username and password, and hashes the password for secure storage.
     * Initializes the user with default level 1, zero experience, and a starting balance.
     *
     * @param username the username for the user
     * @param password the password for the user
     * @throws IllegalArgumentException if the username or password is invalid
     */
    public User(String username, String password) {
        if (!isValidPassword(password) || !isValidUserName(username)) {
            throw new IllegalArgumentException("Invalid Username or Password");
        }
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.level = 1;
        this.experience = 0;
    }

//    public void addExperience(int experienceToAdd) {
//        this.experience += experienceToAdd;
//        updateLevel();
//    }
//
//    public int getExperienceToNextLevel() {
//        int experienceNeeded = calculateExperienceToNextLevel(this.level) - this.experience;
//        return Math.max(0, experienceNeeded); // Zwracamy 0, jeśli użytkownik już przekroczył próg
//    }
//
//    private void updateLevel() {
//        int experienceToNextLevel = calculateExperienceToNextLevel(this.level);
//
//        while (this.experience >= experienceToNextLevel) {
//            this.level++;
//            this.experience -= experienceToNextLevel;
//            experienceToNextLevel = calculateExperienceToNextLevel(this.level);
//            System.out.println("Użytkownik " + this.username + " awansował na poziom " + this.level + "!");
//        }
//    }
//
//    public int calculateExperienceToNextLevel(int level) {
//        return 50 * level + 50;
//    }
//

    /**
     * Validates the username against a regular expression.
     * The username must be 3-16 characters long and can include letters, numbers, underscores, and hyphens.
     *
     * @param userName the username to validate
     * @return true if the username is valid, false otherwise
     */
    private boolean isValidUserName(String userName) {
        String userRegex = "^[a-zA-Z0-9_-]{3,16}$";
        return Pattern.compile(userRegex).matcher(userName).matches();
    }

    /**
     * Validates the password against a regular expression.
     * The password must be 8-16 characters long, include at least one lowercase letter, one uppercase letter,
     * one digit, and one special character.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    /**
     * Hashes the provided password using the SHA-256 algorithm.
     *
     * @param password the password to hash
     * @return the hashed password as a hexadecimal string, or null if the hashing algorithm is unavailable
     */
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

//    public boolean verifyPassword(String password) {
//        return this.hashedPassword.equals(hashPassword(password));
//    }



    // Adds winnings to the player's balance
//    public void addWinnings(int amount) {
//        balance += amount;
//    }
}
