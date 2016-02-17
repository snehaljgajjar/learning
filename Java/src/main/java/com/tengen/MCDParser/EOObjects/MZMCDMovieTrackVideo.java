package com.tengen.MCDParser.EOObjects;

import com.tengen.MCDParser.utils.MCDXmlTagName.MovieTag;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author: pgajjar @date: 8/26/13 @time: 11:10 PM
 */
@SuppressWarnings("unused")
public class MZMCDMovieTrackVideo extends MZMCDMovieTrack {
    private static Logger log = Logger.getLogger(MZMCDMovieTrackVideo.class.getName());

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
    private String sdAvcNalPicParamSetPPSId;
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
                if (childNode.getNodeName().equalsIgnoreCase(MovieTag.TRACK_ID.getTag())) {
                    trackId = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.LANGUAGE_TAG_NAME.getTag())) {
                    trackLanguageNumeric = Integer.parseInt(attributes.getNamedItem(MovieTag.NUMERIC_ATTR_NAME.getTag()).getNodeValue());
                    trackLanguage = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.EXTD_LANGUAGE_TAG_NAME.getTag())) {
                    extendedLanguage = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.ALTERNATE_GROUP.getTag())) {
                    alternateGroup = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.MATRIX_NAME.getTag())) {
                    matrixIdentity = Boolean.getBoolean(attributes.getNamedItem(MovieTag.IDENTITY_ATTR_NAME.getTag()).getNodeValue());
                    matrixClob = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DATA_SIZE.getTag())) {
                    dataSizeUnits = attributes.getNamedItem(MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    dataSize = Long.parseLong(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DURATION_NAME.getTag())) {
                    trackDurationMilliSeconds = Integer.parseInt(attributes.getNamedItem(MovieTag.MILLISECONDS_ATTR_NAME.getTag()).getNodeValue());
                    trackDurationTrack = Integer.parseInt(attributes.getNamedItem(MovieTag.TRACK_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    trackDurationUnits = attributes.getNamedItem(MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    trackDuration = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.EDIT_LIST_NAME.getTag())) {
                    editListCount = Integer.parseInt(attributes.getNamedItem(MovieTag.COUNT_ATTR_NAME.getTag()).getNodeValue());
                    editListEmpties = Integer.parseInt(attributes.getNamedItem(MovieTag.EMPTIES_ATTR_NAME.getTag()).getNodeValue());
                    editListNormalRate = Boolean.getBoolean(attributes.getNamedItem(MovieTag.NORMAL_RATE_ATTR_NAME.getTag()).getNodeValue());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.SAMPLE_DESC_COUNT.getTag())) {
                    sampleDescCount = Integer.parseInt(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DATA_RATE_NAME.getTag())) {
                    dataRateUnits = attributes.getNamedItem(MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                    dataRate = Float.parseFloat(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.CODEC_NAME.getTag())) {
                    codecName = attributes.getNamedItem(MovieTag.NAME_ATTR_NAME.getTag()).getNodeValue();
                    codecType = attributes.getNamedItem(MovieTag.TYPE_ATTR_NAME.getTag()).getNodeValue();
                    codec = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DURATION_STATISTICS_NAME.getTag())) {
                    durationStatMeanDuration = Float.parseFloat(attributes.getNamedItem(MovieTag.MEAN_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    durationStatTimeScale = Integer.parseInt(attributes.getNamedItem(MovieTag.TIME_SCALE_ATTR_NAME.getTag()).getNodeValue());
                    durationStatTotalDuration = Integer.parseInt(attributes.getNamedItem(MovieTag.TOTAL_DURATION_ATTR_NAME.getTag()).getNodeValue());
                    durationStatUniform = Boolean.parseBoolean(attributes.getNamedItem(MovieTag.UNIFORM_ATTR_NAME.getTag()).getNodeValue());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DATA_RATE_STATISTICS_NAME.getTag())) {
                    dataRateStatUnit = attributes.getNamedItem(MovieTag.UNITS_ATTR_NAME.getTag()).getNodeValue();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.ENCODED_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        encodedDimWidth = dimensions.getWidth();
                        encodedDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.DISPLAY_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        dispDimWidth = dimensions.getWidth();
                        dispDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.TRACK_DIMENSIONS_NAME.getTag())) {
                    NodeList encodedDimChildren = childNode.getChildNodes();
                    Dimensions dimensions = getDimensions(encodedDimChildren);
                    if (dimensions != null) {
                        trackDimWidth = dimensions.getWidth();
                        trackDimHeight = dimensions.getHeight();
                    }
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.FRAME_RATE_NAME.getTag())) {
                    frameRate = Float.parseFloat(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.FIELD_DOMINANCE_NAME.getTag())) {
                    fieldDominance = childNode.getTextContent();
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.MAX_SAMPLE_SIZE_NAME.getTag())) {
                    maxSampleSize = Long.parseLong(childNode.getTextContent());
                } else if (childNode.getNodeName().equalsIgnoreCase(MovieTag.SAMPLE_DESC_NAME.getTag())) {
                    populateSampleDescriptionParams(childNode);
                }
            }
        }
    }

    private void populateSampleDescriptionParams(Node sdNode) {
        if (sdNode != null) {
            NodeList sdChildren = sdNode.getChildNodes();
            for (int i = 0; i < sdChildren.getLength(); i++) {
                Node sdChild = sdChildren.item(i);
                if (sdChild.getNodeType() == Node.ELEMENT_NODE) {
                    if (sdChild.getNodeName().equalsIgnoreCase(MovieTag.COLR_NAME.getTag())) {
                        NodeList colrChildren = sdChild.getChildNodes();
                        if (colrChildren != null) {
                            for (int j = 0; j < colrChildren.getLength(); j++) {
                                Node colrChild = colrChildren.item(j);
                                if (colrChild.getNodeType() == Node.ELEMENT_NODE) {
                                    if (colrChild.getNodeName().equalsIgnoreCase(MovieTag.COLR_PARAM_TYPE_NAME.getTag())) {
                                        sdColrParamType = colrChild.getTextContent();
                                    } else if (colrChild.getNodeName().equalsIgnoreCase(MovieTag.MATRIX_INDEX_NAME.getTag())) {
                                        sdColrMatrixIndex = Integer.parseInt(colrChild.getTextContent());
                                    } else if (colrChild.getNodeName().equalsIgnoreCase(MovieTag.PRIMARIES_INDEX_NAME.getTag())) {
                                        sdColrPrimIndex = Integer.parseInt(colrChild.getTextContent());
                                    } else if (colrChild.getNodeName().equalsIgnoreCase(MovieTag.TRANSFER_FUNCTION_INDEX_NAME.getTag())) {
                                        sdColrTransferFunctIndex = Integer.parseInt(colrChild.getTextContent());
                                    }
                                }
                            }
                        }
                    } else if (sdChild.getNodeName().equalsIgnoreCase(MovieTag.FIEL_NAME.getTag())) {
                        NodeList fielChildren = sdChild.getChildNodes();
                        if (fielChildren != null) {
                            for (int j = 0; j < fielChildren.getLength(); j++) {
                                Node fielChild = fielChildren.item(j);
                                if (fielChild.getNodeType() == Node.ELEMENT_NODE) {
                                    if (fielChild.getNodeName().equalsIgnoreCase(MovieTag.FIELD_COUNT_NAME.getTag())) {
                                        sdFielCount = Integer.parseInt(fielChild.getTextContent());
                                    } else if (fielChild.getNodeName().equalsIgnoreCase(MovieTag.FIELD_ORDERING_NAME.getTag())) {
                                        sdFielOrdering = Integer.parseInt(fielChild.getTextContent());
                                    }
                                }
                            }
                        }
                    } else if (sdChild.getNodeName().equalsIgnoreCase(MovieTag.PASP_NAME.getTag())) {
                        NodeList paspChildren = sdChild.getChildNodes();
                        if (paspChildren != null) {
                            for (int j = 0; j < paspChildren.getLength(); j++) {
                                Node paspChild = paspChildren.item(j);
                                if (paspChild.getNodeType() == Node.ELEMENT_NODE) {
                                    if (paspChild.getNodeName().equalsIgnoreCase(MovieTag.HORIZONTAL_SPACING_NAME.getTag())) {
                                        sdPaspHorizontalSpacing = Integer.parseInt(paspChild.getTextContent());
                                    } else if (paspChild.getNodeName().equalsIgnoreCase(MovieTag.VERTICAL_SPACING_NAME.getTag())) {
                                        sdPaspVerticalSpacing = Integer.parseInt(paspChild.getTextContent());
                                    }
                                }
                            }
                        }
                    } else if (sdChild.getNodeName().equalsIgnoreCase(MovieTag.AVCC_NAME.getTag())) {
                        NodeList avccChildren = sdChild.getChildNodes();
                        if (avccChildren != null) {
                            for (int j = 0; j < avccChildren.getLength(); j++) {
                                Node avccChild = avccChildren.item(j);
                                if (avccChild.getNodeType() == Node.ELEMENT_NODE) {
                                    if (avccChild.getNodeName().equalsIgnoreCase(MovieTag.PROFILE_NAME.getTag())) {
                                        sdAvcProfile = avccChild.getTextContent();
                                    } else if (avccChild.getNodeName().equalsIgnoreCase(MovieTag.COMPATABILITY_NAME.getTag())) {
                                        sdAvcCompatibility = Integer.parseInt(avccChild.getTextContent());
                                    } else if (avccChild.getNodeName().equalsIgnoreCase(MovieTag.LEVEL_NAME.getTag())) {
                                        sdAvcLevel = Float.parseFloat(avccChild.getTextContent());
                                    } else if (avccChild.getNodeName().equalsIgnoreCase(MovieTag.NAL_UNITS_NAME.getTag())) {
                                        populateSDAvcNalParams(avccChild);
                                    }
                                }
                            }
                        }
                    } else if (sdChild.getNodeName().equalsIgnoreCase(MovieTag.UUID_NAME.getTag())) {
                        sdUUID = sdChild.getTextContent();
                    }
                }
            }
        }
    }

    private void populateSDAvcNalParams(Node nalUnitsNode) {
        NodeList nalUnitsChildren = nalUnitsNode.getChildNodes();
        if (nalUnitsChildren != null) {
            for (int i = 0; i < nalUnitsNode.getChildNodes().getLength(); i++) {
                Node nalUnitsChild = nalUnitsChildren.item(i);
                if (nalUnitsChild.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList ppsChildren = nalUnitsChild.getChildNodes();
                    for (int j = 0; j < ppsChildren.getLength(); j++) {
                        Node ppsChild = ppsChildren.item(j);
                        if (ppsChild.getNodeType() == Node.ELEMENT_NODE) {
                            NamedNodeMap ppsAttributes = ppsChild.getAttributes();
                            sdAvcNalPicParamSetECMFlag = ppsAttributes.getNamedItem(MovieTag.ECM_FLAG_NAME.getTag()).getNodeValue();
                            sdAvcNalPicParamSetPPSId = ppsAttributes.getNamedItem(MovieTag.PPS_ID_NAME.getTag()).getNodeValue();
                            sdAvcNalPicParamSetSPSId = ppsAttributes.getNamedItem(MovieTag.SPS_ID_NAME.getTag()).getNodeValue();
                        }
                    }
                }
            }
        }
    }

    private Dimensions getDimensions(NodeList dimChildren) {
        Dimensions dimensions = null;
        if (dimChildren != null) {
            Integer width = null;
            Integer height = null;
            for (int i = 0; i < dimChildren.getLength(); i++) {
                Node node = dimChildren.item(i);
                if (node.getNodeName().equalsIgnoreCase(MovieTag.WIDTH_NAME.getTag())) {
                    width = Integer.parseInt(node.getTextContent());
                } else if (node.getNodeName().equalsIgnoreCase(MovieTag.HEIGHT_NAME.getTag())) {
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

    public String getSdAvcNalPicParamSetPPSId() {
        return sdAvcNalPicParamSetPPSId;
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
