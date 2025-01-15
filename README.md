# ver-0.0.4
> This version add opening connection with database and close that connection

# User.java

This Java class represents a user with a unique ID, username, and a securely hashed password. (fixing)

## Features (fixed)

*   **Constructor User:** replace `(isValidPassword(hashedPassword) && isValidUserName(username))` to `(!isValidPassword(password) && !isValidUserName(username))`

*   **Adding getters And setters:** adding getter and setter to all the variables for feature work

# ConnectionInit.java

This class represents a simple database connection and close the connection using properties from `databaseAssets.properties`

## Features

*   **Database Connection Initialization:** Easy to establish connection to database using the JDBC API with proper configuration for the URL, username, and password thru separate file (databaseAssets.properties).

*   **Connection Closing Mechanism:** Includes a secure and robust method to close the database connection, preventing resource leaks and ensuring system stability.

*   **Error Handling:** Provides comprehensive error messages during connection initialization or closure, making debugging straightforward and efficient.

*   **Modular Design:** Encapsulates database connection logic within a dedicated ConnectionInit class, promoting code reuse and maintainability.

*   **Using mysql-connector-j-9.0.0:** To connect to the database we are using [Mysql-connector](https://dev.mysql.com/downloads/)