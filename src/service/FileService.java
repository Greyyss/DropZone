package service;

import java.util.ArrayList;
import java.util.List;
import model.FileMetadata;
import model.Room;
import patterns.builder.FileMetadataBuilder;

/**
 * Service responsible for file management operations.
 *
 * Provides functionality for creating file metadata,
 * storing files in rooms and retrieving file lists.
 *
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 */
public class FileService {

    private static int nextId = 1;

    /**
     * Creates a new FileMetadata instance.
     *
     * @param name File name.
     * @param size File size in bytes.
     * @param owner File owner.
     * @param checksum File checksum value.
     * @param path Physical file path.
     * @return Created FileMetadata object.
     */
    public FileMetadata createMetadata(
            String name,
            long size,
            String owner,
            String checksum,
            String path) {

        return new FileMetadataBuilder()
                .id(nextId++)
                .name(name)
                .size(size)
                .owner(owner)
                .checksum(checksum)
                .path(path)
                .build();
    }

    /**
     * Adds a file to a room.
     *
     * @param room Target room.
     * @param file File metadata to add.
     */
    public void addFileToRoom(Room room, FileMetadata file) {
        room.addFile(file);
    }

    /**
     * Returns all files stored in a room.
     *
     * @param room Room to inspect.
     * @return List of files.
     */
    public List<FileMetadata> listFiles(Room room) {

        if (room == null) {
            return new ArrayList<>();
        }

        return room.getFiles();
    }
}