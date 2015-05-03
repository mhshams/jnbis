package org.jnbis;

import java.util.HashMap;
import java.util.Set;

/**
 * this class hold decoded data from a NIST file.
 *
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class DecodedData {
    public enum Format {
        JPEG("jpeg"),
        GIF("gif"),
        PNG("png");

        private String code;

        public String code() {
            return code;
        }

        Format(String code) {
            this.code = code;
        }
    }

    private HashMap<Integer, String> text;
    private HashMap<Integer, BinaryData> binary;

    DecodedData() {
        text = new HashMap<Integer, String>();
        binary = new HashMap<Integer, BinaryData>();
    }

    void putText(Integer key, String value) {
        text.put(key, value);
    }

    void putBinary(Integer key, String type, byte[] value) {
        binary.put(key, new BinaryData(type, value));
    }

    public Set<Integer> getBinaryKeys() {
        return binary.keySet();
    }

    public Set<Integer> getTextKeys() {
        return text.keySet();
    }

    public String getText(Integer key) {
        return text.containsKey(key) ? text.get(key) : null;
    }

    public BinaryData getBinary(Integer key) {
        return binary.containsKey(key) ? binary.get(key) : null;
    }

    public static final class BinaryData {
        private String type;
        private byte[] data;

        public BinaryData(String type, byte[] data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public byte[] getData() {
            return data;
        }
    }
}
