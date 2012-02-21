package com.alibaba.antx.config.entry;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.generator.ConfigGeneratorCallback;
import com.alibaba.antx.util.scanner.ScannerException;
import com.alibaba.antx.util.scanner.ZipScanner;

/**
 * ����һ��Jar�ļ����͵���������Ϣ��
 * 
 * @author Michael Zhou
 */
public class ZipConfigEntry extends ConfigEntry {
    /**
     * ����һ����㡣
     * 
     * @param resource ָ��������Դ
     * @param settings antxconfig������
     */
    public ZipConfigEntry(ConfigResource resource, ConfigSettings settings) {
        super(resource, settings);
    }

    /**
     * ɨ���㡣
     * 
     * @param istream zip�ļ���������
     */
    protected void scan(InputStream istream) {
        Handler handler = new Handler();
        ZipScanner scanner = new ZipScanner(getConfigEntryResource().getURL(), handler);

        scanner.setInputStream(istream);

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
        boolean needCloseOutputStream = false;
        boolean needCloseInputStream = false;
        boolean success = false;
        File destfile = null;
        File tempfile = null;

        getConfigSettings().debug("Processing files in " + getConfigEntryResource());

        ZipInputStream zis = null;
        ZipOutputStream zos = null;
        Set dirs = new HashSet();

        try {
            // �����ostream
            if (ostream == null) {
                destfile = getConfigEntryResource().getFile();

                if ((destfile == null) || !destfile.exists()) {
                    throw new ConfigException("Could not find " + getConfigEntryResource().getURL());
                }

                tempfile = new File(destfile.getParentFile(), destfile.getName() + ".tmp");
                ostream = new BufferedOutputStream(new FileOutputStream(tempfile), 8192);
                needCloseOutputStream = true;
            }

            // �����istream
            if (istream == null) {
                istream = getConfigEntryResource().getURL().openStream();

                if (!(istream instanceof BufferedInputStream)) {
                    istream = new BufferedInputStream(istream, 8192);
                }

                needCloseInputStream = true;
            }

            zis = new ZipInputStream(istream);
            zos = new ZipOutputStream(ostream);

            ZipEntry zipEntry;

            getGenerator().startSession(getConfigSettings().getPropertiesSet());

            while ((zipEntry = zis.getNextEntry()) != null) {
                processZipEntry(zipEntry, zis, zos, dirs);
            }

            getGenerator().getSession().checkNonprocessedTemplates();
            getGenerator().getSession().generateLog(new ZipCallback(zos, dirs));

            success = true;
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            getGenerator().closeSession();

            // ����zip�ļ��������ر���
            if (zos != null) {
                try {
                    zos.finish();
                } catch (IOException e) {
                }
            }

            // �������������ɵ�ǰentry���Դ򿪵ģ��Źر���
            if (needCloseInputStream && (istream != null)) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }

            // ������������ɵ�ǰentry���Դ򿪵ģ��Źر���
            if (needCloseOutputStream && (ostream != null)) {
                try {
                    ostream.flush();
                    ostream.close();
                } catch (IOException e) {
                }

                // ����ɹ�������ʱ�ļ��ĳ���ʽ�ļ�������ɾ����ʱ�ļ�
                if (success) {
                    destfile.delete();
                    tempfile.renameTo(destfile);
                } else {
                    tempfile.delete();
                }
            }
        }
    }

    private void processZipEntry(ZipEntry zipEntry, ZipInputStream zis, ZipOutputStream zos, Set dirs)
            throws IOException {
        String name = zipEntry.getName();
        ConfigEntry subEntry = getSubEntry(name);

        if (subEntry != null) {
            // ����һ��Ƕ�׵�jar entry
            ZipEntry zipEntryToWrite = new ZipEntry(zipEntry.getName());

            zos.putNextEntry(zipEntryToWrite);

            subEntry.generate(zis, zos);
        } else if (getGenerator().isTemplateFile(name)) {
            // �Ȱ��������ڴ����ΪҪ�ö��
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            io(zis, baos);

            byte[] bytes = baos.toByteArray();

            copyFile(zipEntry, new ByteArrayInputStream(bytes), zos);

            // ����һ��ģ�壬Ҫ������������Ӧ���ļ�
            getGenerator().getSession().generate(name, new ZipCallback(bytes, zos, dirs));
        } else if (getGenerator().isDestFile(name)) {
            // ����ļ�����ģ�����ɵ��ļ����ǣ��ʺ���֮
        } else if (getGenerator().isDescriptorLogFile(name)) {
            // ����ļ�����descriptor��־�ļ����ǣ��ʺ���֮
        } else if (zipEntry.isDirectory()) {
            // ����һ����Ŀ¼���ڲ��ظ�����Ŀ¼��ǰ���£����Ƽ���
            mkdirs(zipEntry.getName(), zos, dirs);
        } else {
            // ����һ����ͨ�ļ������Ƽ���
            copyFile(zipEntry, zis, zos);
        }
    }

    private void mkdirs(String dir, ZipOutputStream zos, Set dirs) throws IOException {
        dir = dir.replace('\\', '/');

        while (dir.startsWith("/")) {
            dir = dir.substring(1);
        }

        if (!dir.endsWith("/")) {
            dir += "/";
        }

        for (int index = dir.indexOf("/"); index > 0; index = dir.indexOf("/", index + 1)) {
            String subDir = dir.substring(0, index + 1);

            if (!dirs.contains(subDir)) {
                zos.putNextEntry(new ZipEntry(subDir));
                dirs.add(subDir);
            }
        }
    }

    private void copyFile(ZipEntry zipEntry, InputStream istream, ZipOutputStream zos) throws IOException {
        zos.putNextEntry(new ZipEntry(zipEntry));
        io(istream, zos);
    }

    private void io(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    private ConfigEntry getSubEntry(String name) {
        ConfigEntry[] entries = getSubEntries();

        for (int i = 0; i < entries.length; i++) {
            ConfigEntry subEntry = entries[i];

            if (subEntry.getName().equals(name.replace('\\', '/'))) {
                return subEntry;
            }
        }

        return null;
    }

    /**
     * ת�����ַ�����
     * 
     * @return �ַ�����ʾ
     */
    public String toString() {
        return "ZipConfigEntry[" + getConfigEntryResource() + "]";
    }

    /**
     * ��������Ŀ���ļ���callback��
     */
    private final class ZipCallback implements ConfigGeneratorCallback {
        private final byte[]          bytes;
        private final ZipOutputStream zos;
        private final Set             dirs;

        private ZipCallback(ZipOutputStream zos, Set dirs) {
            this(null, zos, dirs);
        }

        private ZipCallback(byte[] bytes, ZipOutputStream zos, Set dirs) {
            this.bytes = bytes;
            this.zos = zos;
            this.dirs = dirs;
        }

        public void nextEntry(ConfigDescriptor descriptor, String template, String dest) {
            try {
                makeParentDirs(dest);
                zos.putNextEntry(new ZipEntry(dest));
            } catch (IOException e) {
                throw new ConfigException(e);
            }

            getGenerator().getSession().setInputStream(new ByteArrayInputStream(bytes));
            getGenerator().getSession().setOutputStream(zos);
        }

        public void logEntry(ConfigDescriptor descriptor, String logfileName) {
            try {
                makeParentDirs(logfileName);
                zos.putNextEntry(new ZipEntry(logfileName));
            } catch (IOException e) {
                throw new ConfigException(e);
            }

            getGenerator().getSession().setOutputStream(zos);
        }

        public void closeEntry() {
            // ����Ҫ�ر�������Ϊ��zip stream��
        }

        private void makeParentDirs(String name) throws IOException {
            int index = name.lastIndexOf("/");

            if (index > 0) {
                mkdirs(name.substring(0, index + 1), zos, dirs);
            }
        }
    }
}
