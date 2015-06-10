package org.jnbis.record;

/**
 * @author ericdsoto
 */
public abstract class BaseImageRecord extends BaseRecord {

    // IDC
    private String imageDesignationCharacter;
    // HLL
    private String horizontalLineLength;
    // VLL
    private String verticalLineLength;
    // GCA / BCA
    private String compressionAlgorithm;
    // DATA
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
