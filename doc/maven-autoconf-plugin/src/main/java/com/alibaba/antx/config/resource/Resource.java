package com.alibaba.antx.config.resource;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.alibaba.antx.config.ConfigException;

public abstract class Resource implements Comparable {
    private final Session     session;
    private final ResourceURI uri;

    public Resource(Session session, ResourceURI uri) {
        this.session = session;
        this.uri = uri;
    }

    public final Session getSession() {
        return session;
    }

    public final ResourceURI getURI() {
        return uri;
    }

    public abstract Resource getRelatedResource(String suburi);

    public String getContentType() {
        return null;
    }

    public String getCharset() {
        return null;
    }

    public abstract byte[] getContent();

    public abstract InputStream getInputStream();

    public abstract OutputStream getOutputStream();

    public abstract boolean isDirectory();

    public abstract List list();

    /**
     * Ŀ¼�����ļ�ǰ�棬Ȼ���ļ�������
     */
    public int compareTo(Object other) {
        if (other instanceof Resource) {
            Resource otherResource = (Resource) other;
            int type = isDirectory() ? 0 : 1;
            int otherType = otherResource.isDirectory() ? 0 : 1;

            if (type != otherType) {
                return type - otherType;
            } else {
                return uri.getName().compareTo(otherResource.uri.getName());
            }
        }

        return -1;
    }

    protected void assertFile() {
        if (isDirectory()) {
            throw new ConfigException("Resource is not a file: " + getURI());
        }
    }

    protected void assertDirectory() {
        if (!isDirectory()) {
            throw new ConfigException("Resource is not a directory: " + getURI());
        }
    }

    public String toString() {
        return uri.toString();
    }
}
