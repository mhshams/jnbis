package org.jnbis.internal.record.writer;


import java.util.HashMap;
import java.util.Map;

import org.jnbis.internal.NistHelper.RecordType;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public class RecordWriterFactory {
    private static final Map<RecordType, RecordWriter<?>> WRITERS = new HashMap<>();

    static {
        WRITERS.put(RecordType.RT1_TRANSACTION_INFO, new TransactionInfoWriter());
        WRITERS.put(RecordType.RT2_USER_DEFINED_TEXT, new UserDefinedTextWriter());
        WRITERS.put(RecordType.RT4_HR_GS_FINGERPRINT, new HighResolutionGrayscaleFingerprintWriter());
//        WRITERS.put(RecordType.RT7_USER_DEFINED_IMAGE, new UserDefinedImageWriter());
//        WRITERS.put(RecordType.RT8_SIGNATURE_IMAGE, new SignatureImageWriter());
//        WRITERS.put(RecordType.RT9_MINUTIAE_DATA, new MinutiaeDataWriter());
//        WRITERS.put(RecordType.RT10_FACIAL_N_SMT_IMAGE_DATA, new FacialAndSmtImageWriter());
//        WRITERS.put(RecordType.RT13_VR_LATENT_IMAGE, new VariableResolutionLatentImageWriter());
        WRITERS.put(RecordType.RT14_VR_FINGERPRINT, new VariableResolutionFingerprintWriter());
//        WRITERS.put(RecordType.RT15_VR_PALMPRINT, new VariableResolutionPalmprintWriter());
//        WRITERS.put(RecordType.RT17_IRIS_IMAGE, new IrisImageWriter());
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseRecord> RecordWriter<T> writer(RecordType type) {
        if (!WRITERS.containsKey(type)) {
            throw new UnsupportedOperationException("Record type " + type.type + " not supported");
        }
        return (RecordWriter<T>) WRITERS.get(type);
    }
}
