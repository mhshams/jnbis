package org.jnbis.api.model;

import java.io.Serializable;

/**
 * A <code>Bitmap</code> instance contains an image information in bitmap format.
 *
 * @version 1.0.0
 * @since Oct 6, 2007
 */
public class Bitmap implements Serializable {
    private final byte[] pixels;
    private final int width;
    private final int height;
    private final int ppi;
    private final int depth;
    private final int lossyFlag;

    private final int length;

    /**
     * Creates an instance of <code>Bitmap</code> with specified data.
     *
     * @param pixels    the image pixes, not null
     * @param width     the image width, not null
     * @param height    the image height, not null
     * @param ppi       pixel per inch, not null
     * @param depth     the image depth, not null
     * @param lossyFlag lossy flag, not null
     */
    public Bitmap(byte[] pixels, int width, int height, int ppi, int depth, int lossyFlag) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.ppi = ppi;
        this.depth = depth;
        this.lossyFlag = lossyFlag;

        this.length = pixels != null ? pixels.length : 0;
    }

    /**
     * Returns the image width
     *
     * @return the image width, not null
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the image height
     *
     * @return the image height, not null
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the image ppi
     *
     * @return the image ppi, not null
     */
    public int getPpi() {
        return ppi;
    }

    /**
     * Returns the image pixels
     *
     * @return the image pixels, not null
     */
    public byte[] getPixels() {
        return pixels;
    }

    /**
     * Returns the image length
     *
     * @return the image length, not null
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the image depth
     *
     * @return the image depth, not null
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Returns the image lossy flag
     *
     * @return the image lossy flag, not null
     */
    public int getLossyFlag() {
        return lossyFlag;
    }
}
