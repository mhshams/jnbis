package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.internal.NistHelper;

/**
 * @author ericdsoto
 */
public class HighResolutionGrayscaleFingerprintReader extends RecordReader {

    @Override
    public HighResolutionGrayscaleFingerprint read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T4::NULL pointer to T4 record");
        }

        HighResolutionGrayscaleFingerprint fingerprint = new HighResolutionGrayscaleFingerprint();

        /* Total length of record, including 4.001 */
        int recordLength = readInt(token);
        /*
         * We read 4 bytes to get the length, so 4.002 starts 4 bytes from token
         * pos
         */
        int fieldStart = token.pos + 4;
        /* Fixed byte length for 4.002 to 4.008 (Note: 4.005 has 6 occurrences (bytes)) */
        int fieldSize = 14;
        /* Image data size from end of 4.008 to recordLength */
        int imageSize = recordLength - 4 - fieldSize;

        /* Wrap the token buffer so that we can get the correct byte order easily */
        ByteBuffer buffer = ByteBuffer.wrap(token.buffer, fieldStart, fieldSize);
        buffer.order(ByteOrder.BIG_ENDIAN);

        int idc = buffer.get();
        int imp = buffer.get();
        int[] fgp = new int[6];
        for (int f = 0; f < fgp.length; f++) {
            fgp[f] = buffer.get();
        }
        int isr = buffer.get();
        int hll = buffer.getShort();
        int vll = buffer.getShort();
        int gca = buffer.get();

        /*
         * We can't use the ByteBuffer for this as it does the endian conversion
         * on the data
         */
        byte[] data = new byte[imageSize];
        System.arraycopy(token.buffer, fieldStart + fieldSize, data, 0, imageSize);

        fingerprint.setIdc(idc);
        fingerprint.setImpressionType(imp);
        fingerprint.setFingerPosition(fgp);
        fingerprint.setImageScanningResolution(isr);
        fingerprint.setHorizontalLineLength(hll);
        fingerprint.setVerticalLineLength(vll);
        fingerprint.setCompressionAlgorithm(String.valueOf(gca));
        fingerprint.setImageData(data);

        token.pos += recordLength;
        return fingerprint;
    }
}
