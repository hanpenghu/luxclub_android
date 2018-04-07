package com.hsbank.util.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hsbank.util.java.type.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Android数字证书_工具类
 * Created by Administrator on 2015/12/20.
 */
public class X509CertificateUtil {
    /**消息摘要算法：MD5*/
    public static final String MESSAGE_DIGEST_ALGORITHM_MD5 = "MD5";
    /**消息摘要算法：SHA1*/
    public static final String MESSAGE_DIGEST_ALGORITHM_SHA1 = "SHA1";
    /**消息摘要算法：SHA-256*/
    public static final String MESSAGE_DIGEST_ALGORITHM_SHA256 = "SHA-256";

    /**
     * 得到指定数字证书的指定算法的指纹
     * @param cert              指定数字证书
     * @param algorithm         指定算法(MD5、SHA1、SHA-256)
     * @return
     */
    public static String getFingerprint(X509Certificate cert, String algorithm) {
        String resultValue = "";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] certByteArray = cert.getEncoded();
            resultValue = ByteUtil.toHexString(md.digest(certByteArray));
            md.reset();
        } catch (CertificateEncodingException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        }
        return resultValue;
    }

    /**
     * 从上下文中得到数字证书
     * @param context   上下文对象
     * @return
     */
    public static X509Certificate[] getSignatureInfo(Context context) {
        X509Certificate[] certificates = null;
        try {
            PackageInfo pi = context
                    .getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            context.getApplicationContext().getPackageName(),
                            PackageManager.GET_SIGNATURES);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            certificates = new X509Certificate[pi.signatures.length];
            for (int i = 0; i < certificates.length; i++) {
                byte[] cert = pi.signatures[i].toByteArray();
                InputStream inStream = new ByteArrayInputStream(cert);
                certificates[i] = (X509Certificate) certificateFactory.generateCertificate(inStream);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        } catch (CertificateException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        }
        return certificates;
    }

    /**
     * 从apk文件中得到数字证书
     * @param apkFile
     * @return
     */
    public static byte[] getCertificate(File apkFile) {
        byte[] resultValue = null;
        try {
            JarFile jarFile = new JarFile(apkFile);
            JarEntry jarEntry = (JarEntry) jarFile.getEntry("AndroidManifest.xml");
            if (jarEntry == null) {
                jarFile.close();
                return null;
            }
            InputStream is = jarFile.getInputStream(jarEntry);
            byte[] buff = new byte[2048];
            while (is.read(buff, 0, buff.length) != -1) {
            }
            is.close();
            if (jarEntry.getCertificates() == null || jarEntry.getCertificates().length == 0) {
                jarFile.close();
                return null;
            }
            Certificate signer = jarEntry.getCertificates()[0];
            jarFile.close();
            resultValue = signer.getEncoded();
        } catch (CertificateEncodingException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        } catch (IOException e) {
            Log.e(X509CertificateUtil.class.getName(), e.getMessage());
        }
        return resultValue;
    }
}
