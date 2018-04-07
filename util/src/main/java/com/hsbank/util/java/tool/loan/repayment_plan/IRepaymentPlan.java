package com.hsbank.util.java.tool.loan.repayment_plan;

import com.hsbank.util.java.tool.loan.util.bean.RepaymentPlanItem;

import java.util.Date;
import java.util.List;

/**
 * 还款计划接口
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
public interface IRepaymentPlan {	
	/**
	 * 生成还款计划
	 * @param totalLoan 					贷款总额，如：10000
	 * @param totalMonth					贷款期数，如：12
	 * @param annualInterestRate 			年利率，如：年利率为12.0%，则输入0.12
	 * @param discount						年利率折扣，如：7折优惠利率，则输入0.7
	 * @param strBeginDate					开始日期
	 * @return								还款计划
	 */
	public List<RepaymentPlanItem> generate(double totalLoan, int totalMonth, double annualInterestRate, double discount, String strBeginDate);
	
	/**
	 * 生成还款计划
	 * @param totalLoan 					贷款总额，如：10000
	 * @param totalMonth					贷款期数，如：12
	 * @param annualInterestRate 			年利率，如：年利率为12.0%，则输入0.12
	 * @param discount						年利率折扣，如：7折优惠利率，则输入0.7
	 * @param beginDate						开始日期
	 * @return								还款计划
	 */
	public List<RepaymentPlanItem> generate(double totalLoan, int totalMonth, double annualInterestRate, double discount, Date beginDate);
	
	/**
	 * 计算预期利息
	 * @param totalLoan 					贷款总额，如：10000
	 * @param totalMonth					贷款期数，如：12
	 * @param annualInterestRate 			年利率，如：年利率为12.0%，则输入0.12
	 * @param discount						年利率折扣，如：7折优惠利率，则输入0.7
	 * @return								预期利息
	 */
	public double calculate(double totalLoan, int totalMonth, double annualInterestRate, double discount);
}
