package com.alibaba.antx.config.resource.http;

import java.net.URI;
import java.util.Set;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;

import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceDriver;
import com.alibaba.antx.config.resource.ResourceURI;
import com.alibaba.antx.config.resource.Session;
import com.alibaba.antx.config.resource.AuthenticationHandler.UsernamePassword;
import com.alibaba.antx.config.resource.util.ResourceContext;
import com.alibaba.antx.config.resource.util.ResourceKey;

public class HttpSession extends Session {
    private final HttpClient client;

    public HttpSession(ResourceDriver driver) {
        super(driver);

        client = new HttpClient();

        client.getParams().setAuthenticationPreemptive(true);
        client.getParams().setParameter(CredentialsProvider.PROVIDER, new CredentialsProvider() {
            public Credentials getCredentials(AuthScheme scheme, String host, int port, boolean proxy)
                    throws CredentialsNotAvailableException {
                URI uri = ResourceContext.get().getCurrentURI();
                String username = ResourceContext.get().getCurrentUsername();
                Set visitedURIs = ResourceContext.get().getVisitedURIs();
                ResourceKey key = new ResourceKey(new ResourceURI(uri));
                String message;

                message = "\n";
                message += "Authentication required.\n";
                message += "realm: " + scheme.getRealm() + "\n";
                message += "  uri: " + uri + "\n";

                UsernamePassword up = getResourceManager().getAuthenticationHandler().authenticate(message, uri,
                        username, visitedURIs.contains(key));

                visitedURIs.add(key);

                return new UsernamePasswordCredentials(up.getUsername(), up.getPassword());
            }
        });
    }

    public HttpClient getClient() {
        return client;
    }

    public boolean acceptOption(String optionName) {
        if ("charset".equals(optionName)) {
            return true;
        }

        return false;
    }

    public Resource getResource(final ResourceURI uri) {
        return new HttpResource(this, uri);
    }
}
