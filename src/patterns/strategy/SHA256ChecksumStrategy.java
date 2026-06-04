package patterns.strategy;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * SHA-256 checksum implementation.
 *
 * Implements the Strategy Pattern to provide
 * a SHA-256 hashing algorithm for file validation.
 *
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 */
public class SHA256ChecksumStrategy implements ChecksumStrategy {

    /**
     * Calculates the SHA-256 checksum of a file.
     *
     * @param filePath Path of the file to process.
     * @return SHA-256 hash value as a hexadecimal string.
     * @throws Exception If an error occurs while reading the file.
     */
    @Override
    public String calculate(String filePath) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        FileInputStream fis = new FileInputStream(filePath);

        byte[] buffer = new byte[4096];

        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        fis.close();

        byte[] hashBytes = digest.digest();

        StringBuilder hexString = new StringBuilder();

        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}