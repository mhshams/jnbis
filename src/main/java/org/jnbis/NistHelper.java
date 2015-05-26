package org.jnbis;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 7, 2007
 */
public class NistHelper {
    static final CharsetDecoder CP1256 = Charset.forName("cp1256").newDecoder();
    static final CharsetDecoder USASCII = Charset.forName("US-ASCII").newDecoder();
    static final CharsetDecoder UTF8 = Charset.forName("UTF-8").newDecoder();
    static final CharsetDecoder UTF16 = Charset.forName("UTF-16").newDecoder();

    // Logical record types (used here.)
    static final int RT_TRANSACTION_INFO = 1;
    static final int RT_USER_DEFINED_TEXT = 2;
    static final int RT_HR_GS_FINGERPRINT = 4;
    static final int RT_FACIAL_N_SMT_IMAGE_DATA = 10;

    // Information separators
    static final char SEP_US = 31;
    static final char SEP_RS = 30;
    static final char SEP_GS = 29;
    static final char SEP_FS = 28;

    static final int FIELD_MAX_LENGTH = 1024;

    static final char[] TAG_SEP_DOT = {'.', '.'};
    static final char[] TAG_SEP_COLN = {':', ':'};
    static final char[] TAG_SEP_GSFS = {SEP_GS, SEP_FS};

    static class Tag {
        final int type;
        final int field;

        public Tag(int type, int field) {
            this.type = type;
            this.field = field;
        }
    }

    static class Token {
        final byte[] buffer;
        int pos;

        String header;
        int crt;

        CharsetDecoder charset;

        Token(byte[] buffer) {
            this.buffer = buffer;
            this.charset = CP1256;
        }

        // directory of charset.
        void setCharSetDecoder(String dcs) {
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
