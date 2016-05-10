package org.jnbis.internal;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jnbis.FileUtils;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.NistBuilder;
import org.jnbis.api.handler.NistHandler;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.HighResolutionGrayscaleFingerprint;
import org.jnbis.api.model.record.TransactionInformation;
import org.jnbis.api.model.record.UserDefinedDescriptiveText;
import org.jnbis.api.model.record.VariableResolutionFingerprint;
import org.jnbis.api.model.record.VariableResolutionFingerprint.AMP;
import org.jnbis.api.model.record.VariableResolutionFingerprint.ASEG;
import org.junit.Test;

public class NistEncoderTest {

    @Test
    public void encodeSample() throws IOException {
        String filename = FileUtils.absolute("samples/nist/sample.an2");
        Nist sample = Jnbis.nist().decode(filename);
        NistHandler handler = Jnbis.nist();
        handler.setNist(sample);
        byte[] encoded = handler.encode();
        File file = new File("/Users/mark/Temp", "encodeSample.nist");
        Files.write(file.toPath(), encoded);
    }
    
    @Test
    public void encodeUserDefinedText() throws IOException {
        
        TransactionInformation txInfo = new TransactionInformation();
        txInfo.setTypeOfTransaction("Special");
        txInfo.setDestinationAgencyId("Dest Agency");
        txInfo.setOriginatingAgencyId("Orig Agency");
        txInfo.setControlNumber(UUID.randomUUID().toString());
        txInfo.setNativeScanningResolution("00.00"); //TODO
        txInfo.setNominalTransmittingResolution("00:00"); //TODO
        
        UserDefinedDescriptiveText udt = new UserDefinedDescriptiveText();
        udt.addUserDefinedField(3, "UDT 1");
        udt.addUserDefinedField(4, "UDT 2");
        udt.addUserDefinedField(7, "UDT 7");
        
        byte[] encodedNist = Jnbis.nist()
                .create()
                    .transactionInfo(txInfo)
                    .add(udt)
                    .build()
                .encode();
        
        Nist decoded = Jnbis.nist().decode(encodedNist);
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getTransactionInfo().getTransactionContent().getFirstRecordCategoryCode());
        assertEquals(1, decoded.getTransactionInfo().getTransactionContent().getContentRecordCount());
        
        assertEquals(1, decoded.getTransactionInfo().getTransactionContent().getIdcs().size());
        
        assertEquals(1, decoded.getUserDefinedTexts().size());
        
        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals(udt.getUserDefinedFields().size(), userDefinedFields.size());
        assertEquals(udt.getUserDefinedFields().get(3), userDefinedFields.get(3));
        assertEquals(udt.getUserDefinedFields().get(4), userDefinedFields.get(4));

    }
    
    @Test
    public void encodeType4() throws IOException {
        
        TransactionInformation txInfo = new TransactionInformation();
        txInfo.setTypeOfTransaction("Special");
        txInfo.setDestinationAgencyId("Dest Agency");
        txInfo.setOriginatingAgencyId("Orig Agency");
        txInfo.setControlNumber(UUID.randomUUID().toString());
        txInfo.setNativeScanningResolution("19.69");
        txInfo.setNominalTransmittingResolution("19.69");
        
        UserDefinedDescriptiveText udt = new UserDefinedDescriptiveText();
        udt.addUserDefinedField(3, "UDT 1");
        udt.addUserDefinedField(4, "UDT 2");
        udt.addUserDefinedField(7, "UDT 7");
        
        List<HighResolutionGrayscaleFingerprint> fps = new ArrayList<>();
        for (int i=1; i < 15; i++) {
            HighResolutionGrayscaleFingerprint fp = new HighResolutionGrayscaleFingerprint();
            fp.setImpressionType(3);
            fp.setFingerPosition(new int[] {i, -1, -1, -1, -1, -1});
            fp.setImageScanningResolution(0);
            fp.setHorizontalLineLength(i * 10);
            fp.setVerticalLineLength(i * 11);
            fp.setCompressionAlgorithm("1");
            
            String fileName = findFileName(i, "wsq");
            byte[] imageData = FileUtils.read(new File(fileName));
            fp.setImageData(imageData);
            
            fps.add(fp);
        }

        NistBuilder builder = Jnbis.nist()
                .create()
                .transactionInfo(txInfo)
                .add(udt);
        
        for (HighResolutionGrayscaleFingerprint fp: fps) {
            builder.add(fp);
        }

        byte[] encodedNist = builder.build()
                .encode();
        
        File file = new File("/Users/mark/Temp", "encodeType4.nist");
        Files.write(file.toPath(), encodedNist);
        
        Nist decoded = Jnbis.nist().decode(encodedNist);
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getTransactionInfo().getTransactionContent().getFirstRecordCategoryCode());
        assertEquals(1 + fps.size(), decoded.getTransactionInfo().getTransactionContent().getContentRecordCount());
        
        assertEquals(1 + fps.size(), decoded.getTransactionInfo().getTransactionContent().getIdcs().size());
        
        assertEquals(1, decoded.getUserDefinedTexts().size());
        
        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals(udt.getUserDefinedFields().size(), userDefinedFields.size());
        assertEquals(udt.getUserDefinedFields().get(3), userDefinedFields.get(3));
        assertEquals(udt.getUserDefinedFields().get(4), userDefinedFields.get(4));

        assertEquals(fps.size(), decoded.getHiResGrayscaleFingerprints().size());
        for (HighResolutionGrayscaleFingerprint fp: decoded.getHiResGrayscaleFingerprints()) {
            HighResolutionGrayscaleFingerprint orig = fps.get(fp.getFingerPosition()[0] - 1);
            assertEquals(orig.getIdc(), fp.getIdc());
            assertEquals(orig.getImpressionType(), fp.getImpressionType());
            assertEquals(orig.getImageScanningResolution(), fp.getImageScanningResolution());
            assertEquals(orig.getHorizontalLineLength(), fp.getHorizontalLineLength());
            assertEquals(orig.getVerticalLineLength(), fp.getVerticalLineLength());
            assertEquals(orig.getCompressionAlgorithm(), fp.getCompressionAlgorithm());
            assertArrayEquals(orig.getImageData(), fp.getImageData());
        }
    }
    
    
    @Test
    public void encodeType14() throws IOException {
        TransactionInformation txInfo = new TransactionInformation();
        txInfo.setTypeOfTransaction("Special");
        txInfo.setDestinationAgencyId("Dest Agency");
        txInfo.setOriginatingAgencyId("Orig Agency");
        txInfo.setControlNumber(UUID.randomUUID().toString());
        txInfo.setNativeScanningResolution("19.69");
        txInfo.setNominalTransmittingResolution("19.69");
        
        UserDefinedDescriptiveText udt = new UserDefinedDescriptiveText();
        udt.addUserDefinedField(3, "UDT 1");
        udt.addUserDefinedField(4, "UDT 2");
        udt.addUserDefinedField(7, "UDT 7");
        
        List<VariableResolutionFingerprint> fps = new ArrayList<>();
        for (int i=1; i < 15; i++) {
            VariableResolutionFingerprint fp = new VariableResolutionFingerprint();
            fp.setImpressionType(3);
            fp.setSourceAgency(txInfo.getOriginatingAgencyId());
            fp.setCaptureDate(new Date());
            fp.setHorizontalLineLength(i * 10);
            fp.setVerticalLineLength(i * 11);
            fp.setCompressionAlgorithm("WSQ20");
            fp.setBitsPerPixel(8);
            
            fp.getFingerPosition().add(i);
            fp.getFingerPosition().add(i + 1); //Should be discarded
            
            if (i % 5 == 0) {
                AMP amp = new AMP();
                amp.frap = i;
                amp.amputatedOrBandagedCode = "XX";
                fp.getAmputatedOrBandaged().add(amp);
                amp = new AMP();
                amp.frap = i + 1;
                amp.amputatedOrBandagedCode = "UP";
                fp.getAmputatedOrBandaged().add(amp);
                
                for (int a = 0; a < 5; a++) {
                    ASEG value = new ASEG();
                    value.frictionRidgeAltSegmentPosition = a;
                    value.numberOfPoints = a + 5;
                    value.points = new int[value.numberOfPoints][2];
                    for (int p = 0; p < value.numberOfPoints; p++) {
                        value.points[p][0] = p * 10;
                        value.points[p][1] = p * 11;
                    }
                    fp.getAlternateFingerSementPositions().add(value);
                }
            }

            /*
             * While the image data is optional in NIST 2011 version, it was mandatory
             * even when Amputated Or Bandaged code = "UP" (unable to print). So for backward
             * capability we won't skip setting image data in the above AMP code block.
             */
            String fileName = findFileName(i, "wsq");
            byte[] imageData = FileUtils.read(new File(fileName));
            fp.setImageData(imageData);
            
            fps.add(fp);
        }

        NistBuilder builder = Jnbis.nist()
                .create()
                .transactionInfo(txInfo)
                .add(udt);
        
        for (VariableResolutionFingerprint fp: fps) {
            builder.add(fp);
        }

        byte[] encodedNist = builder.build()
                .encode();
        
        File dir = new File("/Users/mark/Temp");
        File file = new File(dir, "encodeType14.nist");
        Files.write(file.toPath(), encodedNist);
        
        Nist decoded = Jnbis.nist().decode(encodedNist);
        assertNotNull(decoded.getTransactionInfo());
        assertEquals(1, decoded.getTransactionInfo().getTransactionContent().getFirstRecordCategoryCode());
        assertEquals(1 + fps.size(), decoded.getTransactionInfo().getTransactionContent().getContentRecordCount());
        
        assertEquals(1 + fps.size(), decoded.getTransactionInfo().getTransactionContent().getIdcs().size());
        
        assertEquals(1, decoded.getUserDefinedTexts().size());
        
        Map<Integer, String> userDefinedFields = decoded.getUserDefinedTexts().get(0).getUserDefinedFields();
        assertEquals(udt.getUserDefinedFields().size(), userDefinedFields.size());
        assertEquals(udt.getUserDefinedFields().get(3), userDefinedFields.get(3));
        assertEquals(udt.getUserDefinedFields().get(4), userDefinedFields.get(4));

        assertEquals(fps.size(), decoded.getVariableResFingerprints().size());
        for (VariableResolutionFingerprint fp: decoded.getVariableResFingerprints()) {
            VariableResolutionFingerprint orig = fps.get(fp.getFingerPosition().get(0) - 1);
            assertEquals(orig.getIdc(), fp.getIdc());
            assertEquals(orig.getImpressionType(), fp.getImpressionType());
            assertEquals(orig.getHorizontalLineLength(), fp.getHorizontalLineLength());
            assertEquals(orig.getVerticalLineLength(), fp.getVerticalLineLength());
            assertEquals(orig.getCompressionAlgorithm(), fp.getCompressionAlgorithm());
            if (fp.getImageData() != null) {
                Jnbis.wsq().decode(fp.getImageData()).toPng().asFile(dir.getAbsolutePath() + "fp" + fp.getIdc() + ".png");
            }
            assertArrayEquals(orig.getImageData(), fp.getImageData());
        }
    }
    
    private String findFileName(int imageCharacter, String ext) {
        return FileUtils.absolute(String.format("samples/nist/fp-%d.%s", imageCharacter, ext));
    }
}
