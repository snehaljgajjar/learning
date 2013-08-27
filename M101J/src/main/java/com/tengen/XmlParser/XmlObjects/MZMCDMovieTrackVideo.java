package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MZMCDXmlTagName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author: pgajjar @date: 8/26/13 @time: 11:10 PM
 */
public class MZMCDMovieTrackVideo extends MZMCDMovieTrack {
    private class Dimensions {
        private final Integer width;
        private final Integer height;

        public Dimensions(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }

        private Integer getWidth() {
            return width;
        }

        private Integer getHeight() {
            return height;
        }
    }

    // TODO: EO - the PK of MZ_MCD_MOVIE_TRACK table.
    private final Integer trackIdFK = 1;
    private Float durationStatMeanDuration;
    private Integer durationStatTimeScale;
    private Integer durationStatTotalDuration;
    private Boolean durationStatUniform;
    private String dataRateStatUnit;
    private Integer encodedDimWidth;
    private Integer encodedDimHeight;
    private Integer dispDimWidth;
    private Integer dispDimHeight;
    private Integer trackDimWidth;
    private Integer trackDimHeight;
    private Float frameRate;
    private String fieldDominance;
    private String sdColrParamType;
    private Integer sdColrMatrixIndex;
    private Integer sdColrPrimIndex;
    private Integer sdColrTransferFunctIndex;
    private Integer sdPaspHorizontalSpacing;
    private Integer sdPaspVerticalSpacing;
    private Integer sdFielCount;
    private Integer sdFielOrdering;
    private String sdAvcProfile;
    private Integer sdAvcCompatibility;
    private Float sdAvcLevel;
    private String sdAvcNalPicParamSetECMFlag;
    private String sdAvcNalPicParamSetPCSId;
    private String sdAvcNalPicParamSetSPSId;
    private String sdUUID;
    private Long maxSampleSize;

    public MZMCDMovieTrackVideo(Node track) {
        super(track);
        populateTrackChildTags(track.getChildNodes());
    }

    private void populateTrackChildTags(NodeList children) {
        for (int i = 0; i < children.getLength(); i++) {
            Node childNode = children.item(i);
            NamedNodeMap attributes = childNode.getAttributes();
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.TRACK_ID.getTag())) {
                    trackId = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.LANGUAGE_TAG_NAME.getTag())) {
                    trackLanguageNumeric = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.NUMERIC_ATTR_NAME.getTag()).getNodeValue());
                    trackLanguage = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.EXTD_LANGUAGE_TAG_NAME.getTag())) {
                    extendedLanguage = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.ALTERNATE_GROUP.getTag())) {
                    alternateGroup = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.MATRIX_NAME.getTag())) {
                    matrixIdentity = Boolean.getBoolean(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.IDENTITY_ATTR_NAME.getTag()).getNodeValue());
                    matrixClob = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DATA_SIZE.getTag())) {
                    dataSizeUnits = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    dataSize = Long.parseLong(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DURATION_NAME.getTag())) {
                    trackDurationMilliSeconds = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.MILLISECONDS_ATTR_NAME.getTag()).getNodeValue());
                    trackDurationTrack = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TRACK_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    trackDurationUnits = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    trackDuration = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.EDIT_LIST_NAME.getTag())) {
                    editListCount = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.COUNT_ATTR_NAME.getTag()).getNodeValue());
                    editListEmpties = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.EMPTIES_ATTR_NAME.getTag()).getNodeValue());
                    editListNormalRate = Boolean.getBoolean(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.NORMAL_RATE_ATTR_NAME.getTag()).getNodeValue());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.SAMPLE_DESC_COUNT.getTag())) {
                    sampleDescCount = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DATA_RATE_NAME.getTag())) {
                    dataRateUnits = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    dataRate = Float.parseFloat(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.CODEC_NAME.getTag())) {
                    codecName = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.NAME_ATTR_NAME.getTag()).getNodeValue();
                    codecType = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TYPE_ATTR_NAME.getTag()).getNodeValue();
                    codec = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DURATION_STATISTICS_NAME.getTag())) {
                    durationStatMeanDuration = Float.parseFloat(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.MEAN_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    durationStatTimeScale = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TIME_SCALE_ATTR_NAME.getTag()).getNodeValue());
                    durationStatTotalDuration = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TOTAL_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    durationStatUniform = Boolean.parseBoolean(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.UNIFORM_ATTR_NAME.getTag()).getNodeValue());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DATA_RATE_STATISTICS_NAME.getTag())) {
                    dataRateStatUnit = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.ENCODED_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        encodedDimWidth = dimensions.getWidth();
                        encodedDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.DISPLAY_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        dispDimWidth = dimensions.getWidth();
                        dispDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.TRACK_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        trackDimWidth = dimensions.getWidth();
                        trackDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.FRAME_RATE_NAME.getTag())) {
                    frameRate = Float.parseFloat(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.FIELD_DOMINANCE_NAME.getTag())) {
                    fieldDominance = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.MAX_SAMPLE_SIZE_NAME.getTag())) {
                    maxSampleSize = Long.parseLong(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.SAMPLE_DESC_NAME.getTag())) {
                    populateSampleDescriptionParams(childNode, attributes);
                }
            }
        }
    }

    private void populateSampleDescriptionParams(Node childNode, NamedNodeMap attributes) {
    }

    private Dimensions getDimensions(NodeList dimChildren) {
        Dimensions dimensions = null;
        if (dimChildren != null) {
            Integer width = null;
            Integer height = null;
            for (int i = 0; i < dimChildren.getLength(); i++) {
                Node node = dimChildren.item(i);
                if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.WIDTH_NAME.getTag())) {
                    width = Integer.parseInt(node.getTextContent());
                } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.HEIGHT_NAME.getTag())) {
                    height = Integer.parseInt(node.getTextContent());
                }
            }
            dimensions = new Dimensions(width, height);
        }
        return dimensions;
    }

    public Integer getTrackIdFK() {
        return trackIdFK;
    }

    public Float getDurationStatMeanDuration() {
        return durationStatMeanDuration;
    }

    public Integer getDurationStatTimeScale() {
        return durationStatTimeScale;
    }

    public Integer getDurationStatTotalDuration() {
        return durationStatTotalDuration;
    }

    public Boolean getDurationStatUniform() {
        return durationStatUniform;
    }

    public String getDataRateStatUnit() {
        return dataRateStatUnit;
    }

    public Integer getEncodedDimWidth() {
        return encodedDimWidth;
    }

    public Integer getEncodedDimHeight() {
        return encodedDimHeight;
    }

    public Integer getDispDimWidth() {
        return dispDimWidth;
    }

    public Integer getDispDimHeight() {
        return dispDimHeight;
    }

    public Integer getTrackDimWidth() {
        return trackDimWidth;
    }

    public Integer getTrackDimHeight() {
        return trackDimHeight;
    }

    public Float getFrameRate() {
        return frameRate;
    }

    public String getFieldDominance() {
        return fieldDominance;
    }

    public String getSdColrParamType() {
        return sdColrParamType;
    }

    public Integer getSdColrMatrixIndex() {
        return sdColrMatrixIndex;
    }

    public Integer getSdColrPrimIndex() {
        return sdColrPrimIndex;
    }

    public Integer getSdColrTransferFunctIndex() {
        return sdColrTransferFunctIndex;
    }

    public Integer getSdPaspHorizontalSpacing() {
        return sdPaspHorizontalSpacing;
    }

    public Integer getSdPaspVerticalSpacing() {
        return sdPaspVerticalSpacing;
    }

    public Integer getSdFielCount() {
        return sdFielCount;
    }

    public Integer getSdFielOrdering() {
        return sdFielOrdering;
    }

    public String getSdAvcProfile() {
        return sdAvcProfile;
    }

    public Integer getSdAvcCompatibility() {
        return sdAvcCompatibility;
    }

    public Float getSdAvcLevel() {
        return sdAvcLevel;
    }

    public String getSdAvcNalPicParamSetECMFlag() {
        return sdAvcNalPicParamSetECMFlag;
    }

    public String getSdAvcNalPicParamSetPCSId() {
        return sdAvcNalPicParamSetPCSId;
    }

    public String getSdAvcNalPicParamSetSPSId() {
        return sdAvcNalPicParamSetSPSId;
    }

    public String getSdUUID() {
        return sdUUID;
    }

    public Long getMaxSampleSize() {
        return maxSampleSize;
    }
}
