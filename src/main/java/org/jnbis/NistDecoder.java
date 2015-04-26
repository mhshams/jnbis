package org.jnbis;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

/**
 * @author hamed
 * @version 1.0
 * @since Apr 29, 2007
 */
public class NistDecoder {

    private WsqDecoder wsqDecoder;
    private ImageUtils imageUtils;

    public NistDecoder() {
        wsqDecoder = new WsqDecoder();
        imageUtils = new ImageUtils();
    }

    public DecodedData decode(String fileName, DecodedData.Format fingerImageFormat) throws IOException {
        return decode(new File(fileName), fingerImageFormat);
    }

    public DecodedData decode(File file, DecodedData.Format fingerImageFormat) throws IOException {
        return decode(new FileInputStream(file), fingerImageFormat);
    }

    public DecodedData decode(InputStream inputStream, DecodedData.Format fingerImageFormat) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray(), fingerImageFormat);
    }

    public DecodedData decode(byte[] nist, DecodedData.Format fingerImageFormat) {
        if (nist == null || nist.length == 0) {
            throw new IllegalArgumentException("data is null or zero length");
        }

        NistHelper.Token token = new NistHelper.Token(nist);
        DecodedData decoded = new DecodedData();

        readTransactionInformation(token);

        while (nextRecord(token)) {
            if (token.crt == NistHelper.RT_USER_DEFINED_TEXT) {
                readUserDefinedDescriptiveText(token, decoded);

            } else if (token.crt == NistHelper.RT_HR_GS_FINGERPRINT) {
                Wsq wsq = readHighResolutionGrayscaleFingerprintImage(token);
                Bitmap bitmap = wsqDecoder.decode(wsq.getData());

                switch (fingerImageFormat) {
                    case JPEG:
                        decoded.putBinary(wsq.getId(), fingerImageFormat.code(), imageUtils.bitmap2jpeg(bitmap));
                        break;
                    case GIF:
                        decoded.putBinary(wsq.getId(), fingerImageFormat.code(), imageUtils.bitmap2gif(bitmap));
                        break;
                    case PNG:
                        decoded.putBinary(wsq.getId(), fingerImageFormat.code(), imageUtils.bitmap2png(bitmap));
                        break;
                    default:
                        throw new RuntimeException("unsupported image format.");
                }

            } else if (token.crt == NistHelper.RT_FACIAL_N_SMT_IMAGE_DATA) {
                readImageData(token, decoded);
            }
        }

        return decoded;
    }

    private void readTransactionInformation(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T1::NULL pointer to T1 record");
        }

        while (true) {
            NistHelper.Tag tag = getTagInfo(token);

            if (tag.type != NistHelper.RT_TRANSACTION_INFO) {
                throw new RuntimeException("T1::Invalid Record Type : " + tag.type);
            }

            String value = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);

            if (tag.field == 3) {
                token.header = value;
            } else if (tag.field == 15) {
                token.setCharSetDecoder(value);
            }

            if (token.buffer[token.pos++] == NistHelper.SEP_FS) {
                break;
            }
        }
    }

    private void readUserDefinedDescriptiveText(NistHelper.Token token, DecodedData DecodedData) {
        if (token.pos >= token.buffer.length) {
            throw new IllegalArgumentException("T1::NULL pointer to T2 record");
        }

        while (true) {
            NistHelper.Tag tag = getTagInfo(token);

            if (tag.type != NistHelper.RT_USER_DEFINED_TEXT) {
                throw new RuntimeException("T2::Invalid Record type = " + tag.type);
            }

            String value = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, true);

            DecodedData.putText(tag.field, value);

            if (token.buffer[token.pos++] == NistHelper.SEP_FS) {
                break;
            }
        }
    }

    private Wsq readHighResolutionGrayscaleFingerprintImage(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T4::NULL pointer to T4 record");
        }

        //Assigning t4-Header values
        int length = (int) readInt(token);
        int fingerPrintNo = token.buffer[token.pos + 6];

        int dataSize = length - 18;

        if (token.pos + dataSize + 17 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 18;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);

        token.pos += length;

        return new Wsq(data, fingerPrintNo);
    }

    private void readImageData(NistHelper.Token token, DecodedData DecodedData) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T10::NULL pointer to T10 record");
        }

        int start = token.pos;

        NistHelper.Tag tag = getTagInfo(token);
        if (tag.field != 1) {
            throw new RuntimeException("T10::Invalid Record type = " + tag.type);
        }

        int length = Integer.parseInt(nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false));
        String format = "";
        while (true) {

            token.pos++;

            tag = getTagInfo(token);
            if (tag.field == 999) {
                byte[] data = new byte[length - (token.pos - start)];
                System.arraycopy(token.buffer, token.pos, data, 0, data.length);
                DecodedData.putBinary(0, format, data);
                token.pos = token.pos + data.length + 1;
                break;
            }

            if (tag.field == 11) {
                format = nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            } else {
                nextWord(token, NistHelper.TAG_SEP_GSFS, NistHelper.FIELD_MAX_LENGTH - 1, false);
            }
        }
    }

    private boolean nextRecord(NistHelper.Token token) {

        if (token.header.length() == 0) {
            return false;
        }

        int rsPos = token.header.indexOf(NistHelper.SEP_RS);
        if (rsPos == -1) {
            rsPos = token.header.length() - 1;
        }

        int usPos = token.header.indexOf(NistHelper.SEP_US);
        token.crt = Integer.parseInt(token.header.substring(0, usPos));
        token.header = token.header.substring(rsPos + 1);

        return true;
    }

    private NistHelper.Tag getTagInfo(NistHelper.Token token) {
        String type = nextWord(token, NistHelper.TAG_SEP_DOT, 2, false);
        token.pos++;
        String field = nextWord(token, NistHelper.TAG_SEP_COLN, 10, false);
        token.pos++;

        return new NistHelper.Tag(Integer.parseInt(type), Integer.parseInt(field));
    }

    private String nextWord(NistHelper.Token token, char[] sepList, int maxLen, boolean udd) {
        int i = 0;
        while (i < maxLen &&
                token.pos < token.buffer.length &&
                token.buffer[token.pos] != sepList[0] &&
                token.buffer[token.pos] != sepList[1]) {
            token.pos++;
            i++;
        }

        byte[] data = new byte[i];
        System.arraycopy(token.buffer, token.pos - i, data, 0, i);

        try {
            return udd ?
                    String.valueOf(token.charset.decode(ByteBuffer.wrap(data))) :
                    String.valueOf(NistHelper.USASCII.decode(ByteBuffer.wrap(data)));
        } catch (CharacterCodingException e) {
            return null;
        }
    }

    private long readInt(NistHelper.Token token) {
        byte byte1 = token.buffer[token.pos];
        byte byte2 = token.buffer[token.pos + 1];
        byte byte3 = token.buffer[token.pos + 2];
        byte byte4 = token.buffer[token.pos + 3];

        return (0xffL & byte1) << 24 | (0xffL & byte2) << 16 | (0xffL & byte3) << 8 | (0xffL & byte4);
    }
}
