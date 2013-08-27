package com.tengen.MCDParser.utils;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * User: pgajjar Date: 8/26/13 Time: 11:19 AM
 */
@SuppressWarnings("unused")
public class MCDXmlTagName {
    public enum McdType {
        JpegAsset("image"),
        IttSubtitleAsset("subtitle_description"),
        SccCaptionAsset("scc_description"),
        Ac3AudioAsset("container", "ac3-audio"),
        QuickTimeMovie("movie"),
        Mpeg2Movie("container"),
        Invalid(null);

        private final String rootTagName;
        private final String rootAttrName;

        McdType(String rootTagName) {
            this(rootTagName, null);
        }

        McdType(String rootTagName, String rootAttrName) {
            this.rootTagName = rootTagName;
            this.rootAttrName = rootAttrName;
        }

        public String getRootTagName() {
            return rootTagName;
        }

        public String getRootAttrName() {
            return rootAttrName;
        }

        public static McdType getMcdType(Element documentElement) {
            String rootTagName = documentElement.getTagName();
            for (McdType type : McdType.values()) {
                if (rootTagName.equalsIgnoreCase(type.getRootTagName())) {
                    NamedNodeMap attributes = documentElement.getAttributes();
                    if (attributes != null && attributes.getLength() > 0 && type.getRootAttrName() != null) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            String attributeName = attributes.item(i).getNodeValue();
                            if (attributeName.contains(type.getRootAttrName())) {
                                return type;
                            }
                        }
                    } else {
                        return type;
                    }
                }
            }

            return Invalid;
        }
    }

    public enum MovieTag {
        MOVIE_TAG_NAME("movie"),
        CLOCK_TAG_NAME("clock"),
        CODECS_ATTR_NAME("codecs"),
        TYPE_ATTR_NAME("type"),
        POSTER_TIME_ATTR_NAME("poster_time"),
        PREVIEW_DURATION_ATTR_NAME("preview_duration"),
        PREVIEW_TIME_ATTR_NAME("preview_time"),
        TIME_SCALE_ATTR_NAME("time_scale"),
        TOTAL_DURATION_ATTR_NAME("total_duration"),
        MATRIX_NAME("matrix"),
        IDENTITY_ATTR_NAME("identity"),
        METADATA_ATOMS("metadata_atoms"),
        FORMAT_ATTR_NAME("format"),
        LOCALE_ATTR_NAME("locale"),
        TRACKS_NAME("tracks"),
        ENABLED_ATTR_NAME("enabled"),
        INDEX_ATTR_NAME("index"),
        TRACK_ID("track_id"),
        LANGUAGE_TAG_NAME("language"),
        NUMERIC_ATTR_NAME("numeric"),
        EXTD_LANGUAGE_TAG_NAME("extended_language"),
        ALTERNATE_GROUP("alternate_group"),
        DATA_SIZE("data_size"),
        DURATION_NAME("duration"),
        MILLISECONDS_ATTR_NAME("milliseconds"),
        TRACK_DURATION_ATTR_NAME("track_duration"),
        UNITS_ATTR_NAME("units"),
        EDIT_LIST_NAME("edit_list"),
        EDIT_LIST_ENTRY_NAME("edit_list_entry"),
        COUNT_ATTR_NAME("count"),
        EMPTIES_ATTR_NAME("empties"),
        NORMAL_RATE_ATTR_NAME("normal_rate"),
        MEDIA_RATE_ATTR_NAME("media_rate"),
        MEDIA_TIME_ATTR_NAME("media_time"),
        SAMPLE_DESC_COUNT("sample_description_count"),
        DATA_RATE_NAME("data_rate"),
        CODEC_NAME("codec"),
        NAME_ATTR_NAME("name"),
        DURATION_STATISTICS_NAME("duration_statistics"),
        MEAN_DURATION_ATTR_NAME("mean_duration"),
        UNIFORM_ATTR_NAME("uniform"),
        DATA_RATE_STATISTICS_NAME("data_rate_statistics"),
        ENCODED_DIMENSIONS_NAME("encoded_dimensions"),
        WIDTH_NAME("width"),
        HEIGHT_NAME("height"),
        DISPLAY_DIMENSIONS_NAME("display_dimensions"),
        TRACK_DIMENSIONS_NAME("track_dimensions"),
        FRAME_RATE_NAME("frame_rate"),
        FIELD_DOMINANCE_NAME("field_dominance"),
        MAX_SAMPLE_SIZE_NAME("maximum_sample_size"),
        SAMPLE_DESC_NAME("sample_description");

        private final String tagName;

        MovieTag(String tagName) {
            this.tagName = tagName;
        }

        public String getTag() {
            return tagName;
        }
    }

    public enum MovieTrackType {
        VIDEO_TRACK("video"),
        SOUND_TRACK("sound"),
        TIMECODE_TRACK("timecode"),
        CHAPTER_TRACK("chapter"),
        CHAPTER_IMAGE_TRACK("chapter_image"),
        UNKNOWN_TRACK("unknown");

        private final String trackType;

        MovieTrackType(String trackType) {
            this.trackType = trackType;
        }

        public String getTrackType() {
            return trackType;
        }
    }
}
