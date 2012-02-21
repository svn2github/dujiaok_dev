package com.alibaba.antx.config.entry;

import java.io.File;
import java.util.Map;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Matcher;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.util.NumberUtil;
import com.alibaba.antx.util.PatternSet;
import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.regex.PathNameCompiler;

public class ConfigEntryFactoryImpl implements ConfigEntryFactory {
    private ConfigSettings settings;

    public ConfigEntryFactoryImpl(ConfigSettings settings) {
        this.settings = settings;
    }

    public ConfigEntry create(ConfigResource resource) {
        File file = resource.getFile();
        String name = resource.getName();

        if (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1);
        }

        if ((file != null) && !file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + file);
        }

        if (name.endsWith(".war")) {
            return createWarEntry(resource);
        }

        if (name.endsWith(".jar") || name.endsWith(".ear") || name.endsWith(".rar")) {
            return createGenericJarEntry(resource);
        }

        if ((file != null) && file.isDirectory()) {
            return createGenericDirectoryEntry(resource);
        }

        return createGenericJarEntry(resource);
    }

    /**
     * 设置公共context。
     * 
     * @param context context
     */
    protected void populateCommonContext(Map context) {
        context.put("stringUtil", new StringUtil());
        context.put("numberUtil", new NumberUtil());
    }

    /**
     * 设置基于war的context，根据descriptor name取得component name。
     * 
     * @param context context
     */
    protected void populateWarContext(Map context, String name) {
        Pattern componentNamePattern;
        PatternMatcher matcher = new Perl5Matcher();
        String componentName = "";

        try {
            componentNamePattern = new PathNameCompiler().compile("META-INF/**/autoconf/auto-config.xml");
        } catch (MalformedPatternException e) {
            throw new ConfigException(e);
        }

        if (matcher.matches(name.replace('\\', '/'), componentNamePattern)) {
            componentName = matcher.getMatch().group(1);
        }

        context.put("component", componentName);
    }

    /**
     * 创建基于war的entry。
     */
    private ConfigEntry createWarEntry(ConfigResource resource) {
        File file = resource.getFile();
        ConfigEntry entry;

        if ((file != null) && file.isDirectory()) {
            entry = new DirectoryConfigEntry(resource, settings) {
                protected void populateDescriptorContext(Map context, String name) {
                    populateCommonContext(context);
                    populateWarContext(context, name);
                }
            };
        } else {
            entry = new ZipConfigEntry(resource, settings) {
                protected void populateDescriptorContext(Map context, String name) {
                    populateCommonContext(context);
                    populateWarContext(context, name);
                }
            };
        }

        entry.setDescriptorPatterns(new PatternSet("META-INF/**/auto-config.xml"));

        entry.setPackagePatterns(new PatternSet("WEB-INF/lib/*.jar"));

        return entry;
    }

    /**
     * 创建基于普通jar的entry。
     */
    private ConfigEntry createGenericJarEntry(ConfigResource resource) {
        File file = resource.getFile();
        ConfigEntry entry;

        if ((file != null) && file.isDirectory()) {
            entry = new DirectoryConfigEntry(resource, settings) {
                protected void populateDescriptorContext(Map context, String name) {
                    populateCommonContext(context);
                }
            };
        } else {
            entry = new ZipConfigEntry(resource, settings) {
                protected void populateDescriptorContext(Map context, String name) {
                    populateCommonContext(context);
                }
            };
        }

        entry.setDescriptorPatterns(new PatternSet("META-INF/**/auto-config.xml"));

        entry.setPackagePatterns(new PatternSet("**/*.jar, **/*.war, **/*.rar, **/*.ear"));

        return entry;
    }

    /**
     * 创建基于普通目录的entry。
     */
    private ConfigEntry createGenericDirectoryEntry(ConfigResource resource) {
        ConfigEntry entry = new DirectoryConfigEntry(resource, settings) {
            protected void populateDescriptorContext(Map context, String name) {
                populateCommonContext(context);
            }
        };

        entry.setDescriptorPatterns(new PatternSet(settings.getDescriptorPatterns(), new PatternSet(
                "conf/**/auto-config.xml, META-INF/**/auto-config.xml")).addDefaultExcludes());

        // 如果是对目录操作，且未指定package patterns，则默认不搜索目录下的packages文件
        entry.setPackagePatterns(new PatternSet(settings.getPackagePatterns(), new PatternSet(null, "**"))
                .addDefaultExcludes());

        return entry;
    }
}
