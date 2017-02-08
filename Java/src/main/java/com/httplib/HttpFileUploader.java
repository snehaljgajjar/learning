package com.httplib;

import com.httplib.util.HttpWebDAVClient;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : pgajjar
 * @since  : 7/13/16
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
            log.fatal("Failed uploading file: " + localFilePath);
        }

        return result;
    }

    public boolean move(@NonNull final String srcDir, @NonNull final String srcFileName, @NonNull final String dstDir, @NonNull final String dstFileName) throws IOException, URISyntaxException {
        boolean result = httpWebDAVClient.move(srcDir, srcFileName, dstDir, dstFileName);

        if (!result) {
            log.fatal("Failed moving file: " + HttpWebDAVClient.remoteDirPathWithoutHostName(srcDir, srcFileName) + " to " + dstDir + File.separator + dstFileName);
        }

        return result;
    }

    public void close() throws IOException {
        httpWebDAVClient.close();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpFileUploader uploader = HttpFileUploader.newInstance();
        System.out.println("Uploaded: " + uploader.upload("/Users/pgajjar/Data/Movies/PK.mp4", "pgajjar/example/test/firsttest/package/provider/vfcs/source/Components/files/", "PK.mp4"));
        System.out.println("Moved: " + uploader.move("pgajjar/example/test/firsttest/package/provider/vfcs/source/Components/files/", "PK.mp4", "move_worked/example/test/firsttest/package/provider/vfcs/source/Components/files/", "PK.mp4"));
        uploader.close();
    }
}
