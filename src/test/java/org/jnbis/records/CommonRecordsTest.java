package org.jnbis.records;

import org.jnbis.FileUtils;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.SignatureImage;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.UserDefinedTestingImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author TeeSofteis
 */

@RunWith(Parameterized.class)
public class CommonRecordsTest {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<File> data() {
        File[] files = new File(FileUtils.absolute("ansi/references"))
                .listFiles((f, name) -> name.endsWith(".an2"));

        assert files != null;
        return Arrays.asList(files);
    }

    private final File file;

    public CommonRecordsTest(File file) {
        this.file = file;
    }

    @Test
    public void verify() {
        Nist decoded = Jnbis.nist().decode(file);

        verifyUserDefinedFields(decoded);
        verifyTransactionInfo(decoded);

        verifySignatureImageIfPresent(decoded.getSignatures());
        verifyUserDefinedTestingImageIfPresent(decoded.getUserDefinedTestingImages());

        verifyOtherCollections(decoded);
    }

    private void verifyUserDefinedFields(Nist decoded) {
        assertThat(decoded.getUserDefinedTexts()).hasSize(1);
        Map<Integer, String> fields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();

        assertThat(fields.get(0)).isNull();
        assertThat(fields.get(1)).isIn("55", "57");
        assertThat(fields.get(2)).isEqualTo("00");
        assertThat(fields.get(3)).isIn(
                "two chinese characters: 華裔",
                "domain defined text place holder");

    }

    private void verifyTransactionInfo(Nist decoded) {
        TransactionInformation info = decoded.getTransactionInfo();

        assertThat(info).isNotNull();
        assertThat(info.getLogicalRecordLength()).isBetween("158", "273");
        assertThat(info.getTypeOfTransaction()).isIn("AMN", "FAUF", "CAR", "ABC");
        assertThat(info.getDestinationAgencyId()).isIn("DAI000000", "Wallace");
        assertThat(info.getOriginatingAgencyId()).isIn("MDNISTIMG", "Gormit");
    }

    private void verifyOtherCollections(Nist decoded) {
        assertThat(decoded.getLowResGrayscaleFingerprints().size()).isIn(0, 1, 14);
        assertThat(decoded.getHiResGrayscaleFingerprints().size()).isIn(0, 1, 2, 4, 14);
        assertThat(decoded.getLowResBinaryFingerprints().size()).isIn(0, 1);
        assertThat(decoded.getHiResBinaryFingerprints().size()).isIn(0, 1);
        assertThat(decoded.getUserDefinedImages().size()).isIn(0, 1);
        assertThat(decoded.getSignatures().size()).isIn(0, 1);
        assertThat(decoded.getMinutiaeData().size()).isIn(0, 1, 2);
        assertThat(decoded.getFacialAndSmtImages().size()).isIn(0, 1, 2, 6);
        assertThat(decoded.getVariableResLatentImages().size()).isIn(0, 1, 5);
        assertThat(decoded.getVariableResFingerprints().size()).isIn(0, 1, 2, 3, 5, 14);
        assertThat(decoded.getVariableResPalmprints().size()).isIn(0, 2);
        assertThat(decoded.getUserDefinedTestingImages().size()).isIn(0, 1);
        assertThat(decoded.getIrisImages().size()).isIn(0, 1);
    }

    private void verifySignatureImageIfPresent(List<SignatureImage> images) {
        if (images.isEmpty()) return;

        SignatureImage image = images.get(0);
        assertThat(image.getLogicalRecordLength()).isIn("48474", "455", "36012");
        assertThat(image.getImageDesignationCharacter()).isEqualTo("1");
        assertThat(image.getImageScanningResolution()).isEqualTo("1");
        assertThat(image.getSignatureType()).isEqualTo("0");
        assertThat(image.getSignatureRepresentationType()).isIn("0", "1");
    }

    private void verifyUserDefinedTestingImageIfPresent(List<UserDefinedTestingImage> images) {
        if (images.isEmpty()) return;

        UserDefinedTestingImage image = images.get(0);

        assertThat(image.getLogicalRecordLength()).isEqualTo("4733");
        assertThat(image.getImageDesignationCharacter()).isEqualTo("01");
        assertThat(image.getScaleUnits()).isEqualTo("1");
        assertThat(image.getHorizontalPixelScale()).isEqualTo("1");
        assertThat(image.getVerticalPixelScale()).isEqualTo("1");
        assertThat(image.getBitsPerPixel()).isEqualTo("24");

        Map<Integer, String> userDefinedFields = image.getUserDefinedFields();
        assertThat(userDefinedFields).hasSize(4);

        // Tag 16.003
        assertThat(userDefinedFields.get(3)).isEqualTo("Wallace\u001FGromit\u001FMcGraw");

        // Tag 16.004
        assertThat(userDefinedFields.get(4)).isEqualTo("Shaun\u001EPreston\u001EPiella Backleicht");

        // Tag 16.005
        assertThat(userDefinedFields.get(5)).isEqualTo("single value");

        // Tag 16.013
        assertThat(userDefinedFields.get(13)).isEqualTo("A1\u001FB1\u001FC1\u001EA2\u001FB2\u001FC2\u001EA3\u001FB3\u001FC3");
    }
}
