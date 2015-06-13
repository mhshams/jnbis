package org.jnbis.internal;

import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class InternalNist extends Nist {

    private TransactionInformation transactionInformation;
    private final HashMap<Integer, UserDefinedDescriptiveText> userDefinedText;
    private final List<LowResolutionGrayscaleFingerprint> lowResolutionGrayscaleFingerprints;
    private final List<HighResolutionGrayscaleFingerprint> hiResolutionGrayscaleFingerprints;
    private final HashMap<Integer, LowResolutionBinaryFingerprint> lowResolutionBinaryFingerprint;
    private final HashMap<Integer, HighResolutionBinaryFingerprint> hiResolutionBinaryFingerprint;
    private final HashMap<Integer, UserDefinedImage> userDefinedImage;
    private final HashMap<Integer, SignatureImage> signatureImage;
    private final HashMap<Integer, MinutiaeData> minutiaeData;
    private final HashMap<Integer, FacialAndSmtImage> facialAndSmtImage;
    private final HashMap<Integer, VariableResolutionLatentImage> variableResolutionLatentImage;
    private final HashMap<Integer, VariableResolutionFingerprint> variableResolutionFingerprint;
    private final HashMap<Integer, VariableResolutionPalmprint> variableResolutionPalmprint;
    private final HashMap<Integer, IrisImage> irisImage;

    public InternalNist() {
        userDefinedText = new HashMap<>();
        lowResolutionGrayscaleFingerprints = new ArrayList<>();
        hiResolutionGrayscaleFingerprints = new ArrayList<>();
        lowResolutionBinaryFingerprint = new HashMap<>();
        hiResolutionBinaryFingerprint = new HashMap<>();
        userDefinedImage = new HashMap<>();
        signatureImage = new HashMap<>();
        minutiaeData = new HashMap<>();
        facialAndSmtImage = new HashMap<>();
        variableResolutionLatentImage = new HashMap<>();
        variableResolutionFingerprint = new HashMap<>();
        variableResolutionPalmprint = new HashMap<>();
        irisImage = new HashMap<>();
    }

    void setTransactionInfo(TransactionInformation transactionInfo) {
        this.transactionInformation = transactionInfo;
    }

    void putUserDefinedText(Integer key, UserDefinedDescriptiveText text) {
        userDefinedText.put(key, text);
    }

    void addLowResGrayscaleFingerPrint(LowResolutionGrayscaleFingerprint fingerprint) {
        lowResolutionGrayscaleFingerprints.add(fingerprint);
    }

    void addHiResGrayscaleFingerPrint(HighResolutionGrayscaleFingerprint fingerprint) {
        hiResolutionGrayscaleFingerprints.add(fingerprint);
    }

    void putLowResBinaryFingerPrint(Integer key, LowResolutionBinaryFingerprint fingerprint) {
        lowResolutionBinaryFingerprint.put(key, fingerprint);
    }

    void putHiResBinaryFingerPrint(Integer key, HighResolutionBinaryFingerprint fingerprint) {
        hiResolutionBinaryFingerprint.put(key, fingerprint);
    }

    void putUserDefinedImage(Integer key, UserDefinedImage image) {
        userDefinedImage.put(key, image);
    }

    void putSignature(Integer key, SignatureImage image) {
        signatureImage.put(key, image);
    }

    void putMinutiaeData(Integer key, MinutiaeData minutiae) {
        minutiaeData.put(key, minutiae);
    }

    void putFacialSmtImage(Integer key, FacialAndSmtImage image) {
        facialAndSmtImage.put(key, image);
    }

    void putVariableResLatentImage(Integer key, VariableResolutionLatentImage image) {
        variableResolutionLatentImage.put(key, image);
    }

    void putVariableResFingerprint(Integer key, VariableResolutionFingerprint fingerprint) {
        variableResolutionFingerprint.put(key, fingerprint);
    }

    void putVariableResPalmprint(Integer key, VariableResolutionPalmprint palmprint) {
        variableResolutionPalmprint.put(key, palmprint);
    }

    void putIrisImage(Integer key, IrisImage image) {
        irisImage.put(key, image);
    }

    public Set<Integer> getUserDefinedTextKeys() {
        return userDefinedText.keySet();
    }

    public Set<Integer> getLowResBinaryFingerPrintKeys() {
        return lowResolutionBinaryFingerprint.keySet();
    }

    public Set<Integer> getHiResBinaryFingerPrintKeys() {
        return hiResolutionBinaryFingerprint.keySet();
    }

    public Set<Integer> getUserDefinedImageKeys() {
        return userDefinedImage.keySet();
    }

    public Set<Integer> getSignatureKeys() {
        return signatureImage.keySet();
    }

    public Set<Integer> getMinutiaeDataKeys() {
        return minutiaeData.keySet();
    }

    public Set<Integer> getFacialSmtKeys() {
        return facialAndSmtImage.keySet();
    }

    public Set<Integer> getVariableResLatentImageKeys() {
        return variableResolutionLatentImage.keySet();
    }

    public Set<Integer> getVariableResFingerprintKeys() {
        return variableResolutionFingerprint.keySet();
    }

    public Set<Integer> getVariableResPalmprintKeys() {
        return variableResolutionPalmprint.keySet();
    }

    public Set<Integer> getIrisImageKeys() {
        return irisImage.keySet();
    }

    public TransactionInformation getTransactionInfo() {
        return transactionInformation;
    }

    public UserDefinedDescriptiveText getUserDefinedText(Integer key) {
        return userDefinedText.containsKey(key) ? userDefinedText.get(key) : null;
    }

    public List<LowResolutionGrayscaleFingerprint> getLowResGrayscaleFingerprints() {
        return lowResolutionGrayscaleFingerprints;
    }

    public List<HighResolutionGrayscaleFingerprint> getHiResGrayscaleFingerprints() {
        return hiResolutionGrayscaleFingerprints;
    }

    public LowResolutionBinaryFingerprint getLowResBinaryFingerprint(Integer key) {
        return lowResolutionBinaryFingerprint.containsKey(key) ? lowResolutionBinaryFingerprint.get(key) : null;
    }

    public HighResolutionBinaryFingerprint getHiResBinaryFingerprint(Integer key) {
        return hiResolutionBinaryFingerprint.containsKey(key) ? hiResolutionBinaryFingerprint.get(key) : null;
    }

    public UserDefinedImage getUserDefinedImage(Integer key) {
        return userDefinedImage.containsKey(key) ? userDefinedImage.get(key) : null;
    }

    public SignatureImage getSignature(Integer key) {
        return signatureImage.containsKey(key) ? signatureImage.get(key) : null;
    }

    public MinutiaeData getMinutiaeData(Integer key) {
        return minutiaeData.containsKey(key) ? minutiaeData.get(key) : null;
    }

    public FacialAndSmtImage getFacialAndSmtImage(Integer key) {
        return facialAndSmtImage.containsKey(key) ? facialAndSmtImage.get(key) : null;
    }

    public VariableResolutionLatentImage getVariableResLatentImage(Integer key) {
        return variableResolutionLatentImage.containsKey(key) ? variableResolutionLatentImage.get(key) : null;
    }

    public VariableResolutionFingerprint getVariableResFingerprint(Integer key) {
        return variableResolutionFingerprint.containsKey(key) ? variableResolutionFingerprint.get(key) : null;
    }

    public VariableResolutionPalmprint getVariableResPalmprint(Integer key) {
        return variableResolutionPalmprint.containsKey(key) ? variableResolutionPalmprint.get(key) : null;
    }

    public IrisImage getIrisImage(Integer key) {
        return irisImage.containsKey(key) ? irisImage.get(key) : null;
    }
}
