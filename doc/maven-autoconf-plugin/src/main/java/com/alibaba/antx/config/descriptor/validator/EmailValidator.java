package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailValidator extends RegexpValidator {
    private static final Log log = LogFactory.getLog(EmailValidator.class);

    public Log getLogger() {
        return log;
    }

    public String getRegexp() {
        return "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*$";
    }

    public String getMode() {
        return "exact";
    }

    protected String getDefaultMessage() {
        return "参数值必须是合法的e-mail地址";
    }
}
