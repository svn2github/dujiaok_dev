package com.alibaba.antx.config.entry;

import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.config.generator.ConfigGeneratorSession;
import com.alibaba.antx.config.generator.DirectoryCallback;
import com.alibaba.antx.util.scanner.DirectoryScanner;
import com.alibaba.antx.util.scanner.Scanner;
import com.alibaba.antx.util.scanner.ScannerException;

/**
 * 代表一个目录类型的配置项信息。
 * 
 * @author Michael Zhou
 */
public class DirectoryConfigEntry extends ConfigEntry {
    /**
     * 创建一个结点。
     * 
     * @param resource 指定结点的资源
     * @param settings antxconfig的设置
     */
    public DirectoryConfigEntry(ConfigResource resource, ConfigSettings settings) {
        super(resource, settings);
    }

    /**
     * 扫描结点。
     */
    protected void scan(InputStream istream) {
        Handler handler = new Handler();
        Scanner scanner = new DirectoryScanner(getConfigEntryResource().getFile(), handler);

        try {
            scanner.scan();
        } catch (ScannerException e) {
            throw new ConfigException(e);
        }

        subEntries = handler.getSubEntries();

        getGenerator().init();
    }

    /**
     * 生成配置文件。
     */
    protected void generate(InputStream istream, OutputStream ostream) {
        getConfigSettings().debug("Processing files in " + getConfigEntryResource().getURL());

        // 处理自己的descriptors
        try {
            ConfigGeneratorSession session = getGenerator().startSession(getConfigSettings().getPropertiesSet());

            session.generate(new DirectoryCallback(getGenerator()));
        } finally {
            getGenerator().closeSession();
        }

        // 处理子entries
        ConfigEntry[] subEntries = getSubEntries();

        for (int i = 0; i < subEntries.length; i++) {
            ConfigEntry subEntry = subEntries[i];

            subEntry.generate(null, null);
        }
    }

    /**
     * 转换成字符串。
     * 
     * @return 字符串表示
     */
    public String toString() {
        return "DirectoryConfigEntry[" + getConfigEntryResource() + "]";
    }
}
