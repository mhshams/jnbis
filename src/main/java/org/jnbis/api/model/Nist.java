package org.jnbis.api.model;

import org.jnbis.api.model.record.*;

import java.util.List;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public abstract class Nist {

    public abstract TransactionInformation getTransactionInfo();

    public abstract List<UserDefinedDescriptiveText> getUserDefinedTexts();

    public abstract List<HighResolutionGrayscaleFingerprint> getHiResGrayscaleFingerprints();

    public abstract List<UserDefinedImage> getUserDefinedImages();

    public abstract List<SignatureImage> getSignatures();

    public abstract List<MinutiaeData> getMinutiaeData();

    public abstract List<FacialAndSmtImage> getFacialAndSmtImages();

    public abstract List<VariableResolutionLatentImage> getVariableResLatentImages();

    public abstract List<VariableResolutionFingerprint> getVariableResFingerprints();

    public abstract List<VariableResolutionPalmprint> getVariableResPalmprints();

    public abstract List<IrisImage> getIrisImages();
}
