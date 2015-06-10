package org.jnbis.record;

/**
 * @author ericdsoto
 */
public class MinutiaeData extends BaseImageRecord {

    // 9.003 - IMP
    private String impressionType;
    // 9.004 - FMT
    private String minutiaeFormat;
    // 9.005 - OFR
    private String originatingFingerprintReadingSystem;
    // 9.006 - FGP
    private String fingerPosition;
    // 9.007 - FPC
    private String fingerprintPatternClassification;
    // 9.008 - CRP
    private String corePosition;
    // 9.009 - DLT
    private String deltaPosition;
    // 9.010 - MIN
    private String numberOfMinutiae;

    public String getImpressionType() {
        return impressionType;
    }

    public void setImpressionType(String impressionType) {
        this.impressionType = impressionType;
    }

    public String getMinutiaeFormat() {
        return minutiaeFormat;
    }

    public void setMinutiaeFormat(String minutiaeFormat) {
        this.minutiaeFormat = minutiaeFormat;
    }

    public String getOriginatingFingerprintReadingSystem() {
        return originatingFingerprintReadingSystem;
    }

    public void setOriginatingFingerprintReadingSystem(String originatingFingerprintReadingSystem) {
        this.originatingFingerprintReadingSystem = originatingFingerprintReadingSystem;
    }

    public String getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(String fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public String getFingerprintPatternClassification() {
        return fingerprintPatternClassification;
    }

    public void setFingerprintPatternClassification(String fingerprintPatternClassification) {
        this.fingerprintPatternClassification = fingerprintPatternClassification;
    }

    public String getCorePosition() {
        return corePosition;
    }

    public void setCorePosition(String corePosition) {
        this.corePosition = corePosition;
    }

    public String getDeltaPosition() {
        return deltaPosition;
    }

    public void setDeltaPosition(String deltaPosition) {
        this.deltaPosition = deltaPosition;
    }

    public String getNumberOfMinutiae() {
        return numberOfMinutiae;
    }

    public void setNumberOfMinutiae(String numberOfMinutiae) {
        this.numberOfMinutiae = numberOfMinutiae;
    }
}
