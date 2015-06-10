package org.jnbis;


import org.jnbis.record.*;

/**
 * Created by ericdsoto on 6/8/15.
 */
public class RecordReaderFactory {

    public RecordReader create(NistHelper.Token token) {
        switch(token.crt) {
            case 0:
            case NistHelper.RT_TRANSACTION_INFO:
                return new TransactionInfoReader(token, new TransactionInformation());
            case NistHelper.RT_USER_DEFINED_TEXT:
                return new UserDefinedTextReader(token, new UserDefinedDescriptiveText());
            case NistHelper.RT_LR_GS_FINGERPRINT:
                return new LowResolutionGrayscaleFingerprintReader(token, new LowResolutionGrayscaleFingerprint());
            case NistHelper.RT_HR_GS_FINGERPRINT:
                return new HighResolutionGrayscaleFingerprintReader(token, new HighResolutionGrayscaleFingerprint());
            case NistHelper.RT_LR_BINARY_FINGERPRINT:
                return new LowResolutionBinaryFingerprintReader(token, new LowResolutionBinaryFingerprint());
            case NistHelper.RT_HR_BINARY_FINGERPRINT:
                return new HighResolutionBinaryFingerprintReader(token, new HighResolutionBinaryFingerprint());
            case NistHelper.RT_USER_DEFINED_IMAGE:
                return new UserDefinedImageReader(token, new UserDefinedImage());
            case NistHelper.RT_SIGNATURE_IMAGE:
                return new SignatureImageReader(token, new SignatureImage());
            case NistHelper.RT_MINUTIAE_DATA:
                return new MinutiaeDataReader(token, new MinutiaeData());
            case NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA:
                return new FacialAndSmtImageReader(token, new FacialAndSmtImage());
            case NistHelper.RT_VR_LATENT_IMAGE:
                return new VariableResolutionLatentImageReader(token, new VariableResolutionLatentImage());
            case NistHelper.RT_VR_FINGERPRINT:
                return new VariableResolutionFingerprintReader(token, new VariableResolutionFingerprint());
            case NistHelper.RT_VR_PALMPRINT:
                return new VariableResolutionPalmprintReader(token, new VariableResolutionPalmprint());
            case NistHelper.RT_IRIS_IMAGE:
                return new IrisImageReader(token, new IrisImage());
            default:
                return null;
        }

    }

}
