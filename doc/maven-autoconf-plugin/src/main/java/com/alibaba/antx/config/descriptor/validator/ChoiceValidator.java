package com.alibaba.antx.config.descriptor.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.config.descriptor.ConfigValidatorException;
import com.alibaba.antx.util.StringUtil;

public class ChoiceValidator extends ConfigValidator {
    private static final Log log = LogFactory.getLog(ChoiceValidator.class);
    private String[]            choices;

    public Log getLogger() {
        return log;
    }

    public String[] getAllChoices() {
        return choices;
    }

    public void setChoice(String choice) {
        choices = StringUtil.split(choice);
    }

    public boolean validate(String value) {
        String[] choices = getAllChoices();

        if ((choices == null) || (choices.length == 0)) {
            throw new ConfigValidatorException("You must define an attribute called 'choice' for choice validator");
        }

        if (value == null) {
            return true;
        }

        value = value.trim();

        if (StringUtil.isEmpty(value)) {
            return true;
        }

        if (getLogger().isDebugEnabled()) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Validating value with choice[");

            for (int i = 0; i < choices.length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(choices[i]);
            }

            buffer.append("]: ").append(value);

            getLogger().debug(buffer.toString());
        }

        for (int i = 0; i < choices.length; i++) {
            if (value.equals(choices[i])) {
                return true;
            }
        }

        return false;
    }

    protected String getDefaultMessage() {
        StringBuffer buffer = new StringBuffer("您必须在下列值中选择：");
        String[] choices = getAllChoices();

        for (int i = 0; i < choices.length; i++) {
            if (i > 0) {
                buffer.append(", ");
            }

            buffer.append(choices[i]);
        }

        return buffer.toString();
    }
}
