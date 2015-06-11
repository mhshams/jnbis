package org.jnbis.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ericdsoto on 6/8/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInformation extends BaseRecord {
    // 1.002 - LEN
    @JsonProperty("version")
    private String version;
    // 1.003 - CNT
    @JsonProperty("file_content")
    private String fileContent;
    // 1.004 - TOT
    @JsonProperty("type_of_transaction")
    private String typeOfTransaction;
    // 1.005 - DAT
    @JsonProperty("date")
    private String date;
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
