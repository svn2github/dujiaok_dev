package com.alibaba.antx.config.resource.file;

import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceDriver;
import com.alibaba.antx.config.resource.ResourceURI;
import com.alibaba.antx.config.resource.Session;

public class FileSession extends Session {
    public FileSession(ResourceDriver driver) {
        super(driver);
    }

    public boolean acceptOption(String optionName) {
        return false;
    }

    public Resource getResource(ResourceURI uri) {
        return new FileResource(this, uri);
    }

}
