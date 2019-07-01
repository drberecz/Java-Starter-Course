import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


class HttpDownloadUtility {

    private static final int BUFFER_SIZE = 4096;


    public static String downloadFile(String fileURL, String saveDir) throws IOException {

        URL url = new URL(fileURL);
            String fileName = "";

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

// always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {


            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field

                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1
                    );
                }

            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length()
                );
            }

            System.out.println(
                    "Content-Type = " + contentType);
            System.out.println(
                    "Content-Disposition = " + disposition);
            System.out.println(
                    "Content-Length = " + contentLength);
            System.out.println(
                    "fileName = " + fileName);

// opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

// opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);

            }

            outputStream.close();

            inputStream.close();

            System.out.println(
                    "File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
        return fileName;
    }
}

public class HttpDownloader {

    public static void main(String[] args) throws IOException, Exception {


        String fileURL = "https://www.otpbank.hu/static/portal/sw/pic/HK_nyar_promo/HK_Promo_nyar_744x278.jpg";
        String saveDir = "./database/";
        
        MakeDir.createDir(saveDir);
        String fileName = HttpDownloadUtility.downloadFile(fileURL, saveDir);
        
        ImageDemo display = new ImageDemo(saveDir +fileName);
        
        
    }
}


class MakeDir{

    static void createDir(String dirName) {

        File newDir = new File(dirName);
        newDir.mkdirs();
        System.out.println("Uj konytar letrehozva : ./database");
    }

}





class ImageDemo {

    public ImageDemo(final String filename) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame editorFrame = new JFrame("BANK OF GEEKS");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(filename));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                ImageIcon imageIcon = new ImageIcon(image);
                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }
}
