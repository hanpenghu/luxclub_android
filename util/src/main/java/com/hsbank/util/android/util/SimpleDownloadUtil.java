package com.hsbank.util.android.util;

import android.os.Handler;
import android.os.Message;

import com.hsbank.util.java.http.HttpsUtil;
import com.hsbank.util.java.tool.FileUtil;
import com.hsbank.util.project.util.BaseProjectCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * 从指定url下载文件：无进度提示，适用于小文件下载
 * Created by Administrator on 2016/1/12.
 */
public class SimpleDownloadUtil extends Thread {
    /**message.what：下载出错*/
    private static final int DOWNLOAD_ERROR = 1;
    /**message.what：下载完成*/
    private static final int DOWNLOAD_FINISHED = 2;
    /**下载回调接口*/
    IDownloadInterface _downloadInterface = null;
    /**下载Url*/
    String _url;
    /**本地文件保存路径名称*/
    String _downloadFilePathName;
    /**处理器*/
    private Handler _handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ERROR:
                    if (_downloadInterface != null) {
                        _downloadInterface.onError(_downloadFilePathName);
                    }
                    break;
                case DOWNLOAD_FINISHED:
                    if (_downloadInterface != null) {
                        _downloadInterface.onFinished(_downloadFilePathName);
                    }
                    break;
            }
        }
    };

    /**
     * 构造函数
     * @param url
     */
    public SimpleDownloadUtil(String url, IDownloadInterface downloadInterface) {
        this._url = url;
        this._downloadInterface = downloadInterface;
        this._downloadFilePathName = BaseProjectCache.getDownloadFilePathName(this._url);
    }

    @Override
    public void run() {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(this._url);
            if (this._url.toLowerCase(Locale.getDefault()).startsWith("https")) {
                //https
                HttpsUtil.trustAllHosts();
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(3 * 1000);
                conn.connect();
                is = conn.getInputStream();
            } else {
                //http
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(3 * 1000);
                conn.connect();
                is = conn.getInputStream();
            }
            if (is != null) {
                File file = FileUtil.createFile(this._downloadFilePathName);
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                while ((ch = is.read(buf)) != -1) {
                    fos.write(buf, 0, ch);
                }
                fos.flush();
            }
            _handler.sendEmptyMessage(DOWNLOAD_FINISHED);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            _handler.sendEmptyMessage(DOWNLOAD_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            _handler.sendEmptyMessage(DOWNLOAD_ERROR);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    _handler.sendEmptyMessage(DOWNLOAD_ERROR);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    _handler.sendEmptyMessage(DOWNLOAD_ERROR);
                }
            }
        }
    }

    /**
     * 下载回调接口
     */
    public interface IDownloadInterface {
        /**
         * 下载出错时的回调接口
         * @param filePathName
         */
        public void onError(String filePathName);

        /**
         * 下载完成时的回调接口
         * @param filePathName
         */
        public void onFinished(String filePathName);
    }
}
