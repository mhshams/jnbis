package org.jnbis.record;

/**
 * Created by ericdsoto on 6/8/15.
 */
public class VariableResolutionFingerprint extends BaseVariableResolutionImageRecord {

    // 14.013 - FGP
    private String fingerPosition;
    // 14.014 - PPD
    private String printPositionDescriptors;
    // 14.015 - PPC
    private String printPositionCoordinates;
    // 14.018 - AMP
    private String amputatedOrBandaged;
    // 14.021 - SEG
    private String fingerprintSegmentationPosition;
    // 14.022 - NQM
    private String nistQualityMetric;
    // 14.023 - SQM
    private String segmentationQualityMetric;
    // 14.024 - FQM
    private String fingerprintQualityMetric;
    // 14.025 - ASEG
    private String alternateFingerSegmentPosition;


    public String getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(String fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public String getPrintPositionDescriptors() {
        return printPositionDescriptors;
    }

    public void setPrintPositionDescriptors(String printPositionDescriptors) {
        this.printPositionDescriptors = printPositionDescriptors;
    }

    public String getPrintPositionCoordinates() {
        return printPositionCoordinates;
    }

    public void setPrintPositionCoordinates(String printPositionCoordinates) {
        this.printPositionCoordinates = printPositionCoordinates;
    }

    public String getAmputatedOrBandaged() {
        return amputatedOrBandaged;
    }

    public void setAmputatedOrBandaged(String amputatedOrBandaged) {
        this.amputatedOrBandaged = amputatedOrBandaged;
    }

    public String getFingerprintSegmentationPosition() {
        return fingerprintSegmentationPosition;
    }

    public void setFingerprintSegmentationPosition(String fingerprintSegmentationPosition) {
        this.fingerprintSegmentationPosition = fingerprintSegmentationPosition;
    }

    public String getNistQualityMetric() {
        return nistQualityMetric;
    }

    public void setNistQualityMetric(String nistQualityMetric) {
        this.nistQualityMetric = nistQualityMetric;
    }

    public String getSegmentationQualityMetric() {
        return segmentationQualityMetric;
    }

    public void setSegmentationQualityMetric(String segmentationQualityMetric) {
        this.segmentationQualityMetric = segmentationQualityMetric;
    }

    public String getFingerprintQualityMetric() {
        return fingerprintQualityMetric;
    }

    public void setFingerprintQualityMetric(String fingerprintQualityMetric) {
        this.fingerprintQualityMetric = fingerprintQualityMetric;
    }

    public String getAlternateFingerSegmentPosition() {
        return alternateFingerSegmentPosition;
    }

    public void setAlternateFingerSegmentPosition(String alternateFingerSegmentPosition) {
        this.alternateFingerSegmentPosition = alternateFingerSegmentPosition;
    }

}
