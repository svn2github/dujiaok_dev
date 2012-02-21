package com.alibaba.antx.config.descriptor;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigResource;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.plugins.PluginCreateRule;
import org.apache.commons.digester.plugins.PluginDeclarationRule;
import org.apache.commons.digester.plugins.PluginRules;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

/**
 * װ��һ��config descriptor�Ĺ����ࡣ
 * 
 * @author Michael Zhou
 */
public class ConfigDescriptorLoader {
    /**
     * ��ָ��������װ�������ļ���
     * 
     * @param url �����ļ���URL
     * @param name descriptor�����֣�·����
     * @return config descriptor
     */
    public synchronized ConfigDescriptor load(ConfigResource descriptorResource, InputStream istream) {
        Digester digester = getDigester();

        digester.push(new ConfigDescriptor(descriptorResource));

        try {
            return (ConfigDescriptor) digester.parse(istream);
        } catch (Exception e) {
            throw new ConfigException("Failed to load config descriptor: " + descriptorResource.getURL(), e);
        }
    }

    /**
     * ȡ��validator���б�
     */
    public synchronized Map loadValidatorClasses() {
        Digester digester = loadValidatorPlugins();

        return (Map) digester.pop();
    }

    /**
     * ������ȡdescriptor��digester��
     */
    protected Digester getDigester() {
        Digester digester = loadValidatorPlugins();

        // config
        digester.addSetProperties("config");

        // config/group
        digester.addObjectCreate("config/group", ConfigGroup.class);
        digester.addSetProperties("config/group");
        digester.addSetNext("config/group", "addGroup");

        // config/group/property
        digester.addObjectCreate("config/group/property", ConfigProperty.class);
        digester.addSetProperties("config/group/property");
        digester.addCallMethod("config/group/property", "afterPropertiesSet");
        digester.addSetNext("config/group/property", "addProperty");

        // config/group/property/validator
        PluginCreateRule pcr = new PluginCreateRule(ConfigValidator.class);

        pcr.setPluginIdAttribute(null, "name");

        digester.addRule("config/group/property/validator", pcr);
        digester.addSetNext("config/group/property/validator", "addValidator");

        // config/script/generate
        digester.addObjectCreate("config/script/generate", ConfigGenerate.class);
        digester.addSetProperties("config/script/generate");
        digester.addSetNext("config/script/generate", "addGenerate");

        digester.clear();

        return digester;
    }

    /**
     * ��ȡvalidators.xml�е�validator���塣
     */
    private Digester loadValidatorPlugins() {
        Digester digester = new Digester();

        digester.setRules(new PluginRules());

        digester.addObjectCreate("config-property-validators", HashMap.class);

        digester.addCallMethod("config-property-validators/validator", "put", 2);
        digester.addCallParam("config-property-validators/validator", 0, "id");
        digester.addCallParam("config-property-validators/validator", 1, "class");

        digester.addRule("config-property-validators/validator", new PluginDeclarationRule());

        InputStream istream = getClass().getResourceAsStream("validators.xml");

        try {
            digester.push(digester.parse(istream));
        } catch (Exception e) {
            throw new ConfigException("Failed to load validators", e);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }
        }

        digester.getRules().clear();

        return digester;
    }
}
