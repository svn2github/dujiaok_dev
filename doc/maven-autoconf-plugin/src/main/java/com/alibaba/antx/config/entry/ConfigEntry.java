package com.alibaba.antx.config.entry;

import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.generator.ConfigGenerator;
import com.alibaba.antx.util.PatternSet;
import com.alibaba.antx.util.SelectorUtil;
import com.alibaba.antx.util.scanner.DefaultScannerHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ����һ�������������Ϣ��
 * 
 * @author Michael Zhou
 */
public abstract class ConfigEntry {
    private ConfigSettings        settings;
    private ConfigResource        resource;
    private PatternSet            descriptorPatterns;
    private PatternSet            packagePatterns;
    protected ConfigEntry[]       subEntries;
    private final ConfigGenerator generator;

    /**
     * ����һ����㡣
     * 
     * @param resource ָ��������Դ
     * @param settings antxconfig������
     */
    public ConfigEntry(ConfigResource resource, ConfigSettings settings) {
        this.resource = resource;
        this.settings = settings;
        this.generator = new ConfigGenerator(settings);
    }

    /**
     * ȡ�ý������ơ�
     * 
     * @return ��������
     */
    public String getName() {
        return getConfigEntryResource().getName();
    }

    /**
     * �������κ�descriptor��sub entries�Ŀս�㡣
     */
    public boolean isEmpty() {
        return (getSubEntries().length == 0) && (getGenerator().getConfigDescriptors().length == 0);
    }

    /**
     * ȡ����Դ��
     * 
     * @return ��Դ
     */
    public ConfigResource getConfigEntryResource() {
        return resource;
    }

    /**
     * ȡ��config���á�
     * 
     * @return config����
     */
    public ConfigSettings getConfigSettings() {
        return settings;
    }

    /**
     * ȡ��config descriptor��patterns�����δ���ã���ʹ��Ĭ��ֵ��
     * 
     * @return config descriptor��pattern
     */
    public PatternSet getDescriptorPatterns() {
        return descriptorPatterns;
    }

    /**
     * ����config descriptor��pattern�����δ���ã���ʹ��Ĭ��ֵ��
     * 
     * @param descriptorsPatterns config descriptor��pattern
     */
    public void setDescriptorPatterns(PatternSet descriptorPatterns) {
        this.descriptorPatterns = descriptorPatterns;
    }

    /**
     * ȡ������ƥ�䵱ǰ����µ������ӽ���pattern��
     * 
     * @return pattern
     */
    public PatternSet getPackagePatterns() {
        return packagePatterns;
    }

    /**
     * ��������ƥ�䵱ǰ����µ������ӽ���pattern��
     * 
     * @param packagePatterns pattern
     */
    public void setPackagePatterns(PatternSet packagePatterns) {
        this.packagePatterns = packagePatterns;
    }

    /**
     * ȡ�õ�ǰconfig����Ӧ��generator��
     * 
     * @return generator����
     */
    public ConfigGenerator getGenerator() {
        return generator;
    }

    /**
     * ȡ�õ�ǰconfig����µ������ӽ�㡣
     * 
     * @return �ӽ�����飬��������ڣ��򷵻ؿ�����
     */
    public ConfigEntry[] getSubEntries() {
        return subEntries;
    }

    /**
     * ɨ���㡣
     */
    public void scan() {
        scan(null);
    }

    /**
     * ɨ���㡣
     */
    protected abstract void scan(InputStream istream);

    /**
     * װ��descriptor��context�����������ļ���
     */
    protected void populateDescriptorContext(Map context, String string) {
    }

    /**
     * ���������ļ���
     */
    public void generate() {
        generate(null, null);
    }

    /**
     * ���������ļ���
     */
    protected abstract void generate(InputStream istream, OutputStream ostream);

    /**
     * ɨ�账������
     */
    public class Handler extends DefaultScannerHandler {
        private List subEntries = new ArrayList();

        public ConfigEntry[] getSubEntries() {
            return (ConfigEntry[]) subEntries.toArray(new ConfigEntry[subEntries.size()]);
        }

        public void startScanning() {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Scanning ").append(getScanner().getBaseURL()).append("\n");
            buffer.append("  descriptors: ").append(getDescriptorPatterns()).append("\n");
            buffer.append("     packages: ").append(getPackagePatterns()).append("\n");

            settings.debug(buffer.toString());
        }

        public void file() {
            String name = getScanner().getPath();

            if (isDescriptorFile(name)) {
                settings.debug("Loading descriptor " + getScanner().getURL() + "\n");

                loadDescriptor();
            } else if (isPackageFile(name)) {
                ConfigResource resource = new ConfigResource(getScanner().getURL(), name);
                ConfigEntryFactory factory = getConfigSettings().getConfigEntryFactory();
                ConfigEntry subEntry = factory.create(resource);

                InputStream istream = null;

                try {
                    istream = getScanner().getInputStream();
                    subEntry.scan(istream);
                } finally {
                    if (istream != null) {
                        try {
                            istream.close();
                        } catch (IOException e) {
                        }
                    }
                }

                if (!subEntry.isEmpty()) {
                    subEntries.add(subEntry);
                }
            }
        }

        public void directory() {
            String name = getScanner().getPath();

            if (isPackageFile(name)) {
                ConfigResource resource = new ConfigResource(getScanner().getURL(), name);
                ConfigEntryFactory factory = getConfigSettings().getConfigEntryFactory();
                ConfigEntry subEntry = factory.create(resource);

                subEntry.scan();

                if (!subEntry.isEmpty()) {
                    subEntries.add(subEntry);
                }
            }
        }

        /**
         * �Ƿ����ָ��Ŀ¼���ļ����÷������������ɨ���ٶȡ�
         * 
         * @return ����ǣ��򷵻�<code>true</code>
         */
        public boolean followUp() {
            String name = getScanner().getPath();
            boolean followUp = false;

            followUp |= SelectorUtil.matchPathPrefix(name, getDescriptorPatterns().getIncludes(),
                    getDescriptorPatterns().getExcludes());

            followUp |= SelectorUtil.matchPathPrefix(name, getPackagePatterns().getIncludes(), getPackagePatterns()
                    .getExcludes());

            if (isPackageFile(name)) {
                return false;
            }

            if (!followUp) {
                getConfigSettings().debug("Skipping directory " + name);
            }

            return followUp;
        }

        /**
         * װ��descriptor��
         */
        private void loadDescriptor() {
            URL descriptorURL = getScanner().getURL();
            ConfigResource descriptorResource = new ConfigResource(descriptorURL, getScanner().getPath());

            ConfigDescriptor descriptor;
            InputStream istream = null;

            try {
                istream = getScanner().getInputStream();
                descriptor = getGenerator().addConfigDescriptor(descriptorResource, istream);
            } finally {
                if (istream != null) {
                    try {
                        istream.close();
                    } catch (IOException e) {
                    }
                }
            }

            populateDescriptorContext(descriptor.getContext(), descriptor.getName());
        }

        /**
         * �鿴ָ�������Ƿ����descriptor��patterns��
         * 
         * @param name Ҫƥ�������
         * @return �������descriptor��patterns���򷵻�<code>true</code>
         */
        private boolean isDescriptorFile(String name) {
            return SelectorUtil.matchPath(name, getDescriptorPatterns().getIncludes(), getDescriptorPatterns()
                    .getExcludes());
        }

        /**
         * �鿴ָ�������Ƿ����jarfile��patterns��
         * 
         * @param name Ҫƥ�������
         * @return �������jarfile��patterns���򷵻�<code>true</code>
         */
        private boolean isPackageFile(String name) {
            return SelectorUtil.matchPath(name, getPackagePatterns().getIncludes(), getPackagePatterns().getExcludes());
        }
    }
}
