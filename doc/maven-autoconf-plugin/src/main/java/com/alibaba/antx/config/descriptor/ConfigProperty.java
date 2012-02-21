package com.alibaba.antx.config.descriptor;

import com.alibaba.antx.config.descriptor.validator.RequiredValidator;

import java.util.ArrayList;
import java.util.List;

public class ConfigProperty {
    private ConfigGroup group;
    private String      name;
    private String      defaultValue;
    private String      description;
    private boolean     required   = true;
    private boolean     nullable   = true;
    private List        validators = new ArrayList();

    public ConfigDescriptor getConfigDescriptor() {
        return getConfigGroup().getConfigDescriptor();
    }

    public ConfigGroup getConfigGroup() {
        return group;
    }

    public void setConfigGroup(ConfigGroup group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List getValidators() {
        return validators;
    }

    public void addValidator(ConfigValidator validator) {
        validator.setConfigProperty(this);
        validators.add(validator);

        if (validator instanceof RequiredValidator) {
            this.nullable = false;
        }
    }

    public void afterPropertiesSet() {
        // RequiredValidator是一个特殊的validator，默认情况下required=true
        if (required) {
            addValidator(new RequiredValidator());
        }
    }

    public boolean isNullable() {
        return nullable;
    }

    public String toString() {
        return "Property[name=" + getName() + "]";
    }
}
