package com.alibaba.antx.config.generator.expr;

/**
 * 代表一个常量表达式，该表达式的值不依赖于context。
 * 
 * @author Michael Zhou
 */
public class ConstantExpression implements Expression {
    private String value;

    public ConstantExpression(String value) {
        this.value = value;
    }

    public String getExpressionText() {
        return String.valueOf(value);
    }

    public Object evaluate(ExpressionContext context) {
        return value;
    }

    public String toString() {
        return getExpressionText();
    }
}
