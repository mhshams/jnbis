package org.jnbis.internal.record.reader;


import org.jnbis.NistHelper;

/**
 * @author ericdsoto
 */
public class RecordReaderFactory {

    public RecordReader create(NistHelper.Token token) {
        switch (token.crt) {
            case 0:
            case NistHelper.RT_TRANSACTION_INFO:
                return new TransactionInfoReader(token);
            case NistHelper.RT_USER_DEFINED_TEXT:
                return new UserDefinedTextReader(token);
            case NistHelper.RT_LR_GS_FINGERPRINT:
                return new LowResolutionGrayscaleFingerprintReader(token);
            case NistHelper.RT_HR_GS_FINGERPRINT:
                return new HighResolutionGrayscaleFingerprintReader(token);
            case NistHelper.RT_LR_BINARY_FINGERPRINT:
                return new LowResolutionBinaryFingerprintReader(token);
            case NistHelper.RT_HR_BINARY_FINGERPRINT:
                return new HighResolutionBinaryFingerprintReader(token);
            case NistHelper.RT_USER_DEFINED_IMAGE:
                return new UserDefinedImageReader(token);
            case NistHelper.RT_SIGNATURE_IMAGE:
                return new SignatureImageReader(token);
            case NistHelper.RT_MINUTIAE_DATA:
                return new MinutiaeDataReader(token);
            case NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA:
                return new FacialAndSmtImageReader(token);
            case NistHelper.RT_VR_LATENT_IMAGE:
                return new VariableResolutionLatentImageReader(token);
            case NistHelper.RT_VR_FINGERPRINT:
                return new VariableResolutionFingerprintReader(token);
            case NistHelper.RT_VR_PALMPRINT:
                return new VariableResolutionPalmprintReader(token);
            case NistHelper.RT_IRIS_IMAGE:
                return new IrisImageReader(token);
            default:
                return null;
        }

    }

}
