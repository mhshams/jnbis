package org.jnbis.record;

/**
 * Created by ericdsoto on 6/8/15.
 */
public abstract class BaseGrayscaleFingerprint extends BaseImageRecord {
    // IMP
    private String impressionType;
    // FGP
    private String fingerPosition;
    // ISR
    private String imageScanningResolution;

    public String getImpressionType() {
        return impressionType;
    }

    public void setImpressionType(String impressionType) {
        this.impressionType = impressionType;
    }

    public String getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(String fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public String getImageScanningResolution() {
        return imageScanningResolution;
    }

    public void setImageScanningResolution(String imageScanningResolution) {
        this.imageScanningResolution = imageScanningResolution;
    }
}
