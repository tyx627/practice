package com.tyx.mypractice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 下载文件的工具类
 * Created by tyx on 2018/4/20 0020.
 */

public class DownLoadUtil {

    private static DownLoadUtil downloadUtil;

    public static DownLoadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownLoadUtil();
        }
        return downloadUtil;
    }

    /**
     * 判断文件是否已经存在指定目录下
     * 文件存在，并且文件大小一致，则认为文件存在
     * @param dataSize
     * @param targetPath
     * @return
     */
    private boolean dataIsExist(String fileName, long dataSize, String targetPath) {
        File file = new File(targetPath, fileName);
        return file.exists() && file.length() == dataSize;
    }

    /**
     * 从本地获取字符串类型数据
     * @param filePath
     */
    public String getDataFromLocal(String filePath){
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        BufferedReader in = null;
        try {
            fis = new FileInputStream(file);
            in = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = in.readLine()) != null){
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis)
                    fis.close();
                if (null != in)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public interface OnDownloadListener{
        /**
         * 下载成功
         */
        void onDownloadSuccess(String data);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

}
