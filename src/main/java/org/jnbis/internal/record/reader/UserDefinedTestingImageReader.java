package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.api.model.record.UserDefinedTestingImage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author TeeSofteis
 */
public class UserDefinedTestingImageReader extends RecordReader {
    @Override
    public UserDefinedTestingImage read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T16::NULL pointer to T16 record");
        }

        UserDefinedTestingImage userDefinedTestingImage = new UserDefinedTestingImage();

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T16::Invalid Record type = " + tag.type);
        }

        Integer length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));
        userDefinedTestingImage.setLogicalRecordLength(length.toString());

        while (true) {
            token.pos++;
            tag = getTagInfo(token);

            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                userDefinedTestingImage.setImageData(data);
                break;
            }

            String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            switch (tag.field) {
                case 1:
                    userDefinedTestingImage.setLogicalRecordLength(word);
                    break;
                case 2:
                    userDefinedTestingImage.setImageDesignationCharacter(word);
                    break;
                case 6:
                    userDefinedTestingImage.setHorizontalLineLength(word);
                    break;
                case 7:
                    userDefinedTestingImage.setVerticalLineLength(word);
                    break;
                case 8:
                    userDefinedTestingImage.setScaleUnits(word);
                    break;
                case 9:
                    userDefinedTestingImage.setHorizontalPixelScale(word);
                    break;
                case 10:
                    userDefinedTestingImage.setVerticalPixelScale(word);
                    break;
                case 11:
                    userDefinedTestingImage.setCompressionAlgorithm(word);
                    break;
                case 12:
                    userDefinedTestingImage.setBitsPerPixel(word);
                    break;
                default:
                    if ((2 < tag.field && tag.field < 6) || (12 < tag.field && tag.field < 999)) {
                        // User defined fields could be found at tag 3-5 and 13-998. As the name implies,
                        // it is not obvious which format the data has. From my point of view, the best
                        // solution is to handle the data as an array of text, therefore split data into
                        // sub fields and items as you need.
                        userDefinedTestingImage.addUserDefinedField(tag.field, word);
                    }
                    break;
            }
        }
        return userDefinedTestingImage;
    }
}