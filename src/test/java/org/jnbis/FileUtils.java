package org.jnbis;

import org.jnbis.record.FacialAndSmtImage;
import org.jnbis.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.record.VariableResolutionFingerprint;

import java.io.*;

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
