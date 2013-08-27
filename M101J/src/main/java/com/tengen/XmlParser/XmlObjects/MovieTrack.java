package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MCDXmlTagName.MovieTrackType;
import org.w3c.dom.Node;

/**
 * @author: pgajjar @date: 8/26/13 @time: 5:23 PM
 */
public class MovieTrack {
//    private MovieTrack(Node node) {
//
//    }

    public static MZMCDMovieTrack createMovieTrack(Node node) {
        if (node.getNodeName().equalsIgnoreCase(MovieTrackType.VIDEO_TRACK.getTrackType())) {
            return new MZMCDMovieTrackVideo(node);
        } else if (node.getNodeName().equalsIgnoreCase(MovieTrackType.SOUND_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MovieTrackType.TIMECODE_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MovieTrackType.CHAPTER_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MovieTrackType.CHAPTER_IMAGE_TRACK.getTrackType())) {

        } else if (node.getNodeName().equalsIgnoreCase(MovieTrackType.UNKNOWN_TRACK.getTrackType())) {

        }
        return null;
    }
}
