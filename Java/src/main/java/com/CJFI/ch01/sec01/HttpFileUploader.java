package com.CJFI.ch01.sec01;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @author  : pgajjar
 * @since   : 7/13/16
 */
public class HttpFileUploader {
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HttpFileUploader.class.getName());
    private final CloseableHttpClient httpClient;
    public final static String HTTP_HOST_URL = "http://localhost/uploads/";

    public static class HttpMkCol extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "MKCOL";

        public HttpMkCol(String url) {
            this(URI.create(url));
        }

        public HttpMkCol(URI url) {
            this.setURI(url);
            this.setHeader("Content-Type", "text/xml" + "; charset=" + "UTF-8".toLowerCase());
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }

    public static class HttpPropFind extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "PROPFIND";

        public HttpPropFind(String url) {
            this(URI.create(url));
        }

        public HttpPropFind(URI url) {
            this.setURI(url);
            this.setHeader("Content-Type", "text/xml" + "; charset=" + "UTF-8".toLowerCase());
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }

    private HttpFileUploader() {
        httpClient = HttpClients.createDefault();
    }

    @Nonnull
    public static HttpFileUploader newInstance() {
        return new HttpFileUploader();
    }

    public boolean upload(String localFilePath, String dirName, String targetFileName) throws IOException {
        try {
            boolean result = true;

            if (!HttpFileUploaderUtil.mkdirRecursive(dirName)) {
                log.info("Failed creating directory: " + dirName + " on " + HTTP_HOST_URL);
                return false;
            }

            final String targetHttpHostFilePath = HTTP_HOST_URL + dirName + File.separator + targetFileName;
            log.info("Received an HTTP Put request for Local File: " + localFilePath + ", to Target: " + targetHttpHostFilePath);
            final HttpPut httpPut = new HttpPut(targetHttpHostFilePath);
            // add the 100 continue directive
            httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
            FileEntity fileEntity = new FileEntity(new File(localFilePath), ContentType.APPLICATION_OCTET_STREAM);
            httpPut.setEntity(fileEntity);
            HttpResponse response = httpClient.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("Received HTTP " + statusCode + ", Full Response: " + response);

            if (!HttpFileUploaderUtil.successfulResponse(statusCode)) {
                log.info("Failed uploading file: " + localFilePath + "HTTP " + statusCode + ", Full Response: " + response);
                result = false;
            }

            httpPut.completed();

            return result;
        } catch (IOException e) {
            log.info("Failed Uploding file: " + localFilePath + "Exception: " + e);
            throw e;
        }
    }

    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpFileUploader uploader = HttpFileUploader.newInstance();
        System.out.println(uploader.upload("/Users/pgajjar/Data/Movies/PK.mp4", "pgajjar/example/test/firsttest/package/provider/vfcs/source/Components/files/", "PK.mp4"));
        uploader.close();
    }
}
