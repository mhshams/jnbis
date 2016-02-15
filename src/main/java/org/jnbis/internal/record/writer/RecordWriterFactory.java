package org.jnbis.internal.record.writer;


import org.jnbis.internal.NistHelper;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public class RecordWriterFactory {
    private static final RecordWriter<?>[] WRITERS = new RecordWriter[18];

    static {
        WRITERS[0] = new TransactionInfoWriter();
        WRITERS[NistHelper.RT_TRANSACTION_INFO] = new TransactionInfoWriter();
        WRITERS[NistHelper.RT_USER_DEFINED_TEXT] = new UserDefinedTextWriter();
//        WRITERS[NistHelper.RT_LR_GS_FINGERPRINT] = new LowResolutionGrayscaleFingerprintWriter();
        WRITERS[NistHelper.RT_HR_GS_FINGERPRINT] = new HighResolutionGrayscaleFingerprintWriter();
//        WRITERS[NistHelper.RT_LR_BINARY_FINGERPRINT] = new LowResolutionBinaryFingerprintWriter();
//        WRITERS[NistHelper.RT_HR_BINARY_FINGERPRINT] = new HighResolutionBinaryFingerprintWriter();
//        WRITERS[NistHelper.RT_USER_DEFINED_IMAGE] = new UserDefinedImageWriter();
//        WRITERS[NistHelper.RT_SIGNATURE_IMAGE] = new SignatureImageWriter();
//        WRITERS[NistHelper.RT_MINUTIAE_DATA] = new MinutiaeDataWriter();
//        WRITERS[NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA] = new FacialAndSmtImageWriter();
//        WRITERS[11] = null;
//        WRITERS[12] = null;
//        WRITERS[NistHelper.RT_VR_LATENT_IMAGE] = new VariableResolutionLatentImageWriter();
//        WRITERS[NistHelper.RT_VR_FINGERPRINT] = new VariableResolutionFingerprintWriter();
//        WRITERS[NistHelper.RT_VR_PALMPRINT] = new VariableResolutionPalmprintWriter();
//        WRITERS[16] = null;
//        WRITERS[NistHelper.RT_IRIS_IMAGE] = new IrisImageWriter();
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseRecord> RecordWriter<T> writer(int crt) {
        if (WRITERS[crt] == null) {
            throw new UnsupportedOperationException("Record type " + crt + " not supported");
        }
        return (RecordWriter<T>) WRITERS[crt];
    }
}
