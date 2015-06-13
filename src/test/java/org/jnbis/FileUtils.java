package org.jnbis;

import org.jnbis.record.FacialAndSmtImage;
import org.jnbis.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.record.VariableResolutionFingerprint;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class FileUtils {
    public static void save(byte[] data, String name) {
        FileOutputStream bos = null;
        try {
            bos = new FileOutputStream(name);
            bos.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(bos);
        }
    }

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

    public static void saveAll(DecodedData decoded, DecodedData.Format format, String path) {
        File directory = new File(path);
        if (directory.exists()) {
            directory.mkdir();
        }
        for (Integer key : decoded.getFacialSmtKeys()) {
            FacialAndSmtImage image = decoded.getFacialAndSmtImage(key);
            if (image != null) {
                save(image.getImageData(), path + "/" + key + "." + format.code());
            }
        }
        for (Integer key : decoded.getHiResBinaryFingerPrintKeys()) {
            HighResolutionGrayscaleFingerprint image = decoded.getHiResGrayscaleFingerprint(key);
            if (image != null) {
                save(image.getImageData(), path + "/" + key + "." + format.code());
            }
        }
        for (Integer key : decoded.getVariableResFingerprintKeys()) {
            VariableResolutionFingerprint image = decoded.getVariableResFingerprint(key);
            if (image != null) {
                save(image.getImageData(), path + "/" + key + "." + format.code());
            }
        }
    }

    public static String absoluteFile(String name) {
        URL url = FileUtils.class.getClassLoader().getResource(name);
        return url != null ? url.getFile() : null;
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
