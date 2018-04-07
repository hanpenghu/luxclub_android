package com.hsbank.util.java.tool.loan.credit_assignment.test;

import com.hsbank.util.java.tool.loan.credit_assignment.CreditAssignment;
import com.hsbank.util.java.tool.loan.util.LoanConstant;
import com.hsbank.util.java.tool.loan.util.bean.RepaymentPlanItem;
import com.hsbank.util.java.type.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 债权转让: 根据【最原始标的】的还款计划，生成两个新的还款计划
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
public class CreditAssignmentTest {
	
	public static void main(String[] args) {
		//贷款总额
		double totalLoan = 5000.00;
		//转让债权
		double assignmentLoan = 5000.00;
		//剩余债权
		double remainingLoan = 0.00;
		//转让日期
		Date assignmentDate = StringUtil.toDate("2015-07-16 12:12:12");
		//【最原始标的】还款计划
		List<RepaymentPlanItem> repaymentPlan = new ArrayList<RepaymentPlanItem>();
		repaymentPlan.add(createItem("2015-07-01 12:12:12", "2015-07-31 12:12:12", 11000, 10000, 1000));
		repaymentPlan.add(createItem("2015-08-01 12:12:12", "2015-08-31 12:12:12", 11000, 10000, 1000));
		Map<String, List<RepaymentPlanItem>> map = CreditAssignment.getInstance().generate(totalLoan, assignmentLoan, remainingLoan, assignmentDate, repaymentPlan);
		//转让债权_还款计划
		List<RepaymentPlanItem> assignmentLoanPlan = map.get(LoanConstant.LOAN_ASSIGNMENT);
		System.out.println("============================LOAN_ASSIGNMENT=============================");
		for (RepaymentPlanItem item : assignmentLoanPlan) {
			System.out.println(item.toString());
		}
		//剩余债权_还款计划
		List<RepaymentPlanItem> remainingLoanPlan = map.get(LoanConstant.LOAN_REMAINING);
		System.out.println("============================LOAN_REMAINING=============================");
		for (RepaymentPlanItem item : remainingLoanPlan) {
			System.out.println(item.toString());
		}
	}
	
	/**
	 * 生成一条还款记录
	 * @param strBeginDate
	 * @param strEndDate
	 * @param principalAndInterest
	 * @param principal
	 * @param interest
	 * @return
	 */
	private static RepaymentPlanItem createItem(String strBeginDate, String strEndDate, double principalAndInterest, double principal, double interest) {
		RepaymentPlanItem resultValue = new RepaymentPlanItem();
		Date beginDate = StringUtil.toDate(strBeginDate);
		Date endDate = StringUtil.toDate(strEndDate);
		resultValue.setBeginDate(beginDate);
		resultValue.setEndDate(endDate);
		resultValue.setPrincipalAndInterest(principalAndInterest);
		resultValue.setPrincipal(principal);
		resultValue.setInterest(interest);
		return resultValue;
	}
}
