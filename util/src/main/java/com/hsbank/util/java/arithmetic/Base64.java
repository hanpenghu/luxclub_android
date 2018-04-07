package com.hsbank.util.java.arithmetic;

import com.hsbank.util.java.constant.CharSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base64编解码算法
 * <p>
 * 因为用到了“com.sun.”开头的类，是Sun公司的专用 API，可能会在未来版本中删除
 * 所以可能会出现如下警告：
 * Access restriction: The type BASE64Encoder is not accessible due to restriction on required library C:\Java\...\lib\rt.jar
 * 解决方法：
 * 这个是eclipse的设置问题，它默认把这些受访问限制的API设成了ERROR，你只要把
 * Windows -> Preferences -> Java -> Complicer -> Errors/Warnings
 * 里面的Deprecated and restricted API中的Forbidden references(access rules)选为Warning就可以编译通过了。
 * <p>
 * Base64严格地说，属于编码格式，而非加密算法。它的编码、解码操作是可逆的。
 * <p>
 * <li>什么是Base64？<br>
 * 按照RFC2045的定义，Base64被定义为：<br>
 * Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。<br>
 * （The Base64 Content-Transfer-Encoding is designed to represent arbitrary
 *  sequences of octets in a form that need not be humanly readable.）
 * <p>
 * <li>为什么要使用Base64？<br>
 * 在设计这个编码的时候，我想设计人员最主要考虑了3个问题：<br>
 * 1.是否加密？<br>
 * 2.加密算法复杂程度和效率 <br>
 * 3.如何处理传输？<br>
 * 加密是肯定的，但是加密的目的不是让用户发送非常安全的Email。这种加密方式主要就是“防君子不防小人”。
 * 即达到一眼望去完全看不出内容即可。基于这个目的加密算法的复杂程度和效率也就不能太大和太低。和上一个
 * 理由类似，MIME协议等用于发送Email的协议解决的是如何收发Email，而并不是如何安全的收发Email。因
 * 此算法的复杂程度要小，效率要高，否则因为发送Email而大量占用资源，路就有点走歪了。但是，如果是基于
 * 以上两点，那么我们使用最简单的恺撒法即可，为什么Base64看起来要比恺撒法复杂呢？这是因为在Email的
 * 传送过程中，由于历史原因，Email只被允许传送ASCII字符，即一个8位字节的低7位。因此，如果您发送了
 * 一封带有非ASCII字符（即字节的最高位是1）的Email通过有“历史问题”的网关时就可能会出现问题。网关
 * 可能会把最高位置为0。很明显，问题就这样产生了！因此，为了能够正常的传送Email，这个问题就必须考虑！
 * 所以，单单靠改变字母的位置的恺撒之类的方案也就不行了。关于这一点可以参考RFC2046。基于以上的一些
 * 主要原因产生了Base64编码。 
 * <p>
 * <li>算法详解 <br>
 * Base64编码要求把3个8位字节（3*8=24）转化为4个6位的字节（4*6=24），之后在6位的前面补两个0，
 * 形成8位一个字节的形式。 具体转化形式如下:<pre>
 * 字符串“张3”
 * 11010101   11000101   00110011
 * 00110101   00011100   00010100   00110011</pre>
 * 可以这么考虑：把8位的字节连成一串110101011100010100110011<br>
 * 然后每次从右到左顺序选6个出来之后再把这6个二进制数前面再添加两个0，就成了一个新的字节。之后再选出6个来，
 * 再添加0，依此类推，直到24个二进制数全部被选完。 <br>
 * 让我们来看看实际结果：<pre>
 * 字符串“张3”
 * 11010101 HEX:D5   11000101 HEX:C5   00110011 HEX:33
 * 00110101          00011100          00010100           00110011
 * 字符5              字符^\            字符^T              字符3
 * 十进制53           十进制28           十进制20            十进制51</pre>
 * 这样“张3”这个字符串就被Base64表示为“5^\^T3”了么？。错！Base64编码方式并不是单纯利用转化完的内容进行
 * 编码。像‘^\’字符是控制字符，并不能通过计算机显示出来，在某些场合就不能使用了。Base64有其自身的编码表，
 * 如下：<br><pre>
 *                      The Base64 Alphabet
 * Value/Encoding   Value/Encoding   Value/Encoding   Value/Encoding
 *  0     A          17    R          34    i          51    z
 *  1     B          18    S          35    j          52    0
 *  2     C          19    T          36    k          53    1
 *  3     D          20    U          37    l          54    2
 *  4     E          21    V          38    m          55    3
 *  5     F          22    W          39    n          56    4
 *  6     G          23    X          40    o          57    5
 *  7     H          24    Y          41    p          58    6
 *  8     I          25    Z          42    q          59    7
 *  9     J          26    a          43    r          60    8
 *  10    K          27    b          44    s          61    9
 *  11    L          28    c          45    t          62    +
 *  12    M          29    d          46    u          63    /
 *  13    N          30    e          47    v          (pad) =
 *  14    O          31    f          48    w
 *  15    P          32    g          49    x
 *  16    Q          33    h          50    y</pre>
 * 这也是Base64名称的由来，而Base64编码的结果不是根据算法把编码变为高两位是0而低6位代表数据，而是变为了上表
 * 的形式，如“A”就有7位，而“a”就只有6位。表中，编码的编号对应的是得出的新字节的十进制值。因此，从上表中可以得
 * 到对应的Base64编码：<pre>
 * 字符串“张3”
 * 11010101 HEX:D5     11000101 HEX:C5     00110011 HEX:33
 * 00110101            00011100            00010100            00110011
 * 字符5                字符^\              字符^T               字符3
 * 十进制53             十进制28             十进制20             十进制51
 * 字符1                字符c               字符U                字符z</pre>
 * 这样，字符串“张3”经过编码后就成了字符串“1cUz”了。 <br>
 * Base64将3个字节转变为4个字节，因此，编码后的代码量（以字节为单位，下同）约比编码前的代码量多了1/3。之所以说
 * 是“约”，是因为如果代码量正好是3的整数倍，那么自然是多了1/3。但如果不是呢？ 细心的人可能已经注意到了，
 * 在The Base64 Alphabet中的最后一个有一个(pad) =字符。这个字符的目的就是用来处理这个问题的。当代码量不是3的
 * 整数倍时，代码量/3的余数自然就是2或者1。转换的时候，结果不够6位的用0来补上相应的位置，之后再在6位的前面补两个0。
 * 转换完空出的结果就用就用“=”来补位。譬如结果若最后余下的为2个字节的“张”：<pre>
 * 字符串“张”
 * 11010101 HEX:D5    11000101 HEX:C5
 * 00110101           00011100          00010100
 * 十进制53            十进制28           十进制20          pad
 * 字符1               字符c             字符U             字符=</pre>
 * 这样，最后的2个字节被整理成了“1cU=”。<br>
 * 同理，若原代码只剩下一个字节，那么将会添加两个“=”。只有这两种情况，所以，Base64的编码最多会在编码结尾有两个“=”
 * 至于将Base64的解码，只是一个简单的编码的逆过程，
 * <p>
 * 值得注意的是，MIME规定一行最多76个字符。<br>
 * <p>
 * CreateDate 2006-12-27
 *
 * @author wuyuan.xie
 * @version 1.0
 *
 */
public class Base64 {
    /**私有构造函数*/
    private Base64() {
    }

    /**
     * 编码
     * @param byteArray 	待编码的字节数组
     * @return String 		编码后的base64字符串
     */
    public static String encode(byte[] byteArray) {
        String s = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
        //编码出来的结果有可能会多出一个回车换行符
        //下面的操作就是去掉这个多出的回车换行符
        Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }

    /**
     * 用默认字符集编码
     * @param s 			待编码的字符串
     * @return String 		编码后的base64字符串
     */
    public static String encode(String s) {
        return encode(s.getBytes());
    }

    /**
     * 用指定字符集编码
     * @param s 			待编码的字符串
     * @param encoding		指定字符集
     * @return String 		编码后的base64字符串
     */
    public static String encode(String s, String encoding) {
        String resultValue = "";
        try {
            resultValue = encode(s.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultValue;
    }

    /**
     * 用指定字符集编码
     * @param s 			待编码的字符串
     * @param cs			指定字符集
     * @return				编码后的字符串
     */
    public static String encode(String s, CharSet cs) {
        return encode(s, cs.value());
    }

    /**
     * 用指定字符集编码: gbk
     * @param s 			待编码的字符串
     * @return				编码后的字符串
     */
    public static String encodeGBK(String s) {
        return encode(s, CharSet.GBK.value());
    }

    /**
     * 用指定字符集编码: utf-8
     * @param s 			待编码的字符串
     * @return				编码后的字符串
     */
    public static String encodeUTF8(String s) {
        return encode(s, CharSet.UTF_8.value());
    }

    /**
     * 解码
     * @param s					待编码的字符串
     * @return					解码后的字符串
     */
    public static byte[] decode(String s) {
        return android.util.Base64.decode(s, android.util.Base64.DEFAULT);
    }

    /**
     * 用指定字符集解码
     * @param s 			待解码的base64字符串
     * @param encoding 		字符集
     * @return		 		解码后的字符串
     */
    public static String decode(String s, String encoding) {
        String returnValue = null;
        byte[] byteArray = decode(s);
        if (encoding == null || "".equals(encoding)) {
            returnValue = new String(byteArray);
        } else {
            try {
                returnValue = new String(byteArray, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    /**
     * 用指定字符集解码
     * @param s 			待解码的字符串
     * @param cs 			字符集
     * @return		 		解码后的字符串
     */
    public static String decode(String s, CharSet cs) {
        return decode(s, cs.value());
    }

    /**
     * 用指定字符集解码: gbk
     * @param s 			待解码的字符串
     * @return		 		解码后的字符串
     */
    public static String decodeGBK(String s) {
        return decode(s, CharSet.GBK.value());
    }

    /**
     * 用指定字符集解码: utf-8
     * @param s 			待解码的字符串
     * @return		 		解码后的字符串
     */
    public static String decodeUTF8(String s) {
        return decode(s, CharSet.UTF_8.value());
    }

    /**
     * 编码Url
     * @param s				待编码的字符串
     * @return				编码后的字符串
     */
    public static String encodeUrl(String s) {
        String r = encode(s);
        return r == null ? "" : r.replaceAll("\\+", "!").replaceAll("/", "-");
    }

    /**
     * 解码Url
     * @param s				    待解码的字符串
     * @return				    解码后的字符串
     */
    public static String decodeUrl(String s) {
        return decode(s.replaceAll("-", "/").replaceAll("\\!", "+"), "");
    }

    /**
     * 将指定文件的内容编码成Base64字符串
     * @param filePathName      文件路径名称
     * @return
     */
    public static String encodeFile(String filePathName) {
        String resultValue = "";
        try {
            File f = new File(filePathName);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[(int)f.length()];
            fis.read(buffer);
            fis.close();
            resultValue = android.util.Base64.encodeToString(buffer, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultValue;
    }
}
