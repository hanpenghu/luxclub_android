package com.hsbank.util.java.type;

import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.constant.BigDecimalFormat;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额_公共类
 * @author xwy
 * 2011-04-01
 */
public final class BigDecimalUtil {
    /**
     * 最后一位如果大于0，则向前进一位，正负数都如此
     * 如： 7.1 -&gt; 8； -7.1 -&gt; -8 */
    public static final int ROUND_UP = BigDecimal.ROUND_UP;
    /**
     * 最后一位不管是什么都会被舍弃
     * 如： 7.1 -&gt; 7； -7.1 -&gt; -7 */
    public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;
    /**
     * 如果是正数，按ROUND_UP处理，如果是负数，按照ROUND_DOWN处理
     * 如： 7.1 -&gt; 8； -7.1 -&gt; -7
     * 这种近似的结果都会&gt;=实际值*/
    public static final int ROUND_CEILING = BigDecimal.ROUND_CEILING;
    /**
     * 跟BigDecimal_ROUND_CEILING相反
     * 如: 7.1 -&gt; 7； -7.1 -&gt; -8
     * 这种处理的结果&lt;=实际值   */
    public static final int ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
    /**
     * 如果最后一位&lt;5则舍弃，如果&gt;=5则向前进一位
     * 如：7.5-&gt;8；7.4-&gt;7；-7.5-&gt;-8 */
    public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
    /**
     * 如果最后一位&lt;=5则舍弃，如果&gt;5则向前进一位
     * 如：7.5 -&gt; 7；7.6 -&gt; 8；-7.5 -&gt; -7 */
    public static final int ROUND_HALF_DOWN = BigDecimal.ROUND_HALF_DOWN;
    /**
     * 如果倒数第二位是奇数，按照BigDecimal.ROUND_HALF_UP处理，
     * 如果是偶数，按照 BigDecimal.ROUND_HALF_DOWN来处理
     * 如: 7.5 -&gt; 8； 8.5 -&gt; 8； 7.4 -&gt; 7； -7.5 -&gt; -8 */
    public static final int ROUND_HALF_EVEN = BigDecimal.ROUND_HALF_EVEN;
    /**默认运算精度 */
    private static final int DEFAULT_SCALE = 24;

    public static void main(String[] args) {
        System.out.println(round(new BigDecimal(-7.1), 0, ROUND_UP));
        System.out.println(round(new BigDecimal(-7.1), 0, ROUND_DOWN));
    }

    /**
     * 进位
     * @param data				参数
     * @param scale				级别
     * @param roundingMode		进位模式
     * @return					结果
     */
    public static BigDecimal round(BigDecimal data, int scale, int roundingMode) {
        return data == null ? null : data.divide(BigDecimal.ONE, scale, roundingMode);
    }

    /**
     * 加
     * @param numbers	参数
     * @return			结果
     */
    public static BigDecimal add(BigDecimal... numbers) {
        BigDecimal resultValue = BigDecimal.ZERO;
        if (numbers.length == 0) {
            return resultValue;
        }
        if (numbers.length == 1) {
            return numbers[0];
        }
        for (BigDecimal number : numbers) {
            if (number != null) {
                resultValue = resultValue.add(number);
            }
        }
        return resultValue;
    }

    /**
     * 减
     * @param numbers	参数
     * @return			结果
     */
    public static BigDecimal sub(BigDecimal... numbers) {
        BigDecimal resultValue = BigDecimal.ZERO;
        if (numbers.length == 0) {
            return resultValue;
        }
        if (numbers.length == 1) {
            return numbers[0];
        }
        if (numbers[0] != null) {
            resultValue = numbers[0];
        }
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] != null) {
                resultValue = resultValue.subtract(numbers[i]);
            }
        }
        return resultValue;
    }

    /**
     * 乘
     * @param numbers	参数
     * @return			结果
     */
    public static BigDecimal multiply(BigDecimal... numbers) {
        BigDecimal resultValue = BigDecimal.ONE;
        if (numbers.length == 0) {
            return resultValue;
        }
        if (numbers.length == 1) {
            return numbers[0];
        }
        for (BigDecimal number : numbers) {
            if (number == null) {
                return BigDecimal.ZERO;
            }
            resultValue = resultValue.multiply(number);
        }
        return resultValue;
    }

    /**
     * 除
     * @param numbers	参数
     * @return			结果
     */
    public static BigDecimal divide(BigDecimal... numbers) {
        return divide(DEFAULT_SCALE, ROUND_HALF_UP, numbers);
    }

    /**
     * 除
     * @param scale				级别
     * @param roundingMode		进位模式
     * @param numbers			参数
     * @return					结果
     */
    public static BigDecimal divide(int scale, int roundingMode,
                                    BigDecimal... numbers) {
        BigDecimal retval = BigDecimal.ONE;
        if (numbers.length == 0) {
            return retval;
        }
        if (numbers[0] == null) {
            numbers[0] = BigDecimal.ZERO;
        }
        if (numbers.length == 1) {
            return numbers[0];
        }
        retval = numbers[0];
        int iLength = numbers.length;
        for (int i = 1; i < iLength; i++) {
            if (numbers[i] == null) {
                numbers[i] = BigDecimal.ZERO;
            }
            retval = retval.divide(numbers[i], scale, roundingMode);
        }
        return retval;
    }

    /**
     * 转换成String
     * @param bd		BigDecimal对象
     * @param format	格式
     * @return			结果
     */
    public static String toString(BigDecimal bd, BigDecimalFormat format) {
        JavaUtil.assertNotNull(format);
        return null == bd ? null : new DecimalFormat(format.value()).format(bd);
    }
}
