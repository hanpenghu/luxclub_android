package com.hsbank.util.android.util.http.okhttp.https;

/**
 * https是什么?
 * -----------------------------------------------------------------
 * https其实就是个安全版的 http.
 * https跟 http的最大区别在于https多加了一个保障通讯安全的层(SSL/TLS)
 * https通过SSL/TLS传递数据
 * <p>
 * SSL/TLS:
 * SSL(Secure Sockets Layer) 是一种在客户端跟服务器端建立一个加密连接的安全标准。
 * 一般用来加密网络服务器跟浏览器, 或者是邮件服务器跟邮件客户端(如: Outlook)之间传输的数据。
 * SSL能保障敏感信息(如:银行卡号, 社保卡号, 登陆凭证等)的传输安全。
 * SSL位于TCP/IP和HTTP协议之间，它能够：
 * 1.验证证书：			认证用户和服务器，确保数据发送到正确的客户机和服务器；
 * 2.加密数据：			以防止数据中途被窃取；
 * 3.维护数据的完整性：	（摘要算法）确保数据在传输过程中不被改变。
 * <p>
 * 什么是自签名证书( self-signed certicates)
 * 自签名证书就是没有通过受信任的证书颁发机构, 自己给自己颁发的证书
 * ---------------------------------------------------------------
 * <p>
 * #############################################################
 * 一、#########################生成证书#########################
 * #############################################################
 * 利用 JDK 自带的 keytool 即可完成。命令如下：
 * 先cmd到Java\jdk\bin目录下，然后
 * keytool -genkey -alias mayland_studio -keyalg RSA -keystore d:/mayland_studio.jks -validity 10000 -storepass mayland
 * 输入keystore密码：  mayland
 * 您的名字与姓氏是什么？
 *   [Unknown]：  wuyuan.xie
 * 您的组织单位名称是什么？
 *   [Unknown]：  mayland
 * 您的组织名称是什么？
 *  [Unknown]：  mayland
 * 您所在的城市或区域名称是什么？
 *   [Unknown]：  shanghai
 * 您所在的州或省份名称是什么？
 *   [Unknown]：  shanghai
 * 该单位的两字母国家代码是什么
 *  [Unknown]：  cn
 * CN=wuyuan.xie, OU=mayland, O=mayland, L=shanghai, ST=shanghai, C=cn 正确吗？
 *   [否]：  y
 * 输入tomcat的主密码（直接回车）
 * 证书生成完毕。生成的文件为.keytore，默认放在当前用户的$home目录下。
 * ---------------------------------------------------------------
 * 参数解释：
 * -genkey      在用户主目录中创建一个默认文件".keystore"
 * -alias       产生别名
 * -keystore    指定密钥库的文件路径名称(取代默认文件：.keystore)，一般不会在系统盘，不然会报异常，没权限写入
 * -keyalg      指定密钥的算法(如 RSA、DSA)，如果不指定默认采用DSA
 * -validity    指定创建的证书有效期（单位：天），默认是90天
 * -keysize     指定密钥长度
 * -storepass   指定密钥库的密码(获取keystore信息所需的密码)
 * -keypass     指定别名条目的密码(私钥的密码)
 * ---------------------------------------------------------------
 * <p>
 * #############################################################
 * 二、#########################查看证书#########################
 * #############################################################
 * keytool -list -v -keystore [.keystore文件路径名称] -storepass [keystore密码]
 * 举例如下：
 * ===============================================================================
 * keytool -list -v -keystore d:/mayland_studio.jks -storepass mayland
 *
 * 密钥库类型: JKS
 * 密钥库提供方: SUN
 *
 * 您的密钥库包含 1 个条目
 *
 * 别名: mayland
 * 创建日期: 2015-12-19
 * 条目类型: PrivateKeyEntry
 * 证书链长度: 1
 * 证书[1]:
 * 所有者: CN=wuyuan.xie, OU=mayland, O=mayland, L=shanghai, ST=shanghai, C=cn
 * 发布者: CN=wuyuan.xie, OU=mayland, O=mayland, L=shanghai, ST=shanghai, C=cn
 * 序列号: 3a8c0726
 * 有效期开始日期: Sat Dec 19 12:03:41 CST 2015, 截止日期: Wed May 06 12:03:41 CST 2043
 * 证书指纹:
 *          MD5: 20:C9:CC:3D:D0:60:AB:8C:85:E5:FB:D0:1A:4A:53:86
 *          SHA1: 12:E6:FA:0A:49:15:A5:E3:49:26:1D:EB:48:A8:44:8D:F7:D9:1B:B5
 *          SHA256: 42:83:14:97:65:CF:6A:B4:96:23:03:48:29:DD:75:C5:59:16:84:5D:F8:E2:E1:F3:4A:8B:7F:9D:73:2D:28:1A
 *          签名算法名称: SHA256withRSA
 *          版本: 3
 *
 * 扩展:
 *
 * #1: ObjectId: 2.5.29.14 Criticality=false
 * SubjectKeyIdentifier [
 * KeyIdentifier [
 * 0000: EC 4D 22 F7 A2 D8 E6 B8   69 33 BA 12 D9 DC 74 44  .M".....i3....tD
 * 0010: 58 F3 9A 7C                                        X...
 * ]
 * ]
 * ===================================================================================
 * <p>
 * #############################################################
 * 三、#########################签发证书#########################
 * #############################################################
 * keytool -export -alias mayland_studio -file d:/mayland_studio.cer -keystore d:/mayland_studio.jks -storepass mayland
 * 命令行下，执行上述命令，即可在指定路径下，生成包含公钥的证书文件：mayland_studio.cer
 * <p>
 * #############################################################
 * 四、#########################格式转换#########################
 * #############################################################
 * Java平台默认识别jks格式的证书文件，但是android平台只识别bks格式的证书文件,
 * 需要将jks文件转化为bks文件，转化方法，自行百度。
 * <p>
 * #############################################################
 * 五、#########################用字符串替代证书##################
 * #############################################################
 * 我们可以将证书中的内容提取出来，写成字符串常量，这样就不需要证书文件了
 * keytool -printcert -rfc -file d:/mayland_studio.cer
 * 执行上述命令，得到证书内容如下
 * -----BEGIN CERTIFICATE-----
 *  MIIDdzCCAl+gAwIBAgIEZEP1ETANBgkqhkiG9w0BAQsFADBsMQswCQYDVQQGEwJjbjERMA8GA1UE
 *  CBMIc2hhbmdoYWkxETAPBgNVBAcTCHNoYW5naGFpMRAwDgYDVQQKEwdtYXlsYW5kMRAwDgYDVQQL
 *  EwdtYXlsYW5kMRMwEQYDVQQDEwp3dXl1YW4ueGllMB4XDTE1MTIxOTA2MjUwNVoXDTQzMDUwNjA2
 *  MjUwNVowbDELMAkGA1UEBhMCY24xETAPBgNVBAgTCHNoYW5naGFpMREwDwYDVQQHEwhzaGFuZ2hh
 *  aTEQMA4GA1UEChMHbWF5bGFuZDEQMA4GA1UECxMHbWF5bGFuZDETMBEGA1UEAxMKd3V5dWFuLnhp
 *  ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAI8ojxezdMT/xfBd0/kV3sWvravj2uQ7
 *  xjmYeqkb6oE0B7p0Nv0BuIuuBx82vji2TZ0JJi/TVAPxi4stiuBHisGZJevicORg+RuetY/OgNBV
 *  bje+lNC/40mymNqu5FHx6hkMk4PCTq+w2+MDvVU5V4t/BGObqVFAVD1vkhzVCqTmlFQ1FxiMZCbb
 *  2+o1S89Ql9qVQ/b588C/U0tAF+Q9rNEWpx9UH0GF08hJK9kWLgkI7BRwR9f/w6TXj5WdinbyHIeW
 *  YaxFmNVFwbYfgJlj3YgI0BT4sPXuMrPE7FJFNMXqh2oODgNSprGuTWcmsKK2uUfopJbya8ZOBPZD
 *  LTbDUwMCAwEAAaMhMB8wHQYDVR0OBBYEFAdLFIS3LTDKPaveEIZrYtuFE4ptMA0GCSqGSIb3DQEB
 *  CwUAA4IBAQBfJgOEoL/XBBibD0AtUMUVGPpefbP6pcq81Xl1TQOPgBT0pBv8utoUb+hKebLjlObn
 *  QjYE11BV5UuHNelphU7eTbTtTpsaLInfergz0w0FM4tqJvMAPNoZGvsQUV2H45CXs+mrCRXYaTEQ
 *  XnMTc2FO+fpDPuIMJwcXV6lispKtcmPKNcLo8YU+droFquSZMY30uz9PfSoPxIevrkfQhrgKc63o
 *  ZbEkg+svZLXnSUjHG2vKl94S5PEIxqlh+Jr9nQeKySYmac3fRBNro1ti92/KTtaHZvVghBVRduk7
 *  mY30kDvCbKsan1Z/mexDdnGquJs/WhOc7LzJ1PuQ14ilveJd
 * -----END CERTIFICATE-----
 * <p>
 * @author arthur_xie
 * 2013-09-10
 */
public class OkHttpsConstant {
	/**Https证书密码*/
	public static final String HTTPS_KEY_PASSWORD = "mayland";
	/**Https证书内容*/
	public static final String HTTPS_KEY_CONTENT = "-----BEGIN CERTIFICATE-----\n" +
			"MIIDdzCCAl+gAwIBAgIEZEP1ETANBgkqhkiG9w0BAQsFADBsMQswCQYDVQQGEwJjbjERMA8GA1UE\n" +
			"CBMIc2hhbmdoYWkxETAPBgNVBAcTCHNoYW5naGFpMRAwDgYDVQQKEwdtYXlsYW5kMRAwDgYDVQQL\n" +
			"EwdtYXlsYW5kMRMwEQYDVQQDEwp3dXl1YW4ueGllMB4XDTE1MTIxOTA2MjUwNVoXDTQzMDUwNjA2\n" +
			"MjUwNVowbDELMAkGA1UEBhMCY24xETAPBgNVBAgTCHNoYW5naGFpMREwDwYDVQQHEwhzaGFuZ2hh\n" +
			"aTEQMA4GA1UEChMHbWF5bGFuZDEQMA4GA1UECxMHbWF5bGFuZDETMBEGA1UEAxMKd3V5dWFuLnhp\n" +
			"ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAI8ojxezdMT/xfBd0/kV3sWvravj2uQ7\n" +
			"xjmYeqkb6oE0B7p0Nv0BuIuuBx82vji2TZ0JJi/TVAPxi4stiuBHisGZJevicORg+RuetY/OgNBV\n" +
			"bje+lNC/40mymNqu5FHx6hkMk4PCTq+w2+MDvVU5V4t/BGObqVFAVD1vkhzVCqTmlFQ1FxiMZCbb\n" +
			"2+o1S89Ql9qVQ/b588C/U0tAF+Q9rNEWpx9UH0GF08hJK9kWLgkI7BRwR9f/w6TXj5WdinbyHIeW\n" +
			"YaxFmNVFwbYfgJlj3YgI0BT4sPXuMrPE7FJFNMXqh2oODgNSprGuTWcmsKK2uUfopJbya8ZOBPZD\n" +
			"LTbDUwMCAwEAAaMhMB8wHQYDVR0OBBYEFAdLFIS3LTDKPaveEIZrYtuFE4ptMA0GCSqGSIb3DQEB\n" +
			"CwUAA4IBAQBfJgOEoL/XBBibD0AtUMUVGPpefbP6pcq81Xl1TQOPgBT0pBv8utoUb+hKebLjlObn\n" +
			"QjYE11BV5UuHNelphU7eTbTtTpsaLInfergz0w0FM4tqJvMAPNoZGvsQUV2H45CXs+mrCRXYaTEQ\n" +
			"XnMTc2FO+fpDPuIMJwcXV6lispKtcmPKNcLo8YU+droFquSZMY30uz9PfSoPxIevrkfQhrgKc63o\n" +
			"ZbEkg+svZLXnSUjHG2vKl94S5PEIxqlh+Jr9nQeKySYmac3fRBNro1ti92/KTtaHZvVghBVRduk7\n" +
			"mY30kDvCbKsan1Z/mexDdnGquJs/WhOc7LzJ1PuQ14ilveJd\n" +
			"-----END CERTIFICATE-----";
}
