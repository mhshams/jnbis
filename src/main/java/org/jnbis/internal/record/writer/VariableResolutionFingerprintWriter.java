package org.jnbis.internal.record.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.jnbis.api.model.record.VariableResolutionFingerprint;
import org.jnbis.api.model.record.VariableResolutionFingerprint.AMP;
import org.jnbis.api.model.record.VariableResolutionFingerprint.ASEG;
import org.jnbis.api.model.record.VariableResolutionFingerprint.FQM;
import org.jnbis.api.model.record.VariableResolutionFingerprint.NQM;
import org.jnbis.api.model.record.VariableResolutionFingerprint.PPC;
import org.jnbis.api.model.record.VariableResolutionFingerprint.PPD;
import org.jnbis.api.model.record.VariableResolutionFingerprint.SEG;
import org.jnbis.api.model.record.VariableResolutionFingerprint.SQM;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.RecordType;

public class VariableResolutionFingerprintWriter extends RecordWriter<VariableResolutionFingerprint> {

    @Override
    public RecordType getRecordType() {
        return RecordType.RT14_VR_FINGERPRINT;
    }

    @Override
    public void write(OutputStream out, VariableResolutionFingerprint record) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(buffer, NistHelper.UTF8);

        writeField(writer, 2, record.getIdc());

        writeField(writer, 3, record.getImpressionType());

        writeField(writer, 4, record.getSourceAgency());

        writeField(writer, 5, record.getCaptureDate());

        writeField(writer, 6, record.getHorizontalLineLength());

        writeField(writer, 7, record.getVerticalLineLength());

        writeField(writer, 8, record.getScaleUnits());

        writeField(writer, 9, record.getHorizontalPixelScale());

        writeField(writer, 10, record.getVerticalPixelScale());

        writeField(writer, 11, record.getCompressionAlgorithm());

        writeField(writer, 12, record.getBitsPerPixel());

        if (!record.getFingerPosition().isEmpty()) {
            /*
             * In the 2007 and 2008 versions of the standard, this field had a
             * repeating subfield that could occur up to 6 times. Since only one
             * image is sent per record, the maximum should have been 1. To
             * maintain backward compatibility, the subfield structure has been
             * retained, but with a maximum occurrence of one.
             */
            writeField(writer, 13, record.getFingerPosition().get(0));
        } else {
            throw new IllegalArgumentException("FGP is mandatory");
        }

        PPD ppd = record.getPrintPositionDescriptors();
        if (ppd != null) {
            writer.write(fieldTag(14));
            writer.write(ppd.decimalFPC);
            writer.write(NistHelper.SEP_US);
            writer.write(ppd.fingerImageCode);

            writer.write(NistHelper.SEP_GS);
        }

        {
            List<PPC> values = record.getPrintPositionCoordinates();
            if (!values.isEmpty()) {
                writer.write(fieldTag(15));
                for (int i = 0; i < values.size(); i++) {
                    PPC value = values.get(i);

                    writer.write(value.fullFingerView);
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.locationOfSegment);
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.leftHorizontalCoordinate);
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.rightHorizontalCoordinate);
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.topVerticalCoordinate);
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.bottomVerticalCoordinate);
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }

        writeField(writer, 16, record.getScannedHorizontalPixelScale());
        writeField(writer, 17, record.getScannedVerticalPixelScale());

        {
            List<AMP> values = record.getAmputatedOrBandaged();
            if (!values.isEmpty()) {
                writer.write(fieldTag(18));
                for (int i = 0; i < values.size(); i++) {
                    AMP value = values.get(i);

                    writer.write(String.valueOf(value.frap));
                    writer.write(NistHelper.SEP_US);

                    writer.write(value.amputatedOrBandagedCode);
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                
                writer.write(NistHelper.SEP_GS);
            }
        }

        /* 19 Reserved */
        
        writeField(writer, 20, record.getComment());
        
        {
            List<SEG> values = record.getFingerprintSegmentationPosition();
            if (!values.isEmpty()) {
                writer.write(fieldTag(21));
                for (int i = 0; i < values.size(); i++) {
                    SEG value = values.get(i);

                    writer.write(String.valueOf(value.frictionRidgeSegmentPosition));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.leftHorizontalCoordinate));
                    writer.write(NistHelper.SEP_US);
                    
                    writer.write(String.valueOf(value.rightHorizontalCoordinate));
                    writer.write(NistHelper.SEP_US);
                    
                    writer.write(String.valueOf(value.topVerticalCoordinate));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.bottomVerticalCoordinate));
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }
        
        {
            List<NQM> values = record.getNistQualityMetric();
            if (!values.isEmpty()) {
                writer.write(fieldTag(22));
                for (int i = 0; i < values.size(); i++) {
                    NQM value = values.get(i);

                    writer.write(String.valueOf(value.frictionRidgeNistQualityPosition));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.nistImageQualityScore));
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }

        {
            List<SQM> values = record.getSegmentationQualityMetric();
            if (!values.isEmpty()) {
                writer.write(fieldTag(23));
                for (int i = 0; i < values.size(); i++) {
                    SQM value = values.get(i);

                    writer.write(String.valueOf(value.frictionRidgeSegmentQualityPosition));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.qualityValue));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.algorithmVendorId));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.algorithmProductId));
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }

        {
            List<FQM> values = record.getFingerprintQualityMetric();
            if (!values.isEmpty()) {
                writer.write(fieldTag(24));
                for (int i = 0; i < values.size(); i++) {
                    FQM value = values.get(i);

                    writer.write(String.valueOf(value.frictionRidgeMetricPosition));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.qualityValue));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.algorithmVendorId));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.algorithmProductId));
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }

        {
            List<ASEG> values = record.getAlternateFingerSementPositions();
            if (!values.isEmpty()) {
                writer.write(fieldTag(25));
                for (int i = 0; i < values.size(); i++) {
                    ASEG value = values.get(i);

                    writer.write(String.valueOf(value.frictionRidgeAltSegmentPosition));
                    writer.write(NistHelper.SEP_US);

                    writer.write(String.valueOf(value.numberOfPoints));
                    writer.write(NistHelper.SEP_US);

                    for (int p = 0; p < value.numberOfPoints; p++) {
                        writer.write(String.valueOf(value.points[p][0]));
                        writer.write(NistHelper.SEP_US);
                        
                        writer.write(String.valueOf(value.points[p][1]));
                        if (p < value.numberOfPoints - 1 || i < values.size() - 1) {
                            writer.write(NistHelper.SEP_RS);
                        }
                    }
                    if (i < values.size() - 1) {
                        writer.write(NistHelper.SEP_US);
                    }
                }
                writer.write(NistHelper.SEP_GS);
            }
        }

        if (record.getImageData() != null && record.getImageData().length > 0) {
            writer.write(fieldTag(999));
            writer.flush();
            buffer.write(record.getImageData());
        } else {
            writer.flush();
            buffer.write(NistHelper.SEP_FS);
        }
        
        writeRecord(out, buffer);
        
        writer.close();
    }

}
