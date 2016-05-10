package org.jnbis.internal.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ericdsoto
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseGrayscaleFingerprintRecord extends BaseImageRecord {
    // IMP
    @JsonProperty("impression_type")
    private int impressionType;
    // FGP
    @JsonProperty("finger_position")
    private int[] fingerPosition;
    // ISR
    @JsonProperty("image_scanning_resolution")
    private int imageScanningResolution;

    public int getImpressionType() {
        return impressionType;
    }

    public void setImpressionType(int impressionType) {
        this.impressionType = impressionType;
    }

    public int[] getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(int[] fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public int getImageScanningResolution() {
        return imageScanningResolution;
    }

    public void setImageScanningResolution(int imageScanningResolution) {
        this.imageScanningResolution = imageScanningResolution;
    }
}
