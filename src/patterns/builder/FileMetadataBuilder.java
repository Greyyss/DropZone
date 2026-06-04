package patterns.builder;

import model.FileMetadata;

/**
 * Builder class used to create FileMetadata objects.
 *
 * Implements the Builder Pattern to simplify the
 * construction of file metadata instances.
 * @author Grace Solano
 * @author Dilan Badilla
 * @author Luis Gomez
 */
public class FileMetadataBuilder {

    private int id;
    private String name;
    private long size;
    private String owner;
    private String checksum;
    private String path;

    /**
     * Sets the file identifier.
     *
     * @param id File ID.
     * @return Current builder instance.
     */
    public FileMetadataBuilder id(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the file name.
     *
     * @param name File name.
     * @return Current builder instance.
     */
    public FileMetadataBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the file size.
     *
     * @param size File size in bytes.
     * @return Current builder instance.
     */
    public FileMetadataBuilder size(long size) {
        this.size = size;
        return this;
    }

    /**
     * Sets the file owner.
     *
     * @param owner File owner.
     * @return Current builder instance.
     */
    public FileMetadataBuilder owner (String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Sets the file checksum.
     *
     * @param checksum Checksum value.
     * @return Current builder instance.
     */
    public FileMetadataBuilder checksum(String checksum) {
        this.checksum = checksum;
        return this;
    }

    /**
     * Sets the file storage path.
     *
     * @param path File path.
     * @return Current builder instance.
     */
    public FileMetadataBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Creates and returns a FileMetadata object.
     *
     * @return New FileMetadata instance.
     */
    public FileMetadata build() {
        return new FileMetadata(
                id,
                name,
                size,
                owner,
                checksum,
                path
        );
    }
}