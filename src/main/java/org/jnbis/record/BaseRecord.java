package org.jnbis.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by ericdsoto on 6/8/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRecord implements Serializable {

    // X.001 - LEN
    @JsonProperty("logical_record_length")
    private String logicalRecordLength;

    public String getLogicalRecordLength() {
        return logicalRecordLength;
    }

    public void setLogicalRecordLength(String logicalRecordLength) {
        this.logicalRecordLength = logicalRecordLength;
    }

}
