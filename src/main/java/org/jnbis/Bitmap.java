package org.jnbis;

import java.io.Serializable;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 6, 2007
 */
public class Bitmap implements Serializable {
    private int width;
    private int height;
    private int ppi;
    private int depth;
    private int lossyflag;

    private byte[] pixels;
    private int length;

    public Bitmap(byte[] pixels, int width, int height, int ppi, int depth, int lossyflag) {
        this.pixels = pixels;
        this.length = pixels != null ? pixels.length : 0;

        this.width = width;
        this.height = height;
        this.ppi = ppi;
        this.depth = depth;
        this.lossyflag = lossyflag;
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

    public int getLossyflag() {
        return lossyflag;
    }
}
