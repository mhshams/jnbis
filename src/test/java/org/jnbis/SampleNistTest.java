package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class SampleNistTest {
    // Impression-type, horizontal-length, vertical-length
    private static final String[][] IMAGES_PROPERTIES = {
            {"3", "804", "752"},
            {"3", "804", "752"},
            {"3", "804", "752"},
            {"3", "776", "752"},
            {"3", "796", "752"},
            {"3", "804", "748"},
            {"3", "804", "748"},
            {"3", "804", "748"},
            {"3", "776", "748"},
            {"3", "796", "748"},
            {"2", "392", "1000"},
            {"2", "412", "1000"},
            {"2", "1572", "1000"},
            {"2", "1608", "1000"}
    };

    private static Nist nist;

    @BeforeClass
    public static void setup() {
        nist = Jnbis.nist().decode(FileUtils.absolute("samples/nist/sample.an2"));
    }

    @Test
    public void userDefinedTexts() {
        Map<Integer, String> userDefinedFields = nist.getUserDefinedTexts().get(0).getUserDefinedFields();

        assertEquals("57", userDefinedFields.get(1));
        assertEquals("00", userDefinedFields.get(2));
        assertEquals("domain defined text place holder", userDefinedFields.get(3));

    }

    @Test
    public void fingerPrints() throws Exception {
        List<HighResolutionGrayscaleFingerprint> fingerprints = nist.getHiResGrayscaleFingerprints();

        assertEquals(14, fingerprints.size());

        for (HighResolutionGrayscaleFingerprint fingerPrint : fingerprints) {

            String idcValue = fingerPrint.getImageDesignationCharacter();
            int idcIntValue = Integer.parseInt(idcValue);

            String message = "For '" + idcValue + "' : ";

            assertEquals(message, idcValue, fingerPrint.getFingerPosition());

            assertEquals(message, "0", fingerPrint.getImageScanningResolution());
            assertEquals(message, "1", fingerPrint.getCompressionAlgorithm());

            assertEquals(message, IMAGES_PROPERTIES[idcIntValue - 1][0], fingerPrint.getImpressionType());
            assertEquals(message, IMAGES_PROPERTIES[idcIntValue - 1][1], fingerPrint.getHorizontalLineLength());
            assertEquals(message, IMAGES_PROPERTIES[idcIntValue - 1][2], fingerPrint.getVerticalLineLength());

            byte[] bytes = Jnbis.wsq()
                    .decode(fingerPrint.getImageData())
                    .toPng()
                    .asByteArray();

            ImageAssert.assertImagesEquals(new File(findFileName(idcValue)), bytes);
        }
    }

    private String findFileName(String imageCharacter) {
        return FileUtils.absolute("samples/nist/fp-" + String.format("%s", imageCharacter) + ".png");
    }
}