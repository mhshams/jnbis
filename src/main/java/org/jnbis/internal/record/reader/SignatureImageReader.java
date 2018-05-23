package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.api.model.record.SignatureImage;

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

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 12, data, 0, data.length + 12 - 12);

        token.pos += length;
        signatureImage.setImageData(data);
        signatureImage.setLogicalRecordLength(String.valueOf(length));

        return signatureImage;
    }
}
