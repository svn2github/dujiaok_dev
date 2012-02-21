package com.alibaba.antx.config.descriptor.validator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.util.StringUtil;

public class HostExistValidator extends ConfigValidator {
    private static final Log log = LogFactory.getLog(HostExistValidator.class);
    private String              hostname;

    public Log getLogger() {
        return log;
    }

    public boolean validate(String value) {
        if (value == null) {
            return true;
        }

        value = value.trim();

        if (StringUtil.isEmpty(value)) {
            return true;
        }

        getLogger().info("Validating host name or IP address: " + value);

        hostname = value;

        try {
            InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            return false;
        }

        return true;
    }

    protected String getDefaultMessage() {
        return "非法的域名或IP：" + hostname;
    }
}
