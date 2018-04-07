package com.hsbank.util.java.tool.loan.repayment_plan;

import com.hsbank.util.java.tool.loan.repayment_plan.impl.AverageCapital;
import com.hsbank.util.java.tool.loan.repayment_plan.impl.AverageCapitalPlusInterest;
import com.hsbank.util.java.tool.loan.repayment_plan.impl.InterestFirstDebtService;
import com.hsbank.util.java.tool.loan.repayment_plan.impl.OneTimeDebtService;
import com.hsbank.util.java.tool.loan.util.LoanConstant;
import com.hsbank.util.java.type.StringUtil;

import java.util.Locale;

/**
 * 工厂模式: 生成还款计划
 * @author wuyuan.xie
 * CreateDate 2007-09-11
 */
public class RepaymentPlanFactory {
	
	/**
	 * 得到一个工厂接口的实现类的实例
	 * @param repaymentMethod 		还款方式
	 * @return 
	 */
	public static IRepaymentPlan getInstance(String repaymentMethod) {
		IRepaymentPlan instance = null;
		repaymentMethod = StringUtil.dealString(repaymentMethod).toLowerCase(Locale.getDefault());
		if (LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST.equals(repaymentMethod)) {
			instance = new AverageCapitalPlusInterest();
		} else if (LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL.equals(repaymentMethod)) {
			instance = new AverageCapital();
		} else if (LoanConstant.REPAYMNET_METHOD_ONE_TIME.equals(repaymentMethod)) {
			instance = new OneTimeDebtService();
		} else if (LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST.equals(repaymentMethod)) {
			instance = new InterestFirstDebtService();
		}
		return instance;
	}
}
