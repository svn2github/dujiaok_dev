package com.alibaba.antx.util.scanner;

import com.alibaba.antx.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �ļ�ɨ������
 *
 * @author Michael Zhou
 */
public class DirectoryScanner extends AbstractScanner {
    private File    basedir;
    private URL     baseURL;
    private boolean followSymlinks = true;

/**
     * ����һ���ļ�Ŀ¼ɨ������
     *
     * @param basedir �ļ�Ŀ¼
     * @param handler �ص�����
     */
    public DirectoryScanner(File basedir, ScannerHandler handler) {
        super(handler);

        if (!basedir.exists() || !basedir.isDirectory()) {
            throw new IllegalArgumentException(basedir + " is not a directory");
        }

        try {
            this.baseURL = basedir.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(basedir + " is not a directory");
        }

        this.basedir = basedir;
    }

    /**
     * ȡ��ɨ��ĸ�Ŀ¼��
     *
     * @return ����ɨ��ĸ�Ŀ¼
     */
    public File getBasedir() {
        return basedir;
    }

    /**
     * ȡ��ɨ��ĸ�Ŀ¼��URL��
     *
     * @return ����ɨ��ĸ�Ŀ¼��URL
     */
    public URL getBaseURL() {
        return baseURL;
    }

    /**
     * ȡ�õ�ǰ����ɨ����ļ���URL��
     *
     * @return URL
     */
    public URL getURL() {
        try {
            return new File(getBasedir(), getPath()).toURI().toURL();
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
        try {
            return new FileInputStream(new File(getBasedir(), getPath()));
        } catch (FileNotFoundException e) {
            throw new ScannerException(e);
        }
    }

    /**
     * �Ƿ�ɨ��������ӡ�
     *
     * @return ����ǣ��򷵻�<code>true</code>
     */
    public boolean isFollowSymlinks() {
        return followSymlinks;
    }

    /**
     * �����Ƿ�ɨ��������ӡ�
     *
     * @param followSymlinks �Ƿ�ɨ���������
     */
    public void setFollowSymlinks(boolean followSymlinks) {
        this.followSymlinks = followSymlinks;
    }

    /**
     * ִ��ɨ�衣
     */
    public void scan() {
        Set processed = new HashSet();

        getScannerHandler().setScanner(this);

        getScannerHandler().startScanning();

        scandir(getBasedir(), processed);

        getScannerHandler().endScanning();
    }

    /**
     * ɨ��ָ��Ŀ¼��
     *
     * @param dir ��ɨ���Ŀ¼
     * @param processed �ѱ�ɨ��ľ���·����������ֹ��Ϊ�������Ӵ����µ��ظ�ɨ��
     */
    protected void scandir(File dir, Set processed) {
        // ��ֹ������������ѭ��
        try {
            String canonicalPath = dir.getCanonicalPath();

            if (processed.contains(canonicalPath)) {
                return;
            }

            processed.add(canonicalPath);
        } catch (IOException e) {
            throw new ScannerException(e);
        }

        // �г���ǰĿ¼�µ������ļ�
        String[] files = dir.list();

        if (files == null) {
            throw new ScannerException("IO error scanning directory " + dir.getAbsolutePath());
        }

        // �ų��������ӣ������Ҫ�Ļ���
        if (!followSymlinks) {
            List noLinks = new ArrayList(files.length);

            for (int i = 0; i < files.length; i++) {
                try {
                    if (!FileUtil.isSymbolicLink(dir, files[i])) {
                        noLinks.add(files[i]);
                    }
                } catch (IOException e) {
                    System.err.println("IOException caught while checking for links, couldn't get cannonical path!");
                    noLinks.add(files[i]);
                }
            }

            files = (String[]) noLinks.toArray(new String[noLinks.size()]);
        }

        // �ݹ�ɨ���ļ���Ŀ¼
        for (int i = 0; i < files.length; i++) {
            String name      = getPath() + files[i];
            File   file      = new File(dir, files[i]);
            String savedPath;

            if (file.isDirectory()) {
                savedPath    = setPath(name + '/');

                getScannerHandler().directory();

                if (getScannerHandler().followUp()) {
                    scandir(file, processed);
                }
            } else {
                savedPath = setPath(name);

                getScannerHandler().file();
            }

            setPath(savedPath);
        }
    }
}
