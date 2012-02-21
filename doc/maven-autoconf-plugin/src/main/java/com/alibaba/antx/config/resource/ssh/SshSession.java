package com.alibaba.antx.config.resource.ssh;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.resource.AuthenticationHandler.UsernamePassword;
import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceNotFoundException;
import com.alibaba.antx.config.resource.ResourceURI;
import com.alibaba.antx.config.resource.Session;
import com.alibaba.antx.config.resource.util.ResourceContext;
import com.alibaba.antx.config.resource.util.ResourceKey;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class SshSession extends Session {
    private final JSch jsch;
    private final Map  channels;

    public SshSession(SshResourceDriver driver) {
        super(driver);
        this.jsch = new JSch();
        this.channels = Collections.synchronizedMap(new HashMap());
    }

    public boolean acceptOption(String optionName) {
        if ("charset".equals(optionName)) {
            return true;
        }

        return false;
    }

    public Resource getResource(ResourceURI uri) {
        try {
            ChannelSftp channel = getOrCreateChannel(uri);
            SftpATTRS stat;

            try {
                stat = channel.stat(uri.getPath());
            } catch (SftpException e) {
                throw new ResourceNotFoundException(e);
            }

            return new SshResource(this, channel, uri, stat);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    protected ChannelSftp getOrCreateChannel(ResourceURI uri) {
        final ResourceKey key = new ResourceKey(uri);
        ChannelSftp channel;

        synchronized (channels) {
            channel = (ChannelSftp) channels.get(key);

            if (channel == null) {
                try {
                    ResourceContext.get().setCurrentURI(uri.getURI());

                    com.jcraft.jsch.Session session = jsch.getSession(key.getUser(), key.getHost(), key.getPort());

                    session.setUserInfo(new UserInfo() {
                        private UsernamePassword up;

                        public String getPassphrase() {
                            return null;
                        }

                        public boolean promptPassphrase(String message) {
                            return true;
                        }

                        public String getPassword() {
                            return up == null ? null : up.getPassword();
                        }

                        public boolean promptPassword(String message) {
                            URI uri = ResourceContext.get().getCurrentURI();
                            String username = ResourceContext.get().getCurrentUsername();
                            Set visitedURIs = ResourceContext.get().getVisitedURIs();

                            message = "\nAuthentication required: \n" + uri + "\n";

                            up = getResourceManager().getAuthenticationHandler().authenticate(message, uri, username,
                                    visitedURIs.contains(key));

                            visitedURIs.add(key);

                            return up != null;
                        }

                        public boolean promptYesNo(String str) {
                            return true;
                        }

                        public void showMessage(String message) {
                        }
                    });

                    session.connect();

                    channel = (ChannelSftp) session.openChannel("sftp");
                    channel.connect();

                    String charset = uri.getOption("charset");

                    if (charset != null) {
                        channel.setFilenameEncoding(charset);
                    }

                    channels.put(key, channel);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ConfigException(e);
                } finally {
                    ResourceContext.get().setCurrentURI(null);
                }
            }
        }

        return channel;
    }

    public void close() {
        synchronized (channels) {
            for (Iterator i = channels.values().iterator(); i.hasNext();) {
                ChannelSftp channel = (ChannelSftp) i.next();

                i.remove();

                if (channel != null) {
                    channel.quit();

                    
                    channel.getSession().disconnect();
                    
                }
            }
        }
    }
}
