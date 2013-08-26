package com.tengen.XmlParser.XmlObjects;

import com.tengen.XmlParser.MZMCDXmlTagName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author pgajjar @date: 8/26/13 @time: 2:49 PM
 */
public class MovieClock {
    private final Integer posterTime;
    private final Integer previewDuration;
    private final Integer previewTime;
    private final Integer timeScale;
    private final Integer totalDuration;

    private MovieClock(Node clockNode) {
        NamedNodeMap attributes = clockNode.getAttributes();
        posterTime = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.POSTER_TIME_ATTR_NAME.getTag()).getNodeValue());
        previewDuration = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.PREVIEW_DURATION_ATTR_NAME.getTag()).getNodeValue());
        previewTime = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.PREVIEW_TIME_ATTR_NAME.getTag()).getNodeValue());
        timeScale = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TIME_SCALE_ATTR_NAME.getTag()).getNodeValue());
        totalDuration = Integer.parseInt(attributes.getNamedItem(MZMCDXmlTagName.MovieTag.TOTAL_DURATION_ATTR_NAME.getTag()).getNodeValue());
    }

    public static MovieClock createMovieClock(Node clockNode) {
        return new MovieClock(clockNode);
    }

    public Integer getPosterTime() {
        return posterTime;
    }

    public Integer getPreviewDuration() {
        return previewDuration;
    }

    public Integer getPreviewTime() {
        return previewTime;
    }

    public Integer getTimeScale() {
        return timeScale;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }
}
