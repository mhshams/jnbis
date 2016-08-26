package org.jnbis.api.handler;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.TransactionInformation.InfoDesignation;
import org.jnbis.api.model.record.TransactionInformation.TransactionContent;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.api.model.record.VariableResolutionFingerprint;
import org.jnbis.internal.NistHelper.RecordType;
import org.jnbis.internal.record.BaseRecord;

public abstract class NistBuilder {

    protected NistHandler handler;
    private TransactionContent transactionContent;
    private int currentIdc = 0;
    
    protected void updateTransactionContent(RecordType recordType, BaseRecord record) {
        if (transactionContent == null) {
            transactionContent = new TransactionContent();
        }
        
        if (transactionContent.getFirstRecordCategoryCode() == 0) {
            transactionContent.setFirstRecordCategoryCode(recordType.type);
        }
        
        transactionContent.setContentRecordCount(transactionContent.getContentRecordCount() + 1);
        
        /* The IDC may be set on the record to link multiple records. If not
         * then assume that it is to be generated/assigned.
         */
        Integer recordIdc = record.getIdc();
        if (recordIdc != null) {
            if (currentIdc < recordIdc) {
                currentIdc = recordIdc + 1;
            }
        } else {
            recordIdc = currentIdc++;
            record.setIdc(recordIdc);
        }
        
        transactionContent.getIdcs().add(new InfoDesignation(recordType.type, recordIdc));
    }
    
    public NistHandler build() {
        Nist nist = getNist();
        nist.getTransactionInfo().setTransactionContent(transactionContent);
        handler.setNist(nist);
        
        transactionContent = null;
        currentIdc = 0;
        return handler;
    }
    
    protected abstract Nist getNist();
    
    public abstract NistBuilder transactionInfo(TransactionInformation type1);
    public abstract NistBuilder add(UserDefinedDescriptiveText type2);
    public abstract NistBuilder add(HighResolutionGrayscaleFingerprint type4);
    public abstract NistBuilder add(VariableResolutionFingerprint type14);
}
