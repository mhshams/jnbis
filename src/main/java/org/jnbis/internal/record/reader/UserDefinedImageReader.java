package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.UserDefinedImage;
import org.jnbis.internal.NistHelper;

/**
 * @author ericdsoto
 */
public class UserDefinedImageReader extends RecordReader {

    @Override
    public UserDefinedImage read(NistHelper.Token token) {
        UserDefinedImage userDefinedImage = new UserDefinedImage();

        ByteBuffer buffer = token.buffer;
        
        /* Total length of record, including field 001 */
        int recordLength = buffer.getInt();
        userDefinedImage.setLogicalRecordLength(recordLength);

        int idc = buffer.get();
        userDefinedImage.setIdc(idc);

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        userDefinedImage.setImageData(data);

        return userDefinedImage;
    }
}
