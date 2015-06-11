package org.jnbis;

import org.jnbis.record.LowResolutionBinaryFingerprint;

/**
 * Created by ericdsoto on 6/8/15.
 */
public class LowResolutionBinaryFingerprintReader extends RecordReader {

    NistHelper.Token token;
    LowResolutionBinaryFingerprint fingerprint;

    public LowResolutionBinaryFingerprintReader(NistHelper.Token token, LowResolutionBinaryFingerprint fingerprint) {
        this.token = token;
        this.fingerprint = fingerprint;
    }

    @Override
    public LowResolutionBinaryFingerprint read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T3::NULL pointer to T3 record");
        }

        //Assigning t3-Header values
        Integer length = (int) readInt(token);
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
        fingerprint.setLogicalRecordLength(length.toString());

        return fingerprint;
    }
}
