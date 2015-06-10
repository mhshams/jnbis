package org.jnbis;

import org.jnbis.record.UserDefinedDescriptiveText;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

    private static final NistDecoder DECODER = new NistDecoder();

    @Test
    public void type_3() throws IOException {
        DecodedData decoded = decode(FILES[0]);
        commonAssert(decoded);
        assertEquals(1, decoded.getLowResGrayscaleFingerPrintKeys().size());
    }

    @Test
    public void type4_14_slaps() throws IOException {
        DecodedData decoded = decode(FILES[1]);
        commonAssert(decoded);
        assertEquals(2, decoded.getHiResGrayscaleFingerPrintKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type4_slaps() throws IOException {
        DecodedData decoded = decode(FILES[2]);
        commonAssert(decoded);
        assertEquals(4, decoded.getHiResGrayscaleFingerPrintKeys().size());
    }

    @Test
    public void type4_tpcard() throws IOException {
        DecodedData decoded = decode(FILES[3]);
        commonAssert(decoded);
        assertEquals(14, decoded.getHiResGrayscaleFingerPrintKeys().size());
    }

    @Test
    public void type5() throws IOException {
        DecodedData decoded = decode(FILES[4]);
        commonAssert(decoded);
        assertEquals(1, decoded.getLowResBinaryFingerPrintKeys().size());
    }

    @Test
    public void type6() throws IOException {
        DecodedData decoded = decode(FILES[5]);
        commonAssert(decoded);
        assertEquals(1, decoded.getHiResBinaryFingerPrintKeys().size());
    }

    @Test
    public void type7_latent() throws IOException {
        DecodedData decoded = decode(FILES[6]);
        commonAssert(decoded);
        assertEquals(1, decoded.getUserDefinedImageKeys().size());
    }

    @Test
    public void type8_sig() throws IOException {
        DecodedData decoded = decode(FILES[7]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatureKeys().size());
    }

    @Test
    public void type8_sig_fax() throws IOException {
        DecodedData decoded = decode(FILES[8]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatureKeys().size());
    }

    @Test
    public void type8_sig_raw() throws IOException {
        DecodedData decoded = decode(FILES[9]);
        commonAssert(decoded);
        assertEquals(1, decoded.getSignatureKeys().size());
    }

    @Test
    public void type9_4_iafis() throws IOException {
        DecodedData decoded = decode(FILES[10]);
        commonAssert(decoded);
        assertEquals(1, decoded.getHiResGrayscaleFingerPrintKeys().size());
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
    }

    @Test
    public void type9_10_14() throws IOException {
        DecodedData decoded = decode(FILES[11]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getFacialSmtKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type9_13_9_14_m1() throws IOException {
        DecodedData decoded = decode(FILES[12]);
        commonAssert(decoded);
        assertEquals(2, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getVariableResLatentImageKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type9_13_m1() throws IOException {
        DecodedData decoded = decode(FILES[13]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getVariableResLatentImageKeys().size());
    }

    @Test
    public void type9_13_std() throws IOException {
        DecodedData decoded = decode(FILES[14]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getVariableResLatentImageKeys().size());
    }

    @Test
    public void type9_14_m1() throws IOException {
        DecodedData decoded = decode(FILES[15]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type9_14_std() throws IOException {
        DecodedData decoded = decode(FILES[16]);
        commonAssert(decoded);
        assertEquals(1, decoded.getMinutiaeDataKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type10_14_17_piv_index_iris() throws IOException {
        DecodedData decoded = decode(FILES[17]);
        commonAssert(decoded);
        assertEquals(1, decoded.getFacialSmtKeys().size());
        assertEquals(2, decoded.getVariableResFingerprintKeys().size());
        assertEquals(1, decoded.getIrisImageKeys().size());
    }

    @Test
    public void type10_branded_tattoo_mark() throws IOException {
        DecodedData decoded = decode(FILES[18]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialSmtKeys().size());
    }

    @Test
    public void type10_sap10() throws IOException {
        DecodedData decoded = decode(FILES[19]);
        commonAssert(decoded);
        assertEquals(1, decoded.getFacialSmtKeys().size());
    }

    @Test
    public void type10_scar_face_sap50() throws IOException {
        DecodedData decoded = decode(FILES[20]);
        commonAssert(decoded);
        assertEquals(6, decoded.getFacialSmtKeys().size());
    }

    @Test
    public void type10_tattoo_face_sap20() throws IOException {
        DecodedData decoded = decode(FILES[21]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialSmtKeys().size());
    }

    @Test
    public void type10_tattoo_zoom() throws IOException {
        DecodedData decoded = decode(FILES[22]);
        commonAssert(decoded);
        assertEquals(2, decoded.getFacialSmtKeys().size());
    }

    @Test
    public void type13_14_latent_match() throws IOException {
        DecodedData decoded = decode(FILES[23]);
        commonAssert(decoded);
        assertEquals(1, decoded.getVariableResLatentImageKeys().size());
        assertEquals(1, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type13_tip_eji_j2l() throws IOException {
        DecodedData decoded = decode(FILES[24]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResLatentImageKeys().size());
    }

    @Test
    public void type13_tip_eji_wsq() throws IOException {
        DecodedData decoded = decode(FILES[25]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResLatentImageKeys().size());
    }

    @Test
    public void type14_amp_nqm_utf8() throws IOException {
        DecodedData decoded = decode(FILES[26]);
        UserDefinedDescriptiveText userDefinedText = decoded.getUserDefinedText(0);
        assertEquals("55", userDefinedText.getLogicalRecordLength());
        assertEquals("00", userDefinedText.getImageDesignationCharacter());
        assertEquals("two chinese characters: 華裔", userDefinedText.getDomainDefinedText());
    }

    @Test
    public void type14_tip_eji_j2l() throws IOException {
        DecodedData decoded = decode(FILES[27]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type14_tip_eji_wsq() throws IOException {
        DecodedData decoded = decode(FILES[28]);
        commonAssert(decoded);
        assertEquals(5, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type14_tpcard_nqm() throws IOException {
        DecodedData decoded = decode(FILES[29]);
        commonAssert(decoded);
        assertEquals(14, decoded.getVariableResFingerprintKeys().size());
    }

    @Test
    public void type15_palms() throws IOException {
        DecodedData decoded = decode(FILES[30]);
        commonAssert(decoded);
        assertEquals(2, decoded.getVariableResPalmprintKeys().size());
    }

    @Test
    public void type17_iris() throws IOException {
        DecodedData decoded = decode(FILES[31]);
        commonAssert(decoded);
        assertEquals(1, decoded.getIrisImageKeys().size());
    }

    private void commonAssert(DecodedData decoded) {
        assertEquals(1, decoded.getTransactionKeys().size());
        assertEquals(1, decoded.getUserDefinedTextKeys().size());

        UserDefinedDescriptiveText userDefinedText = decoded.getUserDefinedText(0);
        assertEquals("57", userDefinedText.getLogicalRecordLength());
        assertEquals("00", userDefinedText.getImageDesignationCharacter());
        assertEquals("domain defined text place holder", userDefinedText.getDomainDefinedText());
    }

    private DecodedData decode(String name) throws IOException {
        InputStream file = SampleTest.class.getClassLoader().getResourceAsStream(String.format(FILE_PATH, name));
        DecodedData decoded = DECODER.decode(file, DecodedData.Format.PNG);
        assertNotNull(decoded);
        // For local check.
        //FileUtils.saveAll(decoded, DecodedData.Format.PNG, "target/" + name);
        return decoded;
    }
}
