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

        //TODO Change to the style of VariableResolutionFingerprintWriter
        for (int i = 2; i < 16; i++) {

            String tag = fieldTag(RecordType.RT1_TRANSACTION_INFO, i);
            writer.write(tag);

            switch (i) {
            case 2:
                if (record.getVersion() != null) {
                    writer.write(record.getVersion());
                } else {
                    writer.write("0500");
                }
                break;
            case 3:
                TransactionContent cnt = record.getTransactionContent();
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
                    writer.write(String.format("%02d", idc.getInformationDesignationCharacter()));
                    if (idcIndex < idcCount - 1) {
                        writer.write(NistHelper.SEP_RS);
                    }
                }
                break;
            case 4:
                writer.write(record.getTypeOfTransaction());
                break;
            case 5:
                writer.write(formatDate(record.getDate()));
                break;
            case 6:
                /* Optional */
                if (record.getPriority() != null && !record.getPriority().trim().isEmpty()) {
                    writer.write(record.getPriority());
                }
                break;
            case 7:
                writer.write(record.getDestinationAgencyId());
                break;
            case 8:
                writer.write(record.getOriginatingAgencyId());
                break;
            case 9:
                writer.write(record.getControlNumber());
                break;
            case 10:
                /* Optional */
                if (record.getControlReferenceNumber() != null && !record.getControlReferenceNumber().trim().isEmpty()) {
                    writer.write(record.getControlReferenceNumber());
                }
                break;
            case 11:
                writer.write(record.getNativeScanningResolution());
                break;
            case 12:
                writer.write(record.getNominalTransmittingResolution());
                break;
            case 13:
                /* Optional */
                if (record.getDomainName() != null && !record.getDomainName().trim().isEmpty()) {
                    writer.write(record.getDomainName());
                    /*
                     * NOTE: Don't currently support DVN (domain version number)
                     */
                }
                break;
            case 14:
                /* Optional */
                if (record.getGreenwichMeanTime() != null && !record.getGreenwichMeanTime().trim().isEmpty()) {
                    writer.write(record.getGreenwichMeanTime());
                }
                break;
            case 15:
                writer.write(String.format("%03d", 3));
                writer.write(NistHelper.SEP_US);
                writer.write(NistHelper.UTF8.name());
                break;
            }

            if (i < 15) {
                /* Don't put a separator after the last field */
                writer.write(NistHelper.SEP_GS);
            }
        }

        writer.flush();
        buffer.write(NistHelper.SEP_FS);
        
        writeRecord(out, buffer);        
        
        writer.close();
    }
}
