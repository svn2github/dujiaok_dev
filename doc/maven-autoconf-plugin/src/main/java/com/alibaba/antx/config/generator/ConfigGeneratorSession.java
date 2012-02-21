package com.alibaba.antx.config.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.context.Context;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.descriptor.ConfigGenerate;
import com.alibaba.antx.config.props.PropertiesSet;
import com.alibaba.antx.util.i18n.LocaleInfo;

/**
 * ����һ������ʱ״̬��
 */
public class ConfigGeneratorSession {
    protected final ConfigGenerator generator;
    protected final Map             props;
    private final Map               descriptorLogs;
    private final Set               processedDestfiles;
    private ConfigGenerate          currentGenerate;
    private InputStream             currentInputStream;
    private OutputStream            currentOutputStream;

    protected ConfigGeneratorSession(ConfigGenerator generator, PropertiesSet propSet) {
        this.generator = generator;
        this.props = propSet.getMergedProperties();
        this.descriptorLogs = new HashMap();
        this.processedDestfiles = new HashSet();

        // ��ʼ����־������д�뵽��descriptor���е�Ŀ¼�С�
        ConfigDescriptor[] descriptors = generator.getConfigDescriptors();
        Date now = new Date();

        for (int i = 0; i < descriptors.length; i++) {
            ConfigDescriptor descriptor = descriptors[i];
            String descriptorName = descriptor.getName();
            StringWriter logBuffer = new StringWriter();
            PrintWriter log = new PrintWriter(logBuffer, true);

            descriptorLogs.put(descriptorName, new Object[] { logBuffer, log, descriptor });

            // ��ʼ��־����
            log.println("Last Configured at: " + now);
            log.println();

            log.println("Base URL: " + descriptor.getBaseURL());
            log.println("Descriptor: " + descriptorName);
            log.println();
        }
    }

    /**
     * ���õ�ǰ��������
     */
    public void setInputStream(InputStream istream) {
        this.currentInputStream = istream;
    }

    /**
     * ���õ�ǰ�������
     */
    public void setOutputStream(OutputStream ostream) {
        this.currentOutputStream = ostream;
    }

    /**
     * ȡ��velocity context��
     */
    public Context getVelocityContext() {
        if (currentGenerate == null) {
            throw new IllegalStateException("Have not call nextEntry method yet");
        }

        final Map descriptorProps = new HashMap(props);

        PropertiesLoader.mergeProperties(descriptorProps, currentGenerate.getConfigDescriptor().getContext());

        return new Context() {
            public Object put(String key, Object value) {
                Object oldValue = get(key);

                descriptorProps.put(key, value);

                return oldValue;
            }

            public Object get(String key) {
                return PropertiesLoader.evaluate(key, descriptorProps);
            }

            public boolean containsKey(Object key) {
                return descriptorProps.containsKey(key);
            }

            public Object[] getKeys() {
                Set keySet = descriptorProps.keySet();

                return (Object[]) keySet.toArray(new Object[keySet.size()]);
            }

            public Object remove(Object key) {
                Object oldValue = get(String.valueOf(key));

                descriptorProps.remove(key);

                return oldValue;
            }
        };
    }

    /**
     * ������template������Ӧ���ļ�����������־��
     */
    public void generate(ConfigGeneratorCallback callback) {
        for (Iterator i = generator.generateTemplateFiles.keySet().iterator(); i.hasNext();) {
            String template = (String) i.next();

            generate(template, callback);
        }

        generateLog(callback);
    }

    /**
     * ����ָ����template��������Ӧ���ļ���
     */
    public void generate(String template, ConfigGeneratorCallback callback) {
        List generates = (List) generator.generateTemplateFiles.get(template);

        if ((generates == null) || generates.isEmpty()) {
            throw new ConfigException("No defined template " + template);
        }

        for (Iterator i = generates.iterator(); i.hasNext();) {
            try {
                currentGenerate = (ConfigGenerate) i.next();

                callback.nextEntry(currentGenerate.getConfigDescriptor(), currentGenerate.getTemplate(),
                        currentGenerate.getDestfile());

                if ((currentInputStream == null) || (currentOutputStream == null)) {
                    throw new IllegalStateException("InputStream/OutputStream has not been set");
                }

                // ��¼�������destfiles
                processedDestfiles.add(currentGenerate.getDestfile());

                generate(currentGenerate, currentInputStream, currentOutputStream);
            } finally {
                try {
                    callback.closeEntry();
                } finally {
                    currentGenerate = null;
                    currentInputStream = null;
                    currentOutputStream = null;
                }
            }
        }
    }

    private void generate(ConfigGenerate generate, InputStream istream, OutputStream ostream) {
        String charset = generate.getCharset();
        String outputCharset = generate.getOutputCharset();
        PrintWriter descriptorLog = (PrintWriter) ((Object[]) descriptorLogs.get(generate.getConfigDescriptor()
                .getName()))[1];

        Reader reader = null;
        Writer writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(istream, charset)) {
                public void close() throws IOException {
                    // ����ر�
                }
            };
            writer = new BufferedWriter(new OutputStreamWriter(ostream, outputCharset)) {
                public void close() throws IOException {
                    // ����ر�
                }
            };

            descriptorLog.println("Generating " + generate.getTemplate() + " => " + generate.getDestfile());

            generator.logger.info("<" + generate.getConfigDescriptor().getBaseURL() + ">\n    Generating "
                    + generate.getTemplate() + " => " + generate.getDestfile() + "\n");

            VelocityTemplateEngine.getInstance().render(getVelocityContext(), reader, writer);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new ConfigException(e);
            }
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException e) {
                }
            }
        }
    }

    public void generateLog(ConfigGeneratorCallback callback) {
        for (Iterator i = descriptorLogs.values().iterator(); i.hasNext();) {
            try {
                Object[] logPair = (Object[]) i.next();
                StringWriter logBuffer = (StringWriter) logPair[0];
                PrintWriter log = (PrintWriter) logPair[1];
                ConfigDescriptor descriptor = (ConfigDescriptor) logPair[2];
                String logfile = generator.getDescriptorLogFile(descriptor);

                callback.logEntry(descriptor, logfile);

                String logContent = logBuffer.toString();
                Writer writer = null;

                try {
                    writer = new BufferedWriter(new OutputStreamWriter(currentOutputStream, LocaleInfo.getDefault()
                            .getCharset())) {
                        public void close() throws IOException {
                            // ����ر�
                        }
                    };

                    generator.logger.info("<" + descriptor.getBaseURL() + ">\n    Generating log file: " + logfile
                            + "\n");

                    writer.write(logContent);
                } catch (IOException e) {
                    throw new ConfigException(e);
                } finally {
                    if (writer != null) {
                        try {
                            writer.flush();
                        } catch (IOException e) {
                        }
                    }
                }
            } finally {
                callback.closeEntry();

                currentGenerate = null;
                currentInputStream = null;
                currentOutputStream = null;
            }
        }
    }

    /**
     * �鿴��û����©û�����ɵ�template��
     */
    public void checkNonprocessedTemplates() {
        for (Iterator i = generator.generateDestFiles.keySet().iterator(); i.hasNext();) {
            String destfile = (String) i.next();

            if (!processedDestfiles.contains(destfile)) {
                ConfigGenerate generate = (ConfigGenerate) generator.generateDestFiles.get(destfile);
                String template = generate.getTemplate();

                throw new ConfigException("Could not find template file: " + template + " for descriptor: "
                        + generate.getConfigDescriptor().getURL());
            }
        }
    }

    /**
     * �ر�session���ƺ�����
     */
    public void close() {
    }
}
