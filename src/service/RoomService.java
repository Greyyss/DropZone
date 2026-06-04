package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import model.Room;
import model.User;

/**
 * Service responsible for room management.
 *
 * Handles room creation, room joining and
 * room retrieval operations.
 *
* @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 */
public class RoomService {

    private static final Map<String, Room> rooms = new HashMap<>();
    private static int nextId = 1;

    /**
     * Creates a new room.
     *
     * @param roomName Name of the room.
     * @return true if the room was created,
     * false if it already exists.
     */
    public boolean createRoom(String roomName) {

        if (rooms.containsKey(roomName)) {
            return false;
        }

        Room room = new Room(
                nextId++,
                roomName
        );

        rooms.put(roomName, room);

        return true;
    }

    /**
     * Adds a user to an existing room.
     *
     * @param roomName Name of the room.
     * @param user User joining the room.
     * @return true if successful, false otherwise.
     */
    public boolean joinRoom(String roomName, User user) {

        Room room = rooms.get(roomName);

        if (room == null) {
            return false;
        }

        room.addUser(user);

        return true;
    }

    /**
     * Returns a room by its name.
     *
     * @param roomName Room name.
     * @return Room instance.
     */
    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    /**
     * Returns the names of all available rooms.
     *
     * @return Set of room names.
     */
    public Set<String> getRoomNames() {
        return new HashSet<>(rooms.keySet());
    }
}