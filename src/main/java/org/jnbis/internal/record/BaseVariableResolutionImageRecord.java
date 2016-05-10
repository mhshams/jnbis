package org.jnbis.internal.record;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ericdsoto
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseVariableResolutionImageRecord extends BaseImageRecord {
    // X.003 - IMP
    @JsonProperty("impression_type")
    private int impressionType;
    // X.004 - SRC
    @JsonProperty("source_agency")
    private String sourceAgency;
    // X.005 - FCD/PCD
    @JsonProperty("capture_date")
    private Date captureDate;
    // X.008 - SLC
    @JsonProperty("scale_units")
    private int scaleUnits;
    // X.009 - HPS
    @JsonProperty("horizontal_pixel_scale")
    private int horizontalPixelScale;
    // X.010 - VPS
    @JsonProperty("vertical_pixel_scale")
    private int verticalPixelScale;
    // X.012 - BPX
    @JsonProperty("bits_per_pixel")
    private int bitsPerPixel;
    // X.016 - SHPS
    @JsonProperty("scanned_horizontal_pixel_scale")
    private int scannedHorizontalPixelScale;
    // X.017 - SVPS
    @JsonProperty("scanned_vertical_pixel_scale")
    private int scannedVerticalPixelScale;
    // X.020 - COM
    @JsonProperty("comment")
    private String comment;
    // X.030 - DMM
    @JsonProperty("device_monitoring_mode")
    private String deviceMonitoringMode;

    public int getImpressionType() {
        return impressionType;
    }

    public void setImpressionType(int impressionType) {
        this.impressionType = impressionType;
    }

    public String getSourceAgency() {
        return sourceAgency;
    }

    public void setSourceAgency(String sourceAgency) {
        this.sourceAgency = sourceAgency;
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public int getScaleUnits() {
        return scaleUnits;
    }

    public void setScaleUnits(int scaleUnits) {
        this.scaleUnits = scaleUnits;
    }

    public int getHorizontalPixelScale() {
        return horizontalPixelScale;
    }

    public void setHorizontalPixelScale(int horizontalPixelScale) {
        this.horizontalPixelScale = horizontalPixelScale;
    }

    public int getVerticalPixelScale() {
        return verticalPixelScale;
    }

    public void setVerticalPixelScale(int verticalPixelScale) {
        this.verticalPixelScale = verticalPixelScale;
    }

    public int getBitsPerPixel() {
        return bitsPerPixel;
    }

    public void setBitsPerPixel(int bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

    public int getScannedHorizontalPixelScale() {
        return scannedHorizontalPixelScale;
    }

    public void setScannedHorizontalPixelScale(int scannedHorizontalPixelScale) {
        this.scannedHorizontalPixelScale = scannedHorizontalPixelScale;
    }

    public int getScannedVerticalPixelScale() {
        return scannedVerticalPixelScale;
    }

    public void setScannedVerticalPixelScale(int scannedVerticalPixelScale) {
        this.scannedVerticalPixelScale = scannedVerticalPixelScale;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeviceMonitoringMode() {
        return deviceMonitoringMode;
    }

    public void setDeviceMonitoringMode(String deviceMonitoringMode) {
        this.deviceMonitoringMode = deviceMonitoringMode;
    }
}
