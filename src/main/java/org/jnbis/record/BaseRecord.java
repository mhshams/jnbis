package org.jnbis.record;

import java.io.Serializable;

/**
 * Created by ericdsoto on 6/8/15.
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
