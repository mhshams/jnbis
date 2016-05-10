package org.jnbis.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.internal.NistHelper.RecordType;
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

        RecordWriter<TransactionInformation> writer = writerFactory.writer(RecordType.RT1_TRANSACTION_INFO);
        TransactionInformation txInfo = nist.getTransactionInfo();
        writer.write(baos, txInfo);
        
        writeRecords(RecordType.RT2_USER_DEFINED_TEXT, nist.getUserDefinedTexts());
        
        writeRecords(RecordType.RT4_HR_GS_FINGERPRINT, nist.getHiResGrayscaleFingerprints());
        
        writeRecords(RecordType.RT7_USER_DEFINED_IMAGE, nist.getUserDefinedImages());
        
        writeRecords(RecordType.RT8_SIGNATURE_IMAGE, nist.getSignatures());
        
        writeRecords(RecordType.RT9_MINUTIAE_DATA, nist.getMinutiaeData());
        
        writeRecords(RecordType.RT10_FACIAL_N_SMT_IMAGE_DATA, nist.getFacialAndSmtImages());
        
        writeRecords(RecordType.RT13_VR_LATENT_IMAGE, nist.getVariableResLatentImages());
        
        writeRecords(RecordType.RT14_VR_FINGERPRINT, nist.getVariableResFingerprints());
        
        writeRecords(RecordType.RT15_VR_PALMPRINT, nist.getVariableResPalmprints());
        
        writeRecords(RecordType.RT17_IRIS_IMAGE, nist.getIrisImages());
        
        return baos.toByteArray();
    }

    private <T extends BaseRecord> void writeRecords(RecordType type, List<T> records) throws IOException {
        if (records == null || records.isEmpty()) {
            return;
        }
        
        RecordWriter<T> writer = writerFactory.writer(type);
        for (T record: records) {
            writer.write(baos, record);
        }
    }
    
}
