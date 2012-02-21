package com.alibaba.antx.config.generator;

import com.alibaba.antx.config.descriptor.ConfigDescriptor;

public interface ConfigGeneratorCallback {
    /**
     * 切换到下一个目标文件，并设置相应的输入/输出流。
     */
    void nextEntry(ConfigDescriptor descriptor, String template, String destFile);

    /**
     * 切换到日志文件，并设置相应的输入/输出流。
     */
    void logEntry(ConfigDescriptor descriptor, String logfileName);

    /**
     * 关闭一个目标或日志文件。
     */
    void closeEntry();
}
