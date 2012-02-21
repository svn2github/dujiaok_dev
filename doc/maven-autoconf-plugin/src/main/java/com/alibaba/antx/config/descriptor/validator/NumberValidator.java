package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NumberValidator extends RegexpValidator {
    private static final Log log = LogFactory.getLog(NumberValidator.class);

    public Log getLogger() {
        return log;
    }

    public String getRegexp() {
        return "^\\d+$";
    }

    public String getMode() {
        return "exact";
    }

    protected String getDefaultMessage() {
        return "参数值必须是数字";
    }
}
