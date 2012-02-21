package com.alibaba.antx.config.descriptor;

import com.alibaba.antx.config.ConfigException;

/**
 * 代表一个config validator的异常
 * 
 * @author Michael Zhou
 */
public class ConfigValidatorException extends ConfigException {
    private static final long serialVersionUID = -2992328279981424971L;

    /**
     * 创建一个异常。
     */
    public ConfigValidatorException() {
        super();
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     */
    public ConfigValidatorException(String message) {
        super(message);
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ConfigValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 创建一个异常。
     * 
     * @param cause 异常原因
     */
    public ConfigValidatorException(Throwable cause) {
        super(cause);
    }
}
