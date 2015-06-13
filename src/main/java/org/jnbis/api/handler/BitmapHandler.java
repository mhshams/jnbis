package org.jnbis.api.handler;

import org.jnbis.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 */
public final class BitmapHandler {
    private static final int[] MASKS = {0x000000ff, 0x000000ff, 0x000000ff};

    private final Bitmap bitmap;

    public BitmapHandler(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public FileHandler toPng() {
        return new FileHandler(convert(bitmap, "png"));
    }

    public FileHandler toGif() {
        return new FileHandler(convert(bitmap, "gif"));
    }

    public FileHandler toJpg() {
        return new FileHandler(convert(bitmap, "jpeg"));
    }

    public Bitmap asBitmap() {
        return bitmap;
    }

    private byte[] convert(Bitmap bitmap, String format) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        DataBuffer buffer = new DataBufferByte(bitmap.getPixels(), bitmap.getLength());
        WritableRaster writableRaster = Raster.createPackedRaster(buffer, width, height, width, MASKS, null);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bimage.setData(writableRaster);

        try (ByteArrayOutputStream bstream = new ByteArrayOutputStream()) {
            ImageIO.write(bimage, format, bstream);
            bstream.close();
            return bstream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
