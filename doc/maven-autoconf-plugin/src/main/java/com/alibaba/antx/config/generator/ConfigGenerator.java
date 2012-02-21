package com.alibaba.antx.config.generator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigLogger;
import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.descriptor.ConfigDescriptorLoader;
import com.alibaba.antx.config.descriptor.ConfigGenerate;
import com.alibaba.antx.config.props.PropertiesSet;
import com.alibaba.antx.util.FileUtil;
import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.i18n.LocaleInfo;

public class ConfigGenerator {
    final ConfigLogger             logger;
    private List                   configDescriptors     = new LinkedList();
    Map                            generateTemplateFiles = new HashMap();
    Map                            generateDestFiles     = new HashMap();
    private ConfigGeneratorSession session;
    private boolean                initialized           = false;

    public ConfigGenerator(ConfigLogger logger) {
        this.logger = logger;
    }

    /**
     * 添加一个descriptor，但必须在init方法被调用前。
     */
    public ConfigDescriptor addConfigDescriptor(ConfigResource descriptorResource) {
        URL descriptorURL = descriptorResource.getURL();
        InputStream istream = null;

        try {
            istream = descriptorURL.openStream();

            if (!(istream instanceof BufferedInputStream)) {
                istream = new BufferedInputStream(istream, 8192);
            }

            return addConfigDescriptor(descriptorResource, istream);
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 添加一个descriptor，但必须在init方法被调用前。
     */
    public ConfigDescriptor addConfigDescriptor(ConfigResource descriptorResource, InputStream istream) {
        if (initialized) {
            throw new IllegalStateException("Cannot add config descriptors after initialization");
        }

        ConfigDescriptorLoader loader = new ConfigDescriptorLoader();
        ConfigDescriptor descriptor = loader.load(descriptorResource, istream);

        configDescriptors.add(descriptor);

        return descriptor;
    }

    /**
     * 在所有的descriptor都被加入进来以后，需要执行该方法进行初始化。
     */
    public void init() {
        if (initialized) {
            return;
        }

        initialized = true;

        for (Iterator i = configDescriptors.iterator(); i.hasNext();) {
            ConfigDescriptor descriptor = (ConfigDescriptor) i.next();
            String basename = descriptor.getName() + "/../";

            for (int j = 0; j < descriptor.getGenerates().length; j++) {
                ConfigGenerate generate = descriptor.getGenerates()[j];

                // 设置charset/outputCharset
                String charset = generate.getCharset();
                String outputCharset = generate.getOutputCharset();

                if (charset == null) {
                    charset = LocaleInfo.getDefault().getCharset();
                }

                if (outputCharset == null) {
                    outputCharset = charset;
                }

                generate.setCharset(charset);
                generate.setOutputCharset(outputCharset);

                // 设置template/destfile
                String template = generate.getTemplate();
                String destFile = generate.getDestfile();

                if (StringUtil.isBlank(template)) {
                    logger.warn("Missing template attribute in <generate>: file=" + descriptor.getURL());

                    descriptor.removeGenerate(generate);
                    continue;
                }

                if (StringUtil.isBlank(destFile)) {
                    logger.warn("Missing destfile attribute in <generate>: file=" + descriptor.getURL());

                    descriptor.removeGenerate(generate);
                    continue;
                }

                template = FileUtil.normalizeUnixPath(basename + template);
                destFile = FileUtil.normalizeUnixPath(destFile);

                generate.setTemplate(template);
                generate.setDestfile(destFile);

                // 防止destfile重复
                if (generateDestFiles.containsKey(destFile)) {
                    ConfigGenerate originalGenerate = (ConfigGenerate) generateDestFiles.get(destFile);

                    if (originalGenerate.getConfigDescriptor() == descriptor) {
                        logger.info("Duplicated destfile " + destFile + "\n  in " + descriptor.getURL());
                    } else {
                        logger.info("Duplicated destfile " + destFile + "\n  in  " + descriptor.getURL() + "\n  and "
                                + originalGenerate.getConfigDescriptor().getURL());
                    }

                    descriptor.removeGenerate(generate);
                    continue;
                }

                generateDestFiles.put(destFile, generate);

                // 但template是可以重复的，一个template可生成多个文件
                List generates = (List) generateTemplateFiles.get(template);

                if (generates == null) {
                    generates = new LinkedList();
                    generateTemplateFiles.put(template, generates);
                }

                generates.add(generate);
            }
        }
    }

    private void ensureInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Cannot call this method before initialization");
        }
    }

    public ConfigDescriptor[] getConfigDescriptors() {
        ensureInitialized();
        return (ConfigDescriptor[]) configDescriptors.toArray(new ConfigDescriptor[configDescriptors.size()]);
    }

    public boolean isTemplateFile(String template) {
        ensureInitialized();
        return generateTemplateFiles.containsKey(template);
    }

    public boolean isDestFile(String destfile) {
        ensureInitialized();
        return generateDestFiles.containsKey(destfile);
    }

    public String getDescriptorLogFile(ConfigDescriptor descriptor) {
        return descriptor.getName() + ".log";
    }

    public boolean isDescriptorLogFile(String name) {
        ensureInitialized();

        for (Iterator i = configDescriptors.iterator(); i.hasNext();) {
            ConfigDescriptor descriptor = (ConfigDescriptor) i.next();

            if (getDescriptorLogFile(descriptor).equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ConfigGeneratorSession getSession() {
        ensureInitialized();

        if (session == null) {
            throw new IllegalStateException("ConfigGeneratorSession has not yet been initialized");
        }

        return session;
    }

    public ConfigGeneratorSession startSession(PropertiesSet propSet) {
        ensureInitialized();
        session = new ConfigGeneratorSession(this, propSet);
        return session;
    }

    public void closeSession() {
        ensureInitialized();

        if (this.session != null) {
            this.session.close();
            this.session = null;
        }
    }
}
