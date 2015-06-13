package org.jnbis;

import org.jnbis.internal.InternalNistDecoder;

import java.io.*;

/**
 * @author hamed
 * @version 1.0
 * @since Apr 29, 2007
 */
public class NistDecoder {

    public DecodedData decode(String fileName, DecodedData.Format fingerImageFormat) throws IOException {
        return decode(new File(fileName), fingerImageFormat);
    }

    public DecodedData decode(File file, DecodedData.Format fingerImageFormat) throws IOException {
        return decode(new FileInputStream(file), fingerImageFormat);
    }

    public DecodedData decode(InputStream inputStream, DecodedData.Format fingerImageFormat) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray(), fingerImageFormat);
    }

    public DecodedData decode(byte[] nist, DecodedData.Format fingerImageFormat) {
        return new InternalNistDecoder().decode(nist, fingerImageFormat);
    }
}
