package org.jnbis.record;

import java.io.Serializable;

/**
 * @author ericdsoto
 */
public abstract class BaseRecord implements Serializable {

    // X.001 - LEN
    private String logicalRecordLength;

    public String getLogicalRecordLength() {
        return logicalRecordLength;
    }

    public void setLogicalRecordLength(String logicalRecordLength) {
        this.logicalRecordLength = logicalRecordLength;
    }

}
