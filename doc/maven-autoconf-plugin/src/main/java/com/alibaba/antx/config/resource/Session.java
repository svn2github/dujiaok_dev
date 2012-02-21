package com.alibaba.antx.config.resource;

public abstract class Session {
    private final ResourceDriver driver;

    public Session(ResourceDriver driver) {
        this.driver = driver;
    }

    public ResourceManager getResourceManager() {
        return driver.getResourceManager();
    }

    public ResourceDriver getResourceDriver() {
        return driver;
    }

    public abstract Resource getResource(ResourceURI uri);

    public abstract boolean acceptOption(String optionName);

    public void close() {
    }
}
