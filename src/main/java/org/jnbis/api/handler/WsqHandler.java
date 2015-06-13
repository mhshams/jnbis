package org.jnbis.api.handler;

import org.jnbis.internal.WsqDecoder;

import java.io.*;

/**
 * A handler for WSQ images.
 */
public final class WsqHandler {
    private static final WsqDecoder DECODER = new WsqDecoder();

    /**
     * decodes the WSQ image with given file name and returns a <code>BitmapHandler</code> containing the decoded info.
     *
     * @param fileName the WSQ image file name, not null
     * @return a BitmapHandler containing the decoded info, not null
     * @see BitmapHandler
     */
    public BitmapHandler decode(String fileName) {
        return decode(new File(fileName));
    }

    /**
     * decodes the given WSQ file and returns a <code>BitmapHandler</code> containing the decoded info.
     *
     * @param file the WSQ image file , not null
     * @return a BitmapHandler containing the decoded info, not null
     * @see BitmapHandler
     */
    public BitmapHandler decode(File file) {
        try {
            return decode(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unexpected error.", e);
        }
    }

    /**
     * decodes the given WSQ image with given <code>InputStream</code> and returns a <code>BitmapHandler</code> containing the decoded info.
     *
     * @param inputStream the WSQ image input stream, not null
     * @return a BitmapHandler containing the decoded info, not null
     * @see BitmapHandler
     */
    public BitmapHandler decode(InputStream inputStream) {
        try (BufferedInputStream input = new BufferedInputStream(inputStream);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            byte[] data = new byte[16384];
            int nRead;
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                output.write(data, 0, nRead);
            }
            output.flush();

            return decode(output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("unexpected error.", e);
        }
    }

    /**
     * decodes the given WSQ file with given data as byte array  and returns a <code>BitmapHandler</code> containing the decoded info.
     *
     * @param data the WSQ image as byte array, not null
     * @return a BitmapHandler containing the decoded info, not null
     * @see BitmapHandler
     */
    public BitmapHandler decode(final byte[] data) {
        return new BitmapHandler(DECODER.decode(data));
    }
}
