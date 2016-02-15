package org.jnbis.internal.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author ericdsoto
 */

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRecord implements Serializable {

    // X.001 - LEN
    @JsonProperty("logical_record_length")
    private String logicalRecordLength;
    
    // X.002 - IDC
    @JsonProperty("idc")
    private Integer idc;

    public String getLogicalRecordLength() {
        return logicalRecordLength;
    }

    public void setLogicalRecordLength(String logicalRecordLength) {
        this.logicalRecordLength = logicalRecordLength;
    }

    public Integer getIdc() {
        return idc;
    }

    public void setIdc(Integer idc) {
        this.idc = idc;
    }

}
