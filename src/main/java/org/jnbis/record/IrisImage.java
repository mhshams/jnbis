package org.jnbis.record;

import org.jnbis.internal.record.BaseVariableResolutionImageRecord;

/**
 * @author ericdsoto
 */
public class IrisImage extends BaseVariableResolutionImageRecord {

    // 17.003 - FID
    private String featureIdentifier;
    // 17.013 - CSP
    private String colorSpace;
    // 17.014 - RAE
    private String rotationAngleOfEye;
    // 17.015 - RAU
    private String rotationUncertainty;
    // 17.016 - IPC
    private String imagePropertyCode;
    // 17.017 - DUI
    private String deviceUniqueIdentifier;
    // 17.018 - GUI
    private String globalUniqueIdentifier;
    // 17.019 - MMS
    private String makeModelSerialNumber;
    // 17.020 - ECL
    private String eyeColor;
    // 17.024 - IQS
    private String imageQualityScore;
    // 17.025 - ALS
    private String acquisitionLightingSpectrum;
    // 17.025 - IRD
    private String irisDiameter;


    public String getFeatureIdentifier() {
        return featureIdentifier;
    }

    public void setFeatureIdentifier(String featureIdentifier) {
        this.featureIdentifier = featureIdentifier;
    }

    public String getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(String colorSpace) {
        this.colorSpace = colorSpace;
    }

    public String getRotationAngleOfEye() {
        return rotationAngleOfEye;
    }

    public void setRotationAngleOfEye(String rotationAngleOfEye) {
        this.rotationAngleOfEye = rotationAngleOfEye;
    }

    public String getRotationUncertainty() {
        return rotationUncertainty;
    }

    public void setRotationUncertainty(String rotationUncertainty) {
        this.rotationUncertainty = rotationUncertainty;
    }

    public String getImagePropertyCode() {
        return imagePropertyCode;
    }

    public void setImagePropertyCode(String imagePropertyCode) {
        this.imagePropertyCode = imagePropertyCode;
    }

    public String getDeviceUniqueIdentifier() {
        return deviceUniqueIdentifier;
    }

    public void setDeviceUniqueIdentifier(String deviceUniqueIdentifier) {
        this.deviceUniqueIdentifier = deviceUniqueIdentifier;
    }

    public String getGlobalUniqueIdentifier() {
        return globalUniqueIdentifier;
    }

    public void setGlobalUniqueIdentifier(String globalUniqueIdentifier) {
        this.globalUniqueIdentifier = globalUniqueIdentifier;
    }

    public String getMakeModelSerialNumber() {
        return makeModelSerialNumber;
    }

    public void setMakeModelSerialNumber(String makeModelSerialNumber) {
        this.makeModelSerialNumber = makeModelSerialNumber;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getImageQualityScore() {
        return imageQualityScore;
    }

    public void setImageQualityScore(String imageQualityScore) {
        this.imageQualityScore = imageQualityScore;
    }

    public String getAcquisitionLightingSpectrum() {
        return acquisitionLightingSpectrum;
    }

    public void setAcquisitionLightingSpectrum(String acquisitionLightingSpectrum) {
        this.acquisitionLightingSpectrum = acquisitionLightingSpectrum;
    }

    public String getIrisDiameter() {
        return irisDiameter;
    }

    public void setIrisDiameter(String irisDiameter) {
        this.irisDiameter = irisDiameter;
    }
}
