package com.alibaba.antx.config.generator.expr;

/**
 * 代表一个表达式。
 * 
 * @author Michael Zhou
 */
public interface Expression {
    /**
     * 取得表达式字符串表示。
     * 
     * @return 表达式字符串表示
     */
    String getExpressionText();

    /**
     * 在指定的上下文中计算表达式。
     * 
     * @param context 上下文
     * @return 表达式的计算结果
     */
    Object evaluate(ExpressionContext context);
}
