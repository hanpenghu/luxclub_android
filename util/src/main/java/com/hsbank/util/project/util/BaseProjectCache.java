package com.hsbank.util.project.util;

import android.os.Environment;

import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.http.UrlUtil;
import com.hsbank.util.java.tool.FileUtil;

/**
 * 本地缓存相关常量类
 * Created by Administrator on 2015/12/21.
 */
public class BaseProjectCache {
    //---------------------------------存放本地文件的路径----------------------
    /**根路径*/
    public static final String FILE_PATH_ROOT = Environment.getExternalStorageDirectory().getPath() + "/" + AndroidAppInfo.getInstance().getPackageName();
    /**图片路径*/
    public static final String FILE_PATH_IMAGE = FILE_PATH_ROOT + "/image/";
    /**相片路径*/
    public static final String FILE_PATH_PHOTO = FILE_PATH_ROOT + "/photo/";
    /**更新文件路径*/
    public static final String FILE_PATH_UPDATE = FILE_PATH_ROOT + "/update/";
    /**下载文件路径*/
    public static final String FILE_PATH_DOWNLOAD = FILE_PATH_ROOT + "/download/";
    /**更新文件命名规则*/
    public static final String FILE_NAME_UPDATE = "%s_update.apk";

    /**
     * 得到一个新的更新文件的名称
     * @return
     */
    public static String getUpdateFileName() {
        return String.format(FILE_NAME_UPDATE, JavaUtil.getFileNamePrefix());
    }

    /**
     * 得到一个新的更新文件的路径名称
     * @return
     */
    public static String getUpdateFilePathName() {
        FileUtil.createDir(FILE_PATH_UPDATE);
        return FILE_PATH_UPDATE + getUpdateFileName();
    }

    /**
     * 得到一个新的下载文件的名称
     * @param url
     * @return
     */
    public static String getDownloadFileName(String url) {
        String fileName = UrlUtil.getFileName(url);
        return JavaUtil.getFileNamePrefix() + "_" + fileName;
    }

    /**
     * 得到一个新的下载文件的路径名称
     * @param url
     * @return
     */
    public static String getDownloadFilePathName(String url) {
        FileUtil.createDir(FILE_PATH_DOWNLOAD);
        return FILE_PATH_DOWNLOAD + getDownloadFileName(url);
    }
}