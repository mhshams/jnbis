package org.jnbis.api.handler;

import org.jnbis.api.model.Nist;
import org.jnbis.internal.NistDecoder;

import java.io.*;

/**
 * A handler for Nist files.
 */
public final class NistHandler {
    private static final NistDecoder DECODER = new NistDecoder();

    /**
     * decodes the NIST file with given file name and returns a <code>Nist</code> containing the decoded info.
     *
     * @param fileName the NIST file name, not null
     * @return a Nist instance containing decoded info, not null
     * @see Nist
     */
    public Nist decode(String fileName) {
        return decode(new File(fileName));
    }

    /**
     * decodes the given WSQ file and returns a <code>Nist</code> containing the decoded info.
     *
     * @param file the NIST file , not null
     * @return a Nist instance containing decoded info, not null
     * @see Nist
     */
    public Nist decode(File file) {
        try {
            return decode(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unexpected error.", e);
        }
    }

    /**
     * decodes the given NIST file with given <code>InputStream</code> and returns a <code>Nist</code> containing the decoded info.
     *
     * @param inputStream the NIST file input stream, not null
     * @return a Nist instance containing decoded info, not null
     * @see Nist
     */
    public Nist decode(InputStream inputStream) {
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
     * decodes the given WSQ file with given data as byte array  and returns a <code>Nist</code> containing the decoded info.
     *
     * @param data the NIST file as byte array, not null
     * @return a Nist instance containing decoded info, not null
     * @see Nist
     */
    public Nist decode(final byte[] data) {
        return DECODER.decode(data);
    }
}
