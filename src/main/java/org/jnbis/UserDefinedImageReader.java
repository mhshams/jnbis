package org.jnbis;

import org.jnbis.record.UserDefinedImage;

/**
 * @author ericdsoto
 */
public class UserDefinedImageReader extends RecordReader {

    NistHelper.Token token;
    UserDefinedImage userDefinedImage;

    public UserDefinedImageReader(NistHelper.Token token, UserDefinedImage userDefinedImage) {
        this.token = token;
        this.userDefinedImage = userDefinedImage;
    }

    @Override
    public UserDefinedImage read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T7::NULL pointer to T7 record");
        }

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
