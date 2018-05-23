package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.api.model.record.HighResolutionBinaryFingerprint;

/**
 * @author ericdsoto
 */
public class HighResolutionBinaryFingerprintReader extends RecordReader {

    @Override
    public HighResolutionBinaryFingerprint read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T4::NULL pointer to T4 record");
        }

        HighResolutionBinaryFingerprint fingerprint = new HighResolutionBinaryFingerprint();

        //Assigning t4-Header values
        int length = (int) read4BytesAsInt(token);
        int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 18;

        if (token.pos + dataSize + 17 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 18;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);

        token.pos += length;
        fingerprint.setImageDesignationCharacter(String.valueOf(fingerPrintNo));
        fingerprint.setImageData(data);
        fingerprint.setLogicalRecordLength(String.valueOf(length));

        return fingerprint;
    }
}
