package Util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tjhan on 2017-05-24.
 */

public class UploadUtil {
    private static UploadUtil uploadUtil;

    public static UploadUtil getInstance() {
        if (uploadUtil == null) {
            uploadUtil = new UploadUtil();
        }
        return uploadUtil;
    }

    /**
     * 上传图片到指定url的服务器
     * 注：远程存储参数json尚未使用,返回值尚未精确为图片地址
     * @param url
     * @param json
     * @param uploadFile
     * @return
     */
    public String UploadFile(String url, String json, String uploadFile) {
        String boundary = "*****";
        String twoHyphens = "--";
        String end = "\r\n";
        String newName = "new_photo_name.jpg";
        try {
            URL uploadUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream ds =
                    new DataOutputStream(connection.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file1\";filename=\"" +
                    newName + "\"" + end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
          /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
          /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            fStream.close();
            ds.flush();
          /* 取得Response内容 */
            InputStream is = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            ds.close();
            //返回保存到服务器的图片url
            return b.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
