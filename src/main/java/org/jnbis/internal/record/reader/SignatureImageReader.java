package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.SignatureImage;

/**
 * @author ericdsoto
 */
public class SignatureImageReader extends RecordReader {

    @Override
    public SignatureImage read(NistHelper.Token token) {
        SignatureImage signatureImage = new SignatureImage();

        ByteBuffer buffer = token.buffer;
        
        /* Total length of record, including field 001 */
        int recordLength = buffer.getInt();

        int idc = buffer.get();
        int sig = buffer.get();
        int srt = buffer.get();
        int isr = buffer.get();
        int hll = buffer.getShort();
        int vll = buffer.getShort();

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        signatureImage.setLogicalRecordLength(recordLength);
        signatureImage.setIdc(idc);
        /*
        signatureImage.setSignatureType(sig);
        signatureImage.setSignatureRepresentationType(srt);
        signatureImage.setImageScanningResolution(isr);
        */
        signatureImage.setHorizontalLineLength(hll);
        signatureImage.setVerticalLineLength(vll);
        signatureImage.setImageData(data);

        return signatureImage;
    }
}
