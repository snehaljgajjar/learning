package com.httplib.util;

import com.httplib.method.HttpMkCol;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
public final class HttpWebDAVClient {
    private static Logger log = org.apache.log4j.Logger.getLogger(HttpWebDAVClient.class.getName());
    private final String httpHostWebDAVBaseUrl;

    @Nonnull
    private final CloseableHttpClient httpClient;

    private HttpWebDAVClient(@Nonnull final String _httpHostWebDAVBaseUrl) {
        httpHostWebDAVBaseUrl = _httpHostWebDAVBaseUrl;
        httpClient = HttpClients.createDefault();
    }

    public static HttpWebDAVClient newInstance(@Nonnull final String _httpHostUrl) {
        return new HttpWebDAVClient(_httpHostUrl);
    }

    private static boolean successfulResponse(final int responseCode) {
        // Accept both 200, and 201 for backwards-compatibility reasons
        return responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK;
    }

    @Nonnull
    private String remoteDirPath(@Nonnull final String dirName) {
        return httpHostWebDAVBaseUrl + dirName;
    }

    @Nonnull
    private String remoteDirPath(@Nonnull final String dirName, @Nonnull final String fileName) {
        return httpHostWebDAVBaseUrl + remoteDirPathWithoutHostName(dirName, fileName);
    }

    @Nonnull
    private static String remoteDirPathWithoutHostName(@Nonnull final String dirName, @Nonnull final String fileName) {
        return dirName + File.separator + fileName;
    }

    private boolean executeMethod(@Nonnull HttpRequestBase httpRequest) {
        try {
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            httpRequest.completed();
            return successfulResponse(statusCode);
        } catch (Exception e) {
            log.info("Failed with exception: " + e);
            return false;
        }
    }

    public boolean exists(@Nonnull final String dirName) {
        return executeMethod(new HttpHead(remoteDirPath(dirName)));
    }

    public boolean mkdir(@Nonnull final String dirName) throws URISyntaxException {
        if (!exists(dirName)) {
            return executeMethod(new HttpMkCol(remoteDirPath(dirName)));
        } else {
            log.info(remoteDirPath(dirName) + " exists.");
        }
        return true;
    }

    public boolean put(@Nonnull final String localFilePath, @Nonnull final String targetDirName, @Nonnull final String targetFileName) throws IOException, URISyntaxException {
        return put(new File(localFilePath), targetDirName, targetFileName);
    }

    public boolean put(@Nonnull final File localFile, @Nonnull final String targetDirName, @Nonnull final String targetFileName) throws IOException, URISyntaxException {
        if (!localFile.exists()) {
            log.info("Local File: " + localFile.getAbsolutePath() + " doesn't exist, can't proceed upload to " + httpHostWebDAVBaseUrl);
            return false;
        }

        if (!mkdirRecursive(targetDirName)) {
            log.info("Failed creating directory: " + targetDirName + " on " + httpHostWebDAVBaseUrl);
            return false;
        }

        final String targetHttpHostFilePath = remoteDirPath(targetDirName, targetFileName);
        log.info("Received an HTTP Put request for Local File: " + localFile.getAbsolutePath() + ", to Target Path: " + targetHttpHostFilePath);

        if (!exists(remoteDirPathWithoutHostName(targetDirName, targetFileName))) {
            // add the 100 continue directive
            final HttpPut httpPut = new HttpPut(targetHttpHostFilePath);
            httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
            FileEntity fileEntity = new FileEntity(localFile, ContentType.APPLICATION_OCTET_STREAM);
            httpPut.setEntity(fileEntity);

            return executeMethod(httpPut);
        } else {
            log.info("Remote file: " + targetHttpHostFilePath + " exists");
            return true;
        }
    }

    public boolean mkdirRecursive(@Nonnull final String dirName) throws IOException, URISyntaxException {
        final String[] dirs = dirName.split(File.separator);
        StringBuilder dirToCreate = new StringBuilder();
        for (String dir : dirs) {
            dirToCreate.append(dir).append(File.separator);
            log.info("Creating directory: " + remoteDirPath(dirToCreate.toString()));
            if (!mkdir(dirToCreate.toString())) {
                log.info("Failed creating directory: " + remoteDirPath(dirToCreate.toString()));
                return false;
            }
        }
        return true;
    }

    public void close() throws IOException {
        httpClient.close();
    }
}
