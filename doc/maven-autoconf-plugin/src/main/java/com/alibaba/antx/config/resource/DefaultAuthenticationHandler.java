package com.alibaba.antx.config.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.resource.util.ResourceUtil;
import com.alibaba.antx.util.PasswordField;
import com.alibaba.antx.util.StringUtil;

/**
 * Ĭ�ϵĻ��ڿ���̨��AuthenticationHandler��
 */
public class DefaultAuthenticationHandler implements AuthenticationHandler {
    private final ResourceManager resourceManager;
    private final File            passwordFile;

    public DefaultAuthenticationHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.passwordFile = new File(System.getProperty("user.home"), "passwd.antxconfig");
    }

    public UsernamePassword authenticate(String message, URI uri, String username, boolean visited) {
        // ������URI�������δ��ѯ�ʹ��������Ŵ�password�ļ���ȡ������
        if (!visited) {
            UsernamePassword userPass = loadPassword(uri);

            if (userPass != null) {
                return userPass;
            }
        }

        resourceManager.getOut().println(message);

        String password;

        try {
            while (StringUtil.isEmpty(username)) {
                resourceManager.getOut().print("Enter Username: ");
                resourceManager.getOut().flush();
                username = StringUtil.trimWhitespace(resourceManager.getIn().readLine());
            }

            password = new PasswordField().getPassword(resourceManager.getOut(), "Enter Password: ", message);
        } catch (IOException e) {
            throw new ConfigException(e);
        }

        savePassword(uri, username, password);

        return new UsernamePassword(username, password);
    }

    private UsernamePassword loadPassword(URI uri) {
        Map passwords = loadPasswordFile();
        String key = getKey(uri);
        String encodedPassword = (String) passwords.get(key);

        if (encodedPassword == null) {
            return null;
        }

        String userPass;

        try {
            userPass = new String(Base64.decodeBase64(encodedPassword.getBytes("8859_1")), "8859_1");
        } catch (Exception e) {
            throw new ConfigException(e);
        }

        int index = userPass.indexOf(":");
        String user = null;
        String pass = null;

        if (index >= 0) {
            user = userPass.substring(0, index);
            pass = userPass.substring(index + 1);
        } else {
            return null;
        }

        return new UsernamePassword(user, pass);
    }

    private void savePassword(URI uri, String username, String password) {
        try {
            Properties passwords = loadPasswordFile();

            String key = getKey(uri);
            String userPass = username + ":" + password;
            String encodedPassword;

            encodedPassword = new String(Base64.encodeBase64(userPass.getBytes("8859_1")), "8859_1");

            passwords.put(key, encodedPassword);

            boolean succ = savePasswordFile(passwords);

            if (!succ) {
                throw new ConfigException("Cannot save file: " + passwordFile.getAbsolutePath());
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    private Properties loadPasswordFile() {
        Properties passwords = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream(passwordFile);
            passwords.load(is);
        } catch (Exception e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        return passwords;
    }

    private boolean savePasswordFile(Properties passwords) throws IOException, FileNotFoundException {
        File tmp = File.createTempFile(passwordFile.getName() + ".", ".tmp", passwordFile.getParentFile());
        OutputStream os = null;

        tmp.deleteOnExit();

        try {
            os = new FileOutputStream(tmp);
            passwords.store(os, "Passwords for antxconfig");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        passwordFile.delete();

        return tmp.renameTo(passwordFile);
    }

    private String getKey(URI uri) {
        StringBuffer buf = new StringBuffer();

        buf.append(uri.getScheme()).append("://");

        String user = ResourceUtil.getUsername(uri);
        if (!StringUtil.isEmpty(user)) {
            buf.append(user).append("@");
        }

        buf.append(uri.getHost());

        if (uri.getPort() > 0) {
            buf.append(":").append(uri.getPort());
        }

        return buf.toString();
    }
}
