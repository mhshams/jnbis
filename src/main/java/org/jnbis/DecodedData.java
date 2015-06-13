package org.jnbis;

import org.jnbis.record.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class DecodedData {
    public enum Format {
        JPEG("jpeg"),
        GIF("gif"),
        PNG("png");

        private final String code;

        public String code() {
            return code;
        }

        Format(String code) {
            this.code = code;
        }
    }

    private final Map<Integer, TransactionInformation> transactionInformation;
    private final Map<Integer, UserDefinedDescriptiveText> userDefinedText;
    private final Map<Integer, LowResolutionGrayscaleFingerprint> lowResolutionGrayscaleFingerprint;
    private final Map<Integer, HighResolutionGrayscaleFingerprint> hiResolutionGrayscaleFingerprint;
    private final Map<Integer, LowResolutionBinaryFingerprint> lowResolutionBinaryFingerprint;
    private final Map<Integer, HighResolutionBinaryFingerprint> hiResolutionBinaryFingerprint;
    private final Map<Integer, UserDefinedImage> userDefinedImage;
    private final Map<Integer, SignatureImage> signatureImage;
    private final Map<Integer, MinutiaeData> minutiaeData;
    private final Map<Integer, FacialAndSmtImage> facialAndSmtImage;
    private final Map<Integer, VariableResolutionLatentImage> variableResolutionLatentImage;
    private final Map<Integer, VariableResolutionFingerprint> variableResolutionFingerprint;
    private final Map<Integer, VariableResolutionPalmprint> variableResolutionPalmprint;
    private final Map<Integer, IrisImage> irisImage;

    DecodedData() {
        transactionInformation = new HashMap<>();
        userDefinedText = new HashMap<>();
        lowResolutionGrayscaleFingerprint = new HashMap<>();
        hiResolutionGrayscaleFingerprint = new HashMap<>();
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

    void putTransactionInfo(Integer key, TransactionInformation transactionInfo) {
        transactionInformation.put(key, transactionInfo);
    }

    void putUserDefinedText(Integer key, UserDefinedDescriptiveText text) {
        userDefinedText.put(key, text);
    }

    void putLowResGrayscaleFingerPrint(Integer key, LowResolutionGrayscaleFingerprint fingerprint) {
        lowResolutionGrayscaleFingerprint.put(key, fingerprint);
    }

    void putHiResGrayscaleFingerPrint(Integer key, HighResolutionGrayscaleFingerprint fingerprint) {
        hiResolutionGrayscaleFingerprint.put(key, fingerprint);
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

    public Set<Integer> getTransactionKeys() {
        return transactionInformation.keySet();
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

    public Set<Integer> getLowResGrayscaleFingerPrintKeys() {
        return lowResolutionGrayscaleFingerprint.keySet();
    }

    public Set<Integer> getHiResGrayscaleFingerPrintKeys() {
        return hiResolutionGrayscaleFingerprint.keySet();
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

    public TransactionInformation getTransactionInfo(Integer key) {
        return transactionInformation.containsKey(key) ? transactionInformation.get(key) : null;
    }

    public UserDefinedDescriptiveText getUserDefinedText(Integer key) {
        return userDefinedText.containsKey(key) ? userDefinedText.get(key) : null;
    }

    public LowResolutionGrayscaleFingerprint getLowResGrayscaleFingerprint(Integer key) {
        return lowResolutionGrayscaleFingerprint.containsKey(key) ? lowResolutionGrayscaleFingerprint.get(key) : null;
    }

    public HighResolutionGrayscaleFingerprint getHiResGrayscaleFingerprint(Integer key) {
        return hiResolutionGrayscaleFingerprint.containsKey(key) ? hiResolutionGrayscaleFingerprint.get(key) : null;
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
