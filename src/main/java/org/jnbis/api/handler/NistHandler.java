package org.jnbis.api.handler;

import org.jnbis.api.model.Nist;
import org.jnbis.internal.InternalNistDecoder;

import java.io.*;

/**
 *
 */
public final class NistHandler {

    public Nist decode(String fileName) throws IOException {
        return decode(new File(fileName));
    }

    public Nist decode(File file) throws IOException {
        return decode(new FileInputStream(file));
    }

    public Nist decode(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray());
    }

    public Nist decode(final byte[] data) {
        return new InternalNistDecoder().decode(data);
    }
}
