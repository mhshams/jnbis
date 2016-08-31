package org.jnbis.internal.record.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.RecordType;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author argonaut
 */
public abstract class RecordWriter<T extends BaseRecord> {

    protected DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    abstract public RecordType getRecordType();
    abstract public void write(OutputStream out, T record) throws IOException;

    protected String fieldTag(int field) {
        return fieldTag(getRecordType(), field);
    }
    
    protected String fieldTag(RecordType recordType, int field) {
        return String.format("%d.%03d:", recordType.type, field);
    }
    
    protected void writeField(Writer writer, int field, int value) throws IOException {
        writeField(writer, field, new Integer(value));
    }
    
    protected void writeField(Writer writer, int field, Date value) throws IOException {
        writeField(writer, field, formatDate(value));
    }
    
    protected void writeField(Writer writer, int field, Object value) throws IOException {
        writer.write(fieldTag(field));
        if (value != null) {
            writer.write(String.valueOf(value));
        }
        writer.write(NistHelper.SEP_GS);
    }
    
    protected void writeRecord(OutputStream out, ByteArrayOutputStream buffer) throws IOException {
        writeRecord(out, buffer, false);
    }
    
    protected void writeRecord(OutputStream out, ByteArrayOutputStream buffer, boolean removeTrailingSeparator) throws IOException {
        /*
         * The 001 LEN field contains the length of the entire record, including the
         * size of the LEN field itself.
         */
        String header = fieldTag(1);
        
        int bufferLength = removeTrailingSeparator ? buffer.size() - 1 : buffer.size();
        
        /* +1 for SEP_GS inserted after header, +1 for SEP_FS at end */
        int intermediateLength = header.length() + 1 + bufferLength + 1;
        
        /*
         * If the intermediate length is close to pushing the total length to
         * an extra character (ie, 9990 would become a total record length of
         * 10002 for a Type-14 record) then we have to correct for this by iterating
         * until we get the right total length value.
         */
        String fullHeader = null;
        int totalLength = 0;
        for (int i = 0; i < 10; i++) {
            totalLength = intermediateLength + String.valueOf(intermediateLength + i).length();
            fullHeader = header + totalLength;
            
            if (fullHeader.length() + bufferLength == totalLength) {
                break;
            }
        }

        out.write(fullHeader.getBytes());
        out.write(NistHelper.SEP_GS);
        
        /*
         * In some cases, such as when a Type-14 record doesn't have image data, we need to
         * drop the GS separator from the previous field so we don't end up with <GS><R
         */
        if (!removeTrailingSeparator) {
            out.write(buffer.toByteArray());
        } else {
            byte[] data = buffer.toByteArray();
            out.write(data, 0, data.length - 1);
        }
        
        out.write(NistHelper.SEP_FS);
    }
    
    protected String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }
}
