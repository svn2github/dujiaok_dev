package com.alibaba.antx.config.generator.expr;

import com.alibaba.antx.config.ConfigException;

/**
 * 代表一个expression的异常
 * 
 * @author Michael Zhou
 */
public class ExpressionException extends ConfigException {
    private static final long serialVersionUID = 24552988073596985L;

    /**
     * 创建一个异常。
     */
    public ExpressionException() {
        super();
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     */
    public ExpressionException(String message) {
        super(message);
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 创建一个异常。
     * 
     * @param cause 异常原因
     */
    public ExpressionException(Throwable cause) {
        super(cause);
    }
}
