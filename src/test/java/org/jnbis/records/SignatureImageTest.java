package org.jnbis.records;

import org.jnbis.FileUtils;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.SignatureImage;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SignatureImageTest {
    private static final String[] FILES = {
            "ansi/references/type-8-sig.an2"
    };

    @Test
    public void verify() {
        String fileName = FileUtils.absolute(FILES[0]);
        Nist decoded = Jnbis.nist().decode(fileName);

        commonAssert(decoded);

        // Record 01:
        assertEquals("158", decoded.getTransactionInfo().getLogicalRecordLength());     // 1.001
        assertEquals("FAUF", decoded.getTransactionInfo().getTypeOfTransaction());       // 1.004
        assertEquals("DAI000000", decoded.getTransactionInfo().getDestinationAgencyId()); // 1.007
        assertEquals("MDNISTIMG", decoded.getTransactionInfo().getOriginatingAgencyId());  // 1.008

        // Record 08:
        assertEquals(1, decoded.getSignatures().size());   // only one record

        SignatureImage image = decoded.getSignatures().get(0);
        assertEquals("48474", image.getLogicalRecordLength());   // 8.001
        assertEquals("1", image.getImageDesignationCharacter());   // 8.002
        assertEquals("1", image.getImageScanningResolution());
        assertEquals("0", image.getSignatureType());
        assertEquals("0", image.getSignatureRepresentationType());
    }

    private void commonAssert(Nist decoded) {
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getUserDefinedTexts().size());

        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals("57", userDefinedFields.get(1));
        assertEquals("00", userDefinedFields.get(2));
        assertEquals("domain defined text place holder", userDefinedFields.get(3));
    }
}
