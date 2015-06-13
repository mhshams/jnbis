package org.jnbis.api.model;

import org.jnbis.api.model.record.*;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public abstract class Nist {

    public abstract Set<Integer> getUserDefinedTextKeys();

    public abstract Set<Integer> getLowResBinaryFingerPrintKeys();

    public abstract Set<Integer> getHiResBinaryFingerPrintKeys();

    public abstract Set<Integer> getUserDefinedImageKeys();

    public abstract Set<Integer> getSignatureKeys();

    public abstract Set<Integer> getMinutiaeDataKeys();

    public abstract Set<Integer> getFacialSmtKeys();

    public abstract Set<Integer> getVariableResLatentImageKeys();

    public abstract Set<Integer> getVariableResFingerprintKeys();

    public abstract Set<Integer> getVariableResPalmprintKeys();

    public abstract Set<Integer> getIrisImageKeys();

    public abstract TransactionInformation getTransactionInfo();

    public abstract UserDefinedDescriptiveText getUserDefinedText(Integer key);

    public abstract List<LowResolutionGrayscaleFingerprint> getLowResGrayscaleFingerprints();

    public abstract List<HighResolutionGrayscaleFingerprint> getHiResGrayscaleFingerprints();

    public abstract LowResolutionBinaryFingerprint getLowResBinaryFingerprint(Integer key);

    public abstract HighResolutionBinaryFingerprint getHiResBinaryFingerprint(Integer key);

    public abstract UserDefinedImage getUserDefinedImage(Integer key);

    public abstract SignatureImage getSignature(Integer key);

    public abstract MinutiaeData getMinutiaeData(Integer key);

    public abstract FacialAndSmtImage getFacialAndSmtImage(Integer key);

    public abstract VariableResolutionLatentImage getVariableResLatentImage(Integer key);

    public abstract VariableResolutionFingerprint getVariableResFingerprint(Integer key);

    public abstract VariableResolutionPalmprint getVariableResPalmprint(Integer key);

    public abstract IrisImage getIrisImage(Integer key);
}
