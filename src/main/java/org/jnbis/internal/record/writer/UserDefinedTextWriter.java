package org.jnbis.internal.record.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.internal.NistHelper;

public class UserDefinedTextWriter extends RecordWriter<UserDefinedDescriptiveText> {

    @Override
    public void write(OutputStream out, UserDefinedDescriptiveText record) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(buffer, NistHelper.USASCII.charset().newEncoder());

        String idc = String.format("%d.%03d:%02d", NistHelper.RT_USER_DEFINED_TEXT, 2, record.getIdc());
        writer.write(idc);
        writer.write(NistHelper.SEP_GS);

        Map<Integer, String> userFields = record.getUserDefinedFields();
        int count = 1;
        for (Integer key: userFields.keySet()) {
            if (key == null || key < 3) {
                throw new IllegalArgumentException("User defined fields should be 2.003 and above");
            }
            String tag = String.format("%d.%03d:", NistHelper.RT_USER_DEFINED_TEXT, key);
            writer.write(tag);
            
            writer.write(userFields.get(key));
            
            if (count < userFields.size()) {
                writer.write(NistHelper.SEP_GS);
            }
            count++;
        }
        
        writer.flush();
        int length = buffer.size();
        String header = String.format("%d.%03d:%d", NistHelper.RT_USER_DEFINED_TEXT, 1, length + 10);
        out.write(header.getBytes());
        out.write(NistHelper.SEP_GS);
        
        out.write(buffer.toByteArray());
    }

}
