package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;

/**
 * @author ericdsoto
 */
public class UserDefinedTextReader extends RecordReader {

    @Override
    public UserDefinedDescriptiveText read(NistHelper.Token token) {
        UserDefinedDescriptiveText userDefinedText = new UserDefinedDescriptiveText();

        while (token.buffer.hasRemaining()) {
            Field field = nextField(token);

            switch (field.fieldNumber) {
            case 1:
                userDefinedText.setLogicalRecordLength(field.asInteger());
                break;
            case 2:
                userDefinedText.setIdc(field.asInteger());
                break;
            default:
                /* The values can be encoded using various character encodings, so we decode the value
                 * using the charset specified in Field 1.015 Character encoding / DCS
                 */
                userDefinedText.addUserDefinedField(field.fieldNumber, field.asString(token.charset));
            }

        }

        return userDefinedText;
    }

}
