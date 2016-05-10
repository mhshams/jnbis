package org.jnbis.internal.record.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.RecordType;

public class UserDefinedTextWriter extends RecordWriter<UserDefinedDescriptiveText> {

    @Override
    public RecordType getRecordType() {
        return RecordType.RT2_USER_DEFINED_TEXT;
    }

    @Override
    public void write(OutputStream out, UserDefinedDescriptiveText record) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(buffer, NistHelper.USASCII);

        String idc = String.format("%d.%03d:%02d", RecordType.RT2_USER_DEFINED_TEXT.type, 2, record.getIdc());
        writer.write(idc);
        writer.write(NistHelper.SEP_GS);

        Map<Integer, String> userFields = record.getUserDefinedFields();
        int count = 1;
        for (Integer key : userFields.keySet()) {
            if (key == null || key < 3) {
                throw new IllegalArgumentException("User defined fields should be 2.003 and above");
            }
            String tag = String.format("%d.%03d:", RecordType.RT2_USER_DEFINED_TEXT.type, key);
            writer.write(tag);

            writer.write(userFields.get(key));

            if (count < userFields.size()) {
                writer.write(NistHelper.SEP_GS);
            }
            count++;
        }

        writer.flush();
        buffer.write(NistHelper.SEP_FS);
        
        writeRecord(out, buffer);
    }

}
