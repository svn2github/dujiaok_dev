package com.alibaba.antx.config.generator.expr;

import com.alibaba.antx.config.ConfigException;

/**
 * ����һ��expression���쳣
 * 
 * @author Michael Zhou
 */
public class ExpressionException extends ConfigException {
    private static final long serialVersionUID = 24552988073596985L;

    /**
     * ����һ���쳣��
     */
    public ExpressionException() {
        super();
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     */
    public ExpressionException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     * @param cause �쳣ԭ��
     */
    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ����һ���쳣��
     * 
     * @param cause �쳣ԭ��
     */
    public ExpressionException(Throwable cause) {
        super(cause);
    }
}
