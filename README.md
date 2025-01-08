# ver-0.0.3
> This version add new User class with all the functionality and first prototype of LoginView

# User.java

This Java class represents a user with a unique ID, username, and a securely hashed password.

## Features

*   **User Creation:** Creates a new user with a provided ID, username, and password. The password is immediately hashed using SHA-256 and stored.
*   **Input Validation:** Validates username and password upon creation using regular expressions to enforce specific criteria:
*   **Username:** 3-16 alphanumeric characters, underscores, or hyphens.
*   **Password:** 8-16 characters, containing at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).
*   **Password Hashing:** Uses SHA-256 to hash passwords for secure storage.
*   **Password Verification:** Provides a method to verify a given password against the stored hash.
*   **Getters:** Provides getters for `userID` and `username`.

# LoginView.java

This class represents a simple login view for a Java application using Swing.

## Features

*   Creates a `JFrame` titled "LoginView".
*   Sets the default close operation to exit the application when the window is closed (`JFrame.EXIT_ON_CLOSE`).
*   Sets a fixed window size of 600x400 pixels and disables window resizing.
*   Adds a `JPanel` to contain the login form elements.
*   Uses `FlowLayout` to center the components (labels and text fields) within the panel.
*   Includes labels for "Username:" and "Password:".
*   Provides a `JTextField` for username input and a `JPasswordField` for password input.