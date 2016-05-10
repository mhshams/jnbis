package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.FacialAndSmtImage;

/**
 * @author ericdsoto
 */
public class FacialAndSmtImageReader extends RecordReader {

    @Override
    public FacialAndSmtImage read(NistHelper.Token token) {

        FacialAndSmtImage facialRecord = new FacialAndSmtImage();
        
        ByteBuffer buffer = token.buffer;
        
        while (buffer.hasRemaining()) {
            Field field = nextField(token);
            if (field == null) {
                break;
            }

            switch (field.fieldNumber) {
                case 1:
                    facialRecord.setLogicalRecordLength(field.asInteger());
                    break;
                case 2:
                    facialRecord.setIdc(field.asInteger());
                    break;
                case 3:
                    facialRecord.setImageType(field.asString());
                    break;
                case 4:
                    facialRecord.setSourceAgency(field.asString());
                    break;
                case 5:
                    facialRecord.setPhotoDate(field.asString());
                    break;
                case 6:
                    facialRecord.setHorizontalLineLength(field.asInteger());
                    break;
                case 7:
                    facialRecord.setVerticalLineLength(field.asInteger());
                    break;
                case 8:
                    facialRecord.setScaleUnits(field.asString());
                    break;
                case 9:
                    facialRecord.setHorizontalPixelScale(field.asString());
                    break;
                case 10:
                    facialRecord.setVerticalPixelScale(field.asString());
                    break;
                case 11:
                    facialRecord.setCompressionAlgorithm(field.asString());
                    break;
                case 12:
                    facialRecord.setColorSpace(field.asString());
                    break;
                case 13:
                    facialRecord.setSubjectAcquisitionProfile(field.asString());
                    break;
                case 16:
                    facialRecord.setScannedHorizontalPixelScale(field.asString());
                    break;
                case 17:
                    facialRecord.setScannedVerticalPixelScale(field.asString());
                    break;
                case 20:
                    facialRecord.setSubjectPose(field.asString());
                    break;
                case 21:
                    facialRecord.setPoseOffsetAngle(field.asString());
                    break;
                case 22:
                    facialRecord.setPhotoDescription(field.asString());
                    break;
                case 23:
                    facialRecord.setPhotoAcquisitionSource(field.asString());
                    break;
                case 24:
                    facialRecord.setSubjectQualityScore(field.asString());
                    break;
                case 25:
                    facialRecord.setSubjectPoseAngles(field.asString());
                    break;
                case 26:
                    facialRecord.setSubjectFacialDescription(field.asString());
                    break;
                case 27:
                    facialRecord.setSubjectEyeColor(field.asString());
                    break;
                case 28:
                    facialRecord.setSubjectHairColor(field.asString());
                    break;
                case 29:
                    facialRecord.setFacialFeaturePoints(field.asString());
                    break;
                case 30:
                    facialRecord.setDeviceMonitoringMode(field.asString());
                    break;
                case 40:
                    facialRecord.setNcicDesignationCode(field.asString());
                    break;
                case 41:
                    facialRecord.setScarMarkTattooSize(field.asString());
                    break;
                case 42:
                    facialRecord.setSmtDescriptors(field.asString());
                    break;
                case 43:
                    facialRecord.setColorsPresent(field.asString());
                    break;
                case 999:
                    byte[] imageData = new byte[buffer.remaining()];
                    buffer.get(imageData);
                    facialRecord.setImageData(imageData);
                    break;
            }
        }

        return facialRecord;
    }
}
