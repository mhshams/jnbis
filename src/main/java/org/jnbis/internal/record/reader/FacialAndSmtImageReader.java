package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.record.FacialAndSmtImage;

/**
 * @author ericdsoto
 */
public class FacialAndSmtImageReader extends RecordReader {

    @Override
    public FacialAndSmtImage read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T10::NULL pointer to T10 record");
        }

        FacialAndSmtImage facialRecord = new FacialAndSmtImage();
        
        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T10::Invalid Record type = " + tag.type);
        }

        Integer length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));
        facialRecord.setLogicalRecordLength(length.toString());

        while (true) {

            token.pos++;

            tag = getTagInfo(token);

            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                token.pos = token.pos + data.length;
                facialRecord.setImageData(data);
                break;
            }

            String word = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            switch (tag.field) {
                case 1:
                    facialRecord.setLogicalRecordLength(word);
                    break;
                case 2:
                    facialRecord.setImageDesignationCharacter(word);
                    break;
                case 3:
                    facialRecord.setImageType(word);
                    break;
                case 4:
                    facialRecord.setSourceAgency(word);
                    break;
                case 5:
                    facialRecord.setPhotoDate(word);
                    break;
                case 6:
                    facialRecord.setHorizontalLineLength(word);
                    break;
                case 7:
                    facialRecord.setVerticalLineLength(word);
                    break;
                case 8:
                    facialRecord.setScaleUnits(word);
                    break;
                case 9:
                    facialRecord.setHorizontalPixelScale(word);
                    break;
                case 10:
                    facialRecord.setVerticalPixelScale(word);
                    break;
                case 11:
                    facialRecord.setCompressionAlgorithm(word);
                    break;
                case 12:
                    facialRecord.setColorSpace(word);
                    break;
                case 13:
                    facialRecord.setSubjectAcquisitionProfile(word);
                    break;
                case 16:
                    facialRecord.setScannedHorizontalPixelScale(word);
                    break;
                case 17:
                    facialRecord.setScannedVerticalPixelScale(word);
                    break;
                case 20:
                    facialRecord.setSubjectPose(word);
                    break;
                case 21:
                    facialRecord.setPoseOffsetAngle(word);
                    break;
                case 22:
                    facialRecord.setPhotoDescription(word);
                    break;
                case 23:
                    facialRecord.setPhotoAcquisitionSource(word);
                    break;
                case 24:
                    facialRecord.setSubjectQualityScore(word);
                    break;
                case 25:
                    facialRecord.setSubjectPoseAngles(word);
                    break;
                case 26:
                    facialRecord.setSubjectFacialDescription(word);
                    break;
                case 27:
                    facialRecord.setSubjectEyeColor(word);
                    break;
                case 28:
                    facialRecord.setSubjectHairColor(word);
                    break;
                case 29:
                    facialRecord.setFacialFeaturePoints(word);
                    break;
                case 30:
                    facialRecord.setDeviceMonitoringMode(word);
                    break;
                case 40:
                    facialRecord.setNcicDesignationCode(word);
                    break;
                case 41:
                    facialRecord.setScarMarkTattooSize(word);
                    break;
                case 42:
                    facialRecord.setSmtDescriptors(word);
                    break;
                case 43:
                    facialRecord.setColorsPresent(word);
                    break;
                default:
                    break;
            }
        }

        return facialRecord;
    }
}
