package com.alibaba.antx.config;


/**
 * ����һ��auto config���쳣
 * 
 * @author Michael Zhou
 */
public class ConfigException extends RuntimeException {
    private static final long serialVersionUID = -756372236339875155L;

    /**
     * ����һ���쳣��
     */
    public ConfigException() {
        super();
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     */
    public ConfigException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     * @param cause �쳣ԭ��
     */
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ����һ���쳣��
     * 
     * @param cause �쳣ԭ��
     */
    public ConfigException(Throwable cause) {
        super(cause);
    }
}
