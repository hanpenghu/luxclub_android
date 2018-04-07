package com.hsbank.util.java.tool;

import android.util.Log;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * jeval是为为你的Java应用程序提供可加入的、高性能、数学、布尔和函数表达式的解析和运算的高级资源包
 * @author Administrator
 * 2013-05-06
 */
public class JevalUtil {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String expression = "#{a}-(#{b}-#{c})";
        Map<String, String> variableMap = new HashMap<String, String>();
        variableMap.put("a", "100");
        variableMap.put("b", "50");
        variableMap.put("c", "10");
        Log.d(JevalUtil.class.getName(), "正确答案应该是：100 - (50 - 10) = 60");
        Log.d(JevalUtil.class.getName(), "计算出来的答案是：" + calculate(expression, variableMap));
    }
    
    /**
     * 计算指定表达式的值
     * @param expression            数学运算（加、减、乘、除）表达式
     * @param variableMap           参数值集合
     * @return
     */
    public static String calculate(String expression, Map<String, String> variableMap) {
        String returnValue = "";
        Evaluator eva = new Evaluator();
        //添加变量到Evaluator类实例
        for (Map.Entry<String, String> entry : variableMap.entrySet()) {
            eva.putVariable(entry.getKey(), entry.getValue());
        }
        //计算表达式
        try {
            returnValue = eva.evaluate(expression);
        } catch (EvaluationException e) {
            Log.d(JevalUtil.class.getName(), e.getMessage());
        }
        return returnValue;
    }
}
