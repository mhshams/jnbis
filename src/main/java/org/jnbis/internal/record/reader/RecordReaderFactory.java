package org.jnbis.internal.record.reader;


import org.jnbis.NistHelper;
import org.jnbis.internal.record.BaseRecord;

/**
 * @author ericdsoto
 */
public class RecordReaderFactory {
    private static final RecordReader NOT_SUPPORTED = new RecordReader() {
        @Override
        public BaseRecord read(NistHelper.Token token) {
            throw new UnsupportedOperationException("record type: " + token.crt + " no supported!");
        }
    };

    private static final RecordReader[] READERS = {
            new TransactionInfoReader(),
            new TransactionInfoReader(),
            new UserDefinedTextReader(),
            new LowResolutionGrayscaleFingerprintReader(),
            new HighResolutionGrayscaleFingerprintReader(),
            new LowResolutionBinaryFingerprintReader(),
            new HighResolutionBinaryFingerprintReader(),
            new UserDefinedImageReader(),
            new SignatureImageReader(),
            new MinutiaeDataReader(),
            new FacialAndSmtImageReader(),
            NOT_SUPPORTED,
            NOT_SUPPORTED,
            new VariableResolutionLatentImageReader(),
            new VariableResolutionFingerprintReader(),
            new VariableResolutionPalmprintReader(),
            NOT_SUPPORTED,
            new IrisImageReader()
    };

    public RecordReader reader(NistHelper.Token token) {
        return READERS[token.crt];
    }

    public BaseRecord read(NistHelper.Token token) {
        return reader(token).read(token);
    }
}
