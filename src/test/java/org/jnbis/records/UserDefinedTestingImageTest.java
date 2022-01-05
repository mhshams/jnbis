package org.jnbis.records;

import org.jnbis.FileUtils;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.UserDefinedTestingImage;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author TeeSofteis
 */
public class UserDefinedTestingImageTest {
    private static final String[] FILES = {
            "samples/nist/rec01_rec02_rec16.nst"
    };

    @Test
    public void verify() {
        Nist decoded = decode(FILES[0]);
        commonAssert(decoded);

        // Record 01:
        assertEquals("194", decoded.getTransactionInfo().getLogicalRecordLength());     // 1.001
        assertEquals("ABC", decoded.getTransactionInfo().getTypeOfTransaction());       // 1.004
        assertEquals("Wallace", decoded.getTransactionInfo().getDestinationAgencyId()); // 1.007
        assertEquals("Gormit", decoded.getTransactionInfo().getOriginatingAgencyId());  // 1.008

        assertEquals(1, decoded.getUserDefinedTestingImages().size());   // only one record
        UserDefinedTestingImage image = decoded.getUserDefinedTestingImages().get(0);

        // Record 16:
        assertEquals("4733", image.getLogicalRecordLength());   // 16.001
        assertEquals("01", image.getImageDesignationCharacter());   // 16.002
        assertEquals("1", image.getScaleUnits());
        assertEquals("1", image.getHorizontalPixelScale());
        assertEquals("1", image.getVerticalPixelScale());
        assertEquals("24", image.getBitsPerPixel());

        Map<Integer, String> userDefinedFields = image.getUserDefinedFields();
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
