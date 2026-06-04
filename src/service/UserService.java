package service;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

/**
 * Service responsible for user-related operations.
 *
 * Provides authentication functionality by validating
 * user credentials against the database and returning
 * the corresponding user information.
 * @author Grace Solano
 * @author Dilan Badilla
 * @author Luis Gomez
 */
public class UserService {

    /**
     * Authenticates a user using the provided credentials.
     *
     * Searches the database for a user whose username and
     * password match the supplied values.
     *
     * @param namee Username entered by the user.
     * @param password Password entered by the user.
     * @return A User object if authentication is successful;
     *         otherwise null.
     */
    public User login(String name, String password) {

        String sql =
                "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name.trim());
            statement.setString(2, password.trim());

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                return new User(
                        result.getInt("id"),
                        result.getString("nombre"),
                        result.getString("rol")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}