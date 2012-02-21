package com.alibaba.antx.config.generator;

import com.alibaba.antx.config.descriptor.ConfigDescriptor;

public interface ConfigGeneratorCallback {
    /**
     * �л�����һ��Ŀ���ļ�����������Ӧ������/�������
     */
    void nextEntry(ConfigDescriptor descriptor, String template, String destFile);

    /**
     * �л�����־�ļ�����������Ӧ������/�������
     */
    void logEntry(ConfigDescriptor descriptor, String logfileName);

    /**
     * �ر�һ��Ŀ�����־�ļ���
     */
    void closeEntry();
}
