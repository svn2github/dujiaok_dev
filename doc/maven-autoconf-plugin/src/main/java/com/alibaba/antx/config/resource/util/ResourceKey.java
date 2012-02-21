package com.alibaba.antx.config.resource.util;

import java.io.Serializable;
import java.net.URI;

import com.alibaba.antx.config.resource.ResourceURI;
import com.alibaba.antx.util.StringUtil;

public class ResourceKey implements Serializable {
    private static final long serialVersionUID = -2027481344124093795L;
    private final String      scheme;
    private final String      user;
    private final String      host;
    private final int         port;

    public ResourceKey(String uri) {
        this(new ResourceURI(URI.create(uri)));
    }

    public ResourceKey(ResourceURI uri) {
        this(uri.getURI().getScheme(), ResourceUtil.getUsername(uri.getURI()), uri.getURI().getHost(), uri.getURI()
                .getPort());
    }

    public ResourceKey(String scheme, String user, String host, int port) {
        this.scheme = scheme;
        this.user = user;
        this.host = host;
        this.port = port <= 0 ? 22 : port;
    }

    public String getUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResourceKey other = (ResourceKey) obj;
        if (host == null) {
            if (other.host != null) {
                return false;
            }
        } else if (!host.equals(other.host)) {
            return false;
        }
        if (port != other.port) {
            return false;
        }
        if (scheme == null) {
            if (other.scheme != null) {
                return false;
            }
        } else if (!scheme.equals(other.scheme)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append(scheme).append("://");

        if (!StringUtil.isEmpty(user)) {
            buf.append(user).append("@");
        }

        buf.append(host);

        if (port > 0) {
            buf.append(":").append(port);
        }

        return buf.toString();
    }
}
