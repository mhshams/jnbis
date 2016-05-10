package org.jnbis.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.FacialAndSmtImage;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.IrisImage;
import org.jnbis.api.model.record.MinutiaeData;
import org.jnbis.api.model.record.SignatureImage;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.TransactionInformation.InfoDesignation;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.api.model.record.UserDefinedImage;
import org.jnbis.api.model.record.VariableResolutionFingerprint;
import org.jnbis.api.model.record.VariableResolutionLatentImage;
import org.jnbis.api.model.record.VariableResolutionPalmprint;
import org.jnbis.internal.NistHelper.Field;
import org.jnbis.internal.NistHelper.RecordType;
import org.jnbis.internal.record.BaseRecord;
import org.jnbis.internal.record.reader.RecordReader;
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

        InternalNist decoded = new InternalNist();

        ByteBuffer buffer = ByteBuffer.wrap(nist);
        buffer.order(ByteOrder.BIG_ENDIAN);
        NistHelper.Token token = new NistHelper.Token(buffer);

        /* Set up the buffer and check the type */
        nextRecord(RecordType.RT1_TRANSACTION_INFO, token);

        TransactionInformation txInfo = (TransactionInformation) readerFactory.read(token);
        decoded.setTransactionInfo(txInfo);

        List<InfoDesignation> recordTypes = txInfo.getTransactionContent().getIdcs();
        for (InfoDesignation idc : recordTypes) {
            RecordType type = RecordType.valueOf(idc.recordCategoryCode);
            nextRecord(type, token);

            BaseRecord record = readerFactory.read(token);

            if (record instanceof UserDefinedDescriptiveText) {
                decoded.addUserDefinedText((UserDefinedDescriptiveText) record);

            } else if (record instanceof HighResolutionGrayscaleFingerprint) {
                decoded.addHiResGrayscaleFingerPrint((HighResolutionGrayscaleFingerprint) record);

            } else if (record instanceof UserDefinedImage) {
                decoded.addUserDefinedImage((UserDefinedImage) record);

            } else if (record instanceof SignatureImage) {
                decoded.addSignature((SignatureImage) record);

            } else if (record instanceof MinutiaeData) {
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

    private boolean nextRecord(RecordType type, NistHelper.Token token) {
        token.crt = type;

        ByteBuffer buffer = token.buffer;
        int bufferPosition = buffer.position();

        /*
         * Reset the limit because it may be set to the previous record length
         * and we need to read past that to find the next one.
         */
        buffer.limit(buffer.capacity());

        int length = 0;

        /*
         * If it's a binary record then it's just going to start with a 4-byte
         * little-endian int that represents the Field 001 LEN length of the
         * record.
         */
        if (type.isBinary) {
            length = buffer.getInt();

            /*
             * Otherwise, it's going to start with an ASCII string in the form:
             * "<type>.001:<length>"
             */
        } else {
            Field header = RecordReader.nextField(token);
            if (header == null) {
                return false;
            }
            length = header.asInteger();
        }

        /*
         * Reset the buffer position to what it was
         */
        buffer.position(bufferPosition);

        /*
         * Specify the limit on the buffer so the record readers can't/don't
         * overrun the record
         */
        buffer.limit(buffer.position() + length);

        return true;
    }

}
