package com.httplib.util;

import com.httplib.method.HttpMkCol;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author  : pgajjar
 * @since   : 7/13/16
 *
 * HttpComponents 4.5.1 version - old version working library
 */
public final class HttpMethodUtil {
    private static Logger log = org.apache.log4j.Logger.getLogger(HttpMethodUtil.class.getName());
    public final static String HTTP_HOST_URL = "http://localhost/uploads/";

    public static boolean successfulResponse(final int responseCode) {
        // Accept both 200, and 201 for backwards-compatibility reasons
        return responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK;
    }

    @Nonnull
    public static String remoteDirPath(@Nonnull final String dirName) {
        return HTTP_HOST_URL + dirName;
    }

    @Nonnull
    public static String remoteDirPath(@Nonnull final String dirName, @Nonnull final String fileName) {
        return HTTP_HOST_URL + remoteDirPathWithoutHostName(dirName, fileName);
    }

    @Nonnull
    public static String remoteDirPathWithoutHostName(@Nonnull final String dirName, @Nonnull final String fileName) {
        return dirName + File.separator + fileName;
    }

    private static boolean executeMethod(@Nonnull final HttpClient httpClient, @Nonnull HttpRequestBase httpRequest) {
        try {
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            httpRequest.completed();
            return successfulResponse(statusCode);
        } catch (Exception e) {
            log.info("Failed with exception: " + e);
            return false;
        }
    }

    public static boolean exists(@Nonnull final HttpClient httpClient, @Nonnull final String dirName) {
        return executeMethod(httpClient, new HttpHead(remoteDirPath(dirName)));
    }

    public static boolean mkdir(@Nonnull final HttpClient httpClient, @Nonnull final String dirName) throws URISyntaxException {
        if (!exists(httpClient, dirName)) {
            return executeMethod(httpClient, new HttpMkCol(remoteDirPath(dirName)));
        } else {
            log.info(remoteDirPath(dirName) + " exists.");
        }
        return true;
    }

    public static boolean put(@Nonnull final HttpClient httpClient, @Nonnull final String localFilePath, @Nonnull final String targetDirName, @Nonnull final String targetFileName) throws IOException, URISyntaxException {
        if (!mkdirRecursive(httpClient, targetDirName)) {
            log.info("Failed creating directory: " + targetDirName + " on " + HTTP_HOST_URL);
            return false;
        }

        final String targetHttpHostFilePath = remoteDirPath(targetDirName, targetFileName);
        log.info("Received an HTTP Put request for Local File: " + localFilePath + ", to Target Path: " + targetHttpHostFilePath);

        if (!exists(httpClient, remoteDirPathWithoutHostName(targetDirName, targetFileName))) {
            // add the 100 continue directive
            final HttpPut httpPut = new HttpPut(targetHttpHostFilePath);
            httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
            FileEntity fileEntity = new FileEntity(new File(localFilePath), ContentType.APPLICATION_OCTET_STREAM);
            httpPut.setEntity(fileEntity);

            return executeMethod(httpClient, httpPut);
        } else {
            log.info("Remote file: " + targetHttpHostFilePath + " exists");
            return true;
        }
    }

    public static boolean mkdirRecursive(@Nonnull final HttpClient httpClient, @Nonnull final String dirName) throws IOException, URISyntaxException {
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
        return true;
    }
}
