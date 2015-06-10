package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.UserDefinedImage;

/**
 * @author ericdsoto
 */
public class UserDefinedImageReader extends RecordReader {

    @Override
    public UserDefinedImage read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T7::NULL pointer to T7 record");
        }

        UserDefinedImage userDefinedImage = new UserDefinedImage();

        //Assigning t7-Header values
        int length = (int) readInt(token);
        //int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 5;

        if (token.pos + dataSize + 4 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 5;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 5, data, 0, data.length + 5 - 5);

        token.pos += length;

        userDefinedImage.setImageData(data);

        return userDefinedImage;
    }
}
