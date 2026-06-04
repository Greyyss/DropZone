package model;

/**
 * Represents a user connected to the DropZone system.
 *
 * Stores basic user information such as identifier,
 * username and assigned role.
 *
 * @author Luis Gomez
 * @author Grace Solano
 * @author Dilan
 */
public class User {

    private int id;
    private String name;
    private String role;

    /**
     * Creates a new user.
     *
     * @param id User identifier.
     * @param name Username.
     * @param role User role.
     */
    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Returns the user identifier.
     *
     * @return User ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the username.
     *
     * @return Username.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user role.
     *
     * @return User role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the username as a string representation.
     *
     * @return Username.
     */
    @Override
    public String toString() {
        return name;
    }
}