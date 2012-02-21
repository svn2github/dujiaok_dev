package com.alibaba.antx.config.descriptor.validator;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.util.StringUtil;

public class UrlValidator extends ConfigValidator {
    private static final Log log            = LogFactory.getLog(UrlValidator.class);
    private boolean             checkHostExist = true;
    private String[]            protocols;
    private boolean             endsWithSlash  = true;
    private String              message;

    public Log getLogger() {
        return log;
    }

    public void setCheckHostExist(boolean checkHostExist) {
        this.checkHostExist = checkHostExist;
    }

    public void setProtocols(String protocols) {
        this.protocols = StringUtil.split(protocols);
    }

    public void setEndsWithSlash(boolean endsWithSlash) {
        this.endsWithSlash = endsWithSlash;
    }

    public boolean validate(String value) {
        if (value == null) {
            return true;
        }

        value = value.trim();

        if (StringUtil.isEmpty(value)) {
            return true;
        }

        URL url;

        // ���URL�Ϸ���
        getLogger().info("Checking URL validility: " + value);
        message = "�Ƿ���URL��" + value;

        try {
            url = new URL(value);

            if (StringUtil.isBlank(url.getHost())) {
                return false;
            }
        } catch (MalformedURLException e) {
            return false;
        }

        // ���Э��
        if (!validateProtocols(url.getProtocol())) {
            message = getProtocolMessage();
            return false;
        }

        // ��������������
        if (checkHostExist) {
            getLogger().info("Validating host name or IP address: " + url.getHost());

            try {
                InetAddress.getByName(url.getHost());
            } catch (UnknownHostException e) {
                message = "�Ƿ���������IP��" + url.getHost();
                return false;
            }
        }

        // ����Ƿ���/��β
        if (endsWithSlash) {
            if (!value.endsWith("/")) {
                message = "URL����/��β";
                return false;
            }
        }

        return true;
    }

    private boolean validateProtocols(String protocol) {
        if (protocol == null) {
            return false;
        }

        protocol = protocol.trim();

        if (StringUtil.isEmpty(protocol)) {
            return false;
        }

        if ((protocols == null) || (protocols.length == 0)) {
            return true;
        }

        if (getLogger().isDebugEnabled()) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Validating protocol with choice[");

            for (int i = 0; i < protocols.length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(protocols[i]);
            }

            buffer.append("]: ").append(protocol);

            getLogger().debug(buffer.toString());
        }

        for (int i = 0; i < protocols.length; i++) {
            if (protocol.equals(protocols[i])) {
                return true;
            }
        }

        return false;
    }

    protected String getDefaultMessage() {
        return message;
    }

    protected String getProtocolMessage() {
        StringBuffer buffer = new StringBuffer("������������ֵ��ѡ��Э�飺");

        for (int i = 0; i < protocols.length; i++) {
            if (i > 0) {
                buffer.append(", ");
            }

            buffer.append(protocols[i]);
        }

        return buffer.toString();
    }
}
