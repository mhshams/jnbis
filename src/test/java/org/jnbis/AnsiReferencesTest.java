package org.jnbis;

import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 */

public class AnsiReferencesTest {
    private static final String FILE_PATH = "ansi/references/type-%s.an2";

    private static final String[] FILES = {
            "3",
            "4-14-slaps",
            "4-slaps",
            "4-tpcard",
            "5",
            "6",
            "7-latent",
            "8-sig",
            "8-sig-fax",
            "8-sig-raw",
            "9-4-iafis",
            "9-10-14",
            "9-13-9-14-m1",
            "9-13-m1",
            "9-13-std",
            "9-14-m1",
            "9-14-std",
            "10-14-17-piv-index-iris",
            "10-branded-tattoo-mark",
            "10-sap10",
            "10-scar-face-sap50",
            "10-tattoo-face-sap20",
            "10-tattoo-zoom",
            "13-14-latent-match",
            "13-tip-eji-j2l",
            "13-tip-eji-wsq",
            "14-amp-nqm-utf8",
            "14-tip-eji-j2l",
            "14-tip-eji-wsq",
            "14-tpcard-nqm",
            "15-palms",
            "17-iris"
    };

    @Test
    public void type4_14_slaps() throws IOException {
        Nist decoded = decode(FILES[1]);
        commonAssert(decoded);
        assertEquals(2, decoded.getHiResGrayscaleFingerprints().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type4_slaps() throws IOException {
        Nist decoded = decode(FILES[2]);
        commonAssert(decoded);
        assertEquals(4, decoded.getHiResGrayscaleFingerprints().size());
    }

    @Test
    public void type4_tpcard() throws IOException {
        Nist decoded = decode(FILES[3]);
        commonAssert(decoded);
        assertEquals(14, decoded.getHiResGrayscaleFingerprints().size());
    }

    @Test
    public void type7_latent() throws IOException {
        Nist decoded = decode(FILES[6]);
        commonAssert(decoded);
        assertEquals(1, decoded.getUserDefinedImages().size());
    }

    @Test
    public void type8_sig() throws IOException {
        Nist decoded = decode(FILES[7]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatures().size());
    }

    @Test
    public void type8_sig_fax() throws IOException {
        Nist decoded = decode(FILES[8]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatures().size());
    }

    @Test
    public void type8_sig_raw() throws IOException {
        Nist decoded = decode(FILES[9]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatures().size());
    }

    @Test
    public void type9_4_iafis() throws IOException {
        Nist decoded = decode(FILES[10]);
        commonAssert(decoded);
        assertEquals(1, decoded.getHiResGrayscaleFingerprints().size());
        assertEquals(1, decoded.getMinutiaeData().size());
    }

    @Test
    public void type9_10_14() throws IOException {
        Nist decoded = decode(FILES[11]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getFacialAndSmtImages().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type9_13_9_14_m1() throws IOException {
        Nist decoded = decode(FILES[12]);
        commonAssert(decoded);
        assertEquals(2, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getVariableResLatentImages().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type9_13_m1() throws IOException {
        Nist decoded = decode(FILES[13]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getVariableResLatentImages().size());
    }

    @Test
    public void type9_13_std() throws IOException {
        Nist decoded = decode(FILES[14]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getVariableResLatentImages().size());
    }

    @Test
    public void type9_14_m1() throws IOException {
        Nist decoded = decode(FILES[15]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type9_14_std() throws IOException {
        Nist decoded = decode(FILES[16]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeData().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type10_14_17_piv_index_iris() throws IOException {
        Nist decoded = decode(FILES[17]);
        commonAssert(decoded);
        assertEquals(1, decoded.getFacialAndSmtImages().size());
        assertEquals(2, decoded.getVariableResFingerprints().size());
        assertEquals(1, decoded.getIrisImages().size());
    }

    @Test
    public void type10_branded_tattoo_mark() throws IOException {
        Nist decoded = decode(FILES[18]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialAndSmtImages().size());
    }

    @Test
    public void type10_sap10() throws IOException {
        Nist decoded = decode(FILES[19]);
        commonAssert(decoded);
        assertEquals(1, decoded.getFacialAndSmtImages().size());
    }

    @Test
    public void type10_scar_face_sap50() throws IOException {
        Nist decoded = decode(FILES[20]);
        commonAssert(decoded);
        assertEquals(6, decoded.getFacialAndSmtImages().size());
    }

    @Test
    public void type10_tattoo_face_sap20() throws IOException {
        Nist decoded = decode(FILES[21]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialAndSmtImages().size());
    }

    @Test
    public void type10_tattoo_zoom() throws IOException {
        Nist decoded = decode(FILES[22]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialAndSmtImages().size());
    }

    @Test
    public void type13_14_latent_match() throws IOException {
        Nist decoded = decode(FILES[23]);
        commonAssert(decoded);
        assertEquals(1, decoded.getVariableResLatentImages().size());
        assertEquals(1, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type13_tip_eji_j2l() throws IOException {
        Nist decoded = decode(FILES[24]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResLatentImages().size());
    }

    @Test
    public void type13_tip_eji_wsq() throws IOException {
        Nist decoded = decode(FILES[25]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResLatentImages().size());
    }

    @Test
    public void type14_amp_nqm_utf8() throws IOException {
        Nist decoded = decode(FILES[26]);
        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals(55, decoded.getUserDefinedTexts().get(0).getLogicalRecordLength());
        assertEquals(new Integer(0), decoded.getUserDefinedTexts().get(0).getIdc());
        assertEquals("two chinese characters: 華裔", userDefinedFields.get(3));
    }

    @Test
    public void type14_tip_eji_j2l() throws IOException {
        Nist decoded = decode(FILES[27]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type14_tip_eji_wsq() throws IOException {
        Nist decoded = decode(FILES[28]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type14_tpcard_nqm() throws IOException {
        Nist decoded = decode(FILES[29]);
        commonAssert(decoded);
        assertEquals(14, decoded.getVariableResFingerprints().size());
    }

    @Test
    public void type15_palms() throws IOException {
        Nist decoded = decode(FILES[30]);
        commonAssert(decoded);
        assertEquals(2, decoded.getVariableResPalmprints().size());
    }

    @Test
    public void type17_iris() throws IOException {
        Nist decoded = decode(FILES[31]);
        commonAssert(decoded);
        assertEquals(1, decoded.getIrisImages().size());
    }

    private void commonAssert(Nist decoded) {
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getUserDefinedTexts().size());

        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals(57, decoded.getUserDefinedTexts().get(0).getLogicalRecordLength());
        assertEquals(new Integer(0), decoded.getUserDefinedTexts().get(0).getIdc());
        assertEquals("domain defined text place holder", userDefinedFields.get(3));
    }

    private Nist decode(String name) throws IOException {
        String fileName = FileUtils.absolute(String.format(FILE_PATH, name));
        Nist nist = Jnbis.nist().decode(fileName);
        assertNotNull(nist);
        return nist;
    }
}
