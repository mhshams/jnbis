package org.jnbis.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jnbis.internal.record.BaseRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ericdsoto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDefinedDescriptiveText extends BaseRecord {

    @JsonProperty("user_defined_fields")
    private Map<Integer, String> userDefinedFields;

    public Map<Integer, String> getUserDefinedFields() {
        return userDefinedFields;
    }

    public void setUserDefinedFields(Map<Integer, String> userDefinedFields) {
        this.userDefinedFields = userDefinedFields;
    }

    public void addUserDefinedField(Integer key, String value) {
        if (userDefinedFields == null) {
            userDefinedFields = new HashMap<>();
        }
        userDefinedFields.put(key, value);
    }
}
