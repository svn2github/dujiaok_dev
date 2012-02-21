package com.alibaba.antx.config.descriptor;

import org.apache.commons.logging.Log;

import com.alibaba.antx.util.StringUtil;

/**
 *  Ù–‘—È÷§∆˜°£
 * 
 * @author Michael Zhou
 */
public abstract class ConfigValidator {
    private ConfigProperty property;
    private String         message;

    public ConfigDescriptor getConfigDescriptor() {
        return getConfigProperty().getConfigDescriptor();
    }

    public ConfigProperty getConfigProperty() {
        return property;
    }

    public void setConfigProperty(ConfigProperty property) {
        this.property = property;
    }

    public abstract Log getLogger();

    public String getMessage() {
        return StringUtil.isEmpty(message) ? getDefaultMessage() : message;
    }

    public void setMessage(String string) {
        message = string;
    }

    protected String getDefaultMessage() {
        return getConfigProperty().getName() + ": " + this;
    }

    public abstract boolean validate(String value);

    public String toString() {
        return StringUtil.getShortClassName(getClass());
    }
}
