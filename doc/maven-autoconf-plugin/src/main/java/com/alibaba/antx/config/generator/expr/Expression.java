package com.alibaba.antx.config.generator.expr;

/**
 * ����һ�����ʽ��
 * 
 * @author Michael Zhou
 */
public interface Expression {
    /**
     * ȡ�ñ��ʽ�ַ�����ʾ��
     * 
     * @return ���ʽ�ַ�����ʾ
     */
    String getExpressionText();

    /**
     * ��ָ�����������м�����ʽ��
     * 
     * @param context ������
     * @return ���ʽ�ļ�����
     */
    Object evaluate(ExpressionContext context);
}
