package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.UserDefinedTestingImage;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author TeeSofteis
 */
public class Record16Test {
    private static final String[] FILES = {
            "samples/nist/rec01_rec02_rec16.nst"
    };

    @Test
    public void type16() {
        Nist decoded = decode(FILES[0]);
        commonAssert(decoded);

        // Record 01:
        assertEquals("194", decoded.getTransactionInfo().getLogicalRecordLength());     // 1.001
        assertEquals("ABC", decoded.getTransactionInfo().getTypeOfTransaction());       // 1.004
        assertEquals("Wallace", decoded.getTransactionInfo().getDestinationAgencyId()); // 1.007
        assertEquals("Gormit", decoded.getTransactionInfo().getOriginatingAgencyId());  // 1.008

        // Record 16:
        assertEquals("4733", decoded.getUserDefinedTestingImages().get(0).getLogicalRecordLength());   // 16.001
        assertEquals("01", decoded.getUserDefinedTestingImages().get(0).getImageDesignationCharacter());   // 16.002

        List<UserDefinedTestingImage> rec16s = decoded.getUserDefinedTestingImages();
        assertEquals(1, rec16s.size());   // only one record

        UserDefinedTestingImage userDefinedTestingImage = rec16s.get(0);
        assertEquals("4733", userDefinedTestingImage.getLogicalRecordLength());
        assertEquals("1", userDefinedTestingImage.getScaleUnits());
        assertEquals("1", userDefinedTestingImage.getHorizontalPixelScale());
        assertEquals("1", userDefinedTestingImage.getVerticalPixelScale());
        assertEquals("24", userDefinedTestingImage.getBitsPerPixel());

        Map<Integer, String> userDefinedFields = userDefinedTestingImage.getUserDefinedFields();
        assertEquals(4, userDefinedFields.size());

        // Tag 16.003
        assertEquals("Wallace\u001FGromit\u001FMcGraw", userDefinedFields.get(3));

        // Tag 16.004
        assertEquals("Shaun\u001EPreston\u001EPiella Backleicht", userDefinedFields.get(4));

        // Tag 16.005
        assertEquals("single value", userDefinedFields.get(5));

        // Tag 16.013
        assertEquals("A1\u001FB1\u001FC1\u001EA2\u001FB2\u001FC2\u001EA3\u001FB3\u001FC3", userDefinedFields.get(13));
    }

    private void commonAssert(Nist decoded) {
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getUserDefinedTexts().size());

        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals("57", userDefinedFields.get(1));
        assertEquals("00", userDefinedFields.get(2));
        assertEquals("domain defined text place holder", userDefinedFields.get(3));
    }

    private Nist decode(String name) {
        String fileName = FileUtils.absolute(name);
        Nist nist = Jnbis.nist().decode(fileName);
        assertNotNull(nist);
        return nist;
    }
}
