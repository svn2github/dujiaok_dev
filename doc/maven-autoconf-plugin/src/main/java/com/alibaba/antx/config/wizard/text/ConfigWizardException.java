package com.alibaba.antx.config.wizard.text;

import com.alibaba.antx.config.ConfigException;

/**
 * 代表一个config wizard的异常。
 * 
 * @author Michael Zhou
 */
public class ConfigWizardException extends ConfigException {
    private static final long serialVersionUID = -756372236339875155L;

    /**
     * 创建一个异常。
     */
    public ConfigWizardException() {
        super();
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     */
    public ConfigWizardException(String message) {
        super(message);
    }

    /**
     * 创建一个异常。
     * 
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ConfigWizardException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 创建一个异常。
     * 
     * @param cause 异常原因
     */
    public ConfigWizardException(Throwable cause) {
        super(cause);
    }
}
