package util;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class ChecksumUtil {

    public static String calculateMD5(
            String path)
            throws Exception {

        MessageDigest md =
                MessageDigest.getInstance("MD5");

        FileInputStream fis =
                new FileInputStream(path);

        byte[] buffer =
                new byte[4096];

        int bytesRead;

        while ((bytesRead =
                fis.read(buffer))
                != -1) {

            md.update(
                    buffer,
                    0,
                    bytesRead);
        }

        fis.close();

        byte[] digest =
                md.digest();

        StringBuilder sb =
                new StringBuilder();

        for (byte b : digest) {

            sb.append(
                    String.format(
                            "%02x",
                            b));
        }

        return sb.toString();
    }
}