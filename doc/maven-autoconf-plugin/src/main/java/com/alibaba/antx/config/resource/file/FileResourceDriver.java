package com.alibaba.antx.config.resource.file;

import com.alibaba.antx.config.resource.ResourceDriver;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.Session;

public class FileResourceDriver extends ResourceDriver {
    public FileResourceDriver(ResourceManager manager) {
        super(manager);
    }

    public Session open() {
        return new FileSession(this);
    }
}
