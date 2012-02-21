package com.alibaba.antx.config;


/**
 * 代表一个auto config的异常
 * 
 * @author Michael Zhou
 */
public class ConfigException extends RuntimeException {
    private static final long serialVersionUID = -756372236339875155L;

    /**
     * 创建一个异常。
     */
    public ConfigException() {
        super();
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     */
    public ConfigException(String message) {
        super(message);
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 创建一个异常。
     * 
     * @param cause 异常原因
     */
    public ConfigException(Throwable cause) {
        super(cause);
    }
}
