package com.hsbank.util.java.constant;

/**
 * 常用字符集_枚举类
 * @author xwy
 * 2011-04-18
 */
public enum CharSet {
	/**
	 * ASCII: American Standard Code for Information Interchange，美国信息交换标准码。
	 */
	ASCII("ASCII"),
	/**
     * ISO 8859-1： 全称ISO/IEC 8859，是国际标准化组织(ISO)及国际电工委员会(IEC)联合制定的一系列8位字符集的标准
     */
    ISO_8859_1("ISO-8859-1"),
	/**
	 * UCS: Universal Character Set
	 * UTF-8: UCS Transformation Format 8-bit
	 */	
	UTF_8("UTF-8"),
	/**
	 * UCS: Universal Character Set
	 * UTF_16BE: UCS Transformation Format 16-bit
	 * BE: Big-Endian
	 */
	UTF_16BE("UTF-16BE"),
	/**
	 * UCS: Universal Character Set
	 * UTF_16LE: UCS Transformation Format 16-bit
	 * BE: Little-Endian
	 */
	UTF_16LE("UTF-16LE"),
	/**
	 * UCS: Universal Character Set
	 * UTF_16: UCS Transformation Format 16-bit
	 * 字节顺序由可选的字节顺序标记来标识
	 */
	UTF_16("UTF-16"),
    /**
     * GBK: 是简繁字符集，包括了GB字符集、BIG5字符集和一些符号，共包括21003个字符
     */
    GBK("GBK"),
    /**
     * GB2312: 是简体字符集，全称为GB2312(80)字符集，共包括国标简体汉字6763个。
     */
    GB_2312("GB2312"),
    /**
     * BIG5: 是台湾繁体字符集，共包括国标繁体汉字13053个。
     */
    BIG_5("BIG5");

    /**字符集*/
    private String value;

    /**
     * 构造函数
     * @param encoding
     *            字符集
     */
    CharSet(String encoding) {
        this.value = encoding;
    }

    /**
	 * 字符集
	 * @return 字符集
	 */
	public String value() {
		return this.value;
	}
}
