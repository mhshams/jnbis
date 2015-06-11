package org.jnbis.internal.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ericdsoto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseGrayscaleFingerprintRecord extends BaseImageRecord {
    // IMP
    @JsonProperty("impression_type")
    private String impressionType;
    // FGP
    @JsonProperty("finger_position")
    private String fingerPosition;
    // ISR
    @JsonProperty("image_scanning_resolution")
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
