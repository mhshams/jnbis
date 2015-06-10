package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.VariableResolutionPalmprint;

/**
 * @author ericdsoto
 */
public class VariableResolutionPalmprintReader extends RecordReader {

    @Override
    public VariableResolutionPalmprint read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T14::NULL pointer to T14 record");
        }

        VariableResolutionPalmprint palmprint = new VariableResolutionPalmprint();

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T14::Invalid Record type = " + tag.type);
        }

        int length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));

        while (true) {

            token.pos++;

            tag = getTagInfo(token);
            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                palmprint.setImageData(data);
                break;
            }

            String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            switch (tag.field) {
                case 1:
                    palmprint.setLogicalRecordLength(word);
                    break;
                case 2:
                    palmprint.setImageDesignationCharacter(word);
                    break;
                case 3:
                    palmprint.setImpressionType(word);
                    break;
                case 4:
                    palmprint.setSourceAgency(word);
                    break;
                case 5:
                    palmprint.setCaptureDate(word);
                    break;
                case 6:
                    palmprint.setHorizontalLineLength(word);
                    break;
                case 7:
                    palmprint.setVerticalLineLength(word);
                    break;
                case 8:
                    palmprint.setScaleUnits(word);
                    break;
                case 9:
                    palmprint.setHorizontalPixelScale(word);
                    break;
                case 10:
                    palmprint.setVerticalPixelScale(word);
                    break;
                case 11:
                    palmprint.setCompressionAlgorithm(word);
                    break;
                case 12:
                    palmprint.setBitsPerPixel(word);
                    break;
                case 13:
                    palmprint.setPalmprintPosition(word);
                    break;
                case 16:
                    palmprint.setScannedHorizontalPixelScale(word);
                    break;
                case 17:
                    palmprint.setScannedVerticalPixelScale(word);
                    break;
                case 20:
                    palmprint.setComment(word);
                    break;
                case 24:
                    palmprint.setPalmprintQualityMetric(word);
                    break;
                case 30:
                    palmprint.setDeviceMonitoringMode(word);
                    break;
                default:
                    break;
            }
        }

        return palmprint;
    }
}
