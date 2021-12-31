package org.jnbis.internal.record.reader;

import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.internal.NistHelper;

/**
 * @author ericdsoto
 */
public class HighResolutionGrayscaleFingerprintReader extends RecordReader {

    @Override
    public HighResolutionGrayscaleFingerprint read(NistHelper.Token token) {
        if (token.pos >= token.buffer.length) {
            throw new RuntimeException("T4::NULL pointer to T4 record");
        }

        HighResolutionGrayscaleFingerprint fingerprint = new HighResolutionGrayscaleFingerprint();

        //Assigning t4-Header values
        int length = (int) read4BytesAsInt(token);

        //Refer to manual for byte positions:
        //https://www.nist.gov/sites/default/files/documents/itl/ansi/sp500-245-a16.pdf

        //Image designation character (IDC)
        int idc = token.buffer[token.pos + 4];
        fingerprint.setImageDesignationCharacter(String.valueOf(idc));

        //Impression type (IMP)
        int imp = token.buffer[token.pos + 5];
        fingerprint.setImpressionType(String.valueOf(imp));

        // Finger position (FGP)
        int fingerPrintNo = token.buffer[token.pos + 6];
        fingerprint.setFingerPosition(String.valueOf(fingerPrintNo));

        // Image scanning resolution (ISR)
        int isr = token.buffer[token.pos + 12];
        fingerprint.setImageScanningResolution(String.valueOf(isr));

        //Horizontal line length (HLL)
        long hll = read2BytesAsInt(token, 13);
        fingerprint.setHorizontalLineLength(String.valueOf(hll));

        //Vertical line length (VLL)
        long vll = read2BytesAsInt(token, 15);
        fingerprint.setVerticalLineLength(String.valueOf(vll));

        //Grayscale compression algorithm (GCA)
        int gca = token.buffer[token.pos + 17];
        fingerprint.setCompressionAlgorithm(String.valueOf(gca));

        int dataSize = length - 18;

        if (token.pos + dataSize + 17 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 18;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);

        token.pos += length;
        fingerprint.setImageDesignationCharacter(String.valueOf(fingerPrintNo));
        fingerprint.setImageData(data);
        fingerprint.setLogicalRecordLength(String.valueOf(length));

        return fingerprint;
    }
}
