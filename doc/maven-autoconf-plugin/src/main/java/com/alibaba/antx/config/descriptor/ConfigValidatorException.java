package com.alibaba.antx.config.descriptor;

import com.alibaba.antx.config.ConfigException;

/**
 * ����һ��config validator���쳣
 * 
 * @author Michael Zhou
 */
public class ConfigValidatorException extends ConfigException {
    private static final long serialVersionUID = -2992328279981424971L;

    /**
     * ����һ���쳣��
     */
    public ConfigValidatorException() {
        super();
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     */
    public ConfigValidatorException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     * @param cause �쳣ԭ��
     */
    public ConfigValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ����һ���쳣��
     * 
     * @param cause �쳣ԭ��
     */
    public ConfigValidatorException(Throwable cause) {
        super(cause);
    }
}
