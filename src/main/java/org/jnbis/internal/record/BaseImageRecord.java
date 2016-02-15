package org.jnbis.internal.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ericdsoto
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseImageRecord extends BaseRecord {
    // HLL
    @JsonProperty("horizontal_line_length")
    private int horizontalLineLength;
    // VLL
    @JsonProperty("vertical_line_length")
    private int verticalLineLength;
    // GCA / BCA
    @JsonProperty("compression_algorithm")
    private String compressionAlgorithm;
    // DATA
    @JsonProperty("image_data")
    private byte[] imageData;

    public int getHorizontalLineLength() {
        return horizontalLineLength;
    }

    public void setHorizontalLineLength(int horizontalLineLength) {
        this.horizontalLineLength = horizontalLineLength;
    }

    public int getVerticalLineLength() {
        return verticalLineLength;
    }

    public void setVerticalLineLength(int verticalLineLength) {
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
