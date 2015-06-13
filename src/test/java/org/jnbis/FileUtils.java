package org.jnbis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class FileUtils {

    public static byte[] read(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("unexpected error", e);
        }
    }

    public static byte[] read(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            throw new RuntimeException("unexpected error", e);
        }

        return buffer.toByteArray();
    }

    public static String absoluteFile(String name) {
        URL url = FileUtils.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("unexpected error: Null URL");
        }
        return url.getFile();
    }
}
