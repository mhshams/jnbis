package org.jnbis.internal.record.reader;


import java.util.HashMap;
import java.util.Map;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.RecordType;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public class RecordReaderFactory {

    private static final Map<RecordType, RecordReader> READERS = new HashMap<>();

    static {
        READERS.put(RecordType.RT1_TRANSACTION_INFO, new TransactionInfoReader());
        READERS.put(RecordType.RT2_USER_DEFINED_TEXT, new UserDefinedTextReader());
        READERS.put(RecordType.RT4_HR_GS_FINGERPRINT, new HighResolutionGrayscaleFingerprintReader());
        READERS.put(RecordType.RT7_USER_DEFINED_IMAGE, new UserDefinedImageReader());
        READERS.put(RecordType.RT8_SIGNATURE_IMAGE, new SignatureImageReader());
        READERS.put(RecordType.RT9_MINUTIAE_DATA, new MinutiaeDataReader());
        READERS.put(RecordType.RT10_FACIAL_N_SMT_IMAGE_DATA, new FacialAndSmtImageReader());
        READERS.put(RecordType.RT13_VR_LATENT_IMAGE, new VariableResolutionLatentImageReader());
        READERS.put(RecordType.RT14_VR_FINGERPRINT, new VariableResolutionFingerprintReader());
        READERS.put(RecordType.RT15_VR_PALMPRINT, new VariableResolutionPalmprintReader());
        READERS.put(RecordType.RT17_IRIS_IMAGE, new IrisImageReader());
    }

    public RecordReader reader(NistHelper.Token token) {
        return READERS.get(token.crt);
    }

    public BaseRecord read(NistHelper.Token token) {
        return reader(token).read(token);
    }
}
