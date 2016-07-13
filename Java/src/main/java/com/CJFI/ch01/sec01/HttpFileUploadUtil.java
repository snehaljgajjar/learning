package com.CJFI.ch01.sec01;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author  : pgajjar
 * @since   : 7/13/16
 */
public final class HttpFileUploadUtil {
    private static Logger log = org.apache.log4j.Logger.getLogger(HttpFileUploadUtil.class.getName());

    public static boolean successfulResponse(final int responseCode) {
        // Accept both 200, and 201 for backwards-compatibility reasons
        return responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK;
    }

    @Nonnull
    private static String remoteDirPath(@Nonnull final String dirName) {
        return HttpFileUploader.HTTP_HOST_URL + dirName;
    }

    private static boolean executeWebDAVMethod(@Nonnull final HttpClient httpClient, @Nonnull HttpRequestBase httpRequest) {
        try {
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            return successfulResponse(statusCode);
        } catch (Exception e) {
            log.info("Failed with exception: " + e);
            return false;
        }
    }

    public static boolean dirExists(@Nonnull final HttpClient httpClient, @Nonnull final String dirName) {
        return executeWebDAVMethod(httpClient, new HttpHead(remoteDirPath(dirName)));
    }

    public static boolean mkdir(@Nonnull final HttpClient httpClient, @Nonnull final String dirName) throws URISyntaxException {
        if (!dirExists(httpClient, dirName)) {
            return executeWebDAVMethod(httpClient, new HttpMkCol(remoteDirPath(dirName)));
        } else {
            log.info(remoteDirPath(dirName) + " exists.");
        }
        return true;
    }

    public static boolean mkdirRecursive(@Nonnull final String dirName) throws IOException, URISyntaxException {
        @Nonnull final CloseableHttpClient httpClient = HttpClients.createDefault();
        final String[] dirs = dirName.split(File.separator);
        StringBuilder dirToCreate = new StringBuilder();
        for (String dir : dirs) {
            dirToCreate.append(dir).append(File.separator);
            log.info("Creating directory: " + remoteDirPath(dirToCreate.toString()));
            if (!mkdir(httpClient, dirToCreate.toString())) {
                log.info("Failed creating directory: " + remoteDirPath(dirToCreate.toString()));
                return false;
            }
        }
        httpClient.close();
        return true;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println(HttpFileUploadUtil.mkdirRecursive("pgajjar1/test/temp/example/user/package/provider/vfcs/"));
    }
}
