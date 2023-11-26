package org.jnbis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;

import java.util.stream.Stream;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;

import java.util.Map;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 */

class AnsiReferencesTest {
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
    void type_3() {
        Nist decoded = decode(FILES[0]);
        commonAssert(decoded);
        assertThat(decoded.getLowResGrayscaleFingerprints()).hasSize(1);
    }

    @Test
    void type4_14_slaps() {
        Nist decoded = decode(FILES[1]);
        commonAssert(decoded);
        assertThat(decoded.getHiResGrayscaleFingerprints()).hasSize(2);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type4_slaps() {
        Nist decoded = decode(FILES[2]);
        commonAssert(decoded);
        assertThat(decoded.getHiResGrayscaleFingerprints()).hasSize(4);
    }

    @Test
    void type4_tpcard() {
        Nist decoded = decode(FILES[3]);
        commonAssert(decoded);
        assertThat( decoded.getHiResGrayscaleFingerprints()).hasSize(14);
    }

    @Test
    void type5() {
        Nist decoded = decode(FILES[4]);
        commonAssert(decoded);
        assertThat(decoded.getLowResBinaryFingerprints()).hasSize(1);
    }

    @Test
    void type6() {
        Nist decoded = decode(FILES[5]);
        commonAssert(decoded);
        assertThat(decoded.getHiResBinaryFingerprints()).hasSize(1);
    }

    @Test
    void type7_latent() {
        Nist decoded = decode(FILES[6]);
        commonAssert(decoded);
        assertThat(decoded.getUserDefinedImages()).hasSize(1);
    }


    // 7: Sig, 8: Sig_fax, 9: sig_raw
    @ParameterizedTest
    @ValueSource(ints= {7, 8, 9})
    void type8(final int filePosition) {
        Nist decoded = decode(FILES[filePosition]);
        commonAssert(decoded);
        assertThat(decoded.getSignatures()).hasSize(1);
    }

    @Test
    void type9_4_iafis() {
        Nist decoded = decode(FILES[10]);
        commonAssert(decoded);
        assertThat(decoded.getHiResGrayscaleFingerprints()).hasSize(1);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
    }

    @Test
    void type9_10_14() {
        Nist decoded = decode(FILES[11]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
        assertThat(decoded.getFacialAndSmtImages()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type9_13_9_14_m1() {
        Nist decoded = decode(FILES[12]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(2);
        assertThat(decoded.getVariableResLatentImages()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type9_13_m1() {
        Nist decoded = decode(FILES[13]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
        assertThat(decoded.getVariableResLatentImages()).hasSize(1);
    }

    @Test
    void type9_13_std() {
        Nist decoded = decode(FILES[14]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
        assertThat(decoded.getVariableResLatentImages()).hasSize(1);
    }

    @Test
    void type9_14_m1() {
        Nist decoded = decode(FILES[15]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type9_14_std() {
        Nist decoded = decode(FILES[16]);
        commonAssert(decoded);
        assertThat(decoded.getMinutiaeData()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type10_14_17_piv_index_iris() {
        Nist decoded = decode(FILES[17]);
        commonAssert(decoded);
        assertThat(decoded.getFacialAndSmtImages()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(2);
        assertThat(decoded.getIrisImages()).hasSize(1);
    }

    // 18: branded_tattoo_mark, 19: sap10, 20: scar_face_sap_50
    // 21: tattoo_face_sap_20, 22: tattoo_zom
    @ParameterizedTest
    @CsvSource({"18,2", "19,1", "20,6", "21,2", "22,2"})
    void type10(final int filePosition, final int size) {
        Nist decoded = decode(FILES[filePosition]);
        commonAssert(decoded);
        assertThat(decoded.getFacialAndSmtImages()).hasSize(size);
    }

    @Test
    void type13_14_latent_match() {
        Nist decoded = decode(FILES[23]);
        commonAssert(decoded);
        assertThat(decoded.getVariableResLatentImages()).hasSize(1);
        assertThat(decoded.getVariableResFingerprints()).hasSize(1);
    }

    @Test
    void type13_tip_eji_j2l() {
        Nist decoded = decode(FILES[24]);
        commonAssert(decoded);
        assertThat(decoded.getVariableResLatentImages()).hasSize(5);
    }

    @Test
    void type13_tip_eji_wsq() {
        Nist decoded = decode(FILES[25]);
        commonAssert(decoded);
        assertThat(decoded.getVariableResLatentImages()).hasSize(5);
    }

    @Test
    void type14_amp_nqm_utf8() {
        Nist decoded = decode(FILES[26]);
        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertThat(userDefinedFields)
            .containsEntry(1, "55")
            .containsEntry(2, "00")
            .containsEntry(3, "two chinese characters: 華裔");
    }

    //27: tip_ejij_j2l, 28: tip_eji_wsq, 29: tpcard_nqm
    @ParameterizedTest
    @CsvSource({"27,5", "28,5", "29,14"})
    void type14(final int filePosition, final int size) {
        Nist decoded = decode(FILES[filePosition]);
        commonAssert(decoded);
        assertThat(decoded.getVariableResFingerprints()).hasSize(size);
    }

    @Test
    void type15_palms() {
        Nist decoded = decode(FILES[30]);
        commonAssert(decoded);
        assertThat(decoded.getVariableResPalmprints()).hasSize(2);
    }

    @Test
    void type17_iris() {
        Nist decoded = decode(FILES[31]);
        commonAssert(decoded);
        assertThat(decoded.getIrisImages()).hasSize(1);
    }

    private void commonAssert(Nist decoded) {
        assertThat(decoded.getTransactionInfo()).isNotNull();
        assertThat(decoded.getUserDefinedTexts()).hasSize(1);

        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertThat(userDefinedFields)
            .containsEntry(1, "57")
            .containsEntry(2, "00")
            .containsEntry(3, "domain defined text place holder");
    }

    private Nist decode(String name) {
        String fileName = FileUtils.absolute(String.format(FILE_PATH, name));
        Nist nist = Jnbis.nist().decode(fileName);
        assertThat(nist).isNotNull();
        return nist;
    }
}
