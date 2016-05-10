package org.jnbis.api.model.record;

import org.jnbis.internal.record.BaseGrayscaleFingerprintRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ericdsoto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HighResolutionGrayscaleFingerprint extends BaseGrayscaleFingerprintRecord {

}
