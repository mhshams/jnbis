package org.jnbis;

import org.jnbis.record.BaseRecord;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

/**
 * @author ericdsoto
 */
public abstract class RecordReader {

    abstract public BaseRecord read();

    protected NistHelper.Tag getTagInfo(NistHelper.Token token) {
        String type = nextWord(token, NistHelper.TAG_SEP_DOT, 2, false);
        token.pos++;
        String field = nextWord(token, NistHelper.TAG_SEP_COLN, 10, false);
        token.pos++;

        return new NistHelper.Tag(Integer.parseInt(type.replace(",","")), Integer.parseInt(field));
    }

    protected String nextWord(NistHelper.Token token, char[] sepList, int maxLen, boolean udd) {
        int i = 0;
        while (i < maxLen &&
                token.pos < token.buffer.length &&
                token.buffer[token.pos] != sepList[0] &&
                token.buffer[token.pos] != sepList[1]) {
            token.pos++;
            i++;
        }

        byte[] data = new byte[i];
        System.arraycopy(token.buffer, token.pos - i, data, 0, i);

        try {
            return udd ?
                    String.valueOf(token.charset.decode(ByteBuffer.wrap(data))) :
                    String.valueOf(NistHelper.USASCII.decode(ByteBuffer.wrap(data)));
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected long readInt(NistHelper.Token token) {
        byte byte1 = token.buffer[token.pos];
        byte byte2 = token.buffer[token.pos + 1];
        byte byte3 = token.buffer[token.pos + 2];
        byte byte4 = token.buffer[token.pos + 3];

        return (0xffL & byte1) << 24 | (0xffL & byte2) << 16 | (0xffL & byte3) << 8 | (0xffL & byte4);
    }

}
