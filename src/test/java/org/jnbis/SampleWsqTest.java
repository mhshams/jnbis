package org.jnbis;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Bitmap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
class SampleWsqTest {
    private static final String WSQ_FILE_NAME = FileUtils.absolute("samples/wsq/sample.wsq");
    private static final String PNG_FILE_NAME = FileUtils.absolute("samples/wsq/sample.png");
    private static final String GIF_FILE_NAME = FileUtils.absolute("samples/wsq/sample.gif");
    private static final String JPG_FILE_NAME = FileUtils.absolute("samples/wsq/sample.jpg");


    @Test
    void wsq2bitmap() {
        Bitmap bitmap = Jnbis.wsq().decode(WSQ_FILE_NAME).asBitmap();
        assertThat(bitmap).isNotNull();

        assertThat(bitmap.getDepth()).isEqualTo(8);
        assertThat(bitmap.getHeight()).isEqualTo(622);
        assertThat(bitmap.getWidth()).isEqualTo(545);
        assertThat(bitmap.getLength()).isEqualTo(622 * 545);
        assertThat(bitmap.getLossyFlag()).isEqualTo(1);
        assertThat(bitmap.getPpi()).isEqualTo(-1);
        assertThat(bitmap.getPixels()).hasSize(622 * 545);
    }

    @Test
    void wsq2pngFile(@TempDir Path tempDir) throws IOException {
        File png = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asFile(tempDir.resolve("temp.png").toFile().getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), png);
    }

    @Test
    void wsq2pngByteArray() throws IOException {
        byte[] pngArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asByteArray();

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), pngArray);
    }

    @Test
    void wsq2pngStream() throws IOException {
        InputStream pngStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toPng().asInputStream();

        ImageAssert.assertImagesEquals(new File(PNG_FILE_NAME), pngStream);
    }

    @Test
    void wsq2gifFile(@TempDir Path tempDir) throws IOException {
        File gif = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asFile(tempDir.resolve("temp.gif").toFile().getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gif);
    }

    @Test
    void wsq2gifByteArray() throws IOException {
        byte[] gifArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asByteArray();

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gifArray);
    }

    @Test
    void wsq2gifStream() throws IOException {
        InputStream gifStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toGif().asInputStream();

        ImageAssert.assertImagesEquals(new File(GIF_FILE_NAME), gifStream);
    }

    @Test
    void wsq2jpgFile(@TempDir Path tempDir) throws IOException {
        File jpg = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asFile(tempDir.resolve("temp.jpg").toFile().getAbsolutePath());

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpg);
    }

    @Test
    void wsq2jpgByteArray() throws IOException {
        byte[] jpgArray = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asByteArray();

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpgArray);
    }

    @Test
    void wsq2jpgStream() throws IOException {
        InputStream jpgStream = Jnbis.wsq().decode(WSQ_FILE_NAME).toJpg().asInputStream();

        ImageAssert.assertImagesEquals(new File(JPG_FILE_NAME), jpgStream);
    }
}