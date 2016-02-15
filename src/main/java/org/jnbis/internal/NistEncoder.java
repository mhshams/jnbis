package org.jnbis.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.internal.record.BaseRecord;
import org.jnbis.internal.record.writer.RecordWriter;
import org.jnbis.internal.record.writer.RecordWriterFactory;


/**
 * @author mpresling
 */
public class NistEncoder {
    private RecordWriterFactory writerFactory;
    private ByteArrayOutputStream baos;

    public NistEncoder() {
        writerFactory = new RecordWriterFactory();
        baos = new ByteArrayOutputStream();
    }

    public Nist create() {
        return new InternalNist();
    }
    
    public byte[] encode(Nist nist) throws IOException {
        if (nist == null) {
            throw new IllegalArgumentException("data is null");
        }

        RecordWriter<TransactionInformation> writer = writerFactory.writer(NistHelper.RT_TRANSACTION_INFO);
        TransactionInformation txInfo = nist.getTransactionInfo();
        writer.write(baos, txInfo);
        
        writeRecords(NistHelper.RT_USER_DEFINED_TEXT, nist.getUserDefinedTexts(), true);
        nextRecord();
        
        writeRecords(NistHelper.RT_LR_GS_FINGERPRINT, nist.getLowResGrayscaleFingerprints(), false);
        
        writeRecords(NistHelper.RT_HR_GS_FINGERPRINT, nist.getHiResGrayscaleFingerprints(), false);
        
        writeRecords(NistHelper.RT_LR_BINARY_FINGERPRINT, nist.getLowResBinaryFingerprints(), false);
        
        writeRecords(NistHelper.RT_HR_BINARY_FINGERPRINT, nist.getHiResBinaryFingerprints(), false);
        
        writeRecords(NistHelper.RT_USER_DEFINED_IMAGE, nist.getUserDefinedImages(), false);
        
        writeRecords(NistHelper.RT_SIGNATURE_IMAGE, nist.getSignatures(), false);
        
        writeRecords(NistHelper.RT_MINUTIAE_DATA, nist.getMinutiaeData(), true);
        
        writeRecords(NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA, nist.getFacialAndSmtImages(), true);
        
        writeRecords(NistHelper.RT_VR_LATENT_IMAGE, nist.getVariableResLatentImages(), true);
        
        writeRecords(NistHelper.RT_VR_FINGERPRINT, nist.getVariableResFingerprints(), true);
        
        writeRecords(NistHelper.RT_VR_PALMPRINT, nist.getVariableResPalmprints(), true);
        
        writeRecords(NistHelper.RT_IRIS_IMAGE, nist.getIrisImages(), true);
        
        return baos.toByteArray();
    }

    private <T extends BaseRecord> void writeRecords(int crt, List<T> records, boolean useSeparator) throws IOException {
        if (records == null || records.isEmpty()) {
            return;
        }
        
        if (useSeparator) {
            nextRecord();
        }

        RecordWriter<T> writer = writerFactory.writer(crt);
        for (T record: records) {
            writer.write(baos, record);
        }
    }
    
    private void nextRecord() {
        baos.write(NistHelper.SEP_FS);
    }

}
