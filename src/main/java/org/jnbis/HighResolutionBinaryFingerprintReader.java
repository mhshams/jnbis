package org.jnbis;

import org.jnbis.record.HighResolutionBinaryFingerprint;

/**
 * Created by ericdsoto on 6/8/15.
 */
public class HighResolutionBinaryFingerprintReader extends RecordReader {

    NistHelper.Token token;
    HighResolutionBinaryFingerprint fingerprint;

    public HighResolutionBinaryFingerprintReader(NistHelper.Token token, HighResolutionBinaryFingerprint fingerprint) {
        this.token = token;
        this.fingerprint = fingerprint;
    }

    @Override
    public HighResolutionBinaryFingerprint read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T4::NULL pointer to T4 record");
        }

        //Assigning t4-Header values
        int length = (int) readInt(token);
        int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 18;

        if (token.pos + dataSize + 17 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 18;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);

        token.pos += length;
        fingerprint.setImageDesignationCharacter(Integer.toString(fingerPrintNo));
        fingerprint.setImageData(data);

        return fingerprint;
    }
}
