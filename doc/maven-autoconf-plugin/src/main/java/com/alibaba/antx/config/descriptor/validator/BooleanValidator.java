package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BooleanValidator extends ChoiceValidator {
    private static final Log log     = LogFactory.getLog(BooleanValidator.class);
    private final String[]      choices = { "true", "false" };

    public Log getLogger() {
        return log;
    }

    public String[] getAllChoices() {
        return choices;
    }
}
