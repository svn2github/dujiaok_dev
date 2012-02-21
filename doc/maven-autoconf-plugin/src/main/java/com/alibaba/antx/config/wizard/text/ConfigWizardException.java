package com.alibaba.antx.config.wizard.text;

import com.alibaba.antx.config.ConfigException;

/**
 * ����һ��config wizard���쳣��
 * 
 * @author Michael Zhou
 */
public class ConfigWizardException extends ConfigException {
    private static final long serialVersionUID = -756372236339875155L;

    /**
     * ����һ���쳣��
     */
    public ConfigWizardException() {
        super();
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     */
    public ConfigWizardException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣��
     * 
     * @param message �쳣��Ϣ
     * @param cause �쳣ԭ��
     */
    public ConfigWizardException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ����һ���쳣��
     * 
     * @param cause �쳣ԭ��
     */
    public ConfigWizardException(Throwable cause) {
        super(cause);
    }
}
