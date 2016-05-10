package org.jnbis.api.model.record;

import java.util.ArrayList;
import java.util.List;

import org.jnbis.internal.record.BaseVariableResolutionImageRecord;

/**
 * @author ericdsoto
 */
@SuppressWarnings("serial")
public class VariableResolutionFingerprint extends BaseVariableResolutionImageRecord {

    public static class PPD {
        public int decimalFPC;
        public String fingerImageCode;
    }

    public static class PPC {
        public String fullFingerView;
        public String locationOfSegment;
        public int leftHorizontalCoordinate;
        public int rightHorizontalCoordinate;
        public int topVerticalCoordinate;
        public int bottomVerticalCoordinate;
    }

    public static class AMP {
        /**
         * friction ridge amputated or bandaged position
         */
        public int frap;
        public String amputatedOrBandagedCode;
    }

    public static class SEG {
        public int frictionRidgeSegmentPosition;
        public int leftHorizontalCoordinate;
        public int rightHorizontalCoordinate;
        public int topVerticalCoordinate;
        public int bottomVerticalCoordinate;
    }

    public static class NQM {
        public int frictionRidgeNistQualityPosition;
        public int nistImageQualityScore;
    }

    public static class SQM {
        public int frictionRidgeSegmentQualityPosition;
        public int qualityValue;
        public String algorithmVendorId;
        public int algorithmProductId;
    }

    public static class FQM {
        public int frictionRidgeMetricPosition;
        public int qualityValue;
        public String algorithmVendorId;
        public int algorithmProductId;
    }

    public static class ASEG {
        public int frictionRidgeAltSegmentPosition;
        public int numberOfPoints;

        /**
         * Note: The following two information items are repeated as pairs, in
         * order by point following the path, up to the final point - FOR A
         * TOTAL OF NOP PAIRS. For each point N, the pairs are represented as
         * index 0 and 1 in a multi-dimensional array:
         * <ul>
         * <li>int[N][0] = horizontalPointOffset</li>
         * <li>int[N][1] = verticalPointOffset</li>
         * </ul>
         */
        public int[][] points;
    }

    /**
     * Field 14.013: Friction ridge generalized position / FGP. This field is
     * mandatory. See Section 7.7.4.2 for details. In the 2007 and 2008 versions
     * of the standard, this field had a repeating subfield that could occur up
     * to 6 times. Since only one image is sent per record, the maximum should
     * have been 1. To maintain backward compatibility, the subfield structure
     * has been retained, but with a maximum occurrence of one.
     */
    private List<Integer> fingerPosition = new ArrayList<>();

    /**
     * Field 14.014: Print position descriptors / PPD. This field shall be
     * present if and only if the finger position code “19” appears in Field
     * 14.013: Friction ridge generalized position / FGP.
     */
    private PPD printPositionDescriptors;

    /**
     * Field 14.015: Print position coordinates / PPC. This field may be present
     * if and only if the finger position code “19” appears in Field 14.013:
     * Friction ridge generalized position / FGP. It is an optional field.
     */
    private List<PPC> printPositionCoordinates = new ArrayList<>();

    /**
     * Field 14.018: Amputated or bandaged / AMP. This optional field shall
     * specify if one or more fingers are amputated or bandaged. This field
     * shall consist of one subfield for each amputated or missing finger. Each
     * subfield shall contain two information items.
     * <ul>
     * <li>The first item is the friction ridge amputated or bandaged position /
     * FRAP between 1 and 10 or 16 or 17 as chosen from Table 8. This
     * information item is the friction ridge amputation position / FRAP, to
     * differentiate it from FGP.</li>
     * <li>The second item is the amputated or bandaged code / ABC, also known
     * as the AMPCD. Table 72 is a list of allowable indicators for the AMPCD.
     * </li>
     * </ul>
     */
    private List<AMP> amputatedOrBandaged = new ArrayList<>();

    /**
     * Field 14.021: Finger segment position / SEG. This optional field shall
     * contain offsets to the locations of image segments containing the
     * individual fingers within the flat images of simultaneous fingers from
     * each hand or the two simultaneous thumbs. This field shall only be
     * present if FGP = 13, 14, 15 or 40-50 from Table 8 as entered in Field
     * 14.013: Friction ridge generalized position / FGP. The Descriptor AMPCD
     * Partial print due to amputation XX Unable to print (e.g., bandaged) UP
     * age 226 ANSI/NIST-ITL 1-2011 subfield occurs at least once, and may be
     * repeated if more than one algorithm is used to segment the image. Each
     * subfield contains five information items.
     * <ul>
     * <li>The first information item is the friction ridge segment position /
     * FRSP with values of 1 to 10 or 16 or 17, selected from Table 8. This
     * information item is called the friction ridge segment position / FRSP to
     * differentiate it from FGP.</li>
     * <li>The second information item is the left horizontal coordinate value /
     * LHC. It is the horizontal offset in pixels to the left edge of the
     * bounding box relative to the origin positioned in the upper left corner
     * of the image.</li>
     * <li>The third information item is the right horizontal coordinate value /
     * RHC. It is the horizontal offset in pixels to the right edge of the
     * bounding box relative to the origin positioned in the upper left corner
     * of the image.</li>
     * <li>The fourth information item is the top vertical coordinate value /
     * TVC is the vertical offset (pixel counts down) to the top of the bounding
     * box.</li>
     * <li>The fifth information item is the bottom vertical coordinate value /
     * BVC. It is the vertical offset from the upper left corner of the image
     * down to the bottom of the bounding box. It is counted in pixels.</li>
     * </ul>
     */
    private List<SEG> fingerprintSegmentationPosition = new ArrayList<>();

    /**
     * Field 14.022: NIST quality metric / NQM. This optional field shall
     * contain the NIST Fingerprint Image Quality (NFIQ) scores for the
     * individual finger(s) derived from the slap impressions or individual
     * rolled fingerprints. It consists of two information items.
     * <ul>
     * <li>The first item is the friction ridge NIST quality position / FRNP
     * between one and ten or 16 or 17, as chosen from Table 8. This information
     * item is called the friction ridge NIST quality position / FRNP to
     * differentiate it from FGP.</li>
     * <li>The second item is the NIST image quality score / IQS which is a
     * quantitative expression of the predicted AFIS matcher accuracy
     * performance of the fingerprint image. The scores range from “1” for the
     * best quality image, to “5” for the worst quality image. A “254” indicates
     * that no score was ever computed while an entry of “255” shall indicate a
     * failed attempt to calculate the image quality metric.</li>
     * </ul>
     */
    private List<NQM> nistQualityMetric = new ArrayList<>();

    /**
     * Field 14.023: Segmentation quality metric / SQM. This optional field
     * provides a measure of estimated correctness regarding the accuracy of the
     * location of the segmented finger within the right or left four finger
     * image (which may include extra digits, if applicable) or the two thumb
     * image. A subfield shall exist for each segmented finger. Each subfield
     * consists of four information items. The first information item is the
     * friction ridge segment quality position / FRQP between one and ten or 16
     * or 17, as chosen from Table 8. This information item is called the
     * friction ridge segment quality position / FRQP to differentiate it from
     * FGP. See Section 7.7.7 for the other information items. The FRQP values
     * shall be in the list of either the FRSP or FRAS values contained in this
     * record.
     */
    private List<SQM> segmentationQualityMetric = new ArrayList<>();

    /**
     * Field 14.024: Fingerprint quality metric / FQM. This optional field shall
     * specify one or more different metrics of fingerprint image quality score
     * data for the image stored in the record. A subfield shall exist for each
     * segmented finger in the image. Each subfield consists of four information
     * items. The first information item is the friction ridge metric position /
     * FRMP between one and ten or 16 or 17, as chosen from Table 8. This
     * information item is called the friction ridge metric position / FRMP to
     * differentiate it from FGP. For information on the other three information
     * items, see Section 7.7.7.
     */
    private List<FQM> fingerprintQualityMetric = new ArrayList<>();

    /**
     * Field 14.025: Alternate finger segment position(s) / ASEG
     * <p>
     * This optional field is an alternate approach to describing the locations
     * for each of the image segments of each of the individual fingers within a
     * flat image containing the capture of four (or more if extra digits exist
     * on the hand) simultaneous fingers or two simultaneous thumbs. This field
     * uses an n-vertex polygon to encompass each finger image segment, where
     * “n” is between 3 and 99. A minimum of three points is required to
     * describe a finger location. The order of the vertices shall be in their
     * consecutive order around the perimeter of the polygon, either clockwise
     * or counterclockwise. No two vertices may occupy the same location. The
     * polygon side defined by the last vertex and the first vertex shall
     * complete the polygon. The polygon shall be a simple, plane figure with no
     * sides crossing and no interior holes.
     * </p>
     * <p>
     * This field shall consist of up to five subfields: the segmentation for
     * each finger is represented in a different subfield. The first information
     * item ( friction ridge alternate segment position / FRAS) is the finger
     * number from Table 8. This information item is called the friction ridge
     * alternate segment position / FRAS to differentiate it from FGP. See
     * Section 7.7.12. The number of information items within each subfield
     * depends on the number of vertices.
     * </p>
     */
    private List<ASEG> alternateFingerSementPositions = new ArrayList<>();

    public List<Integer> getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(List<Integer> fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public PPD getPrintPositionDescriptors() {
        return printPositionDescriptors;
    }

    public void setPrintPositionDescriptors(PPD printPositionDescriptors) {
        this.printPositionDescriptors = printPositionDescriptors;
    }

    public List<PPC> getPrintPositionCoordinates() {
        return printPositionCoordinates;
    }

    public void setPrintPositionCoordinates(List<PPC> printPositionCoordinates) {
        this.printPositionCoordinates = printPositionCoordinates;
    }

    public List<AMP> getAmputatedOrBandaged() {
        return amputatedOrBandaged;
    }

    public void setAmputatedOrBandaged(List<AMP> amputatedOrBandaged) {
        this.amputatedOrBandaged = amputatedOrBandaged;
    }

    public List<SEG> getFingerprintSegmentationPosition() {
        return fingerprintSegmentationPosition;
    }

    public void setFingerprintSegmentationPosition(List<SEG> fingerprintSegmentationPosition) {
        this.fingerprintSegmentationPosition = fingerprintSegmentationPosition;
    }

    public List<NQM> getNistQualityMetric() {
        return nistQualityMetric;
    }

    public void setNistQualityMetric(List<NQM> nistQualityMetric) {
        this.nistQualityMetric = nistQualityMetric;
    }

    public List<SQM> getSegmentationQualityMetric() {
        return segmentationQualityMetric;
    }

    public void setSegmentationQualityMetric(List<SQM> segmentationQualityMetric) {
        this.segmentationQualityMetric = segmentationQualityMetric;
    }

    public List<FQM> getFingerprintQualityMetric() {
        return fingerprintQualityMetric;
    }

    public void setFingerprintQualityMetric(List<FQM> fingerprintQualityMetric) {
        this.fingerprintQualityMetric = fingerprintQualityMetric;
    }

    public List<ASEG> getAlternateFingerSementPositions() {
        return alternateFingerSementPositions;
    }

    public void setAlternateFingerSementPositions(List<ASEG> alternateFingerSementPositions) {
        this.alternateFingerSementPositions = alternateFingerSementPositions;
    }

}
