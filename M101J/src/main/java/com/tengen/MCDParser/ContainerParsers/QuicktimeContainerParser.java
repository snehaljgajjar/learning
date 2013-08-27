package com.tengen.MCDParser.ContainerParsers;

import com.google.common.collect.Lists;
import com.tengen.MCDParser.EOObjects.MZMCDMovie;
import com.tengen.MCDParser.EOObjects.MZMCDMovieTrack;
import com.tengen.MCDParser.EOObjects.MZMCDMovieTrackVideo;
import com.tengen.MCDParser.EOObjects.MovieClock;
import com.tengen.MCDParser.EOObjects.MovieMetadata;
import com.tengen.MCDParser.utils.MCDXmlTagName;
import com.tengen.MCDParser.utils.MCDXmlTagName.MovieTrackType;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collection;

/**
 * @author: pgajjar @date: 8/26/13 @time: 5:23 PM
 */
public class QuicktimeContainerParser {
    private static Logger log = Logger.getLogger(QuicktimeContainerParser.class.getName());

    public QuicktimeContainerParser(Document document) {
        parseMovieDocument(document);
    }

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

    private void parseMovieDocument(Document document) {
        Boolean matrixIdentity = false;
        MovieClock movieClock = null;
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName().equalsIgnoreCase(MCDXmlTagName.MovieTag.CLOCK_TAG_NAME.getTag())) {
                    // Clock tag parsing
                    movieClock = parseClock(node);
                } else if (node.getNodeName().equalsIgnoreCase(MCDXmlTagName.MovieTag.MATRIX_NAME.getTag())) {
                    matrixIdentity = parseMatrixIdentity(node);
                } else if (node.getNodeName().equalsIgnoreCase(MCDXmlTagName.MovieTag.METADATA_ATOMS.getTag())) {
                    Collection<MovieMetadata> movieMetadata = parseMetadataAtoms(node);
                } else if (node.getNodeName().equalsIgnoreCase(MCDXmlTagName.MovieTag.TRACKS_NAME.getTag())) {
                    Collection<MZMCDMovieTrack> movieTracks = parseTracks(node);
                    log.debug("No. of Tracks detected: " + movieTracks.size());
                }
            }
        }

        MZMCDMovie mcdMovie = new MZMCDMovie(document, movieClock, matrixIdentity);
    }

    private Collection<MZMCDMovieTrack> parseTracks(Node track) {
        if (track != null && track.getChildNodes().getLength() > 0) {
            Collection<MZMCDMovieTrack> movieTracks = Lists.newArrayList();
            NodeList tracks = track.getChildNodes();
            for (int i = 0; i < tracks.getLength(); i++) {
                Node node = tracks.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    MZMCDMovieTrack movieTrack = QuicktimeContainerParser.createMovieTrack(node);
                    if (movieTrack != null) {
                        movieTracks.add(movieTrack);
                    }
                }
            }
            return movieTracks;
        }
        return null;
    }

    private Collection<MovieMetadata> parseMetadataAtoms(Node metadataAtoms) {
        if (metadataAtoms != null && metadataAtoms.getChildNodes().getLength() > 0) {
            Collection<MovieMetadata> movieMetadatas = Lists.newArrayList();
            NodeList movieMetadata = metadataAtoms.getChildNodes();
            for (int i = 0; i < movieMetadata.getLength(); i++) {
                Node node = movieMetadata.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    movieMetadatas.add(MovieMetadata.createMovieMetadata(node));
                }
            }
            return movieMetadatas;
        }
        return null;
    }

    private MovieClock parseClock(Node clock) {
        return MovieClock.createMovieClock(clock);
    }

    private Boolean parseMatrixIdentity(Node matrix) {
        return Boolean.getBoolean(matrix.getAttributes().getNamedItem(MCDXmlTagName.MovieTag.IDENTITY_ATTR_NAME.getTag()).getNodeValue());
    }
}
