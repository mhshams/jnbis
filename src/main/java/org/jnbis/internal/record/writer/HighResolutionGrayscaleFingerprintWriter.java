package org.jnbis.internal.record.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.internal.NistHelper.RecordType;

public class HighResolutionGrayscaleFingerprintWriter extends RecordWriter<HighResolutionGrayscaleFingerprint> {

    @Override
    public RecordType getRecordType() {
        return RecordType.RT4_HR_GS_FINGERPRINT;
    }

    @Override
    public void write(OutputStream out, HighResolutionGrayscaleFingerprint record) throws IOException {
        int imageSize = record.getImageData().length;
        
        ByteBuffer buffer = ByteBuffer.allocate(18);
        buffer.order(ByteOrder.BIG_ENDIAN);

        /* Header (length) */
        buffer.putInt(buffer.limit() + imageSize);
        
        /* IDC */
        if (record.getIdc() == null) {
            throw new IllegalArgumentException("IDC is not set");
        }
        buffer.put(record.getIdc().byteValue());
        
        /* IMP */
        buffer.put((byte) record.getImpressionType());
        
        /* FGP */
        if (record.getFingerPosition() == null || record.getFingerPosition().length != 6) {
            throw new IllegalArgumentException("There should be 6 FGP values");
        }
        for (int f: record.getFingerPosition()) {
            buffer.put((byte) f);
        }
        
        /* ISR */
        buffer.put((byte) record.getImageScanningResolution());
        
        /* HLL */
        buffer.putShort((short) record.getHorizontalLineLength());
        
        /* VLL */
        buffer.putShort((short) record.getVerticalLineLength());
        
        /* CGA */
        buffer.put(Byte.parseByte(record.getCompressionAlgorithm()));
        
        out.write(buffer.array());
        
        out.write(record.getImageData());
    }

}
