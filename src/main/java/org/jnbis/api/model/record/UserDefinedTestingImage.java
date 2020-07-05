package org.jnbis.api.model.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jnbis.internal.record.BaseImageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TeeSofteis
 */
public class UserDefinedTestingImage extends BaseImageRecord {

    // 16.003 - 16.005 and 16.013 - 16.998
    // Key is the field identifier and the data is like a 2D array.
    @JsonProperty("user_defined_fields")
    private Map<Integer, ArrayList<ArrayList<String>>> userDefinedFields;

    public Map<Integer, ArrayList<ArrayList<String>>> getUserDefinedFields() {
        return userDefinedFields;
    }

    public void setUserDefinedFields(Map<Integer, ArrayList<ArrayList<String>>> userDefinedFields) {
        this.userDefinedFields = userDefinedFields;
    }

    public void addUserDefinedField(Integer key, ArrayList<String> value) {
        if (userDefinedFields == null) {
            userDefinedFields = new HashMap<>();
        }

        if (userDefinedFields.containsKey(key)) {
            userDefinedFields.get(key).add(value);
        } else {
            userDefinedFields.put(key, new ArrayList<ArrayList<String>>());
            userDefinedFields.get(key).add(value);
        }
    }

    // 16.008 - SLC
    @JsonProperty("scale_units")
    private String scaleUnits;

    // 16.009 - HPS
    @JsonProperty("horizontal_pixel_scale")
    private String horizontalPixelScale;

    // 16.010 - VPS
    @JsonProperty("vertical_pixel_scale")
    private String verticalPixelScale;

    // 16.012 - BPX
    @JsonProperty("bits_per_pixel")
    private String bitsPerPixel;

    public String getScaleUnits() {
        return scaleUnits;
    }

    public void setScaleUnits(String scaleUnits) {
        this.scaleUnits = scaleUnits;
    }

    public String getHorizontalPixelScale() {
        return horizontalPixelScale;
    }

    public void setHorizontalPixelScale(String horizontalPixelScale) {
        this.horizontalPixelScale = horizontalPixelScale;
    }

    public String getVerticalPixelScale() {
        return verticalPixelScale;
    }

    public void setVerticalPixelScale(String verticalPixelScale) {
        this.verticalPixelScale = verticalPixelScale;
    }

    public String getBitsPerPixel() {
        return bitsPerPixel;
    }

    public void setBitsPerPixel(String bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

}
