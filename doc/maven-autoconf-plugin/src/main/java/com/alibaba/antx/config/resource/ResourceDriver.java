package com.alibaba.antx.config.resource;

public abstract class ResourceDriver {
    private final ResourceManager manager;

    public ResourceDriver(ResourceManager manager) {
        this.manager = manager;
    }

    public ResourceManager getResourceManager() {
        return manager;
    }

    public abstract Session open();
}
