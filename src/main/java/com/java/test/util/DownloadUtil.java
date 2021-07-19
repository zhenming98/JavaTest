package com.java.test.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;

/**
 * @Author yzm
 * @Date 2020/6/29 - 12:38
 */
@Slf4j
public class DownloadUtil {

    private static final int BUFFER_SIZE = 8192;

    public static File getNetUrlHttp(String netUrl) {
        String fileName = getFileName(netUrl);
        File file = null;
        URL urlFile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("file_", fileName);
            urlFile = new URL(netUrl);
            inStream = urlFile.openStream();
            os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            log.info("文件获取错误：" + netUrl);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 把带路径的文件地址解析为真实文件名
     * /test/mp4/test.mp4 --> test.mp4
     *
     * @param url String
     */
    public static String getFileName(final String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        newUrl = newUrl.split("[?]")[0];
        String[] strings = newUrl.split("/");
        return strings[strings.length - 1];
    }

}
