package com.hsbank.util.java.tool.loan.repayment_plan.test;

import com.hsbank.util.java.tool.loan.repayment_plan.RepaymentPlanFactory;
import com.hsbank.util.java.tool.loan.util.LoanConstant;
import com.hsbank.util.java.tool.loan.util.bean.RepaymentPlanItem;
import com.hsbank.util.java.type.DatetimeUtil;

import java.util.List;

/**
 * 测试：等额本金
 * @author Arthur.Xie
 * 2015-07-26
 */
public class AverageCapitalTest {
	public static void main(String[] args) {
		//贷款总额
		double totalLoan = 10000.00;
		//年利率
		double annualInterestRate = 0.12;
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalMonth = 12;
		//开始日期
		String strBeginDate = "2015-08-03";
		//生成还款计划
		List<RepaymentPlanItem> repaymentPlan = RepaymentPlanFactory.getInstance(LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL).generate(totalLoan, totalMonth, annualInterestRate, discount, strBeginDate);
		System.out.println("第 N 期,开始日期,截止日期,应还本息,应还本金,应还利息,剩余本金,累计应还本金,累计应还利息");
		for (RepaymentPlanItem item : repaymentPlan) {
			System.out.println(item.getIndex() + "," 
					+ DatetimeUtil.dateToString(item.getBeginDate()) + ","
					+ DatetimeUtil.dateToString(item.getEndDate()) + "," 
					+ item.getPrincipalAndInterest() + "," 
					+ item.getPrincipal() + "," 
					+ item.getInterest() + "," 
					+ item.getPrincipalRemaining() + "," 
					+ item.getSumPrincipal() + "," 
					+ item.getSumInterest());
		}
		System.out.println("累计应还利息 = " + RepaymentPlanFactory.getInstance(LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL).calculate(totalLoan, totalMonth, annualInterestRate, discount));
	}
}
