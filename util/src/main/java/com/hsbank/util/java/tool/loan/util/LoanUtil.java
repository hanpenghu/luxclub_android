package com.hsbank.util.java.tool.loan.util;

import java.math.BigDecimal;

/**
 * 借贷相关公共方法类
 * @author Arthur.Xie
 * 2015-07-26
 */
public class LoanUtil {
	/** 
     * 年利率转换为月利率 
     * @param annualInterestRate 
     * @return 
     */  
    public static double monthlyInterestRate(double annualInterestRate){  
        return annualInterestRate / 12;  
    }
    
    /**
	 * 格式化金额：
	 * <1>.保留两位小数
	 * <2>.ROUND_HALF_UP: 遇到.5的情况时往上近似,例: 1.5 -> 2
	 * @param amount
	 * @return
	 */
	public static Double formatAmount(Double amount) {
		return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
