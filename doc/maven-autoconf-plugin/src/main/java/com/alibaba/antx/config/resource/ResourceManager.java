package com.alibaba.antx.config.resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.resource.file.FileResourceDriver;
import com.alibaba.antx.config.resource.http.HttpResourceDriver;
import com.alibaba.antx.config.resource.ssh.SshResourceDriver;

public class ResourceManager {
    private final Map             drivers            = new HashMap();
    private final Map             sessions           = Collections.synchronizedMap(new HashMap()); // driver -> session
    private AuthenticationHandler authHandlerDefault = new DefaultAuthenticationHandler(this);
    private AuthenticationHandler authHandler;
    private PrintWriter           out;
    private BufferedReader        in;

    public ResourceManager() {
        registerDefaultDrivers();
    }

    private void registerDefaultDrivers() {
        drivers.put("file", new FileResourceDriver(this));
        drivers.put("http", new HttpResourceDriver(this));
        drivers.put("https", drivers.get("http"));
        drivers.put("ssh", new SshResourceDriver(this));
    }

    public AuthenticationHandler getAuthenticationHandler() {
        return authHandler == null ? authHandlerDefault : authHandler;
    }

    public void setAuthenticationHandler(AuthenticationHandler authHandler) {
        this.authHandler = authHandler;
    }

    public Resource getResource(URI uri) {
        Session session = getSession(uri.getScheme());

        return session.getResource(new ResourceURI(uri, session));
    }

    private Session getSession(String type) {
        ResourceDriver driver = (ResourceDriver) drivers.get(type);

        if (driver == null) {
            throw new ConfigException("No drivers for resource type: " + type);
        }

        Session session;

        synchronized (sessions) {
            session = (Session) sessions.get(driver);

            if (session == null) {
                session = driver.open();
                sessions.put(driver, session);
            }
        }

        return session;
    }

    public void close() {
        synchronized (sessions) {
            for (Iterator i = sessions.values().iterator(); i.hasNext();) {
                Session session = (Session) i.next();

                i.remove();
                session.close();
            }
        }
    }

    public void log(String message) {
        getOut().println(message);
        getOut().flush();
    }

    public PrintWriter getOut() {
        if (out == null) {
            out = new PrintWriter(System.out, true);
        }

        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;

    }

    public BufferedReader getIn() {
        if (in == null) {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
}
