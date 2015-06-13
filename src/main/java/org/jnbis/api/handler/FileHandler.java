package org.jnbis.api.handler;

import java.io.*;

/**
 * A handler for files
 */
public final class FileHandler {
    private final byte[] data;

    /**
     * Creates a <code>FileHandler</code> with given file data as byte array.
     *
     * @param data the file as byte array, not null
     */
    public FileHandler(byte[] data) {
        this.data = data;
    }

    /**
     * Writes the file data in the specified file and returns the final <code>File</code>.
     *
     * @param fileName the given file name, not null
     * @return the final File, not null
     */
    public File asFile(String fileName) {
        File file = new File(fileName);
        try (FileOutputStream bos = new FileOutputStream(file)) {
            bos.write(data);
        } catch (IOException e) {
            throw new RuntimeException("unexpected error", e);
        }
        return file;
    }

    /**
     * Wraps the file data in a <code>InputStream</code> and returns it.
     *
     * @return the <code>InputStream</code> that is created with the file data, not null
     */
    public InputStream asInputStream() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Returns the file data as byte array.
     *
     * @return byte array that contains the file data, not null
     */
    public byte[] asByteArray() {
        return data;
    }
}
