package com.alibaba.antx.config.generator;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 在目录中生成文件的callback。
 * 
 * @author Michael Zhou
 */
public class DirectoryCallback implements ConfigGeneratorCallback {
    private final ConfigGenerator generator;
    private File                  destfileBase;
    private InputStream           istream;
    private OutputStream          ostream;

    public DirectoryCallback(ConfigGenerator generator) {
        this.generator = generator;
    }

    public DirectoryCallback(ConfigGenerator generator, File destfileBase) {
        this.generator = generator;
        this.destfileBase = destfileBase;
    }

    public void nextEntry(ConfigDescriptor descriptor, String template, String dest) {
        File templateBase = descriptor.getBaseFile();
        File destfileBase = this.destfileBase;

        if (destfileBase == null) {
            destfileBase = templateBase;
        }

        File templateFile = new File(templateBase, template);
        File destFile = new File(destfileBase, dest);

        // 创建destFile的父目录
        File destBase = destFile.getParentFile();

        destBase.mkdirs();

        if (!destBase.isDirectory()) {
            throw new ConfigException("Could not create directory: " + destBase.getAbsolutePath());
        }

        if (!templateFile.exists()) {
            throw new ConfigException("Could not find template file: " + templateFile.getAbsolutePath()
                    + " for descriptor: " + descriptor.getURL());
        }

        try {
            istream = new BufferedInputStream(new FileInputStream(templateFile), 8192);
            ostream = new BufferedOutputStream(new FileOutputStream(destFile), 8192);
        } catch (FileNotFoundException e) {
            throw new ConfigException(e);
        }

        generator.getSession().setInputStream(istream);
        generator.getSession().setOutputStream(ostream);
    }

    public void logEntry(ConfigDescriptor descriptor, String logfileName) {
        File templateBase = descriptor.getBaseFile();
        File destfileBase = this.destfileBase;

        if (destfileBase == null) {
            destfileBase = templateBase;
        }

        File logfile = new File(destfileBase, logfileName);

        // 创建logfile的父目录
        File logbase = logfile.getParentFile();

        logbase.mkdirs();

        if (!logbase.isDirectory()) {
            throw new ConfigException("Could not create directory: " + logbase.getAbsolutePath());
        }

        try {
            ostream = new BufferedOutputStream(new FileOutputStream(logfile), 8192);
        } catch (FileNotFoundException e) {
            throw new ConfigException(e);
        }

        generator.getSession().setOutputStream(ostream);
    }

    public void closeEntry() {
        if (istream != null) {
            try {
                istream.close();
            } catch (IOException e) {
            }

            istream = null;
        }

        if (ostream != null) {
            try {
                ostream.close();
            } catch (IOException e) {
            }

            ostream = null;
        }
    }
}
