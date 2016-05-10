package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.VariableResolutionLatentImage;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;

/**
 * @author ericdsoto
 */
public class VariableResolutionLatentImageReader extends RecordReader {

    @Override
    public VariableResolutionLatentImage read(NistHelper.Token token) {
        VariableResolutionLatentImage image = new VariableResolutionLatentImage();

        ByteBuffer buffer = token.buffer;
        while (buffer.hasRemaining()) {
            Field field = nextField(token);

            switch (field.fieldNumber) {
            case 1:
                image.setLogicalRecordLength(field.asInteger());
                break;
            case 2:
                image.setIdc(field.asInteger());
                break;
            case 3:
                image.setImpressionType(field.asInteger());
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
                image.setFingerPalmPosition(field.asString());
                break;
            case 14:
                image.setSearchPositionDescriptors(field.asString());
                break;
            case 15:
                image.setPrintPositionCoordinates(field.asString());
                break;
            case 16:
                image.setScannedHorizontalPixelScale(field.asInteger());
                break;
            case 17:
                image.setScannedVerticalPixelScale(field.asInteger());
                break;
            case 20:
                image.setComment(field.asString());
                break;
            case 24:
                image.setLatentQualityMetric(field.asString());
                break;
            case 999:
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                break;
            default:
                System.err.println("Warning: Found type-14 field that is not handled: " + field.asString());
            }
        }

        return image;
    }
}
