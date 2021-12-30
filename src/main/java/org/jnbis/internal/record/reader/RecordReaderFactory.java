package org.jnbis.internal.record.reader;


import org.jnbis.api.model.record.UserDefinedTestingImage;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public class RecordReaderFactory {
    private static final RecordReader NOT_SUPPORTED = new RecordReader() {
        @Override
        public BaseRecord read(NistHelper.Token token) {
            throw new UnsupportedOperationException("record type: " + token.crt + " no supported!");
        }
    };

    private static final RecordReader[] READERS = new RecordReader[18];

    static {
        READERS[0] = new TransactionInfoReader();
        READERS[NistHelper.RT_TRANSACTION_INFO] = new TransactionInfoReader();
        READERS[NistHelper.RT_USER_DEFINED_TEXT] = new UserDefinedTextReader();
        READERS[NistHelper.RT_LR_GS_FINGERPRINT] = new LowResolutionGrayscaleFingerprintReader();
        READERS[NistHelper.RT_HR_GS_FINGERPRINT] = new HighResolutionGrayscaleFingerprintReader();
        READERS[NistHelper.RT_LR_BINARY_FINGERPRINT] = new LowResolutionBinaryFingerprintReader();
        READERS[NistHelper.RT_HR_BINARY_FINGERPRINT] = new HighResolutionBinaryFingerprintReader();
        READERS[NistHelper.RT_USER_DEFINED_IMAGE] = new UserDefinedImageReader();
        READERS[NistHelper.RT_SIGNATURE_IMAGE] = new SignatureImageReader();
        READERS[NistHelper.RT_MINUTIAE_DATA] = new MinutiaeDataReader();
        READERS[NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA] = new FacialAndSmtImageReader();
        READERS[11] = NOT_SUPPORTED;
        READERS[12] = NOT_SUPPORTED;
        READERS[NistHelper.RT_VR_LATENT_IMAGE] = new VariableResolutionLatentImageReader();
        READERS[NistHelper.RT_VR_FINGERPRINT] = new VariableResolutionFingerprintReader();
        READERS[NistHelper.RT_VR_PALMPRINT] = new VariableResolutionPalmprintReader();
        READERS[NistHelper.RT_USER_DEFINED_TESTING_IMAGE] = new UserDefinedTestingImageReader();
        READERS[NistHelper.RT_IRIS_IMAGE] = new IrisImageReader();
    }

    public RecordReader reader(NistHelper.Token token) {
        return READERS[token.crt];
    }

    public BaseRecord read(NistHelper.Token token) {
        return reader(token).read(token);
    }
}
