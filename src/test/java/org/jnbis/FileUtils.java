package org.jnbis;

import java.io.*;

public class FileUtils {
    public static void save(byte[] data, String name) {
        FileOutputStream bos = null;
        try {
            bos = new FileOutputStream(name);
            bos.write(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(bos);
        }
    }

    public static void saveAll(DecodedData decoded, DecodedData.Format format, String path) {
        File directory = new File(path);
        if (directory.exists()) {
            directory.mkdir();
        }
        for (Integer key : decoded.getBinaryKeys()) {
            DecodedData.BinaryData image = decoded.getBinary(key);
            if (image != null) {
                save(image.getData(), path + "/" + key + "." + format.code());
            }
        }
    }

    public static InputStream read(String name) {
        return FileUtils.class.getClassLoader().getResourceAsStream(name);
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
