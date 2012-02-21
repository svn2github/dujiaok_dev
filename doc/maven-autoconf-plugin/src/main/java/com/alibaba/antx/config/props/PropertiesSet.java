package com.alibaba.antx.config.props;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.generator.PropertiesLoader;
import com.alibaba.antx.config.generator.expr.Expression;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.ResourceURI;
import com.alibaba.antx.util.StringUtil;

/**
 * ����һ��props�ļ�����ϡ�
 * 
 * @author Michael Zhou
 */
public class PropertiesSet {
    private final ResourceManager  manager;
    private final SystemProperties systemProps;
    private boolean                inited;
    private PropertiesResource[]   sharedPropertiesFiles;
    private PropertiesFile[]       sharedPropertiesFilesExpanded;
    private PropertiesFile         userPropertiesFile;
    private Map                    namedPropertiesFiles;         // Map: name => List of shared properties file names
    private String                 sharedName;
    private Map                    mergedProps;
    private Set                    mergedKeys;

    public PropertiesSet() {
        this(null, null);
    }

    public PropertiesSet(BufferedReader in, PrintWriter out) {
        this.manager = new ResourceManager();
        this.systemProps = new SystemProperties();

        manager.setIn(in);
        manager.setOut(out);
    }

    public SystemProperties getSystemProperties() {
        return systemProps;
    }

    public PropertiesFile getUserPropertiesFile() {
        return userPropertiesFile;
    }

    public void setUserPropertiesFile(String userPropertiesFile) {
        this.userPropertiesFile = new PropertiesFile(manager, userPropertiesFile);
    }

    public PropertiesResource[] getSharedPropertiesFiles() {
        return sharedPropertiesFiles;
    }

    public void setSharedPropertiesFiles(String[] sharedPropertiesFiles) {
        if (sharedPropertiesFiles == null) {
            this.sharedPropertiesFiles = new PropertiesFile[0];
            return;
        }

        PropertiesResource[] files = new PropertiesResource[sharedPropertiesFiles.length];

        for (int i = 0; i < sharedPropertiesFiles.length; i++) {
            String sharedPropertiesFile = sharedPropertiesFiles[i];

            // ������װfile�����ʧ�ܣ�������URI
            URI uri = ResourceURI.guessURI(sharedPropertiesFile);

            if (new ResourceURI(uri).guessDirectory()) {
                files[i] = new PropertiesFileSet(manager, uri);
            } else {
                files[i] = new PropertiesFile(manager, uri);
            }
        }

        this.sharedPropertiesFiles = files;
    }

    public String getSharedPropertiesFilesName() {
        return sharedName;
    }

    public void setSharedPropertiesFilesName(String sharedName) {
        this.sharedName = StringUtil.isEmpty(sharedName) ? null : sharedName;
    }

    public Map getMergedProperties() {
        init();
        return mergedProps;
    }

    public Set getMergedKeys() {
        init();
        return mergedKeys;
    }

    public PropertiesFile[] getSharedPropertiesFilesExpanded() {
        return sharedPropertiesFilesExpanded;
    }

    public void init() {
        if (inited) {
            return;
        }

        inited = true;

        // user props
        if (userPropertiesFile == null) {
            // ����antx.properties
            File defaultPropertiesFile;

            // ���ҵ�ǰĿ¼
            defaultPropertiesFile = new File("antx.properties").getAbsoluteFile();

            if (!defaultPropertiesFile.exists() || !defaultPropertiesFile.isFile()) {
                defaultPropertiesFile = new File(System.getProperty("user.home"), "antx.properties");
            }

            userPropertiesFile = new PropertiesFile(manager, defaultPropertiesFile.getAbsoluteFile());
        }

        userPropertiesFile.setAllowNonExistence(true);

        // ��system props��user props��ȡ�ã�
        // antx.properties.name1.1
        // antx.properties.name1.2
        // antx.properties.name1.3
        // antx.properties.name1.4
        // antx.properties.name2.1
        // antx.properties.name2.2
        // antx.properties.name2.3
        // antx.properties.name2.4
        Map props = new HashMap();

        PropertiesLoader.mergeProperties(props, systemProps.getProperties());
        PropertiesLoader.mergeProperties(props, userPropertiesFile.getProperties());

        namedPropertiesFiles = getNamedSharedPropertiesFiles(props);

        // ȡ��antx.properties���������û�У�Ĭ��ֵΪ"default",
        if (sharedName == null) {
            if (sharedPropertiesFiles != null && sharedPropertiesFiles.length > 0) {
                sharedName = "default";
            } else {
                Object value = props.get("antx.properties");

                if (value instanceof Expression) {
                    value = ((Expression) value).evaluate(null);
                }

                sharedName = (String) value;

                if (StringUtil.isEmpty(sharedName)) {
                    sharedName = "default";
                }
            }
        }

        // �������������ָ����sharedPropertiesFiles�������namedSharedPropertiesFiles
        // ��������sharedPropertiesFiles
        if (sharedPropertiesFiles != null && sharedPropertiesFiles.length > 0) {
            List fileList = new ArrayList(sharedPropertiesFiles.length);

            for (int i = 0; i < sharedPropertiesFiles.length; i++) {
                PropertiesResource resource = sharedPropertiesFiles[i];

                fileList.add(resource.getURI().toString());
            }

            namedPropertiesFiles.put(sharedName, fileList);
        } else {
            List fileList = (List) namedPropertiesFiles.get(sharedName);
            String[] files = null;

            if (fileList != null) {
                files = (String[]) fileList.toArray(new String[fileList.size()]);
            }

            setSharedPropertiesFiles(files);
        }

        // װ�ز��ϲ�����shared properties
        loadUserProperties(false);
    }

    private final static Pattern ANTX_PROPERTIES_PATTERN;

    static {
        try {
            ANTX_PROPERTIES_PATTERN = new Perl5Compiler().compile("antx\\.properties\\.(\\w+)(\\.(\\d+))?",
                    Perl5Compiler.READ_ONLY_MASK);
        } catch (MalformedPatternException e) {
            throw new ConfigException(e);
        }
    }

    /**
     * �ж�property name�Ƿ񸲸�shared properties�е�ֵ��
     */
    public boolean isShared(String name) {
        for (int i = sharedPropertiesFilesExpanded.length - 1; i >= 0; i--) {
            PropertiesFile sharedFile = sharedPropertiesFilesExpanded[i];

            if (sharedFile.getProperties().containsKey(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * ��mergedProperties�е�ֵ����ȥ��sharedProperperties�к�systemProperties�е�ֵ��
     */
    public Map getModifiedProperties() {
        Map modifiedProperties = new HashMap();

        for (Iterator i = getMergedKeys().iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = toString(getMergedProperties().get(key));
            PatternMatcher matcher = new Perl5Matcher();

            if (value == null) {
                continue;
            }

            // ����antx.properties.*����Ϊ����������
            if (matcher.matches(key, ANTX_PROPERTIES_PATTERN) || "antx.properties".equals(key)) {
                continue;
            }

            String defaultValue = null;

            // ��shared properties����key
            PropertiesFile[] files = getSharedPropertiesFilesExpanded();

            for (int j = files.length - 1; j >= 0; j--) {
                if (files[j].getProperties().containsKey(key)) {
                    defaultValue = toString(files[j].getProperties().get(key));
                    break;
                }
            }

            // ��system properties����key
            if (defaultValue == null && getSystemProperties().getProperties().containsKey(key)) {
                defaultValue = toString(getSystemProperties().getProperties().get(key));
            }

            // ���û�ҵ�������ֵ��ͬ�������modified properties
            if (defaultValue == null || !defaultValue.equals(value)) {
                modifiedProperties.put(key, value);
            }
        }

        // ����antx.properties.*
        for (Iterator i = namedPropertiesFiles.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            List fileList = (List) entry.getValue();
            int index = 1;

            for (Iterator j = fileList.iterator(); j.hasNext(); index++) {
                String file = (String) j.next();
                String key = "antx.properties." + name;

                if (index > 1 || j.hasNext()) {
                    key += "." + index;
                }

                modifiedProperties.put(key, file);
            }
        }

        if (!"default".equals(sharedName)) {
            modifiedProperties.put("antx.properties", sharedName);
        }

        return modifiedProperties;
    }

    private String toString(Object value) {
        if (value == null || value instanceof String) {
            return (String) value;
        }

        if (value instanceof Expression) {
            return ((Expression) value).getExpressionText();
        }

        return String.valueOf(value);
    }

    public void reloadUserProperties() {
        loadUserProperties(true);
    }

    private void loadUserProperties(boolean reload) {
        mergedProps = new HashMap();
        mergedKeys = new TreeSet();

        // system properties
        mergedKeys.addAll(getSystemProperties().getKeys());
        PropertiesLoader.mergeProperties(mergedProps, getSystemProperties().getProperties());

        // shared properties
        List expandedFiles = new LinkedList();

        for (int i = 0; i < getSharedPropertiesFiles().length; i++) {
            loadResource(getSharedPropertiesFiles()[i], mergedProps, mergedKeys, expandedFiles);
        }

        sharedPropertiesFilesExpanded = (PropertiesFile[]) expandedFiles.toArray(new PropertiesFile[expandedFiles
                .size()]);

        // user properties
        if (reload) {
            getUserPropertiesFile().reload();
        }

        mergedKeys.addAll(getUserPropertiesFile().getKeys());
        PropertiesLoader.mergeProperties(mergedProps, getUserPropertiesFile().getProperties());

        checkOverlap(reload);
    }

    /**
     * ���shared properties�еı����ǵ�ֵ��
     */
    private void checkOverlap(boolean reload) {
        for (Iterator i = getMergedKeys().iterator(); i.hasNext();) {
            String key = (String) i.next();
            PatternMatcher matcher = new Perl5Matcher();

            // ����antx.properties.*����Ϊ����������
            if (matcher.matches(key, ANTX_PROPERTIES_PATTERN) || "antx.properties".equals(key)) {
                continue;
            }

            // ��shared properties����key
            PropertiesFile[] files = getSharedPropertiesFilesExpanded();
            List definedInFiles = new ArrayList(files.length + 1);

            if (userPropertiesFile.getProperties().containsKey(key)) {
                definedInFiles.add(userPropertiesFile);
            }

            for (int j = files.length - 1; j >= 0; j--) {
                if (files[j].getProperties().containsKey(key)) {
                    definedInFiles.add(files[j]);
                }
            }

            // �ж��ظ�
            if (definedInFiles.size() > 1) {
                StringBuffer message = new StringBuffer();
                boolean overlapByUserProperties = definedInFiles.get(0) == userPropertiesFile;

                if (!reload || overlapByUserProperties) {
                    message.append("���Ǿ��棺 ");

                    if (overlapByUserProperties) {
                        message.append("�û�properties�ļ��е�ֵ��").append(key).append("��������").append("����properties�ļ��е�ֵ��\n");
                    } else {
                        message.append("��").append(key).append("��������").append(definedInFiles.size()).append(
                                "������properties�ļ��У�����ֵ���Ե�һ��Ϊ׼����\n");
                    }

                    for (Iterator k = definedInFiles.iterator(); k.hasNext();) {
                        PropertiesFile f = (PropertiesFile) k.next();

                        message.append("  - ").append(f.getURI());
                        message.append(", value = ").append(toString(f.getProperties().get(key))).append("\n");
                    }

                    System.out.flush();
                    System.err.println(message);
                    System.err.flush();
                }
            }
        }
    }

    // Map: name => List of shared properties file names
    private Map getNamedSharedPropertiesFiles(Map props) {
        Map names = new TreeMap();

        for (Iterator i = props.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            PatternMatcher matcher = new Perl5Matcher();

            if (matcher.matches(key, ANTX_PROPERTIES_PATTERN)) {
                MatchResult result = matcher.getMatch();
                String name = result.group(1);
                int index;

                try {
                    index = Integer.parseInt(result.group(3));
                } catch (NumberFormatException e) {
                    index = 0;
                }

                if (!names.containsKey(name)) {
                    names.put(name, new TreeMap());
                }

                Object value = props.get(key);

                if (value instanceof Expression) {
                    value = ((Expression) value).evaluate(null);
                }

                ((Map) names.get(name)).put(new Integer(index), value);
            }
        }

        for (Iterator i = names.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            List files = new ArrayList(((Map) entry.getValue()).values());

            entry.setValue(files);
        }

        return names;
    }

    private void loadResource(PropertiesResource resource, Map mergedProperties, Set mergedKeys, List expandedFiles) {
        if (resource instanceof PropertiesFile) {
            PropertiesFile file = (PropertiesFile) resource;

            expandedFiles.add(file);
            mergedKeys.addAll(file.getKeys());

            PropertiesLoader.mergeProperties(mergedProperties, file.getProperties());
        } else if (resource instanceof PropertiesFileSet) {
            PropertiesFileSet files = (PropertiesFileSet) resource;

            for (Iterator i = files.getPropertiesFiles().iterator(); i.hasNext();) {
                PropertiesResource pr = (PropertiesResource) i.next();

                loadResource(pr, mergedProperties, mergedKeys, expandedFiles);
            }
        } else {
            throw new IllegalArgumentException("unknown resource type: " + resource.getClass().getName());
        }
    }
}
