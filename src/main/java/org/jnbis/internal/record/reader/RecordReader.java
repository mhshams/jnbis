package org.jnbis.internal.record.reader;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;
import org.jnbis.internal.NistHelper.InformationItem;
import org.jnbis.internal.NistHelper.Token;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public abstract class RecordReader {
    
    private static final Pattern fieldPattern = Pattern.compile("^(\\d+)\\.(\\d+)$");

    protected DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    abstract public BaseRecord read(NistHelper.Token token);
    
    public static Field nextField(Token token) {
        ByteBuffer buffer = token.buffer;
        String fieldHeader = null;
        
        /* Walk the first few bytes until we find the : separator */
        byte[] bytes = readToSeparator(buffer, NistHelper.TAG_SEP_COLN);
        
        /* Step past the separator we just searched for */
        if (buffer.hasRemaining()) {
            buffer.get();
        }

        if (bytes.length == 0 || bytes[0] == NistHelper.SEP_FS) {
            return null;
        }

        fieldHeader = new String(bytes);

        Matcher m = fieldPattern.matcher(fieldHeader);
        if (m.matches()) {
            int recordType = Integer.parseInt(m.group(1));
            if (recordType != token.crt.type) {
                throw new IllegalArgumentException(String.format("Expected field of Type-%s but got Type-%s", token.crt.type, recordType));
            }
            int fieldNum = Integer.parseInt(m.group(2));
            
            byte[] fieldValue = null;
            /* If it's a binary field then don't read the content because it may contain
             * separator characters that will muck up the buffer position when reading
             * the binary content later.
             */
            if (fieldNum != 999) {
                fieldValue = readToSeparator(buffer, NistHelper.TAG_SEP_GSFS);

                /* Step past the separator */
                buffer.get();
            }
            
            return new Field(token.crt, fieldNum, fieldValue);
        }
        
        throw new IllegalStateException("Couldn't find expected field headers");
    }
    
    public static InformationItem nextInformationItem(Token token) {
        ByteBuffer buffer = token.buffer;
        
        /* Walk the first few bytes until we find the US or RS separator */
        byte[] bytes = readToSeparator(buffer, NistHelper.TAG_SEP_USRS);
        
        /* Step past the separator */
        if (buffer.hasRemaining()) {
            buffer.get();
        }

        if (bytes.length == 0) {
            return null;
        }
        
        InformationItem subField = new InformationItem(bytes);
        return subField;
    }
    
    public static byte[] readToSeparator(ByteBuffer buffer, char[] separators) {
        buffer.mark();
        int i = 0;
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            
            boolean found = false;
            for (char c: separators) {
                if (c == b) {
                    found = true;
                    break;
                }
            }
            
            if (found) {
                break;
            }
            i++;
        }
        
        buffer.reset();
        
        byte[] bytes = new byte[i];
        buffer.get(bytes);
        
        return bytes;
    }

    protected Date parseDate(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
