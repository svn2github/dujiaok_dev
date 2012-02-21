package com.alibaba.antx.config.generator.expr;

/**
 * 代表表达式的上下文。
 * 
 * @author Michael Zhou
 * @version $Id: ExpressionContext.java 689 2004-03-14 15:05:06Z baobao $
 */
public interface ExpressionContext {
    /**
     * 取得指定值。
     * 
     * @param key 键
     * @return 键对应的值
     */
    Object get(String key);

    /**
     * 添加一个值。
     * 
     * @param key 键
     * @param value 对应的值
     */
    void put(String key, Object value);
}
