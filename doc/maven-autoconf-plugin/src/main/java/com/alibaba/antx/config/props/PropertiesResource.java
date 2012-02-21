package com.alibaba.antx.config.props;

import java.io.File;
import java.net.URI;

import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.ResourceNotFoundException;
import com.alibaba.antx.util.i18n.LocaleInfo;

public abstract class PropertiesResource {
    protected final ResourceManager manager;
    private boolean                 allowNonExistence;
    private boolean                 loaded;
    private Resource                resource;
    private URI                     uri;
    private String                  charset;

    public PropertiesResource(ResourceManager manager) {
        this(manager, false);
    }

    public PropertiesResource(ResourceManager manager, boolean allowNonExistence) {
        this.manager = manager;
        this.allowNonExistence = allowNonExistence;
    }

    public URI getURI() {
        return uri;
    }

    protected void setURI(URI uri) {
        this.uri = uri;
    }

    protected void setURI(File file) {
        this.uri = file == null ? null : file.toURI();
    }

    public String getCharset() {
        if (charset == null) {
            return LocaleInfo.getDefault().getCharset();
        }

        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Resource getResource() {
        return resource;
    }

    protected void setResource(Resource res) {
        this.resource = res;
    }

    public boolean isAllowNonExistence() {
        return allowNonExistence;
    }

    public void setAllowNonExistence(boolean allowNonExistence) {
        this.allowNonExistence = allowNonExistence;
    }

    public final void reload() {
        loaded = false;
        load();
    }

    protected final void load() {
        if (!loaded) {
            loaded = true;

            try {
                if (manager != null) {
                    if (resource == null && uri != null) {
                        resource = manager.getResource(uri);
                    }

                    manager.log("Loading " + uri);
                }

                onLoad();
            } catch (ResourceNotFoundException e) {
                if (!allowNonExistence) {
                    throw e;
                } else if (manager != null) {
                    manager.log("Not exists: " + uri);
                    onError();
                }
            }
        }
    }

    protected void onLoad() {
    }

    protected void onError() {
    }

    public String toString() {
        String className = getClass().getName();
        String desc = uri == null ? "" : uri.toString();

        return className.substring(className.lastIndexOf(".") + 1) + "[" + desc + "]";
    }
}
