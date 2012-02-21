package com.alibaba.antx.config.entry;

import com.alibaba.antx.config.ConfigResource;

/**
 * 创建不同类型的<code>ConfigEntry</code>的工厂。
 * 
 * @author Michael Zhou
 */
public interface ConfigEntryFactory {
    ConfigEntry create(ConfigResource resource);
}
