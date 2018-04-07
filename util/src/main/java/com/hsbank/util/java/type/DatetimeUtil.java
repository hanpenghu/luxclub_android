package com.hsbank.util.java.type;

import android.util.Log;

import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.constant.DatetimeField;
import com.hsbank.util.java.constant.DatetimeFormat;
import com.hsbank.util.java.constant.WeekFirstOrEndDay;
import com.hsbank.util.java.tool.config.IConfigFile;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间_公共类
 * <p/>
 * CreateDate 2007-01-25
 * <p/>
 * @author wuyuan.xie
 * @version 1.0
 */
public class DatetimeUtil {
    /**一天的毫秒数: 60*60*1000*24*/
    public final static long MILLIS_DAY = 86400000;
    /**一小时的毫秒数: 60*60*1000*/
    public final static long MILLIS_HOUR = 3600000;
    /**一分钟的毫秒数: 60*1000*/
    public final static long MILLIS_MINUTE = 60000;
    /**默认的参数配置文件名称*/
    private static final String DEFAULT_CONFIG_FILE_NAME = "date.xml";
    /**参数配置文件接口*/
    private static IConfigFile configFile = null;

    /**
     * 判断给定日期所在的年是不是闰年
     * @param d			给定日期
     * @return			是，则返回true；否，则返回false
     */
    public static Boolean isLeapYear(Date d) {
        int y = getYear(d);
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    /**
     * 判断给定的年是不是闰年
     * @param y			给定的年
     * @return			是，则返回true；否，则返回false
     */
    public static Boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    /**
     * 校验指定格式的日期(yyyy-MM-dd)
     * @param strDate		指定格式的日期
     * @return				通过，则返回true；没通过，则返回false
     */
    public static boolean isValidDate(String strDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = new StringBuffer()
                .append("^((\\d{2}(([02468][048])|([13579][26]))")
                .append("[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|")
                .append("(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?")
                .append("((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?(")
                .append("(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?")
                .append("((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))")
                .toString();
        if ((strDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(strDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(strDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 得到指定日期时间的指定区间的值
     * @param d 		日期
     * @param field		年、月、日、时、分、秒
     * @return 			计算后的日期
     */
    public static int getField(Date d, DatetimeField field) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        int returnValue;
        if (field.value() == Calendar.YEAR) {
            returnValue = Calendar.YEAR;
        } else if (field.value() == Calendar.MONTH) {
            returnValue = Calendar.MONTH;
        } else if (field.value() == Calendar.DATE) {
            returnValue = Calendar.DATE;
        } else if (field.value() == Calendar.HOUR) {
            returnValue = Calendar.HOUR;
        } else if (field.value() == Calendar.HOUR_OF_DAY) {
            returnValue = Calendar.HOUR_OF_DAY;
        } else if (field.value() == Calendar.MINUTE) {
            returnValue = Calendar.MINUTE;
        } else if (field.value() == Calendar.SECOND) {
            returnValue = Calendar.SECOND;
        } else {
            returnValue= 0;
        }
        if (field == DatetimeField.MONTH) {
            returnValue ++;
        }
        return returnValue;
    }

    /**
     * 得到当前日期
     * @return	当前日期
     */
    public static Date getCurrentDate() {
        Date d = new Date();
        int offsetHour = 0;
        if (configFile != null) {
            offsetHour = configFile.getInt("offset_hour", 0);
        }
        d = getDate(d, DatetimeField.HOUR_OF_DAY, offsetHour);
        return d;
    }

    /**
     * 得到当前年
     * @return int 当前年
     */
    public static int getCurrentYear() {
        return getField(getCurrentDate(), DatetimeField.YEAR);
    }

    /**
     * 得到年
     * @param d 	指定日期
     * @return 		年
     */
    public static int getYear(Date d) {
        return getField(d, DatetimeField.YEAR);
    }

    /**
     * 得到当前月
     * @return int 当前月
     */
    public static int getCurrentMonth() {
        return getField(getCurrentDate(), DatetimeField.MONTH);
    }

    /**
     * 得到月
     * @param d 	指定日期
     * @return 		月
     */
    public static int getMonth(Date d) {
        return getField(d, DatetimeField.MONTH);
    }

    /**
     * 得到当前日
     * @return 当前日
     */
    public static int getCurrentDay() {
        return getField(getCurrentDate(), DatetimeField.DAY);
    }

    /**
     * 得到日
     * @param d 	指定日期
     * @return		日
     */
    public static int getDay(Date d) {
        return getField(d, DatetimeField.DAY);
    }

    /**
     * 得到当前时
     * @return int 当前时
     */
    public static int getCurrentHour() {
        return getField(getCurrentDate(), DatetimeField.HOUR_OF_DAY);
    }

    /**
     * 得到时
     * @param d 	指定日期
     * @return 		时
     */
    public static int getHour(Date d) {
        return getField(d, DatetimeField.HOUR_OF_DAY);
    }

    /**
     * 得到当前分
     * @return int 当前分
     */
    public static int getCurrentMinute() {
        return getField(getCurrentDate(), DatetimeField.MINUTE);
    }

    /**
     * 得到分
     * @param d 	指定日期
     * @return		分
     */
    public static int getMinute(Date d) {
        return getField(d, DatetimeField.MINUTE);
    }

    /**
     * 得到当前秒
     * @return		当前秒
     */
    public static int getCurrentSecond() {
        return getField(getCurrentDate(), DatetimeField.SECOND);
    }

    /**
     * 得到秒
     * @param d 	指定日期
     * @return		秒
     */
    public static int getSecond(Date d) {
        return getField(d, DatetimeField.SECOND);
    }

    /**
     * 得到与当前日期相差指定区间指定偏移量的日期
     * @param field		指定区间
     * @param offset	指定偏移量
     * @return			日期
     */
    public static Date getDate(DatetimeField field, int offset) {
        return getDate(getCurrentDate(), field, offset);
    }

    /**
     * 得到与指定日期相差指定区间指定偏移量的日期
     * @param d			日期
     * @param field		指定区间
     * @param offset	指定偏移量
     * @return			日期
     */
    public static Date getDate(Date d, DatetimeField field, int offset) {
        Date resultDate = null;
        if (field != null) {
            if (field == DatetimeField.YEAR
                    || field == DatetimeField.MONTH
                    || field == DatetimeField.DAY
                    || field == DatetimeField.HOUR
                    || field == DatetimeField.HOUR_OF_DAY
                    || field == DatetimeField.MINUTE
                    || field == DatetimeField.SECOND) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(d);
                if (field.value() == Calendar.YEAR) {
                    gc.add(Calendar.YEAR, offset);
                } else if (field.value() == Calendar.MONTH) {
                    gc.add(Calendar.MONTH, offset);
                } else if (field.value() == Calendar.DATE) {
                    gc.add(Calendar.DATE, offset);
                } else if (field.value() == Calendar.HOUR) {
                    gc.add(Calendar.HOUR, offset);
                } else if (field.value() == Calendar.HOUR_OF_DAY) {
                    gc.add(Calendar.HOUR_OF_DAY, offset);
                } else if (field.value() == Calendar.MINUTE) {
                    gc.add(Calendar.MINUTE, offset);
                } else if (field.value() == Calendar.SECOND) {
                    gc.add(Calendar.SECOND, offset);
                }
                resultDate = gc.getTime();
            }
        }
        return resultDate;
    }

    /**
     * 得到日期
     * @param year 		年
     * @param month 	月
     * @param date 		日
     * @param hour 		时
     * @param minute 	分
     * @param second 	秒
     * @return			日期
     */
    public static Date getDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 得到日期
     * @param year 		年
     * @param month 	月
     * @param date 		日
     * @return			日期
     */
    public static Date getDate(int year, int month, int date) {
        return getDate(year, month, date, 0, 0, 0);
    }

    /**
     * 得到当前日期是一个星期中的第几天
     * <p>
     * 星期天为1，其余的都比实际星期几大1
     * 可以用Calendar的几个整型常量来判断是星期几, 如下：
     * Calendar.SUNDAY = 1,
     * Calendar.MONDAY = 2,
     * Calendar.TUESDAY = 3,
     * Calendar.WEDNESDAY =4,
     * Calendar.THURSDAY = 5,
     * Calendar.FRIDAY = 6,
     * Calendar.SATURDAY = 7
     * <p>
     * @return 	当前日期是一个星期中的第几天
     */
    public static int getCurrenDayOfWeek() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getCurrentDate());
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到指定日期是一个星期中的第几天
     * <p>
     * 星期天为1，其余的都比实际星期几大1
     * 可以用Calendar的几个整型常量来判断是星期几, 如下：
     * Calendar.SUNDAY = 1,
     * Calendar.MONDAY = 2,
     * Calendar.TUESDAY = 3,
     * Calendar.WEDNESDAY =4,
     * Calendar.THURSDAY = 5,
     * Calendar.FRIDAY = 6,
     * Calendar.SATURDAY = 7
     * <p>
     * @param d		指定日期
     * @return		指定日期是一个星期中的第几天
     */
    public static int getDayOfWeek(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 由生日得到年龄
     * @param iLBirthday 	生日
     * @return				年龄
     */
    public static int getAgeFromBirthday(long iLBirthday) {
        Date birthday = new Date(iLBirthday);
        return getAgeFromBirthday(birthday);
    }

    /**
     * 由生日得到年龄
     * @param strBirthday 	生日
     * @return				年龄
     */
    public static int getAgeFromBirthday(String strBirthday) {
        Date birthday = StringUtil.toDate(strBirthday);
        if (birthday == null) {
            return 0;
        } else {
            return getAgeFromBirthday(birthday);
        }
    }

    /**
     * 由生日得到年龄
     * @param birthday 生日
     * @return			年龄
     */
    public static int getAgeFromBirthday(Date birthday) {
        if (birthday == null) {
            return 0;
        } else {
            //得到当前的年月日
            int currentYear = getCurrentYear();
            int currentMonth = getCurrentMonth();
            int currentDay = getCurrentDay();
            //得到生日的年月日
            int birthdayYear = getYear(birthday);
            int birthdayMonth = getMonth(birthday);
            int birthdayDay = getDay(birthday);
            //得到年之间的差
            int returnValue = currentYear - birthdayYear;
            //得到月或日之间的差对年龄的影响
            if (currentMonth < birthdayMonth || (currentMonth == birthdayMonth && currentDay < birthdayDay)) {
                returnValue --;
            }
            return returnValue < 0 ? 0 : returnValue;
        }
    }

    /**
     * 得到问候语
     * @return		问候语
     */
    public static String getGreeting() {
        String returnValue = "";
        int h = getCurrentHour();
        switch (h) {
            case  0: returnValue = "凌晨好"; break;
            case  1: returnValue = "凌晨好"; break;
            case  2: returnValue = "凌晨好"; break;
            case  3: returnValue = "凌晨好"; break;
            case  4: returnValue = "凌晨好"; break;
            case  5: returnValue = "凌晨好"; break;
            case  6: returnValue = "早上好"; break;
            case  7: returnValue = "早上好"; break;
            case  8: returnValue = "早上好"; break;
            case  9: returnValue = "早上好"; break;
            case 10: returnValue = "早上好"; break;
            case 11: returnValue = "早上好"; break;
            case 12: returnValue = "中午好"; break;
            case 13: returnValue = "中午好"; break;
            case 14: returnValue = "下午好"; break;
            case 15: returnValue = "下午好"; break;
            case 16: returnValue = "下午好"; break;
            case 17: returnValue = "下午好"; break;
            case 18: returnValue = "下午好"; break;
            case 19: returnValue = "晚上好"; break;
            case 20: returnValue = "晚上好"; break;
            case 21: returnValue = "晚上好"; break;
            case 22: returnValue = "晚上好"; break;
            case 23: returnValue = "晚上好"; break;
        }
        return returnValue;
    }

    /**
     * Date转换成Timestamp
     * @param d		日期
     * @return		Timestamp对象
     */
    public static Timestamp dateToTimestamp(Date d) {
        return d == null ? null : new Timestamp(d.getTime());
    }

    /**
     * Date转换成java.sql.Date
     * @param d		日期
     * @return		java.sql.Date对象
     */
    public static java.sql.Date dateToSqlDate(Date d) {
        return new java.sql.Date(d.getTime());
    }

    /**
     * Date转换成String
     * @param d				指定日期
     * @param format 		指定格式
     * @return 				指定格式的日期时间字符串
     */
    public static String datetimeToString(Date d, String format) {
        return (new SimpleDateFormat(format, Locale.getDefault())).format(d);
    }

    /**
     * Date转换成String
     * @param d				日期
     * @return		        指定格式的日期时间字符串
     */
    public static String datetimeToString(Date d) {
        return (new SimpleDateFormat(DatetimeFormat.DEFAULT_DATE_TIME.value(), Locale.getDefault())).format(d);
    }

    /**
     * Date转换成String
     * @return				指定格式的日期时间字符串
     */
    public static String datetimeToString() {
        return datetimeToString(getCurrentDate());
    }

    /**
     * Date转换成String
     * @param d				日期
     * @return				指定格式的日期字符串
     */
    public static String dateToString(Date d) {
        return (new SimpleDateFormat(DatetimeFormat.DEFAULT_DATE.value(), Locale.getDefault())).format(d);
    }

    /**
     * Date转换成String
     * @return				指定格式的日期字符串
     */
    public static String dateToString() {
        return dateToString(getCurrentDate());
    }

    /**
     * Date转换成String
     * @param d				日期
     * @return				指定格式的日期时间字符串
     */
    public static String timeToString(Date d) {
        return (new SimpleDateFormat(DatetimeFormat.DEFAULT_TIME.value(), Locale.getDefault())).format(d);
    }

    /**
     * Date转换成String
     * @return				指定格式的时间字符串
     */
    public static String timeToString() {
        return timeToString(getCurrentDate());
    }

    /**
     * Date转换成String
     * @param format 		指定日期时间格式
     * @return 				指定格式的日期时间字符串
     */
    public static String datetimeToString(DatetimeFormat format) {
        return (new SimpleDateFormat(format.value(), Locale.getDefault())).format(getCurrentDate());
    }

    /**
     * Date转换成String
     * @param format 		指定日期时间格式
     * @return 				指定格式的日期时间字符串
     */
    public static String datetimeToString(String format) {
        return (new SimpleDateFormat(format, Locale.getDefault())).format(getCurrentDate());
    }

    /**
     * Date转换成String
     * @param d 			待格式化的日期时间
     * @param format 		指定日期时间格式
     * @return 				格式化后的日期
     */
    public static String datetimeToString(Date d, DatetimeFormat format) {
        JavaUtil.assertNotNull(format);
        return null == d ? null : new SimpleDateFormat(format.value(), Locale.getDefault()).format(d);
    }

    /**
     * Date转换成String
     * @param d 			待格式化的日期时间
     * @param format	 	指定日期时间格式
     * @param loc 			Locale对象, 表示特定的地理、政治和文化地区
     * @return		 		格式化后的日期时间字符串
     */
    public static String datetimeToString(Date d, DatetimeFormat format, Locale loc) {
        JavaUtil.assertNotNull(format);
        SimpleDateFormat sdf = null;
        if (loc == null) {
            sdf = new SimpleDateFormat(format.value(), Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat(format.value(), loc);
        }
        return sdf.format(d);
    }

    /**
     * Date转换成String
     * @param iL 			待格式化的日期时间
     * @param format 		指定日期时间格式
     * @return String 		格式化后的日期时间
     */
    public static String datetimeToString(long iL, DatetimeFormat format) {
        JavaUtil.assertNotNull(format);
        Date date = new Date(iL);
        SimpleDateFormat sdf = new SimpleDateFormat(format.value(), Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 得到指定年份的所有周
     * <p>
     * @param year			年份，必须&gt;=1900且&gt;=9999
     * @param firstDay		周开始日期(是星期天，还是星期一)
     * @return 				返回一个数组对象组成的列表。每个数组对象中，Date[0]保存本周的开始日期，Date[1]是本周的结束日期
     */
    public static List<Date[]> getWeekListByYear(final int year, WeekFirstOrEndDay firstDay){
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        if (firstDay == null) {
            throw new NullPointerException("请指定周开始日期是星期一，还是星期天？");
        }
        int weekCount = getWeekCountByYear(year, firstDay);
        List<Date[]> returnValue = new ArrayList<Date[]>(weekCount);
        for(int weekIndex = 0; weekIndex < weekCount; weekIndex++) {
            Date[] weekArray = new Date[2];
            weekArray[0] = getFirstDayByYearWeek(year, weekIndex, firstDay);
            weekArray[1] = getEndDayByYearWeek(year, weekIndex, firstDay);
            returnValue.add(weekArray);
        }
        return returnValue;
    }

    /**
     * 得到指定年份的所有周
     * <p>
     * @param year			年份，必须&gt;=1900且&gt;=9999
     * @param firstDay		周开始日期(是星期天，还是星期一)
     * @param df			日期格式
     * @return 				返回一个数组对象组成的列表。每个数组对象中，Date[0]保存本周的开始日期，Date[1]是本周的结束日期
     */
    public static List<String[]> getWeekListByYear(final int year, WeekFirstOrEndDay firstDay, DatetimeFormat df){
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        if (firstDay == null) {
            throw new NullPointerException("请指定周开始日期是星期一，还是星期天？");
        }
        int weekCount = getWeekCountByYear(year, firstDay);
        List<String[]> returnValue = new ArrayList<String[]>(weekCount);
        SimpleDateFormat sdf = new SimpleDateFormat(df.value(), Locale.getDefault());
        for(int weekIndex = 0; weekIndex < weekCount; weekIndex++) {
            String[] weekArray = new String[2];
            weekArray[0] = sdf.format(getFirstDayByYearWeek(year, weekIndex, firstDay));
            weekArray[1] = sdf.format(getEndDayByYearWeek(year, weekIndex, firstDay));
            returnValue.add(weekArray);
        }
        return returnValue;
    }

    /**
     * 得到指定年份的周数
     * @param year 			年份，必须&gt;=1900且&gt;=9999
     * @param firstDay		周开始日期(是星期天，还是星期一)
     * @return				指定年份的周数
     */
    public static int getWeekCountByYear(final int year, WeekFirstOrEndDay firstDay) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        //得到指定年份的最后一天
        Date d = getLastDay(year);
        //得到最后一天属于当年的第几周
        return getWeekIndex(d, firstDay) + 1;
    }

    /**
     * 得到指定年指定周的开始日期
     * @param year 			年份，必须&gt;=1900且&gt;=9999
     * @param weekIndex 	周序号（从0开始）
     * @param firstDay		周开始日期(是星期天，还是星期一)
     * @return 				指定年指定周的开始日期
     */
    public static Date getFirstDayByYearWeek(int year, int weekIndex, WeekFirstOrEndDay firstDay)  {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        if (firstDay == null) {
            throw new NullPointerException("请指定周开始日期是星期一，还是星期天？");
        }
        Calendar c = Calendar.getInstance();
        // <1>.设置每周开始日期
        if (firstDay.value() == Calendar.MONDAY) {
            c.setFirstDayOfWeek(Calendar.MONDAY);
        } else {
            c.setFirstDayOfWeek(Calendar.SUNDAY);
        }
        // <2>.设置日历指向开始日期
        c.set(Calendar.DAY_OF_WEEK, firstDay.value());
        // <3>.设置每周最少为7天
        c.setMinimalDaysInFirstWeek(7);
        // <4>.设置年
        c.set(Calendar.YEAR, year);
        // <5>.设置周
        c.set(Calendar.WEEK_OF_YEAR, weekIndex);
        return c.getTime();
    }

    /**
     * 得到指定年指定周的结束日期
     * @param year 			年，必须&gt;=1900且&gt;=9999
     * @param weekIndex 	周序号（从0开始）
     * @param firstDay		周开始日期(是星期天，还是星期一)
     * @return				指定年指定周的结束日期
     */
    public static Date getEndDayByYearWeek(int year, int weekIndex, WeekFirstOrEndDay firstDay) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        if (firstDay == null) {
            throw new NullPointerException("请指定周开始日期是星期一，还是星期天？");
        }
        Calendar c = Calendar.getInstance();
        // <1>.设置每周开始日期
        if (firstDay.value() == Calendar.MONDAY) {
            c.setFirstDayOfWeek(Calendar.MONDAY);
        } else {
            c.setFirstDayOfWeek(Calendar.SUNDAY);
        }
        // <2>.设置日历指向周结束日期
        if (firstDay == WeekFirstOrEndDay.MONDAY) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }
        // <3>.设置每周最少为7天
        c.setMinimalDaysInFirstWeek(7);
        // <4>.设置年
        c.set(Calendar.YEAR, year);
        // <5>.设置周
        c.set(Calendar.WEEK_OF_YEAR, weekIndex);
        return c.getTime();
    }

    /**
     * 得到指年份的最后一天
     * @param year			指年份
     * @return				指年份的最后一天
     */
    public static Date getLastDay(int year) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年份必须>=1900且>=9999");
        }
        return getDate(year, 12, 31);
    }

    /**
     * 得到指定日期属于当年的第几周
     * @param d				指定日期
     * @param firstDay 		周开始日期(是星期天，还是星期一)
     * @return				指定日期属于当年的第几周
     */
    public static int getWeekIndex(Date d, WeekFirstOrEndDay firstDay) {
        Calendar c = Calendar.getInstance();
        // <1>.设置每周开始日期
        if (firstDay.value() == Calendar.MONDAY) {
            c.setFirstDayOfWeek(Calendar.MONDAY);
        } else {
            c.setFirstDayOfWeek(Calendar.SUNDAY);
        }
        // <2>.设置日历指向开始日期
        c.set(Calendar.DAY_OF_WEEK, firstDay.value());
        // <3>.设置每周最少为7天
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(d);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到【指定年】【指定月】的天数
     * @param year			指定年
     * @param month			指定月
     * @return				【指定年】【指定月】的天数
     */
    public static int getDayCountOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 得到两个日期之间相差的天数
     * @param strDate1 		日期1
     * @param strDate2 		日期2
     * @return 				两个日期之间相差的天数
     */
    public static long dayInterval(String strDate1, String strDate2) {
        Date date1 = StringUtil.toDate(strDate1);
        Date date2 = StringUtil.toDate(strDate2);
        return dayInterval(date2, date1);
    }

    /**
     * 得到两个日期之间相差的天数
     * @param date1 		日期1
     * @param date2 		日期2
     * @return 				两个日期之间相差的天数
     */
    public static long dayInterval(Date date1,Date date2) {
        return (date2.getTime() - date1.getTime()) / MILLIS_DAY;
    }

    /**
     * 把指定字符串形式的日期，从一种指定格式转换成另一种指定格式
     * @param strDate
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String formatDate(String strDate, String fromFormat, String toFormat) {
        Date d = null;
        DateFormat df = new SimpleDateFormat(fromFormat, Locale.getDefault());
        try {
            d = df.parse(strDate);
        } catch (ParseException e) {
            Log.e(StringUtil.class.getName(), e.getMessage());
        }
        return datetimeToString(d, toFormat);
    }
}
