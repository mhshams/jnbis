package org.jnbis.api.handler;

import org.jnbis.api.model.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A handler for bitmap images.
 */
public final class BitmapHandler {
    private static final int[] MASKS = {0x000000ff, 0x000000ff, 0x000000ff};

    private final Bitmap bitmap;

    /**
     * Creates an instance of <code>BitmapHandler</code> with given <code>Bitmap</code>.
     *
     * @param bitmap the given bitmap to handle, not null
     * @see Bitmap
     */
    public BitmapHandler(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Converts the <code>Bitmap</code> data to PNG image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toPng() {
        return new FileHandler(convert(bitmap, "png"));
    }

    /**
     * Converts the <code>Bitmap</code> data to GIF image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toGif() {
        return new FileHandler(convert(bitmap, "gif"));
    }

    /**
     * Converts the <code>Bitmap</code> data to JPEG image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toJpg() {
        return new FileHandler(convert(bitmap, "jpeg"));
    }

    /**
     * Returns the enclosed <code>Bitmap</code> data.
     *
     * @return a FileHandler, not null
     * @see Bitmap
     */
    public Bitmap asBitmap() {
        return bitmap;
    }

    /**
     * Converts the given <code>Bitmap</code> to the specified image format and returns it as byte array.
     *
     * @param bitmap the given bitmap, not null
     * @param format the given target image format, not null
     * @return the converted data in byte array format, not null
     */
    private byte[] convert(Bitmap bitmap, String format) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        DataBuffer buffer = new DataBufferByte(bitmap.getPixels(), bitmap.getLength());
        WritableRaster writableRaster = Raster.createPackedRaster(buffer, width, height, width, MASKS, null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setData(writableRaster);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
