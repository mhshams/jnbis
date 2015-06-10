package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.SignatureImage;

/**
 * @author ericdsoto
 */
public class SignatureImageReader extends RecordReader {

    NistHelper.Token token;
    SignatureImage signatureImage;

    public SignatureImageReader(NistHelper.Token token, SignatureImage signatureImage) {
        this.token = token;
        this.signatureImage = signatureImage;
    }

    @Override
    public SignatureImage read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T8::NULL pointer to T8 record");
        }

        //Assigning t8-Header values
        int length = (int) readInt(token);
        //int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 12;

        if (token.pos + dataSize + 11 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 12;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 12, data, 0, data.length + 12 - 12);

        token.pos += length;
        signatureImage.setImageData(data);

        return signatureImage;
    }
}
