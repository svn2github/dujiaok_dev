package com.alibaba.antx.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ��Zip�ļ���صĹ����ࡣ
 *
 * @author Michael Zhou
 */
public class ZipUtil {
    private static final Log log = LogFactory.getLog(ZipUtil.class);

    /**
     * ȡ��jar URL��
     *
     * @param jarfileURL ����jar�ļ���URL
     * @param path ��Դ��jar�ļ��е�·��
     *
     * @return jar URL
     */
    public static URL getJarURL(URL jarfileURL, String path)
            throws MalformedURLException {
        StringBuffer url = new StringBuffer();

        url.append("jar:").append(jarfileURL.toExternalForm()).append("!");

        if (!path.startsWith("/")) {
            url.append("/");
        }

        url.append(path.replace('\\', '/'));

        return new URL(url.toString());
    }

    /**
     * ɨ��zip�ļ���ȡ�÷���Ҫ��������ļ���
     *
     * @param zipfileURL zip�ļ���URL
     * @param includes �����ļ�
     * @param excludes �������ļ�
     *
     * @return �����ļ���URL
     */
    public static URL[] getFilesInZipFile(URL zipfileURL, String[] includes, String[] excludes)
            throws IOException {
        String[] filenames = getFileNamesInZipFile(zipfileURL, includes, excludes);
        URL[]    urls      = new URL[filenames.length];

        for (int i = 0; i < filenames.length; i++) {
            urls[i] = getJarURL(zipfileURL, filenames[i]);
        }

        return urls;
    }

    /**
     * ɨ��zip�ļ���ȡ�÷���Ҫ��������ļ���
     *
     * @param zipfileURL zip�ļ���URL
     * @param includes �����ļ�
     * @param excludes �������ļ�
     *
     * @return �����ļ�·��
     */
    public static String[] getFileNamesInZipFile(URL zipfileURL, String[] includes,
                                                 String[] excludes)
            throws IOException {
        ZipScanner zipScanner = new ZipScanner();

        zipScanner.setSrc(zipfileURL);
        zipScanner.setIncludes(includes);
        zipScanner.setExcludes(excludes);
        zipScanner.addDefaultExcludes();
        zipScanner.scan();

        return zipScanner.getIncludedFiles();
    }

    /**
     * չ��zip�ļ���ָ��Ŀ¼
     *
     * @param zipfile Zip�ļ�
     * @param todir չ��Ŀ¼
     * @param overwrite �Ƿ񸲸�
     *
     * @throws IOException ��д�ļ�ʧ�ܣ���Zip��ʽ����
     */
    public static void expandFile(File zipfile, File todir, boolean overwrite)
            throws IOException {
        InputStream istream = null;

        try {
            istream = new FileInputStream(zipfile);
            expandFile(istream, todir, overwrite);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * չ��zip�ļ���ָ��Ŀ¼
     *
     * @param istream ������
     * @param todir չ��Ŀ¼
     * @param overwrite �Ƿ񸲸�
     *
     * @throws IOException ��д�ļ�ʧ�ܣ���Zip��ʽ����
     */
    public static void expandFile(InputStream istream, File todir, boolean overwrite)
            throws IOException {
        ZipInputStream zipStream = null;

        if (!(istream instanceof BufferedInputStream)) {
            istream = new BufferedInputStream(istream, 8192);
        }

        try {
            zipStream = new ZipInputStream(istream);

            ZipEntry zipEntry = null;

            while ((zipEntry = zipStream.getNextEntry()) != null) {
                extractFile(todir, zipStream, zipEntry, overwrite);
            }

            log.info("expand complete");
        } finally {
            if (zipStream != null) {
                try {
                    zipStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * չ��һ���ļ���
     *
     * @param todir չ������Ŀ¼
     * @param zipStream ѹ����
     * @param zipEntry zip���
     * @param overwrite ����ļ���Ŀ¼�Ѵ��ڣ��Ƿ񸲸�
     *
     * @throws IOException ��д�ļ�ʧ�ܣ���Zip��ʽ����
     */
    protected static void extractFile(File todir, InputStream zipStream, ZipEntry zipEntry,
                                      boolean overwrite) throws IOException {
        String  entryName   = zipEntry.getName();
        Date    entryDate   = new Date(zipEntry.getTime());
        boolean isDirectory = zipEntry.isDirectory();
        File    targetFile  = FileUtil.getFile(todir, entryName);

        if (!overwrite && targetFile.exists() && (targetFile.lastModified() >= entryDate.getTime())) {
            log.debug("Skipping " + targetFile + " as it is up-to-date");
            return;
        }

        log.info("expanding " + entryName + " to " + targetFile);

        if (isDirectory) {
            targetFile.mkdirs();
        } else {
            File dir = targetFile.getParentFile();

            dir.mkdirs();

            byte[]       buffer  = new byte[8192];
            int          length  = 0;
            OutputStream ostream = null;

            try {
                ostream = new BufferedOutputStream(new FileOutputStream(targetFile), 8192);

                while ((length = zipStream.read(buffer)) >= 0) {
                    ostream.write(buffer, 0, length);
                }
            } finally {
                if (ostream != null) {
                    try {
                        ostream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        targetFile.setLastModified(entryDate.getTime());
    }
}
