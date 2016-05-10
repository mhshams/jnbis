package org.jnbis.internal;

import java.util.Date;

import org.jnbis.api.handler.NistBuilder;
import org.jnbis.api.handler.NistHandler;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.api.model.record.VariableResolutionFingerprint;
import org.jnbis.internal.NistHelper.RecordType;

public class InternalNistBuilder extends NistBuilder {

    private InternalNist nist;
    
    public InternalNistBuilder(NistHandler handler) {
        this.handler = handler;
        this.nist = new InternalNist();
    }
    
    @Override
    protected Nist getNist() {
        // Build CNT
        
        nist.getTransactionInfo().setDate(new Date());
        return nist;
    }

    @Override
    public NistBuilder transactionInfo(TransactionInformation info) {
        nist.setTransactionInfo(info);
        return this;
    }

    @Override
    public NistBuilder add(UserDefinedDescriptiveText type2) {
        nist.addUserDefinedText(type2);
        updateTransactionContent(RecordType.RT2_USER_DEFINED_TEXT, type2);
        return this;
    }

    @Override
    public NistBuilder add(HighResolutionGrayscaleFingerprint type4) {
        nist.addHiResGrayscaleFingerPrint(type4);
        updateTransactionContent(RecordType.RT4_HR_GS_FINGERPRINT, type4);
        return this;
    }

    @Override
    public NistBuilder add(VariableResolutionFingerprint type14) {
        nist.addVariableResFingerprint(type14);
        updateTransactionContent(RecordType.RT14_VR_FINGERPRINT, type14);
        return this;
    }

}
