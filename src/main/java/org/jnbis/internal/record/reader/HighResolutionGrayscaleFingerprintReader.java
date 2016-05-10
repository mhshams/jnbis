package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.internal.NistHelper;

/**
 * @author ericdsoto
 */
public class HighResolutionGrayscaleFingerprintReader extends RecordReader {

    @Override
    public HighResolutionGrayscaleFingerprint read(NistHelper.Token token) {

        HighResolutionGrayscaleFingerprint fingerprint = new HighResolutionGrayscaleFingerprint();

        ByteBuffer buffer = token.buffer;
        
        /* Total length of record, including field 001 */
        int recordLength = buffer.getInt();
        fingerprint.setLogicalRecordLength(recordLength);

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

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        fingerprint.setIdc(idc);
        fingerprint.setImpressionType(imp);
        fingerprint.setFingerPosition(fgp);
        fingerprint.setImageScanningResolution(isr);
        fingerprint.setHorizontalLineLength(hll);
        fingerprint.setVerticalLineLength(vll);
        fingerprint.setCompressionAlgorithm(String.valueOf(gca));
        fingerprint.setImageData(data);

        return fingerprint;
    }
}
