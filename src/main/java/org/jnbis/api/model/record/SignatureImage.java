package org.jnbis.api.model.record;

import org.jnbis.internal.record.BaseImageRecord;

/**
 * @author ericdsoto
 */
public class SignatureImage extends BaseImageRecord {
    private String signatureType;
    private String signatureRepresentationType;
    private String imageScanningResolution;

    public void setSignatureType(String value) {
        this.signatureType = value;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureRepresentationType(String value) {
        this.signatureRepresentationType = value;
    }

    public String getSignatureRepresentationType() {
        return signatureRepresentationType;
    }

    public void setImageScanningResolution(String value) {
        this.imageScanningResolution = value;
    }

    public String getImageScanningResolution() {
        return imageScanningResolution;
    }
}
