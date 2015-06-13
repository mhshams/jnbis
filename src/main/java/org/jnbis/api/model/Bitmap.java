package org.jnbis.api.model;

import java.io.Serializable;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
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

    public Bitmap(byte[] pixels, int width, int height, int ppi, int depth, int lossyFlag) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.ppi = ppi;
        this.depth = depth;
        this.lossyFlag = lossyFlag;

        this.length = pixels != null ? pixels.length : 0;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPpi() {
        return ppi;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public int getLength() {
        return length;
    }

    public int getDepth() {
        return depth;
    }

    public int getLossyFlag() {
        return lossyFlag;
    }
}
