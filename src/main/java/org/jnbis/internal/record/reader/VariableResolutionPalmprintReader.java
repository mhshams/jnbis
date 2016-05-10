package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;

import org.jnbis.api.model.record.VariableResolutionPalmprint;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;

/**
 * @author ericdsoto
 */
public class VariableResolutionPalmprintReader extends RecordReader {

    @Override
    public VariableResolutionPalmprint read(NistHelper.Token token) {
        VariableResolutionPalmprint palmprint = new VariableResolutionPalmprint();

        ByteBuffer buffer = token.buffer;
        while (buffer.hasRemaining()) {
            Field field = nextField(token);

            switch (field.fieldNumber) {
            case 1:
                palmprint.setLogicalRecordLength(field.asInteger());
                break;
            case 2:
                palmprint.setIdc(field.asInteger());
                break;
            case 3:
                palmprint.setImpressionType(field.asInteger());
                break;
            case 4:
                palmprint.setSourceAgency(field.asString());
                break;
            case 5:
                palmprint.setCaptureDate(parseDate(field.asString()));
                break;
            case 6:
                palmprint.setHorizontalLineLength(field.asInteger());
                break;
            case 7:
                palmprint.setVerticalLineLength(field.asInteger());
                break;
            case 8:
                palmprint.setScaleUnits(field.asInteger());
                break;
            case 9:
                palmprint.setHorizontalPixelScale(field.asInteger());
                break;
            case 10:
                palmprint.setVerticalPixelScale(field.asInteger());
                break;
            case 11:
                palmprint.setCompressionAlgorithm(field.asString());
                break;
            case 12:
                palmprint.setBitsPerPixel(field.asInteger());
                break;
            case 13:
                palmprint.setPalmprintPosition(field.asString());
                break;
            case 16:
                palmprint.setScannedHorizontalPixelScale(field.asInteger());
                break;
            case 17:
                palmprint.setScannedVerticalPixelScale(field.asInteger());
                break;
            case 20:
                palmprint.setComment(field.asString());
                break;
            case 24:
                palmprint.setPalmprintQualityMetric(field.asString());
                break;
            case 30:
                palmprint.setDeviceMonitoringMode(field.asString());
                break;
            case 999:
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                break;
            default:
                System.err.println("Warning: Found type-14 field that is not handled: " + field.asString());
            }
        }

        return palmprint;
    }
}
