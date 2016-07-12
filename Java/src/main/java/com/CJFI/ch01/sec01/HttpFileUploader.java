package com.CJFI.ch01.sec01;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author: pgajjar
 * @since: 7/11/16
 */
public class HttpFileUploader {
    private final HttpClient httpclient = HttpClientBuilder.create().build();
    private final HttpPost httppost;

    public HttpFileUploader(String uploadUrl, String fileKey, String filePath) {
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

    public static boolean uploadFile(String url, String fileKey, File file) throws Exception
    {
        HttpClient mHttpClient = HttpClientBuilder.create().build();

        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        try {

            System.out.println("strart upload file");

            HttpPost httppost = new HttpPost(url);

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntity.addPart(fileKey, new FileBody(file));
            httppost.setEntity(multipartEntity);

            httppost.setParams(params);

            System.out.println("strart...");

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

    public static void main(String[] args) {
        final String UPLOAD_URL = "http://localhost";
        HttpFileUploader uploader = new HttpFileUploader(UPLOAD_URL, "attachment_field", "/Users/pgajjar/Data/Movies/PK.mp4");
        System.out.println(uploader.upload());
    }
}
