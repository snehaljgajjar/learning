package com.httplib.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author  : pgajjar
 * @since   : 7/13/16
 *
 * HttpComponents 3.1 version - old version working library
 */
public final class HttpFileUploaderUtil {
    private static Logger log = org.apache.log4j.Logger.getLogger(HttpFileUploaderUtil.class.getName());

    public static boolean successfulResponse(final int responseCode) {
        // Accept both 200, and 201 for backwards-compatibility reasons
        return responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK;
    }

    private static boolean dirExists(@Nonnull final  HttpClient httpClient, @Nonnull final String dirName) throws IOException {
        HeadMethod headMethod = new HeadMethod(HttpMethodUtil.HTTP_HOST_URL + dirName);
        try {
            headMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
            int responseCode = httpClient.executeMethod(headMethod);
            return successfulResponse(responseCode);
        } finally {
            headMethod.releaseConnection();
        }
    }

    private static boolean mkdir(@Nonnull final  HttpClient httpClient, @Nonnull final String dirName) throws IOException {
        if (!dirExists(httpClient, dirName)) {
            MkColMethod mkColMethod = new MkColMethod(HttpMethodUtil.HTTP_HOST_URL + dirName);
            try {
                mkColMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
                int responseCode = httpClient.executeMethod(mkColMethod);
                return successfulResponse(responseCode);
            } finally {
                mkColMethod.releaseConnection();
            }
        } else {
            log.info(HttpMethodUtil.HTTP_HOST_URL + dirName + " exists.");
        }
        return true;
    }

    public static boolean mkdir(@Nonnull final String dirName) throws IOException {
        final  HttpClient httpClient = new HttpClient();
        final String[] dirs = dirName.split(File.separator);
        StringBuilder dirToCreate = new StringBuilder();
        for (String dir : dirs) {
            dirToCreate.append(dir + File.separator);
            if (!mkdir(httpClient, dirToCreate.toString())) {
                return false;
            }
        }
        return true;
    }
}
