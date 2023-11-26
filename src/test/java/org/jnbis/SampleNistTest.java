package org.jnbis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
class SampleNistTest {
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


    @BeforeAll
    public static void setup() {
        nist = Jnbis.nist().decode(FileUtils.absolute("samples/nist/sample.an2"));
    }

    @Test
    void userDefinedTexts() {
        Map<Integer, String> userDefinedFields = nist.getUserDefinedTexts().get(0).getUserDefinedFields();

        assertThat(userDefinedFields)
            .containsEntry(1, "57")
            .containsEntry(2, "00")
            .containsEntry(3, "domain defined text place holder");
    }

    @Test
    void fingerPrints() throws Exception {
        List<HighResolutionGrayscaleFingerprint> fingerprints = nist.getHiResGrayscaleFingerprints();

        assertThat(fingerprints).hasSize(14);

        for (HighResolutionGrayscaleFingerprint fingerPrint : fingerprints) {

            String idcValue = fingerPrint.getImageDesignationCharacter();
            int idcIntValue = Integer.parseInt(idcValue);

            String message = "For '" + idcValue + "' : ";

            assertThat(fingerPrint.getFingerPosition()).as(message).isEqualTo(idcValue);

            assertThat(fingerPrint.getImageScanningResolution()).as(message).isEqualTo("0");
            assertThat( fingerPrint.getCompressionAlgorithm()).as(message).isEqualTo("1");

            assertThat(fingerPrint.getImpressionType()).as(message).isEqualTo(IMAGES_PROPERTIES[idcIntValue - 1][0]);
            assertThat(fingerPrint.getHorizontalLineLength()).as(message).isEqualTo(IMAGES_PROPERTIES[idcIntValue - 1][1]);
            assertThat(fingerPrint.getVerticalLineLength()).as(message).isEqualTo(IMAGES_PROPERTIES[idcIntValue - 1][2]);

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