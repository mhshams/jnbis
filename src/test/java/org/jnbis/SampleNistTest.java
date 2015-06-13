package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class SampleNistTest {

    @Test
    public void nist() throws Exception {
        Nist nist = Jnbis.nist().decode(FileUtils.absolute("samples/nist/sample.an2"));

        UserDefinedDescriptiveText userDefinedText = nist.getUserDefinedText(0);
        Map<Integer, String> userDefinedFields = userDefinedText.getUserDefinedFields();
        Assert.assertEquals("57", userDefinedFields.get(1));
        Assert.assertEquals("00", userDefinedFields.get(2));
        Assert.assertEquals("domain defined text place holder", userDefinedFields.get(3));

        List<HighResolutionGrayscaleFingerprint> hiResGrayscaleFingerprints = nist.getHiResGrayscaleFingerprints();

        Assert.assertEquals(14, hiResGrayscaleFingerprints.size());

        for (HighResolutionGrayscaleFingerprint fingerPrint : hiResGrayscaleFingerprints) {
            byte[] pngArray = Jnbis.wsq()
                    .decode(fingerPrint.getImageData())
                    .toPng()
                    .asByteArray();

            String fileName = findFileName(fingerPrint.getImageDesignationCharacter());

            Assert.assertArrayEquals(FileUtils.read(new File(fileName)), pngArray);
        }
    }

    private String findFileName(String imageCharacter) {
        return FileUtils.absolute("samples/nist/fp-" + String.format("%s", imageCharacter) + ".png");
    }
}