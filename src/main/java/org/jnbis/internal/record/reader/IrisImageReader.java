package org.jnbis.internal.record.reader;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.IrisImage;

/**
 * @author ericdsoto
 */
public class IrisImageReader extends RecordReader {

    @Override
    public IrisImage read(NistHelper.Token token) {

        IrisImage image = new IrisImage();

        ByteBuffer buffer = token.buffer;
        
        while (buffer.hasRemaining()) {
            Field field = nextField(token);
            if (field == null) {
                break;
            }

            switch (field.fieldNumber) {
                case 1:
                    image.setLogicalRecordLength(field.asInteger());
                    break;
                case 2:
                    image.setIdc(field.asInteger());
                    break;
                case 3:
                    image.setFeatureIdentifier(field.asString());
                    break;
                case 4:
                    image.setSourceAgency(field.asString());
                    break;
                case 5:
                    image.setCaptureDate(parseDate(field.asString()));
                    break;
                case 6:
                    image.setHorizontalLineLength(field.asInteger());
                    break;
                case 7:
                    image.setVerticalLineLength(field.asInteger());
                    break;
                case 8:
                    image.setScaleUnits(field.asInteger());
                    break;
                case 9:
                    image.setHorizontalPixelScale(field.asInteger());
                    break;
                case 10:
                    image.setVerticalPixelScale(field.asInteger());
                    break;
                case 11:
                    image.setCompressionAlgorithm(field.asString());
                    break;
                case 12:
                    image.setBitsPerPixel(field.asInteger());
                    break;
                case 13:
                    image.setColorSpace(field.asString());
                    break;
                case 14:
                    image.setRotationAngleOfEye(field.asString());
                    break;
                case 15:
                    image.setRotationUncertainty(field.asString());
                    break;
                case 16:
                    image.setImagePropertyCode(field.asString());
                    break;
                case 17:
                    image.setDeviceUniqueIdentifier(field.asString());
                    break;
                case 18:
                    image.setGlobalUniqueIdentifier(field.asString());
                    break;
                case 19:
                    image.setMakeModelSerialNumber(field.asString());
                    break;
                case 20:
                    image.setEyeColor(field.asString());
                    break;
                case 21:
                    image.setComment(field.asString());
                    break;
                case 22:
                    image.setScannedHorizontalPixelScale(field.asInteger());
                    break;
                case 23:
                    image.setScannedVerticalPixelScale(field.asInteger());
                    break;
                case 24:
                    image.setImageQualityScore(field.asString());
                    break;
                case 25:
                    image.setAcquisitionLightingSpectrum(field.asString());
                    break;
                case 26:
                    image.setIrisDiameter(field.asString());
                    break;
                case 30:
                    image.setDeviceMonitoringMode(field.asString());
                    break;
                case 999:
                    byte[] imageData = new byte[buffer.remaining()];
                    buffer.get(imageData);
                    image.setImageData(imageData);
                    break;
                default:
                    break;
            }
        }

        return image;
    }
}
