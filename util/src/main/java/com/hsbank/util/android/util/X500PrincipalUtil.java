package com.hsbank.util.android.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

import javax.security.auth.x500.X500Principal;

/**
 * Android中文数字证书解析_工具类
 * <p>
 * 转自网络：http://blog.csdn.net/suntongo/article/details/38864413
 * <p>
 * 在智能移动设备信息安全倍受重视的今天，Android软件开发难以绕过的内容之一就是数字证书相关应用，其中，对数字证书的主题、颁发者主题进行解析是必不可少的功能需求。
 * Android现有的API和JDK基本兼容，X509Certificate的getSubjectDN()和getIssuerDN()就是用来干这个的，还有就是getSubjectX500Principal()和getIssuerX500Principal()，
 * 很遗憾的是这些方法得到的结果再用getName()去取得字符串时，有两个问题让人很失望：1.不能正确解释邮箱地址(E=)；2.如果字段中含有非UTF-8编码的字段，得到的是乱码。
 * 这两个问题在所有Android版本上都存在，包括被国内公司改编甚至改名字的版本(那号称进行600+处优化的版本也是如此，我很怀疑他团队里有没有谁懂一点点数字证书知识)。
 * <p>
 * 假设有一个X509Certificate的实例cert，那么：
 * String subjectName = (new X500PrincipalUtil(cert.getSubjectX500Principal())).getName();
 * String issuerName = (new X500PrincipalUtil(cert.getIssuerX500Principal())).getName();
 * 将会得到你满意的结果。
 * <p>
 * Created by Administrator on 2015/12/20.
 */
class X500PrincipalUtil implements Principal {
    final byte[][] OIDs = {{0x55, 0x04, 0x06}, {0x55, 0x04, 0x08}, {0x55, 0x04, 0x07},
            {0x55, 0x04, 0x0a}, {0x55, 0x04, 0x0b}, {0x55, 0x04, 0x03},
            {0x2a, (byte)0x86, 0x48, (byte)0x86, (byte)0xf7, 0x0d, 0x01, 0x09, 0x01}};
    final String[] DNstr = {"C","ST","L","O","OU","CN","E"};
    private ByteArrayInputStream bis = null;

    /**
     * 构造函数
     * @param xp
     */
    public X500PrincipalUtil(X500Principal xp) {
        if (xp == null) {return;}
        bis = new ByteArrayInputStream(xp.getEncoded());
    }

    private int preLen(int tag) {
        int itag;
        if (tag != -1) {
            itag = bis.read();
            if (itag != tag) return 0;
        }
        itag = bis.read();
        if (itag < 0x80) return itag;
        if (itag == 0x81) return bis.read();
        if (itag == 0x82) {
            itag = bis.read();
            itag <<= 8;
            return itag + bis.read();
        }
        return 0;
    }

    @Override
    public String getName() {
        if (bis == null) {return null;}
        byte[] oid = new byte[9];
        int oidType, valueType;
        StringBuilder sb = null;
        if (preLen(0x30) == bis.available()) {
            sb = new StringBuilder();
            while (true) {
                if (preLen(0x31) == 0) break;
                if (preLen(0x30) == 0) break;
                int len = preLen(0x06);
                if (len == 0) break;
                if (len > 9) {
                    oidType = -1;
                    bis.skip(len);
                } else {
                    bis.read(oid, 0, len);
                    for (oidType = DNstr.length -1; oidType > -1; oidType--) {
                        for (len = OIDs[oidType].length -1; len > -1; len--) {
                            if (oid[len] != OIDs[oidType][len]) break;
                        }
                        if (len < 0) break;
                    }
                }
                valueType = bis.read();
                len = preLen(-1);
                if (oidType > -1) {
                    byte[] value = new byte[len];
                    try {
                        bis.read(value);
                        if (sb.length() > 0) sb.append(',');
                        sb.append(DNstr[oidType]).append('=').append(new String(value, (valueType == 0x1e) ? "UTF-16BE" : "UTF-8"));
                    } catch (IOException ignored) {}
                } else {
                    bis.skip(len);
                }
            }
        }
        try {
            bis.close();
        } catch (IOException ignored) {
        }
        return (sb == null)||(sb.length() == 0) ? null : sb.toString();
    }
}
