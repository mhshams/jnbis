package org.jnbis;

import org.jnbis.internal.InternalNistDecoder;

import java.io.*;

/**
 * @author hamed
 * @version 1.0
 * @since Apr 29, 2007
 */
public class NistDecoder {

    public Nist decode(String fileName, Nist.Format fingerImageFormat) throws IOException {
        return decode(new File(fileName), fingerImageFormat);
    }

    public Nist decode(File file, Nist.Format fingerImageFormat) throws IOException {
        return decode(new FileInputStream(file), fingerImageFormat);
    }

    public Nist decode(InputStream inputStream, Nist.Format fingerImageFormat) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray(), fingerImageFormat);
    }

    public Nist decode(byte[] nist, Nist.Format fingerImageFormat) {
        return new InternalNistDecoder().decode(nist);
    }
}
