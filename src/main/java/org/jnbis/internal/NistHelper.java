package org.jnbis.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 7, 2007
 */
public class NistHelper {
    public static final Charset USASCII = Charset.forName("US-ASCII");
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final Charset UTF16 = Charset.forName("UTF-16");

    // Logical record types (used here.)
    public static enum RecordType {
        RT1_TRANSACTION_INFO(1, false),
        RT2_USER_DEFINED_TEXT(2, false),
        RT4_HR_GS_FINGERPRINT(4, true),
        RT7_USER_DEFINED_IMAGE(7, true),
        RT8_SIGNATURE_IMAGE(8, true),
        RT9_MINUTIAE_DATA(9, false),
        RT10_FACIAL_N_SMT_IMAGE_DATA(10, false),
        RT13_VR_LATENT_IMAGE(13, false),
        RT14_VR_FINGERPRINT(14, false),
        RT15_VR_PALMPRINT(15, false),
        RT17_IRIS_IMAGE(17, false)
        ;
        
        public int type;
        public boolean isBinary;

        private RecordType(int number, boolean isBinary) {
            this.type = number;
            this.isBinary = isBinary;
        }
        
        public static RecordType valueOf(int type) {
            for (RecordType val: values()) {
                if (val.type == type) {
                    return val;
                }
            }
            
            return null;
        }
    }
    

    // Information separators
    public static final char SEP_US = 31;
    public static final char SEP_RS = 30;
    public static final char SEP_GS = 29;
    public static final char SEP_FS = 28;

    public static final int FIELD_MAX_LENGTH = 1024;

    public static final char[] TAG_SEP_DOT = {'.'};
    public static final char[] TAG_SEP_COLN = {':'};
    public static final char[] TAG_SEP_GSFS = {SEP_GS, SEP_FS};
    public static final char[] TAG_SEP_USRS = {SEP_US, SEP_RS};
    
    public static class Field {
        public final RecordType recordType;
        public final int fieldNumber;
        public final byte[] value;

        public Field(RecordType recordType, int field, byte[] value) {
            this.recordType = recordType;
            this.fieldNumber = field;
            this.value = value;
        }
        
        public String asString() {
            return asString(NistHelper.USASCII);
        }
        
        public String asString(Charset charset) {
            return new String(value, charset);
        }
        
        public Integer asInteger() {
            return new Integer(asString());
        }
    }
    
    public static class InformationItem extends Field {
        public InformationItem(byte[] value) {
            super(null, 0, value);
        }
    }

    public static class Token {
        public final ByteBuffer buffer;
        public RecordType crt;

        public Charset charset;

        public Token(byte[] bytes) {
            this.buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        
        public Token(ByteBuffer buffer) {
            this.buffer = buffer;
            this.charset = USASCII;
        }

        // directory of charset.
        public void setCharSet(String dcs) {
            if (dcs != null) {
                if (dcs.startsWith("000")) {
                    this.charset = USASCII;
                } else if (dcs.startsWith("002")) {
                    this.charset = UTF16;
                } else if (dcs.startsWith("003")) {
                    this.charset = UTF8;
                }
            }
        }
    }
}
