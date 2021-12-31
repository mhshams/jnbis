package org.jnbis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

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
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getHeight(), actual.getHeight());

        for (int y = 0; y < expected.getHeight(); y++) {
            for (int x = 0; x < expected.getWidth(); x++) {
                assertEquals(expected.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }
}
