package org.jnbis.api.handler;

import org.jnbis.internal.InternalWsqDecoder;

import java.io.*;

/**
 *
 */
public final class WsqHandler {

    public BitmapHandler decode(String fileName) throws IOException {
        return decode(new File(fileName));
    }

    public BitmapHandler decode(File file) throws IOException {
        return decode(new FileInputStream(file));
    }

    public BitmapHandler decode(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray());
    }

    public BitmapHandler decode(final byte[] data) {
        return new BitmapHandler(new InternalWsqDecoder().decode(data));
    }
}
