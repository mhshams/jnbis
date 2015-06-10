package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.UserDefinedDescriptiveText;

/**
 * @author ericdsoto
 */
public class UserDefinedTextReader extends RecordReader {

    @Override
    public UserDefinedDescriptiveText read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new IllegalArgumentException("T1::NULL pointer to T2 record");
        }

        UserDefinedDescriptiveText userDefinedText = new UserDefinedDescriptiveText();

        while (true) {
            NistHelper.Tag tag = getTagInfo(token);

            if (tag.type != NistHelper.RT_USER_DEFINED_TEXT) {
                throw new RuntimeException("T2::Invalid Record type = " + tag.type);
            }

            String value = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, true);

            switch(tag.field) {
                case 1:
                    userDefinedText.setLogicalRecordLength(value);
                    break;
                case 2:
                    userDefinedText.setImageDesignationCharacter(value);
                    break;
                case 3:
                    userDefinedText.setDomainDefinedText(value);
                    break;
                case 15:
                    userDefinedText.setStateId(value);
                    break;
                case 910:
                    userDefinedText.setLastName(value);
                    break;
                case 911:
                    userDefinedText.setMotherMaidenName(value);
                    break;
                case 912:
                    userDefinedText.setFirstName(value);
                    break;
                case 913:
                    userDefinedText.setMiddleInitial(value);
                    break;
                default:
                    break;
            }

            if (token.buffer[token.pos++] == NistHelper.SEP_FS) {
                break;
            }
        }

        return userDefinedText;
    }

}
