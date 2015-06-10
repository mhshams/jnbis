package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.VariableResolutionLatentImage;

/**
 * @author ericdsoto
 */
public class VariableResolutionLatentImageReader extends RecordReader {

    NistHelper.Token token;

    public VariableResolutionLatentImageReader(NistHelper.Token token) {
        this.token = token;
    }

    @Override
    public VariableResolutionLatentImage read() {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T13::NULL pointer to T13 record");
        }

        VariableResolutionLatentImage image = new VariableResolutionLatentImage();

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T13::Invalid Record type = " + tag.type);
        }

        int length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));

        while (true) {

            token.pos++;

            tag = getTagInfo(token);

            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                image.setImageData(data);
                break;
            }

            String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            switch (tag.field) {
                case 1:
                    image.setLogicalRecordLength(word);
                    break;
                case 2:
                    image.setImageDesignationCharacter(word);
                    break;
                case 3:
                    image.setImpressionType(word);
                    break;
                case 4:
                    image.setSourceAgency(word);
                    break;
                case 5:
                    image.setCaptureDate(word);
                    break;
                case 6:
                    image.setHorizontalLineLength(word);
                    break;
                case 7:
                    image.setVerticalLineLength(word);
                    break;
                case 8:
                    image.setScaleUnits(word);
                    break;
                case 9:
                    image.setHorizontalPixelScale(word);
                    break;
                case 10:
                    image.setVerticalPixelScale(word);
                    break;
                case 11:
                    image.setCompressionAlgorithm(word);
                    break;
                case 12:
                    image.setBitsPerPixel(word);
                    break;
                case 13:
                    image.setFingerPalmPosition(word);
                    break;
                case 14:
                    image.setSearchPositionDescriptors(word);
                    break;
                case 15:
                    image.setPrintPositionCoordinates(word);
                    break;
                case 16:
                    image.setScannedHorizontalPixelScale(word);
                    break;
                case 17:
                    image.setScannedVerticalPixelScale(word);
                    break;
                case 20:
                    image.setComment(word);
                    break;
                case 24:
                    image.setLatentQualityMetric(word);
                    break;
                default:
                    break;
            }
        }

        return image;
    }
}
