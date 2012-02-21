package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.util.StringUtil;

public class RequiredValidator extends ConfigValidator {
    private static final Log log = LogFactory.getLog(RequiredValidator.class);

    public Log getLogger() {
        return log;
    }

    public boolean validate(String value) {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Validating value: " + value);
        }

        if (value == null) {
            return false;
        }

        value = value.trim();

        return !StringUtil.isEmpty(value);
    }

    protected String getDefaultMessage() {
        return "Äú»¹Ã»ÓÐÌîÐ´" + getConfigProperty().getName();
    }
}
