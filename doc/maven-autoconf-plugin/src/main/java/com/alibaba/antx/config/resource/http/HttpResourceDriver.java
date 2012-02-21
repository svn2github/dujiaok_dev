package com.alibaba.antx.config.resource.http;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.alibaba.antx.config.resource.ResourceDriver;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.Session;

public class HttpResourceDriver extends ResourceDriver {
    static {
        Protocol.registerProtocol("https", new Protocol("https",
                (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), 443));
    }

    public HttpResourceDriver(ResourceManager manager) {
        super(manager);
    }

    public Session open() {
        return new HttpSession(this);
    }
}
