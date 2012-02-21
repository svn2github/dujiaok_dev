package com.alibaba.antx.config.descriptor.validator;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.util.StringUtil;

public class FileExistValidator extends ConfigValidator {
    private static final Log log = LogFactory.getLog(FileExistValidator.class);
    private String              filename;
    private File                file;

    public Log getLogger() {
        return log;
    }

    public void setFile(String filename) {
        this.filename = filename;
    }

    public boolean validate(String value) {
        if (value == null) {
            return true;
        }

        value = value.trim();

        if (StringUtil.isEmpty(value)) {
            return true;
        }

        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Validating file or directory: " + value);
        }

        if (filename == null) {
            file = new File(value);
        } else {
            file = new File(value, filename);
        }

        return file.isAbsolute() && file.exists();
    }

    protected String getDefaultMessage() {
        return "文件或目录不存在，或不是绝对路径：" + file;
    }
}
