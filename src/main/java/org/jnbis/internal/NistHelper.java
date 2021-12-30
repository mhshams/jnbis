package org.jnbis.internal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 7, 2007
 */
public class NistHelper {
    static final CharsetDecoder CP1256 = Charset.forName("cp1256").newDecoder();
    public static final CharsetDecoder USASCII = Charset.forName("US-ASCII").newDecoder();
    static final CharsetDecoder UTF8 = Charset.forName("UTF-8").newDecoder();
    static final CharsetDecoder UTF16 = Charset.forName("UTF-16").newDecoder();

    // Logical record types (used here.)
    public static final int RT_TRANSACTION_INFO = 1;
    public static final int RT_USER_DEFINED_TEXT = 2;
    public static final int RT_LR_GS_FINGERPRINT = 3;
    public static final int RT_HR_GS_FINGERPRINT = 4;
    public static final int RT_LR_BINARY_FINGERPRINT = 5;
    public static final int RT_HR_BINARY_FINGERPRINT = 6;
    public static final int RT_USER_DEFINED_IMAGE = 7;
    public static final int RT_SIGNATURE_IMAGE = 8;
    public static final int RT_MINUTIAE_DATA = 9;
    public static final int RT_FACIAL_N_SMT_IMAGE_DATA = 10;
    public static final int RT_VR_LATENT_IMAGE = 13;
    public static final int RT_VR_FINGERPRINT = 14;
    public static final int RT_VR_PALMPRINT = 15;
    public static final int RT_USER_DEFINED_TESTING_IMAGE = 16;
    public static final int RT_IRIS_IMAGE = 17;

    // Information separators
    static final char SEP_US = 31;
    static final char SEP_RS = 30;
    static final char SEP_GS = 29;
    public static final char SEP_FS = 28;

    public static final int FIELD_MAX_LENGTH = 1024;

    public static final char[] TAG_SEP_DOT = {'.', '.'};
    public static final char[] TAG_SEP_COLN = {':', ':'};
    public static final char[] TAG_SEP_GSFS = {SEP_GS, SEP_FS};

    public static class Tag {
        public final int type;
        public final int field;

        public Tag(int type, int field) {
            this.type = type;
            this.field = field;
        }
    }

    public static class Token {
        public final byte[] buffer;
        public int pos;

        public String header;
        public int crt;

        public CharsetDecoder charset;

        Token(byte[] buffer) {
            this.buffer = buffer;
            this.charset = CP1256;
        }

        // directory of charset.
        public void setCharSetDecoder(String dcs) {
            if (dcs != null) {
                if (dcs.startsWith("000")) {
                    this.charset = CP1256;
                } else if (dcs.startsWith("002")) {
                    this.charset = UTF16;
                } else if (dcs.startsWith("003")) {
                    this.charset = UTF8;
                }
            }
        }
    }
}
