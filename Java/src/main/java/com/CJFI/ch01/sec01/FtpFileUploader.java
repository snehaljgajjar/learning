package com.CJFI.ch01.sec01;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: pgajjar
 * @since: 7/12/16
 */
public class FtpFileUploader {
    private final String server;
    private final int port;
    private final String user;
    private final String pass;

    private final FTPClient ftpClient;

    private FtpFileUploader(String _server, int _port, String _user, String _pass) throws IOException {
        ftpClient = new FTPClient();
        server = _server;
        port = _port;
        user = _user;
        pass = _pass;

        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();

    }

    public static FtpFileUploader newUploader(String _server, int _port, String _user, String _pass) throws IOException {
        return new FtpFileUploader(_server, _port, _user, _pass);
    }

    public void upload(String localFilePath, String remoteFilePath) throws IOException {
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(localFilePath);

            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }

            // APPROACH #2: uploads second file using an OutputStream
//            File secondLocalFile = new File("E:/Test/Report.doc");
//            String secondRemoteFile = "test/Report.doc";
//            inputStream = new FileInputStream(secondLocalFile);
//
//            System.out.println("Start uploading second file");
//            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
//            byte[] bytesIn = new byte[4096];
//            int read = 0;
//
//            while ((read = inputStream.read(bytesIn)) != -1) {
//                outputStream.write(bytesIn, 0, read);
//            }
//            inputStream.close();
//            outputStream.close();
//
//            boolean completed = ftpClient.completePendingCommand();
//            if (completed) {
//                System.out.println("The second file is uploaded successfully.");
//            }

        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();

            }
        }
    }

    public static void main(String[] args) {
        try {
            FtpFileUploader uploader = FtpFileUploader.newUploader("localhost", 21, "pgajjar", "Aarav_2011");
            uploader.upload("/Users/pgajjar/Data/Movies//**/PK.mp4", "/tmp/PradipGajjarUploadTest.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
