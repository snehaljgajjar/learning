package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MCDXmlTagName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author: pgajjar @date: 8/26/13 @time: 9:29 PM
 */
@SuppressWarnings("unused")
public class MZMCDMovieTrack {
    // TODO: EO - the PK of MZ_MCD_MOVIE table.
    protected final Integer movieId = 1;
    protected final Boolean trackEnabled;
    protected final Integer trackIndex;
    protected final String trackType;
    protected Integer trackId;
    protected Boolean trackColocatedSamples;
    protected Integer trackLanguageNumeric;
    protected String trackLanguage;
    protected String extendedLanguage;
    protected Integer alternateGroup;
    protected Boolean matrixIdentity;
    protected String matrixClob;
    protected String dataSizeUnits;
    protected Long dataSize;
    protected Integer trackDurationMilliSeconds;
    protected Integer trackDurationTrack;
    protected String trackDurationUnits;
    protected Integer trackDuration;
    protected Integer editListCount;
    protected Integer editListEmpties;
    protected Boolean editListNormalRate;
    protected Integer sampleDescCount;
    protected String dataRateUnits;
    protected Float dataRate;
    protected String codecName;
    protected String codecType;
    protected String codec;
    protected Boolean fairPlayProtected;
    protected String trackXmlTagType;

    public MZMCDMovieTrack(Node track) {
        NamedNodeMap attributes = track.getAttributes();
        trackEnabled = attributes.getNamedItem(MCDXmlTagName.MovieTag.ENABLED_ATTR_NAME.getTag()).getNodeValue().equalsIgnoreCase("true");
        trackIndex = Integer.parseInt(attributes.getNamedItem(MCDXmlTagName.MovieTag.INDEX_ATTR_NAME.getTag()).getNodeValue());
        trackType = attributes.getNamedItem(MCDXmlTagName.MovieTag.TYPE_ATTR_NAME.getTag()).getNodeValue();
        trackXmlTagType = track.getNodeName();
        // TODO: Following flags need to be overridden in MZMCDMovieTrackSound table.
        trackColocatedSamples = false;
        fairPlayProtected = false;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public Boolean getTrackEnabled() {
        return trackEnabled;
    }

    public Integer getTrackIndex() {
        return trackIndex;
    }

    public String getTrackType() {
        return trackType;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public Boolean getTrackColocatedSamples() {
        return trackColocatedSamples;
    }

    public Integer getTrackLanguageNumeric() {
        return trackLanguageNumeric;
    }

    public String getTrackLanguage() {
        return trackLanguage;
    }

    public String getExtendedLanguage() {
        return extendedLanguage;
    }

    public Integer getAlternateGroup() {
        return alternateGroup;
    }

    public Boolean getMatrixIdentity() {
        return matrixIdentity;
    }

    public String getMatrixClob() {
        return matrixClob;
    }

    public String getDataSizeUnits() {
        return dataSizeUnits;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public Integer getTrackDurationMilliSeconds() {
        return trackDurationMilliSeconds;
    }

    public Integer getTrackDurationTrack() {
        return trackDurationTrack;
    }

    public String getTrackDurationUnits() {
        return trackDurationUnits;
    }

    public Integer getTrackDuration() {
        return trackDuration;
    }

    public Integer getEditListCount() {
        return editListCount;
    }

    public Integer getEditListEmpties() {
        return editListEmpties;
    }

    public Boolean getEditListNormalRate() {
        return editListNormalRate;
    }

    public Integer getSampleDescCount() {
        return sampleDescCount;
    }

    public String getDataRateUnits() {
        return dataRateUnits;
    }

    public Float getDataRate() {
        return dataRate;
    }

    public String getCodecName() {
        return codecName;
    }

    public String getCodecType() {
        return codecType;
    }

    public String getCodec() {
        return codec;
    }

    public Boolean getFairPlayProtected() {
        return fairPlayProtected;
    }

    public String getTrackXmlTagType() {
        return trackXmlTagType;
    }
}
