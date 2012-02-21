package com.alibaba.antx.config.resource;

import com.alibaba.antx.config.ConfigException;

public class ResourceNotFoundException extends ConfigException {
    private static final long serialVersionUID = -7175361449399092086L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
