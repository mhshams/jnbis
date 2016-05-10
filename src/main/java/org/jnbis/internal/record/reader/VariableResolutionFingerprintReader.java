package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;

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
import org.jnbis.internal.NistHelper.Field;
import org.jnbis.internal.NistHelper.Token;

/**
 * @author ericdsoto
 */
public class VariableResolutionFingerprintReader extends RecordReader {

    @Override
    public VariableResolutionFingerprint read(NistHelper.Token token) {
        VariableResolutionFingerprint fingerprint = new VariableResolutionFingerprint();

        ByteBuffer buffer = token.buffer;

        while (buffer.hasRemaining()) {
            Field field = nextField(token);
            if (field == null) {
                break;
            }
            
            switch (field.fieldNumber) {
            case 1:
                fingerprint.setLogicalRecordLength(field.asInteger());
                break;
            case 2:
                fingerprint.setIdc(field.asInteger());
                break;
            case 3:
                fingerprint.setImpressionType(field.asInteger());
                break;
            case 4:
                fingerprint.setSourceAgency(field.asString());
                break;
            case 5:
                fingerprint.setCaptureDate(parseDate(field.asString()));
                break;
            case 6:
                fingerprint.setHorizontalLineLength(field.asInteger());
                break;
            case 7:
                fingerprint.setVerticalLineLength(field.asInteger());
                break;
            case 8:
                fingerprint.setScaleUnits(field.asInteger());
                break;
            case 9:
                fingerprint.setHorizontalPixelScale(field.asInteger());
                break;
            case 10:
                fingerprint.setVerticalPixelScale(field.asInteger());
                break;
            case 11:
                fingerprint.setCompressionAlgorithm(field.asString());
                break;
            case 12:
                fingerprint.setBitsPerPixel(field.asInteger());
                break;
            case 13: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }
                    fingerprint.getFingerPosition().add(subField.asInteger());
                }
                break;
            }
            case 14: {
                Token subToken = new Token(field.value);
                PPD domain = new PPD();
                fingerprint.setPrintPositionDescriptors(domain);

                Field subField = nextInformationItem(subToken);
                domain.decimalFPC = subField.asInteger();

                subField = nextInformationItem(subToken);
                domain.fingerImageCode = subField.asString();
                break;
            }
            case 15: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    PPC domain = new PPC();
                    fingerprint.getPrintPositionCoordinates().add(domain);

                    domain.fullFingerView = subField.asString();

                    subField = nextInformationItem(subToken);
                    domain.locationOfSegment = subField.asString();

                    subField = nextInformationItem(subToken);
                    domain.leftHorizontalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.rightHorizontalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.topVerticalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.bottomVerticalCoordinate = subField.asInteger();
                }
                break;
            }
            case 16:
                fingerprint.setScannedHorizontalPixelScale(field.asInteger());
                break;
            case 17:
                fingerprint.setScannedVerticalPixelScale(field.asInteger());
                break;
            case 18: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    AMP domain = new AMP();
                    fingerprint.getAmputatedOrBandaged().add(domain);

                    domain.frap = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.amputatedOrBandagedCode = subField.asString();

                }
                break;
            }
            case 20:
                fingerprint.setComment(field.asString());
                break;
            case 21: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    SEG domain = new SEG();
                    fingerprint.getFingerprintSegmentationPosition().add(domain);

                    domain.frictionRidgeSegmentPosition = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.leftHorizontalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.rightHorizontalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.topVerticalCoordinate = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.bottomVerticalCoordinate = subField.asInteger();
                }
                break;
            }
            case 22: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    NQM domain = new NQM();
                    fingerprint.getNistQualityMetric().add(domain);

                    domain.frictionRidgeNistQualityPosition = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.nistImageQualityScore = subField.asInteger();
                }
                break;
            }
            case 23: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    SQM domain = new SQM();
                    fingerprint.getSegmentationQualityMetric().add(domain);

                    domain.frictionRidgeSegmentQualityPosition = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.qualityValue = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.algorithmVendorId = subField.asString();

                    subField = nextInformationItem(subToken);
                    domain.algorithmProductId = subField.asInteger();
                }
                break;
            }
            case 24: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    FQM domain = new FQM();
                    fingerprint.getFingerprintQualityMetric().add(domain);

                    domain.frictionRidgeMetricPosition = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.qualityValue = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.algorithmVendorId = subField.asString();

                    subField = nextInformationItem(subToken);
                    domain.algorithmProductId = subField.asInteger();
                }
                break;
            }
            case 25: {
                Token subToken = new Token(field.value);
                while (true) {
                    Field subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }

                    ASEG domain = new ASEG();
                    fingerprint.getAlternateFingerSementPositions().add(domain);

                    domain.frictionRidgeAltSegmentPosition = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    domain.numberOfPoints = subField.asInteger();

                    domain.points = new int[domain.numberOfPoints][2];
                    for (int i = 0; i < domain.points.length; i++) {
                        subField = nextInformationItem(subToken);
                        domain.points[i][0] = subField.asInteger();
                        
                        subField = nextInformationItem(subToken);
                        domain.points[i][1] = subField.asInteger();
                    }
                }
                break;
            }
            case 30:
                fingerprint.setDeviceMonitoringMode(field.asString());
                break;
            case 999:
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                fingerprint.setImageData(data);
                break;
            default:
                System.err.println("Warning: Found type-14 field that is not handled: " + field.asString());
            }
        }

        return fingerprint;
    }
}
