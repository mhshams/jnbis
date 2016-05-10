package org.jnbis.api.model.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jnbis.internal.record.BaseRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ericdsoto
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInformation extends BaseRecord {

    /**
     * 8.1.3 Field 1.003 Transaction content / CNT This mandatory field shall
     * list and identify each of the records in the transaction by record type
     * and its IDC value. It also specifies the order in which the remaining
     * records shall appear in the file. It shall consist of two or more
     * subfields. The first subfield shall relate to this Type-1 record.
     */
    public static class TransactionContent {

        /**
         * The first information item (first record category code / FRC) within
         * this subfield shall be “1”. This indicates that the first record in
         * the transaction is a Type-1 record consisting of header information.
         */
        public int firstRecordCategoryCode = 1;

        /**
         * The second information item of this subfield (content record count /
         * CRC) shall be the sum of the Type-2 through Type-99 records contained
         * in this transaction. This number is also equal to the count of the
         * remaining subfields of Field 1.003 Transaction content / CNT. The
         * maximum value for CRC is 999.
         */
        public int contentRecordCount;

        /**
         * Each of the remaining subfields of Field 1.003 Transaction content /
         * CNT corresponds to a single Type-2 through Type-99 record contained
         * in the transaction. Two information items shall comprise each of
         * these subfields:
         */
        public List<InfoDesignation> idcs = new ArrayList<>();
        
        public TransactionContent() {}
        
        public TransactionContent(int frc, int crc) {
            this.firstRecordCategoryCode = frc;
            this.contentRecordCount = crc;
        }

        public int getFirstRecordCategoryCode() {
            return firstRecordCategoryCode;
        }

        public void setFirstRecordCategoryCode(int firstRecordCategoryCode) {
            this.firstRecordCategoryCode = firstRecordCategoryCode;
        }

        public int getContentRecordCount() {
            return contentRecordCount;
        }

        public void setContentRecordCount(int contentRecordCount) {
            this.contentRecordCount = contentRecordCount;
        }

        public List<InfoDesignation> getIdcs() {
            return idcs;
        }

    }

    public static class InfoDesignation {
        /**
         * The first information item (record category code / REC), shall
         * contain a number chosen from the “record identifier” column of Table
         * 3.
         */
        public int recordCategoryCode;

        /**
         * The second information item (information designation character / IDC)
         * shall be an integer equal to or greater than zero and less than or
         * equal to 99. See Section 7.3.1.
         */
        public int informationDesignationCharacter;

        public InfoDesignation() {}
        
        public InfoDesignation(int rec, int idc) {
            this.recordCategoryCode = rec;
            this.informationDesignationCharacter = idc;
        }
        
        public int getRecordCategoryCode() {
            return recordCategoryCode;
        }

        public void setRecordCategoryCode(int recordCategoryCode) {
            this.recordCategoryCode = recordCategoryCode;
        }

        public int getInformationDesignationCharacter() {
            return informationDesignationCharacter;
        }

        public void setInformationDesignationCharacter(int informationDesignationCharacter) {
            this.informationDesignationCharacter = informationDesignationCharacter;
        }
    }

    // 1.002 - LEN
    @JsonProperty("version")
    private String version;
    // 1.003 - CNT
    @JsonProperty("file_content")
    private TransactionContent transactionContent;
    // 1.004 - TOT
    @JsonProperty("type_of_transaction")
    private String typeOfTransaction;
    // 1.005 - DAT
    @JsonProperty("date")
    private Date date;
    // 1.006 - PRY
    @JsonProperty("priority")
    private String priority;
    // 1.007 - DAI
    @JsonProperty("destination_agency_id")
    private String destinationAgencyId;
    // 1.008 - ORI
    @JsonProperty("originating_agency_id")
    private String originatingAgencyId;
    // 1.009 - TCN
    @JsonProperty("control_number")
    private String controlNumber;
    // 1.010 - TCR
    @JsonProperty("control_reference_number")
    private String controlReferenceNumber;
    // 1.011 - NSR
    @JsonProperty("native_scanning_resolution")
    private String nativeScanningResolution;
    // 1.012 - NTR
    @JsonProperty("nominal_transmitting_resolution")
    private String nominalTransmittingResolution;
    // 1.013 - DOM
    @JsonProperty("domain_name")
    private String domainName;
    // 1.014 - GMT
    @JsonProperty("greenwich_mean_time")
    private String greenwichMeanTime;
    // 1.015 - DCS
    @JsonProperty("directory_of_charsets")
    private String directoryOfCharsets;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TransactionContent getTransactionContent() {
        return transactionContent;
    }

    public void setTransactionContent(TransactionContent fileContent) {
        this.transactionContent = fileContent;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDestinationAgencyId() {
        return destinationAgencyId;
    }

    public void setDestinationAgencyId(String destinationAgencyId) {
        this.destinationAgencyId = destinationAgencyId;
    }

    public String getOriginatingAgencyId() {
        return originatingAgencyId;
    }

    public void setOriginatingAgencyId(String originatingAgencyId) {
        this.originatingAgencyId = originatingAgencyId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getControlReferenceNumber() {
        return controlReferenceNumber;
    }

    public void setControlReferenceNumber(String controlReferenceNumber) {
        this.controlReferenceNumber = controlReferenceNumber;
    }

    public String getNativeScanningResolution() {
        return nativeScanningResolution;
    }

    public void setNativeScanningResolution(String nativeScanningResolution) {
        this.nativeScanningResolution = nativeScanningResolution;
    }

    public String getNominalTransmittingResolution() {
        return nominalTransmittingResolution;
    }

    public void setNominalTransmittingResolution(String nominalTransmittingResolution) {
        this.nominalTransmittingResolution = nominalTransmittingResolution;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getGreenwichMeanTime() {
        return greenwichMeanTime;
    }

    public void setGreenwichMeanTime(String greenwichMeanTime) {
        this.greenwichMeanTime = greenwichMeanTime;
    }

    public String getDirectoryOfCharsets() {
        return directoryOfCharsets;
    }

    public void setDirectoryOfCharsets(String directoryOfCharsets) {
        this.directoryOfCharsets = directoryOfCharsets;
    }
}
