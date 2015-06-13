package org.jnbis;

import org.jnbis.api.model.Bitmap;
import org.jnbis.api.Jnbis;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    public void wsq2bitmap() throws IOException {
        Bitmap bitmap = Jnbis.wsq().decode(WSQ_FILE_NAME).asBitmap();
        Assert.assertNotNull(bitmap);

        Assert.assertEquals(8, bitmap.getDepth());
        Assert.assertEquals(622, bitmap.getHeight());
        Assert.assertEquals(545, bitmap.getWidth());
        Assert.assertEquals(622 * 545, bitmap.getLength());
        Assert.assertEquals(1, bitmap.getLossyFlag());
        Assert.assertEquals(-1, bitmap.getPpi());
        Assert.assertEquals(622 * 545, bitmap.getPixels().length);
    }

    @Test
    public void wsq2pngFile() throws IOException {
        File png = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().save(temp.newFile("temp.png").getAbsolutePath());

        Assert.assertArrayEquals(FileUtils.read(new File(PNG_FILE_NAME)), FileUtils.read(png));
    }

    @Test
    public void wsq2pngByteArray() throws IOException {
        byte[] pngArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asByteArray();

        Assert.assertArrayEquals(FileUtils.read(new File(PNG_FILE_NAME)), pngArray);
    }

    @Test
    public void wsq2pngStream() throws IOException {
        InputStream pngStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asInputStream();

        Assert.assertArrayEquals(FileUtils.read(new File(PNG_FILE_NAME)), FileUtils.read(pngStream));
    }

    @Test
    public void wsq2gifFile() throws IOException {
        File gif = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().save(temp.newFile("temp.gif").getAbsolutePath());

        Assert.assertArrayEquals(FileUtils.read(new File(GIF_FILE_NAME)), FileUtils.read(gif));
    }

    @Test
    public void wsq2gifByteArray() throws IOException {
        byte[] gifArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asByteArray();

        Assert.assertArrayEquals(FileUtils.read(new File(GIF_FILE_NAME)), gifArray);
    }

    @Test
    public void wsq2gifStream() throws IOException {
        InputStream gifStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asInputStream();

        Assert.assertArrayEquals(FileUtils.read(new File(GIF_FILE_NAME)), FileUtils.read(gifStream));
    }

    @Test
    public void wsq2jpgFile() throws IOException {
        File jpg = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().save(temp.newFile("temp.jpg").getAbsolutePath());

        Assert.assertArrayEquals(FileUtils.read(new File(JPG_FILE_NAME)), FileUtils.read(jpg));
    }

    @Test
    public void wsq2jpgByteArray() throws IOException {
        byte[] jpgArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asByteArray();

        Assert.assertArrayEquals(FileUtils.read(new File(JPG_FILE_NAME)), jpgArray);
    }

    @Test
    public void wsq2jpgStream() throws IOException {
        InputStream jpgStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asInputStream();

        Assert.assertArrayEquals(FileUtils.read(new File(JPG_FILE_NAME)), FileUtils.read(jpgStream));
    }
}