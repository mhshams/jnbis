package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.MinutiaeData;

/**
 * @author ericdsoto
 */
public class MinutiaeDataReader extends RecordReader {

    NistHelper.Token token;
    MinutiaeData minutiaeData;

    public MinutiaeDataReader(NistHelper.Token token, MinutiaeData minutiaeData) {
        this.token = token;
        this.minutiaeData = minutiaeData;
    }

    @Override
    public MinutiaeData read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T9::NULL pointer to T9 record");
        }

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T9::Invalid Record type = " + tag.type);
        }

        int length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));

        //while (true) {

            token.pos++;

            tag = getTagInfo(token);

            //if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                //facialRecord.setImageData(data);
                //break;
            //}

            //String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);

        //}

        return minutiaeData;
    }

    protected NistHelper.Tag getTagInfo(NistHelper.Token token) {
        String type = nextWord(token, NistHelper.TAG_SEP_DOT, 2, false);
        token.pos++;
        String field = nextWord(token, NistHelper.TAG_SEP_COLN, 10, false);
        token.pos++;

        return new NistHelper.Tag(Integer.parseInt(type), Integer.parseInt(field));
    }

}
