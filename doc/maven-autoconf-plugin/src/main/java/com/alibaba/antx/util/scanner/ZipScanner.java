package com.alibaba.antx.util.scanner;

import com.alibaba.antx.util.ZipUtil;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Zip�ļ�ɨ������
 *
 * @author Michael Zhou
 */
public class ZipScanner extends AbstractScanner {
    private URL            zipURL;
    private ZipInputStream zis;
    private ZipEntry       zipEntry;

/**
     * ����һ��zip�ļ�ɨ������
     *
     * @param zipfile zip�ļ�
     * @param handler �ص�����
     */
    public ZipScanner(File zipfile, ScannerHandler handler) {
        super(handler);

        if (!zipfile.exists() || !zipfile.isFile() || !zipfile.canRead()) {
            throw new IllegalArgumentException(zipfile + " is not a readable file");
        }

        try {
            this.zipURL = zipfile.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(zipfile + " is not a readable file");
        }
    }

/**
     * ����һ��zip�ļ�ɨ������
     *
     * @param zipURL zip�ļ���URL
     * @param handler �ص�����
     */
    public ZipScanner(URL zipURL, ScannerHandler handler) {
        super(handler);

        this.zipURL = zipURL;
    }

    /**
     * ȡ��ɨ���zip�ļ���URL��
     *
     * @return ����ɨ���zip�ļ���URL
     */
    public URL getBaseURL() {
        return zipURL;
    }

    /**
     * ȡ�õ�ǰ����ɨ����ļ���URL��
     *
     * @return URL
     */
    public URL getURL() {
        try {
            return ZipUtil.getJarURL(getBaseURL(), getPath());
        } catch (MalformedURLException e) {
            throw new ScannerException(e);
        }
    }

    /**
     * ȡ�õ�ǰ����ɨ����ļ�����������
     *
     * @return ������
     */
    public InputStream getInputStream() {
        return new FilterInputStream(zis) {
                public void close() throws IOException {
                    // ����ر�
                }
            };
    }

    /**
     * ����zip�ļ�����������
     *
     * @param istream zip�ļ���������
     */
    public void setInputStream(InputStream istream) {
        if (istream != null) {
            this.zis = new ZipInputStream(istream);
        }
    }

    /**
     * ȡ�õ�ǰ���ڴ����zip entry��
     *
     * @return zip entry
     */
    public ZipEntry getZipEntry() {
        return zipEntry;
    }

    /**
     * ִ��ɨ�衣
     */
    public void scan() {
        getScannerHandler().setScanner(this);
        getScannerHandler().startScanning();

        boolean needClose = false;

        if (zis == null) {
            try {
                zis       = new ZipInputStream(getBaseURL().openStream());
                needClose = true;
            } catch (IOException e) {
                throw new ScannerException(e);
            }
        }

        try {
            doScan();
        } finally {
            if (needClose) {
                try {
                    zis.close();
                } catch (IOException e) {
                }
            }
        }

        getScannerHandler().endScanning();
    }

    /**
     * ִ��ɨ�衣
     */
    protected void doScan() {
        try {
            while ((zipEntry = zis.getNextEntry()) != null) {
                String savedPath = setPath(zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    getScannerHandler().directory();
                } else {
                    getScannerHandler().file();
                }

                setPath(savedPath);
            }
        } catch (IOException e) {
            System.err.println("WARING! Failed to get ZipEntry: " + zipURL.toExternalForm());
            e.printStackTrace();
        }
    }
}
