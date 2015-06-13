package org.jnbis.internal;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.*;
import org.jnbis.internal.record.BaseRecord;
import org.jnbis.internal.record.reader.RecordReaderFactory;


/**
 * @author hamed
 * @version 1.0
 * @since Apr 29, 2007
 */
public class NistDecoder {
    private RecordReaderFactory readerFactory;

    public NistDecoder() {
        readerFactory = new RecordReaderFactory();
    }

    public Nist decode(byte[] nist) {
        if (nist == null || nist.length == 0) {
            throw new IllegalArgumentException("data is null or zero length");
        }

        NistHelper.Token token = new NistHelper.Token(nist);
        InternalNist decoded = new InternalNist();
        BaseRecord record = readerFactory.read(token);
        decoded.setTransactionInfo((TransactionInformation) record);

        while (nextRecord(token)) {
            if (token.crt < 2) {
                continue;
            }
            record = readerFactory.read(token);

            if (record instanceof UserDefinedDescriptiveText) {
                decoded.addUserDefinedText((UserDefinedDescriptiveText) record);

            } else if (record instanceof LowResolutionGrayscaleFingerprint) {
                decoded.addLowResGrayscaleFingerPrint((LowResolutionGrayscaleFingerprint) record);

            } else if (record instanceof HighResolutionGrayscaleFingerprint) {
                decoded.addHiResGrayscaleFingerPrint((HighResolutionGrayscaleFingerprint) record);

            } else if (record instanceof LowResolutionBinaryFingerprint) {
                decoded.addLowResBinaryFingerPrint((LowResolutionBinaryFingerprint) record);

            } else if (record instanceof HighResolutionBinaryFingerprint) {
                decoded.addHiResBinaryFingerPrint((HighResolutionBinaryFingerprint) record);

            } else if (record instanceof UserDefinedImage) {
                decoded.addUserDefinedImage((UserDefinedImage) record);

            } else if (record instanceof SignatureImage) {
                decoded.addSignature((SignatureImage) record);

            } else if (record instanceof MinutiaeData) {
                //readMinutiaeData(token, decoded);
                decoded.addMinutiaeData((MinutiaeData) record);

            } else if (record instanceof FacialAndSmtImage) {
                decoded.addFacialSmtImage((FacialAndSmtImage) record);

            } else if (record instanceof VariableResolutionLatentImage) {
                decoded.addVariableResLatentImage((VariableResolutionLatentImage) record);

            } else if (record instanceof VariableResolutionFingerprint) {
                decoded.addVariableResFingerprint((VariableResolutionFingerprint) record);

            } else if (record instanceof VariableResolutionPalmprint) {
                decoded.addVariableResPalmprint((VariableResolutionPalmprint) record);

            } else if (record instanceof IrisImage) {
                decoded.addIrisImage((IrisImage) record);
            }
        }

        return decoded;
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

}
