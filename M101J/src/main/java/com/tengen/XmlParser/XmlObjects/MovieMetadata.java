package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MZMCDXmlTagName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author: pgajjar @date: 8/26/13 @time: 4:24 PM
 */
@SuppressWarnings("unused")
public class MovieMetadata {
    // TODO: EO - the PK of MZ_MCD_MOVIE table.
    private final Integer movieId = 1;
    private final String format;
    private final String locale;
    private final String metadataType;
    private final String metadataValue;

    private MovieMetadata(Node movieMetadata) {
        NamedNodeMap attributes = movieMetadata.getAttributes();
        format = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.FORMAT_ATTR_NAME.getTag()).getNodeValue();
        locale = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.LOCALE_ATTR_NAME.getTag()).getNodeValue();
        metadataType = attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TYPE_ATTR_NAME.getTag()).getNodeValue();
        metadataValue = movieMetadata.getTextContent();
    }

    public static MovieMetadata createMovieMetadata(Node node) {
        return new MovieMetadata(node);
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getFormat() {
        return format;
    }

    public String getLocale() {
        return locale;
    }

    public String getMetadataType() {
        return metadataType;
    }

    public String getMetadataValue() {
        return metadataValue;
    }
}
