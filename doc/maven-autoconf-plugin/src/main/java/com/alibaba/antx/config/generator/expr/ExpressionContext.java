package com.alibaba.antx.config.generator.expr;

/**
 * ������ʽ�������ġ�
 * 
 * @author Michael Zhou
 * @version $Id: ExpressionContext.java 689 2004-03-14 15:05:06Z baobao $
 */
public interface ExpressionContext {
    /**
     * ȡ��ָ��ֵ��
     * 
     * @param key ��
     * @return ����Ӧ��ֵ
     */
    Object get(String key);

    /**
     * ���һ��ֵ��
     * 
     * @param key ��
     * @param value ��Ӧ��ֵ
     */
    void put(String key, Object value);
}
