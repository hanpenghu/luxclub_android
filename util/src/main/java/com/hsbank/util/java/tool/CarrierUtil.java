package com.hsbank.util.java.tool;

import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.type.StringUtil;

/**
 * 定义了与电信运营商有关的一些操作方法
 * @author Arthur.Xie
 * CreateDate 2008-11-11 
 */
public class CarrierUtil {
	/**电信运营商： 中国移动*/
    public static final String CARRIER_CODE_OF_CMCC = "cmcc";
    public static final String CARRIER_NAME_OF_CMCC = "中国移动";
    /**电信运营商： 中国联通*/
    public static final String CARRIER_CODE_OF_CUTC = "cutc";
    public static final String CARRIER_NAME_OF_CUTC = "中国联通";
    /**电信运营商： 中国电信*/
    public static final String CARRIER_CODE_OF_CTCC = "ctcc";
    public static final String CARRIER_NAME_OF_CTCC = "中国电信";
	/**键值对*/
	public static final String CARRIERS = "cmcc|中国移动,cutc|中国联通,ctcc|中国电信";
    /**默认的input对象的名称*/
    public static final String DEFAULT_INPUT_NAME= "carrier_code";
	
	/**
	 * 得到运营商名称: 如cmcc-->中国移动;cutc-->中国联通;ctcc-->中国电信
	 * @param code
	 * @return String
	 */
	public static String getName(String code) {
		return StringUtil.dealString(JavaUtil.getMap(CARRIERS).get(code));
	}
	
	/**
	 * 得到一个下拉框的OPTION列表
	 * @param selectedCode
	 * @return
	 */
	public static String getOptions(String selectedCode) {
		return JavaUtil.getOptions(CARRIERS, selectedCode);
	}
	
	/**
	 * 得到与电信运营商有关的一组单选框
	 * @param inputName
	 * @param checkedValue
	 * @return String
	 */
	public static String getRadios(String inputName, String checkedValue) {
		return JavaUtil.getRadios(inputName, CARRIERS, checkedValue);
	}
	
	/**
	 * 得到与电信运营商有关的一组单选框
	 * @param checkedValue
	 * @return String
	 */
	public static String getRadios(String checkedValue) {
		return JavaUtil.getRadios(DEFAULT_INPUT_NAME, CARRIERS, checkedValue);
	}
	
	/**
	 * 得到与电信运营商有关的一组复选框
	 * @param inputName
	 * @param checkedValues
	 * @return String
	 */
	public static String getCheckBoxs(String inputName, String checkedValues) {
		return JavaUtil.getCheckBoxs(inputName, CARRIERS, checkedValues);
	}
	
	/**
	 * 得到与电信运营商有关的一组单选框
	 * @param checkedValues
	 * @return String
	 */
	public static String getCheckBoxs(String checkedValues) {
		return JavaUtil.getCheckBoxs(DEFAULT_INPUT_NAME, CARRIERS, checkedValues);
	}

	/**
	 * 根据IMSI得到移动运营商名称
	 * IMSI：International Mobile Subscriber IdentificationNumber, 国际移动用户识别码）
	 * 是国际上为唯一识别一个移动用户所分配的号码，储存在SIM卡中。
	 * IMSI共有15位，其结构如右：MCC + MNC + MIN
	 * MCC：Mobile Country Code，移动用户所属国家代号，共3位，中国为460;
	 * MNC：Mobile Network Code，移动网络码，共2位，用于识别移动用户所归属的移动通信网;
	 * MIN：是移动用户识别码，用以识别某一移动通信网中的移动用户。共有10位，其结构如右：09 + M0M1M2M3 + ABCD
	 *      其中的M0M1M2M3和MDN号码中的H0H1H2H3可存在对应关系，ABCD四位为自由分配。
	 * IMSI在MIN号码前加了MCC，可以区别出每个用户的来自的国家，因此可以实现国际漫游。在同一个国家内，如果有多个运营商，可以通过MNC来进行区别.
	 * 从技术上讲，IMSI可以彻底解决国际漫游问题。但是由于北美目前仍有大量的AMPS系统使用MIN号码，
	 * 且北美的MDN和MIN采用相同的编号，系统已经无法更改，所以目前国际漫游暂时还是以MIN为主。
	 * 其中以O和1打头的MIN资源称为IRM(International Roaming MIN)，
	 * 由IFAST (International Forum on ANSI-41 Standards Technology)统一管理。
	 * 可以看出，随着用户的增长，用于国际漫游的MIN资源将很快耗尽，全球统一采用IMSI标识用户势在必行.
	 * @param imsi
	 * @return
	 */
	public static String getNameByImsi(String imsi) {
		String resultValue = "";
		imsi = StringUtil.dealString(imsi);
		if (!"".equals(imsi)) {
			//IMSI号前面3位460是国家代号，紧接着后面2位是移动网络码:00或02是中国移动，01是中国联通，03是中国电信。
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				resultValue = CARRIER_NAME_OF_CMCC;
			} else if (imsi.startsWith("46001")) {
				resultValue = CARRIER_NAME_OF_CUTC;
			} else if (imsi.startsWith("46003")) {
				resultValue = CARRIER_NAME_OF_CTCC;
			}
		}
		return resultValue;
	}
}
