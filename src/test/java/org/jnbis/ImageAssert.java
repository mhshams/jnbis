package org.jnbis;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageAssert {

    public static void assertImagesEquals(File expected, InputStream actual) throws IOException {
        assertImagesEquals(ImageIO.read(expected), ImageIO.read(actual));
    }

    public static void assertImagesEquals(File expected, byte[] actual) throws IOException {
        assertImagesEquals(ImageIO.read(expected), ImageIO.read(new ByteArrayInputStream(actual)));
    }

    public static void assertImagesEquals(File expected, File actual) throws IOException {
        assertImagesEquals(ImageIO.read(expected), ImageIO.read(actual));
    }

    private static void assertImagesEquals(BufferedImage expected, BufferedImage actual) {
        assertThat(actual.getWidth()).isEqualTo(expected.getWidth());
        assertThat(actual.getHeight()).isEqualTo(expected.getHeight());

        for (int y = 0; y < expected.getHeight(); y++) {
            for (int x = 0; x < expected.getWidth(); x++) {
                assertThat(actual.getRGB(x, y)).isEqualTo(expected.getRGB(x, y));
            }
        }
    }
}
