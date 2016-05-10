package org.jnbis.internal.record.reader;

import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.TransactionInformation.InfoDesignation;
import org.jnbis.api.model.record.TransactionInformation.TransactionContent;
import org.jnbis.internal.NistHelper;
import org.jnbis.internal.NistHelper.Field;
import org.jnbis.internal.NistHelper.Token;

/**
 * @author ericdsoto
 */
public class TransactionInfoReader extends RecordReader {

    @Override
    public TransactionInformation read(NistHelper.Token token) {
        TransactionInformation transaction = new TransactionInformation();

        while (token.buffer.hasRemaining()) {
            Field field = nextField(token);

            String value = field.asString();

            switch (field.fieldNumber) {
            case 1:
                transaction.setLogicalRecordLength(Integer.parseInt(value));
                break;
            case 2:
                transaction.setVersion(value);
                break;
            case 3: {
                TransactionContent content = new TransactionContent();
                Token subToken = new Token(field.value);
                Field subField = nextInformationItem(subToken);
                content.setFirstRecordCategoryCode(subField.asInteger());

                subField = nextInformationItem(subToken);
                content.setContentRecordCount(subField.asInteger());

                while (true) {
                    subField = nextInformationItem(subToken);
                    if (subField == null) {
                        break;
                    }
                    int recordType = subField.asInteger();

                    subField = nextInformationItem(subToken);
                    int idc = subField.asInteger();

                    content.getIdcs().add(new InfoDesignation(recordType, idc));
                }
                transaction.setTransactionContent(content);
                break;
            }
            case 4:
                transaction.setTypeOfTransaction(value);
                break;
            case 5:
                transaction.setDate(parseDate(value));
                break;
            case 6:
                transaction.setPriority(value);
                break;
            case 7:
                transaction.setDestinationAgencyId(value);
                break;
            case 8:
                transaction.setOriginatingAgencyId(value);
                break;
            case 9:
                transaction.setControlNumber(value);
                break;
            case 10:
                transaction.setControlReferenceNumber(value);
                break;
            case 11:
                transaction.setNativeScanningResolution(value);
                break;
            case 12:
                transaction.setNominalTransmittingResolution(value);
                break;
            case 13:
                transaction.setDomainName(value);
                break;
            case 14:
                transaction.setGreenwichMeanTime(value);
                break;
            case 15: {
                TransactionContent content = new TransactionContent();
                Token subToken = new Token(field.value);
                Field subField = nextInformationItem(subToken);
                content.setFirstRecordCategoryCode(subField.asInteger());

                token.setCharSet(value);
                transaction.setDirectoryOfCharsets(value);
                break;
            }
            }
        }

        return transaction;
    }
}
