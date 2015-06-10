package org.jnbis.record;

/**
 * Created by ericdsoto on 6/8/15.
 */
public class TransactionInformation extends BaseRecord {

    // 1.002 - LEN
    private String version;
    // 1.003 - CNT
    private String fileContent;
    // 1.004 - TOT
    private String typeOfTransaction;
    // 1.005 - DAT
    private String date;
    // 1.006 - PRY
    private String priority;
    // 1.007 - DAI
    private String destinationAgencyId;
    // 1.008 - ORI
    private String originatingAgencyId;
    // 1.009 - TCN
    private String controlNumber;
    // 1.010 - TCR
    private String controlReferenceNumber;
    // 1.011 - NSR
    private String nativeSnanningResolution;
    // 1.012 - NTR
    private String nominalTransmittingResolution;
    // 1.013 - DOM
    private String domainName;
    // 1.014 - GMT
    private String greenwichMeanTime;
    // 1.015 - DCS
    private String directoryOfCharacterSets;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getNativeSnanningResolution() {
        return nativeSnanningResolution;
    }

    public void setNativeSnanningResolution(String nativeSnanningResolution) {
        this.nativeSnanningResolution = nativeSnanningResolution;
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

    public String getDirectoryOfCharacterSets() {
        return directoryOfCharacterSets;
    }

    public void setDirectoryOfCharacterSets(String directoryOfCharacterSets) {
        this.directoryOfCharacterSets = directoryOfCharacterSets;
    }
}
