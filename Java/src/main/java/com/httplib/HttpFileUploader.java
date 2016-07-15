package com.httplib;

import com.httplib.util.HttpWebDAVClient;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : pgajjar
 * @since : 7/13/16
 */
public class HttpFileUploader {
    private static Logger log = org.apache.log4j.Logger.getLogger(HttpFileUploader.class.getName());
    private final static String HTTP_HOST_WEBDAV_BASE_URL = "http://localhost/uploads/";

    @Nonnull private final HttpWebDAVClient httpWebDAVClient;

    private HttpFileUploader() {
        httpWebDAVClient = HttpWebDAVClient.newInstance(HTTP_HOST_WEBDAV_BASE_URL);
    }

    @Nonnull
    public static HttpFileUploader newInstance() {
        return new HttpFileUploader();
    }

    public boolean upload(String localFilePath, String dirName, String targetFileName) throws IOException, URISyntaxException {
        boolean result = httpWebDAVClient.put(localFilePath, dirName, targetFileName);

        if (!result) {
            log.info("Failed uploading file: " + localFilePath);
        }

        return result;
    }

    public void close() throws IOException {
        httpWebDAVClient.close();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpFileUploader uploader = HttpFileUploader.newInstance();
        System.out.println(uploader.upload("/Users/pgajjar/Data/Movies/PK.mp4", "pgajjar/example/test/firsttest/package/provider/vfcs/source/Components/files/", "PK.mp4"));
        uploader.close();
    }
}
