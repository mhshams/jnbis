package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        UserDefinedDescriptiveText userDefinedText = nist.getUserDefinedTexts().get(0);
        Map<Integer, String> userDefinedFields = userDefinedText.getUserDefinedFields();
        assertEquals(57, nist.getUserDefinedTexts().get(0).getLogicalRecordLength());
        assertEquals(new Integer(0), nist.getUserDefinedTexts().get(0).getIdc());
        Assert.assertEquals("domain defined text place holder", userDefinedFields.get(3));

        List<HighResolutionGrayscaleFingerprint> hiResGrayscaleFingerprints = nist.getHiResGrayscaleFingerprints();

        Assert.assertEquals(14, hiResGrayscaleFingerprints.size());

        for (HighResolutionGrayscaleFingerprint fingerPrint : hiResGrayscaleFingerprints) {
            byte[] pngArray = Jnbis.wsq()
                    .decode(fingerPrint.getImageData())
                    .toPng()
                    .asByteArray();

            String fileName = findFileName(fingerPrint.getFingerPosition()[0], "png");

            Assert.assertArrayEquals(FileUtils.read(new File(fileName)), pngArray);
        }
    }

    private String findFileName(int imageCharacter, String ext) {
        return FileUtils.absolute(String.format("samples/nist/fp-%d.%s", imageCharacter, ext));
    }
}