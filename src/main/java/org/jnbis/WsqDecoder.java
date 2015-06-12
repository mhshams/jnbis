package org.jnbis;

import org.jnbis.internal.InternalWsqDecoder;

import java.io.*;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 1, 2007
 */
public class WsqDecoder {

    public Bitmap decode(String fileName) throws IOException {
        return decode(new File(fileName));
    }

    public Bitmap decode(File file) throws IOException {
        return decode(new FileInputStream(file));
    }

    public Bitmap decode(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray());
    }

    public Bitmap decode(final byte[] data) {
        return new InternalWsqDecoder().decode(data);
    }
}
