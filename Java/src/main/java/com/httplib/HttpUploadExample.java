package com.httplib;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author: pgajjar
 * @since: 7/11/16
 */
public class HttpUploadExample {
    private final HttpClient httpclient = HttpClientBuilder.create().build();
    private final HttpPost httppost;

    public static class HttpIOException extends IOException {
        public HttpIOException(String message, StatusLine statusLine) throws IOException {
            String errorMessage = new StringBuilder(message).append(" HTTP response code: ").
                    append(statusLine.getStatusCode()).append(". HTTP response message: ").
                    append(statusLine.getReasonPhrase()).toString();
            throw new IOException(errorMessage);
        }
    }

    public HttpUploadExample(String uploadUrl, String fileKey, String filePath) {
        httppost = new HttpPost(uploadUrl);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            reqEntity.addPart("user", new StringBody("pgajjar"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FileBody bin = new FileBody(new File(filePath));
        reqEntity.addPart(fileKey, bin);

        httppost.setEntity(reqEntity);
    }

    public String upload() {
        String resp = null;
        System.out.println("executing request " + httppost.getRequestLine());
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String page = EntityUtils.toString(resEntity);
                System.out.println("PAGE :" + page);
            }
            System.out.println(response);
            resp = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static boolean uploadFile(String url, String fileKey, String filePath) throws Exception
    {
        HttpClient mHttpClient = HttpClientBuilder.create().build();

        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        try {

            System.out.println("start upload file");

            HttpPost httppost = new HttpPost(url);

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntity.addPart(fileKey, new FileBody(new File(filePath)));
            httppost.setEntity(multipartEntity);

            httppost.setParams(params);

            System.out.println("start...");

            HttpResponse httpResponse = mHttpClient.execute(httppost);

            String result = null;

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            System.out.println("file upload statusCode >>> " + statusCode);

            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                result = EntityUtils.toString(httpResponse.getEntity());
            }

            System.out.println("file upload result >>> " + result);

            if(result != null)
            {
                if(result.indexOf("error_response") >= 0)
                {
                    throw new Exception("File upload failed.");
                }
            }
            else
            {
                throw new Exception("Cannot get connection response");
            }

            System.out.println("file uploaded");

            return true;

        } catch (Exception e) {
            System.out.println("upload file exception");
            e.printStackTrace();
            throw e;
        }
    }


    public int httpPutUploadfile(String url, String fileKey, String filePath) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        // add the 100 continue directive
        httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntity.addPart(fileKey, new FileBody(new File(filePath)));
        httpPut.setEntity(multipartEntity);
        HttpResponse response = httpclient.execute(httpPut);
        StatusLine statusLine = response.getStatusLine();
        if (response.getEntity() != null) {
            response.getEntity().consumeContent();
        }

        int statusCode = statusLine.getStatusCode();
        //Accept both 200, and 201 for backwards-compatibility reasons
        if ((statusCode != HttpStatus.SC_CREATED) && (statusCode != HttpStatus.SC_OK)) {
            throw new HttpIOException("Failed to deploy file:", statusLine);
        }
        return statusCode;
    }


    public int httpPutUploadfile(String url, String filePath) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        // add the 100 continue directive
        httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
        FileEntity fileEntity = new FileEntity(new File(filePath), ContentType.APPLICATION_OCTET_STREAM);
        httpPut.setEntity(fileEntity);
        HttpResponse response = httpclient.execute(httpPut);
        StatusLine statusLine = response.getStatusLine();
        if (response.getEntity() != null) {
            response.getEntity().consumeContent();
        }

        int statusCode = statusLine.getStatusCode();
        //Accept both 200, and 201 for backwards-compatibility reasons
        if ((statusCode != HttpStatus.SC_CREATED) && (statusCode != HttpStatus.SC_OK)) {
            throw new HttpIOException("Failed to deploy file:", statusLine);
        }
        return statusCode;
    }



    public static void main(String[] args) throws Exception {
        final String UPLOAD_URL = "http://localhost/uploads/PK.mp4";
        HttpUploadExample uploader = new HttpUploadExample(UPLOAD_URL, "attachment_field", "/Users/pgajjar/Data/Movies/PK.mp4");
//        System.out.println(uploader.upload());
//
//        uploader.uploadFile(UPLOAD_URL, "attachment_field", "/Users/pgajjar/Data/Movies/PK.mp4");
//        System.out.println("Multipart PUT: " + uploader.httpPutUploadfile(UPLOAD_URL, "attachment_field", "/Users/pgajjar/Data/Movies/PK.mp4"));
        System.out.println("FileEntity PUT: " + uploader.httpPutUploadfile(UPLOAD_URL, "/Users/pgajjar/Data/Movies/PK.mp4"));
    }
}
