package com.alibaba.antx.config.resource.ssh;

import com.alibaba.antx.config.resource.ResourceDriver;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.Session;

public class SshResourceDriver extends ResourceDriver {
    public SshResourceDriver(ResourceManager manager) {
        super(manager);
    }

    public Session open() {
        return new SshSession(this);
    }
}
