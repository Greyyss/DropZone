package model;

import java.time.LocalDateTime;

/**
 * Represents the metadata of a file stored in the system.
 *
 * Contains information such as file name, size,
 * owner, upload date, checksum and storage path.
 *
 * @author Grace Solano
 */
public class FileMetadata {

    private int id;
    private String name;
    private long size;
    private String owner;
    private LocalDateTime date;
    private String checksum;
    private String path;

    /**
     * Creates a new file metadata object.
     *
     * @param id File identifier.
     * @param name File name.
     * @param size File size in bytes.
     * @param owner File owner.
     * @param checksum File checksum value.
     * @param path Physical storage path.
     */
    public FileMetadata(
            int id,
            String name,
            long size,
            String owner,
            String checksum,
            String path) {

        this.id = id;
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.checksum = checksum;
        this.path = path;
        this.date = LocalDateTime.now();
    }

    /**
     * Returns the file identifier.
     *
     * @return File ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the file name.
     *
     * @return File name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the file size.
     *
     * @return File size in bytes.
     */
    public long getSize() {
        return size;
    }

    /**
     * Returns the file owner.
     *
     * @return Owner name.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns the upload date.
     *
     * @return Upload date and time.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns the file checksum.
     *
     * @return Checksum value.
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Returns the physical storage path.
     *
     * @return File path.
     */
    public String getPath() {
        return path;
    }
}