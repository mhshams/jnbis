package org.jnbis.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ericdsoto on 6/8/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseImageRecord extends BaseRecord {
    // IDC
    @JsonProperty("image_designation_character")
    private String imageDesignationCharacter;
    // HLL
    @JsonProperty("horizontal_line_length")
    private String horizontalLineLength;
    // VLL
    @JsonProperty("vertical_line_length")
    private String verticalLineLength;
    // GCA / BCA
    @JsonProperty("compression_algorithm")
    private String compressionAlgorithm;
    // DATA
    @JsonProperty("image_data")
    private byte[] imageData;

    public String getImageDesignationCharacter() {
        return imageDesignationCharacter;
    }

    public void setImageDesignationCharacter(String imageDesignationCharacter) {
        this.imageDesignationCharacter = imageDesignationCharacter;
    }

    public String getHorizontalLineLength() {
        return horizontalLineLength;
    }

    public void setHorizontalLineLength(String horizontalLineLength) {
        this.horizontalLineLength = horizontalLineLength;
    }

    public String getVerticalLineLength() {
        return verticalLineLength;
    }

    public void setVerticalLineLength(String verticalLineLength) {
        this.verticalLineLength = verticalLineLength;
    }

    public String getCompressionAlgorithm() {
        return compressionAlgorithm;
    }

    public void setCompressionAlgorithm(String compressionAlgorithm) {
        this.compressionAlgorithm = compressionAlgorithm;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

}
