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
 * ����һ��Ŀ¼���͵���������Ϣ��
 * 
 * @author Michael Zhou
 */
public class DirectoryConfigEntry extends ConfigEntry {
    /**
     * ����һ����㡣
     * 
     * @param resource ָ��������Դ
     * @param settings antxconfig������
     */
    public DirectoryConfigEntry(ConfigResource resource, ConfigSettings settings) {
        super(resource, settings);
    }

    /**
     * ɨ���㡣
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
     * ���������ļ���
     */
    protected void generate(InputStream istream, OutputStream ostream) {
        getConfigSettings().debug("Processing files in " + getConfigEntryResource().getURL());

        // �����Լ���descriptors
        try {
            ConfigGeneratorSession session = getGenerator().startSession(getConfigSettings().getPropertiesSet());

            session.generate(new DirectoryCallback(getGenerator()));
        } finally {
            getGenerator().closeSession();
        }

        // ������entries
        ConfigEntry[] subEntries = getSubEntries();

        for (int i = 0; i < subEntries.length; i++) {
            ConfigEntry subEntry = subEntries[i];

            subEntry.generate(null, null);
        }
    }

    /**
     * ת�����ַ�����
     * 
     * @return �ַ�����ʾ
     */
    public String toString() {
        return "DirectoryConfigEntry[" + getConfigEntryResource() + "]";
    }
}
