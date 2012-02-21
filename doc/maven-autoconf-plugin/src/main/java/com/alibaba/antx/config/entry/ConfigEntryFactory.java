package com.alibaba.antx.config.entry;

import com.alibaba.antx.config.ConfigResource;

/**
 * ������ͬ���͵�<code>ConfigEntry</code>�Ĺ�����
 * 
 * @author Michael Zhou
 */
public interface ConfigEntryFactory {
    ConfigEntry create(ConfigResource resource);
}
