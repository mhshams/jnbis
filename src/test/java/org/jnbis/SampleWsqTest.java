package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Bitmap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class SampleWsqTest {
    private static final String WSQ_FILE_NAME = FileUtils.absolute("samples/wsq/sample.wsq");
    private static final String PNG_FILE_NAME = FileUtils.absolute("samples/wsq/sample.png");
    private static final String GIF_FILE_NAME = FileUtils.absolute("samples/wsq/sample.gif");
    private static final String JPG_FILE_NAME = FileUtils.absolute("samples/wsq/sample.jpg");

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void wsq2bitmap() {
        Bitmap bitmap = Jnbis.wsq().decode(WSQ_FILE_NAME).asBitmap();
        assertNotNull(bitmap);

        assertEquals(8, bitmap.getDepth());
        assertEquals(622, bitmap.getHeight());
        assertEquals(545, bitmap.getWidth());
        assertEquals(622 * 545, bitmap.getLength());
        assertEquals(1, bitmap.getLossyFlag());
        assertEquals(-1, bitmap.getPpi());
        assertEquals(622 * 545, bitmap.getPixels().length);
    }

    @Test
    public void wsq2pngFile() throws IOException {
        File png = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asFile(temp.newFile("temp.png").getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), png);
    }

    @Test
    public void wsq2pngByteArray() throws IOException {
        byte[] pngArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asByteArray();

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), pngArray);
    }

    @Test
    public void wsq2pngStream() throws IOException {
        InputStream pngStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asInputStream();

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), pngStream);
    }

    @Test
    public void wsq2gifFile() throws IOException {
        File gif = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asFile(temp.newFile("temp.gif").getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gif);
    }

    @Test
    public void wsq2gifByteArray() throws IOException {
        byte[] gifArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asByteArray();

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gifArray);
    }

    @Test
    public void wsq2gifStream() throws IOException {
        InputStream gifStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asInputStream();

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gifStream);
    }

    @Test
    public void wsq2jpgFile() throws IOException {
        File jpg = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asFile(temp.newFile("temp.jpg").getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpg);
    }

    @Test
    public void wsq2jpgByteArray() throws IOException {
        byte[] jpgArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asByteArray();

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpgArray);
    }

    @Test
    public void wsq2jpgStream() throws IOException {
        InputStream jpgStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asInputStream();

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpgStream);
    }
}