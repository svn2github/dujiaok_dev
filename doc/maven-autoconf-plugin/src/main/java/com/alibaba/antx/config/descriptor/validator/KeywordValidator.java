package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KeywordValidator extends RegexpValidator {
    private static final Log log = LogFactory.getLog(KeywordValidator.class);

    public Log getLogger() {
        return log;
    }

    public String getRegexp() {
        return "^\\w+$";
    }

    public String getMode() {
        return "exact";
    }

    protected String getDefaultMessage() {
        return "参数值必须是字母、数字或下划线";
    }
}
