package org.jnbis.internal;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class InternalNist extends Nist {

    private TransactionInformation transactionInformation;
    private final List<UserDefinedDescriptiveText> userDefinedTexts;
    private final List<LowResolutionGrayscaleFingerprint> lowResolutionGrayscaleFingerprints;
    private final List<HighResolutionGrayscaleFingerprint> hiResolutionGrayscaleFingerprints;
    private final List<LowResolutionBinaryFingerprint> lowResolutionBinaryFingerprints;
    private final List<HighResolutionBinaryFingerprint> hiResolutionBinaryFingerprints;
    private final List<UserDefinedImage> userDefinedImages;
    private final List<SignatureImage> signatureImages;
    private final List<MinutiaeData> minutiaeData;
    private final List<FacialAndSmtImage> facialAndSmtImages;
    private final List<VariableResolutionLatentImage> variableResolutionLatentImages;
    private final List<VariableResolutionFingerprint> variableResolutionFingerprints;
    private final List<VariableResolutionPalmprint> variableResolutionPalmprints;
    private final List<UserDefinedTestingImage> userDefinedTestingImages;
    private final List<IrisImage> irisImages;

    public InternalNist() {
        userDefinedTexts = new ArrayList<>();
        lowResolutionGrayscaleFingerprints = new ArrayList<>();
        hiResolutionGrayscaleFingerprints = new ArrayList<>();
        lowResolutionBinaryFingerprints = new ArrayList<>();
        hiResolutionBinaryFingerprints = new ArrayList<>();
        userDefinedImages = new ArrayList<>();
        signatureImages = new ArrayList<>();
        minutiaeData = new ArrayList<>();
        facialAndSmtImages = new ArrayList<>();
        variableResolutionLatentImages = new ArrayList<>();
        variableResolutionFingerprints = new ArrayList<>();
        variableResolutionPalmprints = new ArrayList<>();
        userDefinedTestingImages = new ArrayList<>();
        irisImages = new ArrayList<>();
    }

    void setTransactionInfo(TransactionInformation transactionInfo) {
        this.transactionInformation = transactionInfo;
    }

    void addUserDefinedText(UserDefinedDescriptiveText text) {
        userDefinedTexts.add(text);
    }

    void addLowResGrayscaleFingerPrint(LowResolutionGrayscaleFingerprint fingerprint) {
        lowResolutionGrayscaleFingerprints.add(fingerprint);
    }

    void addHiResGrayscaleFingerPrint(HighResolutionGrayscaleFingerprint fingerprint) {
        hiResolutionGrayscaleFingerprints.add(fingerprint);
    }

    void addLowResBinaryFingerPrint(LowResolutionBinaryFingerprint fingerprint) {
        lowResolutionBinaryFingerprints.add(fingerprint);
    }

    void addHiResBinaryFingerPrint(HighResolutionBinaryFingerprint fingerprint) {
        hiResolutionBinaryFingerprints.add(fingerprint);
    }

    void addUserDefinedImage(UserDefinedImage image) {
        userDefinedImages.add(image);
    }

    void addSignature(SignatureImage image) {
        signatureImages.add(image);
    }

    void addMinutiaeData(MinutiaeData minutiae) {
        minutiaeData.add(minutiae);
    }

    void addFacialSmtImage(FacialAndSmtImage image) {
        facialAndSmtImages.add(image);
    }

    void addVariableResLatentImage(VariableResolutionLatentImage image) {
        variableResolutionLatentImages.add(image);
    }

    void addVariableResFingerprint(VariableResolutionFingerprint fingerprint) {
        variableResolutionFingerprints.add(fingerprint);
    }

    void addVariableResPalmprint(VariableResolutionPalmprint palmprint) {
        variableResolutionPalmprints.add(palmprint);
    }

    void addUserDefinedTestingImage(UserDefinedTestingImage image) {
        userDefinedTestingImages.add(image);
    }
    void addIrisImage(IrisImage image) {
        irisImages.add(image);
    }

    public TransactionInformation getTransactionInfo() {
        return transactionInformation;
    }

    public List<UserDefinedDescriptiveText> getUserDefinedTexts() {
        return userDefinedTexts;
    }

    public List<LowResolutionGrayscaleFingerprint> getLowResGrayscaleFingerprints() {
        return lowResolutionGrayscaleFingerprints;
    }

    public List<HighResolutionGrayscaleFingerprint> getHiResGrayscaleFingerprints() {
        return hiResolutionGrayscaleFingerprints;
    }

    public List<LowResolutionBinaryFingerprint> getLowResBinaryFingerprints() {
        return lowResolutionBinaryFingerprints;
    }

    public List<HighResolutionBinaryFingerprint> getHiResBinaryFingerprints() {
        return hiResolutionBinaryFingerprints;
    }

    public List<UserDefinedImage> getUserDefinedImages() {
        return userDefinedImages;
    }

    public List<SignatureImage> getSignatures() {
        return signatureImages;
    }

    public List<MinutiaeData> getMinutiaeData() {
        return minutiaeData;
    }

    public List<FacialAndSmtImage> getFacialAndSmtImages() {
        return facialAndSmtImages;
    }

    public List<VariableResolutionLatentImage> getVariableResLatentImages() {
        return variableResolutionLatentImages;
    }

    public List<VariableResolutionFingerprint> getVariableResFingerprints() {
        return variableResolutionFingerprints;
    }

    public List<VariableResolutionPalmprint> getVariableResPalmprints() {
        return variableResolutionPalmprints;
    }

    public List<UserDefinedTestingImage> getUserDefinedTestingImages() {
        return userDefinedTestingImages;
    }

    public List<IrisImage> getIrisImages() {
        return irisImages;
    }
}
