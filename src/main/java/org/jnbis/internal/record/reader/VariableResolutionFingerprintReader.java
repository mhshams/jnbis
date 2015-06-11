package org.jnbis.internal.record.reader;

import org.jnbis.NistHelper;
import org.jnbis.record.VariableResolutionFingerprint;

/**
 * @author ericdsoto
 */
public class VariableResolutionFingerprintReader extends RecordReader {

    @Override
    public VariableResolutionFingerprint read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T14::NULL pointer to T14 record");
        }

        VariableResolutionFingerprint fingerprint = new VariableResolutionFingerprint();

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T14::Invalid Record type = " + tag.type);
        }

        Integer length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));
        fingerprint.setLogicalRecordLength(length.toString());

        while (true) {

            token.pos++;

            tag = getTagInfo(token);

            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                fingerprint.setImageData(data);
                break;
            }

            String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            switch (tag.field) {
                case 1:
                    fingerprint.setLogicalRecordLength(word);
                    break;
                case 2:
                    fingerprint.setImageDesignationCharacter(word);
                    break;
                case 3:
                    fingerprint.setImpressionType(word);
                    break;
                case 4:
                    fingerprint.setSourceAgency(word);
                    break;
                case 5:
                    fingerprint.setCaptureDate(word);
                    break;
                case 6:
                    fingerprint.setHorizontalLineLength(word);
                    break;
                case 7:
                    fingerprint.setVerticalLineLength(word);
                    break;
                case 8:
                    fingerprint.setScaleUnits(word);
                    break;
                case 9:
                    fingerprint.setHorizontalPixelScale(word);
                    break;
                case 10:
                    fingerprint.setVerticalPixelScale(word);
                    break;
                case 11:
                    fingerprint.setCompressionAlgorithm(word);
                    break;
                case 12:
                    fingerprint.setBitsPerPixel(word);
                    break;
                case 13:
                    fingerprint.setFingerPosition(word);
                    break;
                case 14:
                    fingerprint.setPrintPositionDescriptors(word);
                    break;
                case 15:
                    fingerprint.setPrintPositionCoordinates(word);
                    break;
                case 16:
                    fingerprint.setScannedHorizontalPixelScale(word);
                    break;
                case 17:
                    fingerprint.setScannedVerticalPixelScale(word);
                    break;
                case 18:
                    fingerprint.setAmputatedOrBandaged(word);
                    break;
                case 20:
                    fingerprint.setComment(word);
                    break;
                case 21:
                    fingerprint.setFingerprintSegmentationPosition(word);
                    break;
                case 22:
                    fingerprint.setNistQualityMetric(word);
                    break;
                case 23:
                    fingerprint.setSegmentationQualityMetric(word);
                    break;
                case 24:
                    fingerprint.setFingerprintQualityMetric(word);
                    break;
                case 25:
                    fingerprint.setAlternateFingerSegmentPosition(word);
                    break;
                case 30:
                    fingerprint.setDeviceMonitoringMode(word);
                    break;
                default:
                    break;
            }
        }

        return fingerprint;
    }
}
