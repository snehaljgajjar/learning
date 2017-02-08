package com.httplib.method;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import javax.annotation.concurrent.NotThreadSafe;
import java.net.URI;

/**
 * @author  : pgajjar
 * @since   : 2/7/17
 */
@NotThreadSafe
public class HttpMove extends HttpEntityEnclosingRequestBase
{
    public static final String METHOD_NAME = "MOVE";

    public HttpMove(URI sourceUrl, URI destinationUrl)
    {
        this.setHeader("Destination", destinationUrl.toString());
        this.setHeader("Overwrite", "T");
        this.setURI(sourceUrl);
    }

    public HttpMove(String sourceUrl, String destinationUrl)
    {
        this(URI.create(sourceUrl), URI.create(destinationUrl));
    }

    @Override
    public String getMethod()
    {
        return METHOD_NAME;
    }
}
