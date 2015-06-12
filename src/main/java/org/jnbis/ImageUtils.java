package org.jnbis;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 6, 2007
 */
public class ImageUtils {
    public static final int[] MASKS = {0x000000ff, 0x000000ff, 0x000000ff};

    public byte[] bitmap2jpeg(Bitmap bitmap) {
        return convert(bitmap, "jpeg");
    }

    public byte[] bitmap2gif(Bitmap bitmap) {
        return convert(bitmap, "gif");
    }

    public byte[] bitmap2png(Bitmap bitmap) {
        return convert(bitmap, "png");
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
