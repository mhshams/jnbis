package org.jnbis.record;

import org.jnbis.internal.record.BaseVariableResolutionImageRecord;

/**
 * @author ericdsoto
 */
public class VariableResolutionLatentImage extends BaseVariableResolutionImageRecord {
    // 13.013 - FGP
    private String fingerPalmPosition;
    // 13.014 - SPD
    private String searchPositionDescriptors;
    // 13.015 - PPC
    private String printPositionCoordinates;
    // 13.024 - LQM
    private String latentQualityMetric;


    public String getFingerPalmPosition() {
        return fingerPalmPosition;
    }

    public void setFingerPalmPosition(String fingerPalmPosition) {
        this.fingerPalmPosition = fingerPalmPosition;
    }

    public String getSearchPositionDescriptors() {
        return searchPositionDescriptors;
    }

    public void setSearchPositionDescriptors(String searchPositionDescriptors) {
        this.searchPositionDescriptors = searchPositionDescriptors;
    }

    public String getPrintPositionCoordinates() {
        return printPositionCoordinates;
    }

    public void setPrintPositionCoordinates(String printPositionCoordinates) {
        this.printPositionCoordinates = printPositionCoordinates;
    }

    public String getLatentQualityMetric() {
        return latentQualityMetric;
    }

    public void setLatentQualityMetric(String latentQualityMetric) {
        this.latentQualityMetric = latentQualityMetric;
    }
}
