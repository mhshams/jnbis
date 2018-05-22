package org.jnbis.internal.record.reader;

import java.util.Arrays;

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
        Integer length = (int) readInt(token);
        
        //Refer to manual for byte positions:
        //https://www.nist.gov/sites/default/files/documents/itl/ansi/sp500-245-a16.pdf
        
        // Finger position (FGP)
        int fingerPrintNo = token.buffer[token.pos + 6];

        //Impression type (IMP)
        int imp = token.buffer[token.pos + 5];
        fingerprint.setImpressionType(String.valueOf(imp));
        
        //Image designation character (IDC)
        int idc = token.buffer[token.pos + 4];
        fingerprint.setImageDesignationCharacter(String.valueOf(idc));
        
        // Image scanning resolution (ISR)
        int isr = token.buffer[token.pos + 12];
        fingerprint.setImageScanningResolution(String.valueOf(isr));
        
        //Grayscale compression algorithm (GCA)
        int gca = token.buffer[token.pos + 17];
        fingerprint.setCompressionAlgorithm(String.valueOf(gca));
        
        //Horizontal line length (HLL)
        byte[] slice = Arrays.copyOfRange(token.buffer, token.pos + 13, token.pos + 15);
        Long hll = byteToInt(slice, 2);

        if (hll != null)
        {	
        	fingerprint.setHorizontalLineLength(String.valueOf(hll));
        }	

        //Vertical line length (VLL)
        slice = Arrays.copyOfRange(token.buffer, token.pos + 15, token.pos + 17);
        Long vll = byteToInt(slice, 2);
        
        if (vll != null)
        {	
        	fingerprint.setVerticalLineLength(String.valueOf(vll));
        }	
        	
        int dataSize = length - 18;

        if (token.pos + dataSize + 17 > token.buffer.length) {
            dataSize += token.buffer.length - token.pos - 18;
        }

        byte[] data = new byte[dataSize];
        System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);

        token.pos += length;
        fingerprint.setImageDesignationCharacter(Integer.toString(fingerPrintNo));
        fingerprint.setImageData(data);
        fingerprint.setLogicalRecordLength(length.toString());

        return fingerprint;
    }
    
    /** length should be less than 4 (for int) **/
    public long byteToInt(byte[] bytes, int length) {
            int val = 0;
            if(length>4) throw new RuntimeException("Too big to fit in int");
            for (int i = 0; i < length; i++) {
                val=val<<8;
                val=val|(bytes[i] & 0xFF);
            }
            return val;
    }
}
