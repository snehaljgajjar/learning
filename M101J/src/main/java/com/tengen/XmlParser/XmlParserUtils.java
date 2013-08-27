package com.tengen.XmlParser;

import com.google.common.collect.Lists;
import com.tengen.XmlParser.XmlObjects.MZMCDMovie;
import com.tengen.XmlParser.XmlObjects.MovieClock;
import com.tengen.XmlParser.XmlObjects.MovieMetadata;
import com.tengen.XmlParser.XmlObjects.MovieTrack;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * User: pgajjar Date: 8/22/13 Time: 5:41 PM
 */
public class XmlParserUtils {
    private static Logger log = Logger.getLogger(XmlParserUtils.class.getName());

    public static Node parse(String xmlFileName) throws XmlParseException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            dbf.setIgnoringElementContentWhitespace(true);
            Document document = dbf.newDocumentBuilder().parse(new File(xmlFileName));
//            document.getDocumentElement().normalize();

            String tagName = document.getDocumentElement().getTagName();
            if (tagName.equalsIgnoreCase(MZMCDXmlTagName.MovieTag.MOVIE_TAG_NAME.getTag())) {
                parseMovieDocument(document);
            }

            return document;
        } catch (ParserConfigurationException e) {
            throw new XmlParseException(XmlParseException.XmlParseErrorCode.XML_PARSING_ERROR, "Parse Configuration Error for file: " + xmlFileName, e);
        } catch (SAXException e) {
            throw new XmlParseException(XmlParseException.XmlParseErrorCode.XML_PARSING_ERROR, "Unable to parse file: " + xmlFileName, e);
        } catch (IOException e) {
            throw new XmlParseException(XmlParseException.XmlParseErrorCode.XML_FILE_IO_ERROR, "IO Error for file: " + xmlFileName, e);
        }
    }

    private static void parseMovieDocument(Document document) {
        Boolean matrixIdentity = false;
        MovieClock movieClock = null;
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.CLOCK_TAG_NAME.getTag())) {
                    // Clock tag parsing
                    movieClock = parseClock(node);
                } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.MATRIX_NAME.getTag())) {
                    matrixIdentity = parseMatrixIdentity(node);
                } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.METADATA_ATOMS.getTag())) {
                    Collection<MovieMetadata> movieMetadata = parseMetadataAtoms(node);
                } else if (node.getNodeName().equalsIgnoreCase(MZMCDXmlTagName.MovieTag.TRACKS_NAME.getTag())) {
                    Collection<MovieTrack> movieTracks = parseTracks(node);
                }
            }
        }

        MZMCDMovie mcdMovie = new MZMCDMovie(document, movieClock, matrixIdentity);
    }

    private static Collection<MovieTrack> parseTracks(Node track) {
        if (track != null && track.getChildNodes().getLength() > 0) {
            Collection<MovieTrack> movieTracks = Lists.newArrayList();
            NodeList tracks = track.getChildNodes();
            for (int i = 0; i < tracks.getLength(); i++) {
                Node node = tracks.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    movieTracks.add(MovieTrack.createMovieTrack(node));
                }
            }
        }
        return null;
    }

    private static Collection<MovieMetadata> parseMetadataAtoms(Node metadataAtoms) {
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

    private static MovieClock parseClock(Node clock) {
        return MovieClock.createMovieClock(clock);
    }

    private static boolean parseMatrixIdentity(Node matrix) {
        return matrix.getAttributes().getNamedItem(MZMCDXmlTagName.MovieTag.IDENTITY_ATTR_NAME.getTag()).getNodeValue().equalsIgnoreCase("true");
    }
}
