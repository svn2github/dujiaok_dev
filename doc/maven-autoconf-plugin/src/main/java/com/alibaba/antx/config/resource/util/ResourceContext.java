package com.alibaba.antx.config.resource.util;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public final class ResourceContext {
    private static ThreadLocal contextHolder = new ThreadLocal();
    private URI                currentURI;
    private String             currentUsername;
    private Set                visitedURIs   = new HashSet();

    private ResourceContext() {
    }

    public static ResourceContext get() {
        ResourceContext context = (ResourceContext) contextHolder.get();

        if (context == null) {
            context = new ResourceContext();
            contextHolder.set(context);
        }

        return context;
    }

    public static void clear() {
        contextHolder.set(null);
    }

    public URI getCurrentURI() {
        return currentURI;
    }

    public void setCurrentURI(URI currentURI) {
        this.currentURI = currentURI;
        this.currentUsername = ResourceUtil.getUsername(currentURI);
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public Set getVisitedURIs() {
        return visitedURIs;
    }
}
