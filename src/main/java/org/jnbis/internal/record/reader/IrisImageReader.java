package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.api.model.record.IrisImage;

/**
 * @author ericdsoto
 */
public class IrisImageReader extends RecordReader {

    @Override
    public IrisImage read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T17::NULL pointer to T17 record");
        }

        IrisImage image = new IrisImage();

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T17::Invalid Record type = " + tag.type);
        }

        Integer length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));
        image.setLogicalRecordLength(length.toString());

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
                    image.setFeatureIdentifier(word);
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
                    image.setColorSpace(word);
                    break;
                case 14:
                    image.setRotationAngleOfEye(word);
                    break;
                case 15:
                    image.setRotationUncertainty(word);
                    break;
                case 16:
                    image.setImagePropertyCode(word);
                    break;
                case 17:
                    image.setDeviceUniqueIdentifier(word);
                    break;
                case 18:
                    image.setGlobalUniqueIdentifier(word);
                    break;
                case 19:
                    image.setMakeModelSerialNumber(word);
                    break;
                case 20:
                    image.setEyeColor(word);
                    break;
                case 21:
                    image.setComment(word);
                    break;
                case 22:
                    image.setScannedHorizontalPixelScale(word);
                    break;
                case 23:
                    image.setScannedVerticalPixelScale(word);
                    break;
                case 24:
                    image.setImageQualityScore(word);
                    break;
                case 25:
                    image.setAcquisitionLightingSpectrum(word);
                    break;
                case 26:
                    image.setIrisDiameter(word);
                    break;
                case 30:
                    image.setDeviceMonitoringMode(word);
                    break;
                default:
                    break;
            }
        }

        return image;
    }
}
