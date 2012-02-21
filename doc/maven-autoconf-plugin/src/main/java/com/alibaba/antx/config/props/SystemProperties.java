package com.alibaba.antx.config.props;

import java.util.HashMap;
import java.util.Properties;
import java.util.TreeSet;

import com.alibaba.antx.config.generator.PropertiesLoader;

/**
 * 代表系统属性。
 * 
 * @author Michael Zhou
 */
public class SystemProperties extends PropertiesFile {
    public SystemProperties() {
        super(null);
    }

    protected void onLoad() {
        Properties sysprops = System.getProperties();

        keys = new TreeSet(sysprops.keySet());
        props = new HashMap();

        PropertiesLoader.mergeProperties(props, sysprops);
    }
}
