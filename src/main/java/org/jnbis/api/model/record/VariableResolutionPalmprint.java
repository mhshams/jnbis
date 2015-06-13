package org.jnbis.api.model.record;

import org.jnbis.internal.record.BaseVariableResolutionImageRecord;

/**
 * @author ericdsoto
 */
public class VariableResolutionPalmprint extends BaseVariableResolutionImageRecord {

    // 14.013 - PLP
    private String palmprintPosition;
    // 14.024 - PQM
    private String palmprintQualityMetric;

    public String getPalmprintPosition() {
        return palmprintPosition;
    }

    public void setPalmprintPosition(String palmprintPosition) {
        this.palmprintPosition = palmprintPosition;
    }

    public String getPalmprintQualityMetric() {
        return palmprintQualityMetric;
    }

    public void setPalmprintQualityMetric(String palmprintQualityMetric) {
        this.palmprintQualityMetric = palmprintQualityMetric;
    }
}
