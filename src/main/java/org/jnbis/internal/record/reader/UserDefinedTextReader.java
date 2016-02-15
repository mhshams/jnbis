package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;

/**
 * @author ericdsoto
 */
public class UserDefinedTextReader extends RecordReader {

    @Override
    public UserDefinedDescriptiveText read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new IllegalArgumentException("T2::NULL pointer to T2 record");
        }

        UserDefinedDescriptiveText userDefinedText = new UserDefinedDescriptiveText();

        while (true) {
            NistHelper.Tag tag = getTagInfo(token);

            if (tag.type != NistHelper.RT_USER_DEFINED_TEXT) {
                throw new RuntimeException("T2::Invalid Record type = " + tag.type);
            }

            String value = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, true);
            switch (tag.field) {
            case 1:
                userDefinedText.setLogicalRecordLength(value);
                break;
            case 2:
                userDefinedText.setIdc(Integer.valueOf(value));
                break;
            default:
                userDefinedText.addUserDefinedField(tag.field, value);
            }

            if (token.buffer[token.pos++] == NistHelper.SEP_FS) {
                break;
            }
        }

        return userDefinedText;
    }

}
