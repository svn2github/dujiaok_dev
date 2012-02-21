package com.alibaba.antx.config.descriptor;

import java.util.ArrayList;
import java.util.List;

public class ConfigGroup {
    private ConfigDescriptor descriptor;
    private String           name;
    private String           description;
    private List             properties = new ArrayList();

    public ConfigDescriptor getConfigDescriptor() {
        return descriptor;
    }

    public void setConfigDescriptor(ConfigDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addProperty(ConfigProperty configProperty) {
        configProperty.setConfigGroup(this);
        properties.add(configProperty);
    }

    public ConfigProperty[] getProperties() {
        return (ConfigProperty[]) properties.toArray(new ConfigProperty[properties.size()]);
    }

    public String toString() {
        return "Group[name=" + getName() + "]";
    }
}
