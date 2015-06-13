package org.jnbis.api.handler;

import java.io.*;

/**
 *
 */
public final class FileHandler {
    private final byte[] data;

    public FileHandler(byte[] data) {
        this.data = data;
    }

    public File save(String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream bos = new FileOutputStream(file)) {
            bos.write(data);
        }
        return file;
    }

    public InputStream asInputStream() {
        return new ByteArrayInputStream(data);
    }

    public byte[] asByteArray() {
        return data;
    }
}
