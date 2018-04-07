package com.hsbank.util.java.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 农历_公共类
 * @author xwy
 * 2011-03-31
 */
public class LunarCalendarUtil {
	/**农历信息数组*/
	final private static long[] LUNAR_CALENDAR_INFO = new long[] {0x04bd8, 0x04ae0, 0x0a570,
			0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
			0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
			0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
			0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
			0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
			0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
			0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
			0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
			0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
			0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
			0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
			0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
			0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
			0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
			0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
			0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
			0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
			0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };
	final private static int[] YEAR_20 = new int[] {1, 4, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 };
	final private static int[] YEAR_19 = new int[] {0, 3, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 };
	final private static int[] YEAR_2000 = new int[] {0, 3, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 };
	/**天干*/
	private static final String[] TIAN_GAN = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
	/**地支*/
	private static final String[] DI_ZHI = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
	/**十二生肖*/
	private static final String[] SHENG_XIAO = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
	
	/**
	 * 得到指定农历年的天数
	 * @param y
	 * @return
	 */
	final private static int getDayCountOfYear(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((LUNAR_CALENDAR_INFO[y - 1900] & i) != 0) {
				sum += 1;
			}
		}
		return (sum + getDayCountOfLeapMonth(y));
	}

	/**
	 * 得到指定农历y年闰月的天数
	 * @param y
	 * @return
	 */
	final private static int getDayCountOfLeapMonth(int y) {
		int returnValue = 0;
		if (getLeapMonth(y) != 0) {
			if ((LUNAR_CALENDAR_INFO[y - 1900] & 0x10000) != 0) {
				returnValue = 30;
			} else {
				returnValue = 29;
			}
		}
		return returnValue;
	}

	/**
	 * 得到指定农历y年闰哪个月 1-12, 没有闰月的返回 0
	 * @param y
	 * @return
	 */
	final private static int getLeapMonth(int y) {
		return (int)(LUNAR_CALENDAR_INFO[y - 1900] & 0xf);
	}

	/**
	 * 得到指定农历年指定月份的天数
	 * @param y
	 * @param m
	 * @return
	 */
	final private static int getDayCountOfMonth(int y, int m) {
		int returnValue = 30;
		if ((LUNAR_CALENDAR_INFO[y - 1900] & (0x10000 >> m)) == 0) {
			returnValue = 29;
		}
		return returnValue;
	}

	/**
	 * 得到指定农历y年的生肖
	 * @param y
	 * @return
	 */
	final public static String getShengXiao(int y) {
		return SHENG_XIAO[(y - 4) % 12];
	}

	/**
	 * 传入月日的offset 传回干支,0=甲子
	 * @param num
	 * @return
	 */
	final private static String cyclicalm(int num) {
		return (TIAN_GAN[num % 10] + DI_ZHI[num % 12]);
	}

	/**
	 * 传入 offset 传回干支, 0=甲子
	 * @param y
	 * @return
	 */
	final public static String cyclical(int y) {
		int num = y - 1900 + 36;
		return (cyclicalm(num));
	}

	/**
	 * 传出农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	 * @param y
	 * @param m
	 * @return
	 */
	protected final long[] Lunar(int y, int m) {
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		//Date baseDate = new Date(1900, 1, 31);
		Date baseDate = new GregorianCalendar(1900+1900,1,31).getTime();
		//Date objDate = new Date(y, m, 1);
		Date objDate = new GregorianCalendar(y+1900,m,1).getTime();
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		if (y < 2000)
			offset += YEAR_19[m - 1];
		if (y > 2000)
			offset += YEAR_20[m - 1];
		if (y == 2000)
			offset += YEAR_2000[m - 1];
		nongDate[5] = offset + 40;
		nongDate[4] = 14;

		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = getDayCountOfYear(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = getLeapMonth(i);// 闰哪个月
		nongDate[6] = 0;

		for (i = 1;i < 13 && offset > 0; i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = getDayCountOfLeapMonth((int) nongDate[0]);
			} else {
				temp = getDayCountOfMonth((int) nongDate[0], i);
			}

			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}

		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}

	/**
	 * 传出y年m月d日对应的农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	final public static long[] calElement(int y, int m, int d)
	{
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		//Date baseDate = new Date(0, 0, 31);
		Date baseDate = new GregorianCalendar(0 + 1900, 0, 31).getTime();
		//Date objDate = new Date(y - 1900, m - 1, d);
		Date objDate = new GregorianCalendar(y,m - 1, d).getTime();
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		nongDate[5] = offset + 40;
		nongDate[4] = 14;

		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = getDayCountOfYear(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = getLeapMonth(i);// 闰哪个月
		nongDate[6] = 0;

		for (i = 1;i < 13 && offset > 0;i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = getDayCountOfLeapMonth((int) nongDate[0]);
			} else {
				temp = getDayCountOfMonth((int) nongDate[0], i);
			}

			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}

		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}
	
	/**
	 * 得到农历天
	 * @param day
	 * @return
	 */
	public static final String getLunarDay(int day) {
		String returnValue = "";
		if (day == 10) {
			return "初十";
		} else if (day==20) {
			return "二十";
		} else if (day==30) {
			return "三十";
		}
		int two = (int) ((day) / 10);
		if (two == 0) {
			returnValue = "初";
		} else if (two == 1) {
			returnValue = "十";
		} else if (two == 2) {
			returnValue = "廿";
		} else if (two == 3) {
			returnValue = "三";
		}
		int one = (int) (day % 10);
		switch (one) {
		case 1:
			returnValue += "一";
			break;
		case 2:
			returnValue += "二";
			break;
		case 3:
			returnValue += "三";
			break;
		case 4:
			returnValue += "四";
			break;
		case 5:
			returnValue += "五";
			break;
		case 6:
			returnValue += "六";
			break;
		case 7:
			returnValue += "七";
			break;
		case 8:
			returnValue += "八";
			break;
		case 9:
			returnValue += "九";
			break;
		}
		return returnValue;
	}
	
	public static long[] today() {
		Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH)+1;
		int date = today.get(Calendar.DATE);
		long[] l = calElement(year, month, date);
		return l;
	}

	public static long[] get(Calendar today) {
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH)+1;
		int date = today.get(Calendar.DATE);
		long[] l = calElement(year, month, date);
		return l;
	}

	/**
	 * 返回代表今日时间的字符串
	 * @param locale
	 * @return
	 */
	public static String today(Locale locale) {
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
		}
		Calendar today = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEE", locale);
		try{
			return sdf.format(today.getTime());
		} finally{
			today = null;
			sdf = null;
		}
	}
}

