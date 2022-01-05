package org.jnbis.internal.record.reader;

import org.jnbis.api.model.record.SignatureImage;
import org.jnbis.internal.NistHelper;

/**
 * @author ericdsoto
 */
public class SignatureImageReader extends RecordReader {

    @Override
    public SignatureImage read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T8::NULL pointer to T8 record");
        }

        SignatureImage signatureImage = new SignatureImage();

        //Assigning t8-Header values
        int length = (int) read4BytesAsInt(token);
        //int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 12;

        if (token.pos + dataSize + 11 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 12;
        }

        //Image designation character (IDC)
        int idc = token.buffer[token.pos + 4];
        signatureImage.setImageDesignationCharacter(String.valueOf(idc));

        //Signature type (SIG)
        int sig = token.buffer[token.pos + 5];
        signatureImage.setSignatureType(String.valueOf(sig));

        //Signature representation type (SRT)
        int srt = token.buffer[token.pos + 6];
        signatureImage.setSignatureRepresentationType(String.valueOf(srt));

        //Image scanning resolution (ISR)
        int isr = token.buffer[token.pos + 7];
        signatureImage.setImageScanningResolution(String.valueOf(isr));

        //Horizontal line length (HLL)
        long hll = read2BytesAsInt(token, 8);
        signatureImage.setHorizontalLineLength(String.valueOf(hll));

        //Vertical line length (VLL)
        long vll = read2BytesAsInt(token, 10);
        signatureImage.setVerticalLineLength(String.valueOf(vll));


        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 12, data, 0, data.length + 12 - 12);

        token.pos += length;
        signatureImage.setImageData(data);
        signatureImage.setLogicalRecordLength(String.valueOf(length));

        return signatureImage;
    }
}
