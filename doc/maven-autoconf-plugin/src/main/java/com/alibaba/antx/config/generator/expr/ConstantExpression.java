package com.alibaba.antx.config.generator.expr;

/**
 * ����һ���������ʽ���ñ��ʽ��ֵ��������context��
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
