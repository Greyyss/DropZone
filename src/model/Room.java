package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a room where users can share and manage files.
 *
 * Each room contains a list of connected users and
 * uploaded files.
 *
 * @author Grace
 * @author Dilan
 * @author Luis
 */
public class Room {

    /** Unique room identifier. */
    private int id;

    /** Room name. */
    private String name;

    /** Users currently in the room. */
    private List<User> users;

    /** Files available in the room. */
    private List<FileMetadata> files;

    /**
     * Creates a new room.
     *
     * @param id room identifier
     * @param name room name
     */
    public Room(int id, String name) {

        this.id = id;
        this.name = name;

        this.users = Collections.synchronizedList(new ArrayList<>());
        this.files = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Adds a user to the room.
     *
     * @param user user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Adds a file to the room.
     *
     * @param file file metadata to add
     */
    public void addFile(FileMetadata file) {
        files.add(file);
    }

    /**
     * Returns the users in the room.
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Returns the files in the room.
     *
     * @return list of files
     */
    public List<FileMetadata> getFiles() {
        return files;
    }

    /**
     * Returns the room identifier.
     *
     * @return room id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the room name.
     *
     * @return room name
     */
    public String getName() {
        return name;
    }
}