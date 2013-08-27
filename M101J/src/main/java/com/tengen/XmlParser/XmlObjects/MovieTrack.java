package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MZMCDXmlTagName;
import org.w3c.dom.Node;

/**
 * @author: pgajjar @date: 8/26/13 @time: 5:23 PM
 */
public class MovieTrack {
    private MovieTrack(Node node) {
        if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.VIDEO_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.SOUND_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.TIMECODE_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.CHAPTER_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.CHAPTER_IMAGE_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTrackType.UNKNOWN_TRACK.getTrackType())) {

        }
    }

    public static MovieTrack createMovieTrack(Node node) {
        return new MovieTrack(node);
    }
}
