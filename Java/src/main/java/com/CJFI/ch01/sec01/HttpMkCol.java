package com.CJFI.ch01.sec01;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author  : pgajjar
 * @since   : 7/13/16
 */
@NotThreadSafe
public class HttpMkCol extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "MKCOL";

    public HttpMkCol() {
        super();
    }

    public HttpMkCol(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpMkCol(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}
