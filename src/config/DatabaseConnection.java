package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides access to the MySQL database.
 *
 * This class centralizes database connection settings
 * and offers a reusable method for obtaining connections.
 *
 * @author Luis Gomez
 * @author Grace Solano
 * @author Dilan Badilla
 */
public class DatabaseConnection {

    /**
     * Database connection URL.
     */
    private static final String URL =
            "jdbc:mysql://localhost:3306/dropzone_db";

    /**
     * Database username.
     */
    private static final String USER =
            "dropzone_user";

    /**
     * Database password.
     */
    private static final String PASSWORD =
            "1234";

    /**
     * Creates and returns a connection to the database.
     *
     * @return Active database connection.
     * @throws SQLException If the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {

        Connection con =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println("Conexion exitosa a MySQL");

        return con;
    }
}