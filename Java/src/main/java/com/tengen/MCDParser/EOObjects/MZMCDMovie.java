package com.tengen.MCDParser.EOObjects;

import com.tengen.MCDParser.utils.MCDXmlTagName.MovieTag;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

/**
 * @author: pgajjar @date: 8/26/13 @time: 2:48 PM
 */
@SuppressWarnings("unused")
public class MZMCDMovie {
    // TODO: EO and fetch MEDIA_CONTAINER_TYPE_ID from MZ_MEDIA_CONTAINER_TYPE table.
    private final Integer mediaContainerTypeId = 1;
    private final String codecs;
    private final String movieType;
    private final Integer posterTime;
    private final Integer previewDuration;
    private final Integer previewTime;
    private final Integer timeScale;
    private final Integer totalDuration;
    private final Boolean matrixIdentity;

    public MZMCDMovie(Document document, MovieClock movieClock, Boolean matrixIdentity) {
        NamedNodeMap attributes = document.getDocumentElement().getAttributes();
        this.codecs = attributes.getNamedItem(MovieTag.CODECS_ATTR_NAME.getTag()).getNodeValue();
        this.movieType = attributes.getNamedItem(MovieTag.TYPE_ATTR_NAME.getTag()).getNodeValue();
        this.posterTime = movieClock.getPosterTime();
        this.previewDuration = movieClock.getPreviewDuration();
        this.previewTime = movieClock.getPreviewTime();
        this.timeScale = movieClock.getTimeScale();
        this.totalDuration = movieClock.getTotalDuration();
        this.matrixIdentity = matrixIdentity;
    }

    public Integer getMediaContainerTypeId() {
        return mediaContainerTypeId;
    }

    public String getCodecs() {
        return codecs;
    }

    public String getMovieType() {
        return movieType;
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

    public Boolean getMatrixIdentity() {
        return matrixIdentity;
    }
}
