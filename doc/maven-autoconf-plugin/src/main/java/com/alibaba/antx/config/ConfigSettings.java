package com.alibaba.antx.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;

import com.alibaba.antx.config.entry.ConfigEntryFactory;
import com.alibaba.antx.config.props.PropertiesSet;
import com.alibaba.antx.util.PatternSet;

public interface ConfigSettings extends ConfigLogger {
    BufferedReader getIn();

    PrintWriter getOut();

    PrintWriter getErr();

    String getCharset();

    PatternSet getDescriptorPatterns();

    PatternSet getPackagePatterns();

    String getInteractiveMode();

    String getMode();

    File[] getDestFiles();

    PropertiesSet getPropertiesSet();

    boolean isVerbose();

    ConfigEntryFactory getConfigEntryFactory();
}
