package org.jnbis;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class FileUtils {

    public static byte[] read(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public static byte[] read(InputStream stream) throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(stream);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            byte[] data = new byte[16384];
            int nRead;
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                output.write(data, 0, nRead);
            }
            output.flush();
            return output.toByteArray();
        }
    }

    public static String absolute(String name) {
        URL url = FileUtils.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("unexpected error: Null URL");
        }
        return url.getFile();
    }
}
