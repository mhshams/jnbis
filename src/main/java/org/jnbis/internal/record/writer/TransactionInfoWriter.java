package org.jnbis.internal.record.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.TransactionInformation.InfoDesignation;
import org.jnbis.api.model.record.TransactionInformation.TransactionContent;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.RecordType;

/**
 * @author argonaut
 */
public class TransactionInfoWriter extends RecordWriter<TransactionInformation> {

    @Override
    public RecordType getRecordType() {
        return RecordType.RT1_TRANSACTION_INFO;
    }

    @Override
    public void write(OutputStream out, TransactionInformation record) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(buffer, NistHelper.USASCII);

        writeField(writer, 2, "0500");

        TransactionContent cnt = record.getTransactionContent();
        writer.write(fieldTag(3));
        writer.write(String.valueOf(cnt.getFirstRecordCategoryCode()));
        writer.write(NistHelper.SEP_US);
        writer.write(String.valueOf(cnt.getContentRecordCount()));
        writer.write(NistHelper.SEP_RS);

        List<InfoDesignation> idcs = cnt.getIdcs();
        int idcCount = idcs.size();
        for (int idcIndex = 0; idcIndex < idcCount; idcIndex++) {
            InfoDesignation idc = idcs.get(idcIndex);
            writer.write(String.valueOf(idc.getRecordCategoryCode()));
            writer.write(NistHelper.SEP_US);
            writer.write(String.format("%d", idc.getInformationDesignationCharacter()));
            if (idcIndex < idcCount - 1) {
                writer.write(NistHelper.SEP_RS);
            }
        }
        writer.write(NistHelper.SEP_GS);
        
        writeField(writer, 4, record.getTypeOfTransaction());
        
        writeField(writer, 5, record.getDate());

        /* Optional */
        if (record.getPriority() != null && !record.getPriority().trim().isEmpty()) {
            writeField(writer, 6, record.getPriority());
        }
        
        writeField(writer, 7, record.getDestinationAgencyId());

        writeField(writer, 8, record.getOriginatingAgencyId());

        writeField(writer, 9, record.getControlNumber());

        /* Optional */
        if (record.getControlReferenceNumber() != null && !record.getControlReferenceNumber().trim().isEmpty()) {
            writeField(writer, 10, record.getControlReferenceNumber());
        }

        writeField(writer, 11, record.getNativeScanningResolution());
        writeField(writer, 12, record.getNominalTransmittingResolution());
        
        /* Optional */
        if (record.getDomainName() != null && !record.getDomainName().trim().isEmpty()) {
            writeField(writer, 13, record.getDomainName());
            writer.write(NistHelper.SEP_US); /* Required, even when DVN not present */
            /*
             * NOTE: Don't currently support optional DVN (domain version number)
             */
        }

        /* Optional */
        if (record.getGreenwichMeanTime() != null && !record.getGreenwichMeanTime().trim().isEmpty()) {
            writeField(writer, 14, record.getGreenwichMeanTime());
        }

        /* Character Encoding (DCS) */
        writer.write(fieldTag(15));
        writer.write(String.valueOf(3));
        writer.write(NistHelper.SEP_US);
        writer.write(NistHelper.UTF8.name());
        /* CSV not currently supported */ 
        writer.write(NistHelper.SEP_US); /* Required, even when DVN not present */

        writer.flush();
        
        writeRecord(out, buffer);        
        
        writer.close();
    }
}
