package com.alibaba.antx.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.alibaba.antx.util.scanner.DefaultScannerHandler;
import com.alibaba.antx.util.scanner.DirectoryScanner;
import com.alibaba.antx.util.scanner.Scanner;
import com.alibaba.antx.util.scanner.ScannerException;

/**
 * �Ͳ����ļ��йصĹ����ࡣ
 *
 * @author Michael Zhou
 */
public class FileUtil {
    /** ϵͳ���ԣ��û�homeĿ¼ */
    public static final String SYS_PROP_USER_HOME = "user.home";

    /** ϵͳ���ԣ��û���ǰĿ¼ */
    public static final String SYS_PROP_USER_CURRENT_DIR = "user.dir";

    /** Ĭ���ų����ļ��� */
    public static final String[] DEFAULT_EXCLUDES = {
                                                        // Miscellaneous typical temporary files
    "**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*",
                                                        
    // CVS
    "**/CVS", "**/CVS/**", "**/.cvsignore",
                                                        
    // SCCS
    "**/SCCS", "**/SCCS/**",
                                                        
    // Visual SourceSafe
    "**/vssver.scc",
                                                        
    // Subversion
    "**/.svn", "**/.svn/**",
                                                        
    // Mac
    "**/.DS_Store"
                                                    };

    /**
     * ȡ���û�homeĿ¼��
     *
     * @return �û�homeĿ¼
     */
    public static File getUserHome() {
        return new File(System.getProperty(SYS_PROP_USER_HOME));
    }

    /**
     * ȡ���û���ǰĿ¼��
     *
     * @return �û���ǰĿ¼
     */
    public static File getUserCurrentDir() {
        return new File(System.getProperty(SYS_PROP_USER_CURRENT_DIR));
    }

    /**
     * ��ָ��·������<code>File</code>�����<code>path</code>Ϊ���·�����������ָ��<code>basedir</code>��
     *
     * @param basedir ���·���ĸ�Ŀ¼
     * @param path ����·�������·��
     *
     * @return �ļ�����
     */
    public static File getFile(String basedir, String path) {
        return getFile(new File(basedir), path);
    }

    /**
     * ��ָ��·������<code>File</code>�����<code>path</code>Ϊ���·�����������ָ��<code>basedir</code>��
     *
     * @param basedir ���·���ĸ�Ŀ¼
     * @param path ����·�������·��
     *
     * @return �ļ�����
     */
    public static File getFile(File basedir, String path) {
        File file = new File(path);

        if (file.isAbsolute()) {
            return file;
        }

        return new File(basedir, path);
    }

    /**
     * ��ָ��·����ʼ�����ļ���һֱ�ҵ���Ŀ¼Ϊֹ��
     *
     * @param dir �����Ŀ¼��ʼ��
     * @param filename Ҫ���ҵ��ļ���
     *
     * @return �ҵ����ļ������δ�ҵ����򷵻�<code>null</code>
     */
    public static File find(String filename) {
        return find(getUserCurrentDir(), filename);
    }

    /**
     * ��ָ��·����ʼ�����ļ���һֱ�ҵ���Ŀ¼Ϊֹ��
     *
     * @param dir �����Ŀ¼��ʼ��
     * @param filename Ҫ���ҵ��ļ���
     *
     * @return �ҵ����ļ������δ�ҵ����򷵻�<code>null</code>
     */
    public static File find(File dir, String filename) {
        if (dir == null) {
            return null;
        }

        File file = new File(dir, filename);

        return file.exists() ? file
                             : find(dir.getParentFile(), filename);
    }

    /**
     * ȡ��������ļ���
     *
     * @param filename �ļ���
     *
     * @return ������ļ�������ļ���Ϊ�գ��򷵻�<code>null</code>
     */
    public static File getCanonicalFile(String filename) {
        if (StringUtil.isEmpty(filename)) {
            return null;
        }

        try {
            return new File(filename).getCanonicalFile();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * �ж�ָ����ԴID�Ƿ������jar�ļ���Ŀ¼�С�
     *
     * @param base Ŀ¼��jar�ļ�
     * @param resourceId ��ԴID
     *
     * @return ������ڣ��򷵻�<code>true</code>
     */
    public static boolean resourceAvailable(File base, String resourceId) {
        boolean available = false;

        if (base.exists()) {
            if (base.isDirectory()) {
                available = new File(base, resourceId).exists();
            } else {
                available = false;

                try {
                    String[] files = ZipUtil.getFileNamesInZipFile(base.toURI().toURL(),
                                                                   new String[] { resourceId }, null);

                    available = ((files != null) && (files.length > 0));
                } catch (IOException e) {
                }
            }
        }

        return available;
    }

    /**
     * ɨ��Ŀ¼��ȡ�÷���Ҫ��������ļ���
     *
     * @param dir Ŀ¼
     * @param includes �����ļ�
     * @param excludes �������ļ�
     *
     * @return �����ļ�
     */
    public static File[] getFilesInDirectory(File dir, String[] includes, String[] excludes) {
        String[] filenames = getFileNamesInDirectory(dir, includes, excludes);
        File[]   files     = new File[filenames.length];

        for (int i = 0; i < filenames.length; i++) {
            files[i] = new File(dir, filenames[i]).getAbsoluteFile();
        }

        return files;
    }

    /**
     * ɨ��Ŀ¼��ȡ�÷���Ҫ��������ļ���
     *
     * @param dir Ŀ¼
     * @param includes �����ļ�
     * @param excludes �������ļ�
     *
     * @return �����ļ�
     */
    public static String[] getFileNamesInDirectory(File dir, String[] includes, String[] excludes) {
        final PatternSet patterns = new PatternSet(includes, excludes).addDefaultExcludes();
        final List       files    = new ArrayList();
        Scanner          scanner  = new DirectoryScanner(dir,
                                                         new DefaultScannerHandler() {
                public boolean followUp() {
                    String name = getScanner().getPath();

                    return SelectorUtil.matchPathPrefix(name, patterns.getIncludes(),
                                                        patterns.getExcludes());
                }

                public void file() throws ScannerException {
                    String name = getScanner().getPath();

                    if (SelectorUtil.matchPath(name, patterns.getIncludes(), patterns.getExcludes())) {
                        files.add(name);
                    }
                }
            });

        scanner.scan();

        return (String[]) files.toArray(new String[files.size()]);
    }

    public static void writeFile(File file, String text) throws IOException {
        FileWriter out = null;

        try {
            out = new FileWriter(file);
            out.write(text);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void deleteDirectory(String directory) throws IOException {
        deleteDirectory(new File(directory));
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Directory " + directory + " unable to be deleted.";

            throw new IOException(message);
        } else {
            return;
        }
    }

    public static void cleanDirectory(String directory) throws IOException {
        cleanDirectory(new File(directory));
    }

    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";

            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";

            throw new IllegalArgumentException(message);
        }

        IOException exception = null;
        File[]      files     = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        } else {
            return;
        }
    }

    public static void forceDelete(String file) throws IOException {
        forceDelete(new File(file));
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else if (!file.delete()) {
            String message = "File " + file + " unable to be deleted.";

            throw new IOException(message);
        }
    }

    public static File normalizeFile(String path) {
        return normalizeFile(new File(path));
    }

    public static File normalizeFile(File file) {
        File         basedir  = file.getParentFile();
        String       filename = file.getName();
        StringBuffer buffer   = new StringBuffer(filename.length());

        char lastChar = '\0';

        for (int i = 0; i < filename.length(); i++) {
            char c = filename.charAt(i);

            if ((c == '_') || (c == '-') || (c == '.') || (c == ',')) {
                if (lastChar != '\0') {
                    if (c != lastChar) {
                        buffer.append(c);
                    }

                    lastChar = c;
                }
            } else {
                buffer.append(c);
                lastChar = c;
            }
        }

        int index = buffer.length() - 1;

        for (; index > 0; index--) {
            if (buffer.charAt(index) == '.') {
                for (int i = index - 1; i >= 0; i--) {
                    char c = buffer.charAt(i);

                    if ((c == '_') || (c == '-') || (c == '.') || (c == ',')) {
                        buffer.deleteCharAt(i);
                    } else {
                        break;
                    }
                }
            }
        }

        return new File(basedir, buffer.toString()).getAbsoluteFile();
    }

    /* ============================================================================ */
    /*  ������singleton��                                                           */
    /* ============================================================================ */
    private static final char   COLON_CHAR     = ':';
    private static final String UNC_PREFIX     = "//";
    private static final String SLASH          = "/";
    private static final String BACKSLASH      = "\\";
    private static final char   SLASH_CHAR     = '/';
    private static final char   BACKSLASH_CHAR = '\\';

    /** ��ǰĿ¼�Ǻţ�"." */
    public static final String CURRENT_DIR = ".";

    /** �ϼ�Ŀ¼�Ǻţ�".." */
    public static final String UP_LEVEL_DIR = "..";

    /* ============================================================================ */
    /*  ���·����                                                                */
    /*                                                                              */
    /*  ȥ��'.'��'..'��֧��windows·����UNC·����                                   */
    /* ============================================================================ */
    /**
     * ���·����<p>�÷������Բ���ϵͳ�����ͣ����Ƿ����ԡ�<code>/</code>����ʼ�ľ���·����ת���������£�
     *  <ol>
     *      <li>·��Ϊ<code>null</code>���򷵻�<code>null</code>��</li>
     *      <li>������backslash("\\")ת����slash("/")��</li>
     *      <li>ȥ���ظ���"/"��"\\"��</li>
     *      <li>ȥ��"."���������".."��������˷һ��Ŀ¼��</li>
     *      <li>��·������"/"��</li>
     *      <li>����·��ĩβ��"/"������еĻ�����</li>
     *      <li>���ھ���·�������".."��˷��·�������˸�Ŀ¼�������Ƿ�·��������<code>null</code>��</li>
     *  </ol>
     *  </p>
     *
     * @param path Ҫ��񻯵�·��
     *
     * @return ��񻯺��·�������·���Ƿ����򷵻�<code>null</code>
     */
    public static String normalizeAbsolutePath(String path) {
        String normalizedPath = normalizePath(path, false);

        if ((normalizedPath != null) && !normalizedPath.startsWith(SLASH)) {
            if (normalizedPath.equals(CURRENT_DIR)
                        || normalizedPath.equals(CURRENT_DIR + SLASH_CHAR)) {
                normalizedPath = SLASH;
            } else if (normalizedPath.startsWith(UP_LEVEL_DIR)) {
                normalizedPath = null;
            } else {
                normalizedPath = SLASH_CHAR + normalizedPath;
            }
        }

        return normalizedPath;
    }

    /**
     * ���·����<p>�÷����Զ��б����ϵͳ�����͡�ת���������£�
     *  <ol>
     *      <li>·��Ϊ<code>null</code>���򷵻�<code>null</code>��</li>
     *      <li>������backslash("\\")ת����slash("/")��</li>
     *      <li>ȥ���ظ���"/"��"\\"��</li>
     *      <li>ȥ��"."���������".."��������˷һ��Ŀ¼��</li>
     *      <li>�վ���·������"/"�������·������"./"��</li>
     *      <li>����·��ĩβ��"/"������еĻ�����</li>
     *      <li>���ھ���·�������".."��˷��·�������˸�Ŀ¼�������Ƿ�·��������<code>null</code>��</li>
     *      <li>����Windowsϵͳ����Щ·���������ǰ׺������������"c:"��UNC��"//hostname"��������Щ·����������ǰ׺����������·�����������������й���</li>
     *      <li>Windows����������ת���ɴ�д����"c:"ת����"C:"��</li>
     *  </ol>
     *  </p>
     *
     * @param path Ҫ��񻯵�·��
     *
     * @return ��񻯺��·�������·���Ƿ����򷵻�<code>null</code>
     */
    public static String normalizePath(String path) {
        return normalizePath(path, isWindows());
    }

    /**
     * ���·�����������£�
     *  <ol>
     *      <li>·��Ϊ<code>null</code>���򷵻�<code>null</code>��</li>
     *      <li>������backslash("\\")ת����slash("/")��</li>
     *      <li>ȥ���ظ���"/"��"\\"��</li>
     *      <li>ȥ��"."���������".."��������˷һ��Ŀ¼��</li>
     *      <li>�վ���·������"/"�������·������"./"��</li>
     *      <li>����·��ĩβ��"/"������еĻ�����</li>
     *      <li>���ھ���·�������".."��˷��·�������˸�Ŀ¼�������Ƿ�·��������<code>null</code>��</li>
     *      <li>����Windowsϵͳ����Щ·���������ǰ׺������������"c:"��UNC��"//hostname"��������Щ·����������ǰ׺����������·�����������������й���</li>
     *      <li>Windows����������ת���ɴ�д����"c:"ת����"C:"��</li>
     *  </ol>
     *
     * @param path Ҫ��񻯵�·��
     *
     * @return ��񻯺��·�������·���Ƿ����򷵻�<code>null</code>
     */
    public static String normalizeWindowsPath(String path) {
        return normalizePath(path, true);
    }

    /**
     * ���Unix����·������֧��Windows����������UNC·����<p>ת���������£�
     *  <ol>
     *      <li>·��Ϊ<code>null</code>���򷵻�<code>null</code>��</li>
     *      <li>������backslash("\\")ת����slash("/")��</li>
     *      <li>ȥ���ظ���"/"��"\\"��</li>
     *      <li>ȥ��"."���������".."��������˷һ��Ŀ¼��</li>
     *      <li>�վ���·������"/"�������·������"./"��</li>
     *      <li>����·��ĩβ��"/"������еĻ�����</li>
     *      <li>���ھ���·�������".."��˷��·�������˸�Ŀ¼�������Ƿ�·��������<code>null</code>��</li>
     *  </ol>
     *  </p>
     *
     * @param path Ҫ��񻯵�·��
     *
     * @return ��񻯺��·�������·���Ƿ����򷵻�<code>null</code>
     */
    public static String normalizeUnixPath(String path) {
        return normalizePath(path, false);
    }

    /**
     * ���·�����������£�
     *  <ol>
     *      <li>·��Ϊ<code>null</code>���򷵻�<code>null</code>��</li>
     *      <li>������backslash("\\")ת����slash("/")��</li>
     *      <li>ȥ���ظ���"/"��"\\"��</li>
     *      <li>ȥ��"."���������".."��������˷һ��Ŀ¼��</li>
     *      <li>�վ���·������"/"�������·������"./"��</li>
     *      <li>����·��ĩβ��"/"������еĻ�����</li>
     *      <li>���ھ���·�������".."��˷��·�������˸�Ŀ¼�������Ƿ�·��������<code>null</code>��</li>
     *      <li>����Windowsϵͳ����Щ·���������ǰ׺������������"c:"��UNC��"//hostname"��������Щ·����������ǰ׺����������·�����������������й���</li>
     *      <li>Windows����������ת���ɴ�д����"c:"ת����"C:"��</li>
     *  </ol>
     *
     * @param path Ҫ��񻯵�·��
     * @param isWindows �Ƿ���windows·�������Ϊ<code>true</code>����֧������������UNC·��
     *
     * @return ��񻯺��·�������·���Ƿ����򷵻�<code>null</code>
     */
    private static String normalizePath(String path, boolean isWindows) {
        if (path == null) {
            return null;
        }

        path = path.trim();

        // ��"\\"ת����"/"���Ա�ͳһ����
        path = path.replace(BACKSLASH_CHAR, SLASH_CHAR);

        // ȡ��ϵͳ�ض���·��ǰ׺������windowsϵͳ�������ǣ�"C:"����"//hostname"
        String prefix = getSystemDependentPrefix(path, isWindows);

        if (prefix == null) {
            return null;
        }

        path = path.substring(prefix.length());

        // ���ھ���·����prefix������"/"��β����֮������·����prefix.length > 0
        if ((prefix.length() > 0) || path.startsWith(SLASH)) {
            prefix += SLASH_CHAR;
        }

        // ����pathβ����"/"
        boolean endsWithSlash = path.endsWith(SLASH);

        // ѹ��·���е�"."��".."
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        StringBuffer    buffer    = new StringBuffer(prefix.length() + path.length());
        int             level     = 0;

        buffer.append(prefix);

        while (tokenizer.hasMoreTokens()) {
            String element = tokenizer.nextToken();

            // ����"."
            if (CURRENT_DIR.equals(element)) {
                continue;
            }

            // ��˷".."
            if (UP_LEVEL_DIR.equals(element)) {
                if (level == 0) {
                    // ���prefix���ڣ�������ͼԽ�����ϲ�Ŀ¼�����ǲ����ܵģ�
                    // ����null����ʾ·���Ƿ���
                    if (prefix.length() > 0) {
                        return null;
                    }

                    buffer.append(UP_LEVEL_DIR).append(SLASH_CHAR);
                } else {
                    level--;

                    boolean found = false;

                    for (int i = buffer.length() - 2; i >= prefix.length(); i--) {
                        if (buffer.charAt(i) == SLASH_CHAR) {
                            buffer.setLength(i + 1);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        buffer.setLength(prefix.length());
                    }
                }

                continue;
            }

            // ��ӵ�path
            buffer.append(element).append(SLASH_CHAR);
            level++;
        }

        // ����ǿյ�·����������Ϊ"./"
        if (buffer.length() == 0) {
            buffer.append(CURRENT_DIR).append(SLASH_CHAR);
        }

        // ��������"/"
        if (!endsWithSlash && (buffer.length() > prefix.length())
                    && (buffer.charAt(buffer.length() - 1) == SLASH_CHAR)) {
            buffer.setLength(buffer.length() - 1);
        }

        return buffer.toString();
    }

    /**
     * ȡ�ú�ϵͳ��ص��ļ���ǰ׺������Windowsϵͳ������������������UNC·��ǰ׺"//hostname"�����������ǰ׺���򷵻ؿ��ַ�����
     *
     * @param path ����·��
     * @param isWindows �Ƿ�Ϊwindowsϵͳ
     *
     * @return ��ϵͳ��ص��ļ���ǰ׺�����·���Ƿ������磺"//"���򷵻�<code>null</code>
     */
    private static String getSystemDependentPrefix(String path, boolean isWindows) {
        if (isWindows) {
            // �ж�UNC·��
            if (path.startsWith(UNC_PREFIX)) {
                // �Ƿ�UNC·����"//"
                if (path.length() == UNC_PREFIX.length()) {
                    return null;
                }

                // ����·��Ϊ//hostname/subpath������//hostname
                int index = path.indexOf(SLASH, UNC_PREFIX.length());

                if (index != -1) {
                    return path.substring(0, index);
                } else {
                    return path;
                }
            }

            // �ж�Windows����·����"c:/..."
            if ((path.length() > 1) && (path.charAt(1) == COLON_CHAR)) {
                return path.substring(0, 2).toUpperCase();
            }
        }

        return "";
    }

    /* ============================================================================ */
    /*  ȡ�û���ָ��basedir���·����                                             */
    /* ============================================================================ */
    /**
     * ���ָ��·���Ѿ��Ǿ���·�������񻯺�ֱ�ӷ���֮������ȡ�û���ָ��basedir�Ĺ��·����<p>�÷����Զ��ж�����ϵͳ�����ͣ������windowsϵͳ����֧��UNC·��������������</p>
     *
     * @param basedir ��Ŀ¼�����<code>path</code>Ϊ���·������ʾ���ڴ�Ŀ¼
     * @param path Ҫ����·��
     *
     * @return ��񻯵�·�������<code>path</code>�Ƿ�����<code>basedir</code>Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String getPathBasedOn(String basedir, String path) {
        return getPathBasedOn(basedir, path, isWindows());
    }

    /**
     * ���ָ��·���Ѿ��Ǿ���·�������񻯺�ֱ�ӷ���֮������ȡ�û���ָ��basedir�Ĺ��·����
     *
     * @param basedir ��Ŀ¼�����<code>path</code>Ϊ���·������ʾ���ڴ�Ŀ¼
     * @param path Ҫ����·��
     *
     * @return ��񻯵�·�������<code>path</code>�Ƿ�����<code>basedir</code>Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String getWindowsPathBasedOn(String basedir, String path) {
        return getPathBasedOn(basedir, path, true);
    }

    /**
     * ���ָ��·���Ѿ��Ǿ���·�������񻯺�ֱ�ӷ���֮������ȡ�û���ָ��basedir�Ĺ��·����
     *
     * @param basedir ��Ŀ¼�����<code>path</code>Ϊ���·������ʾ���ڴ�Ŀ¼
     * @param path Ҫ����·��
     *
     * @return ��񻯵�·�������<code>path</code>�Ƿ�����<code>basedir</code>Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String getUnixPathBasedOn(String basedir, String path) {
        return getPathBasedOn(basedir, path, false);
    }

    /**
     * ���ָ��·���Ѿ��Ǿ���·�������񻯺�ֱ�ӷ���֮������ȡ�û���ָ��basedir�Ĺ��·����
     *
     * @param basedir ��Ŀ¼�����<code>path</code>Ϊ���·������ʾ���ڴ�Ŀ¼
     * @param path Ҫ����·��
     * @param isWindows �Ƿ���windows·�������Ϊ<code>true</code>����֧������������UNC·��
     *
     * @return ��񻯵�·�������<code>path</code>�Ƿ�����<code>basedir</code>Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    private static String getPathBasedOn(String basedir, String path, boolean isWindows) {
        /* ------------------------------------------- *
         * ����ȡ��path��ǰ׺���ж��Ƿ�Ϊ����·����    *
         * ����Ѿ��Ǿ���·���������normalize�󷵻ء� *
         * ------------------------------------------- */
        if (path == null) {
            return null;
        }

        path = path.trim();

        // ��"\\"ת����"/"���Ա�ͳһ����
        path = path.replace(BACKSLASH_CHAR, SLASH_CHAR);

        // ȡ��ϵͳ�ض���·��ǰ׺������windowsϵͳ�������ǣ�"C:"����"//hostname"
        String prefix = getSystemDependentPrefix(path, isWindows);

        if (prefix == null) {
            return null;
        }

        // ����Ǿ���·������ֱ�ӷ���
        if ((prefix.length() > 0)
                    || ((path.length() > prefix.length())
                               && (path.charAt(prefix.length()) == SLASH_CHAR))) {
            return normalizePath(path, isWindows);
        }

        /* ------------------------------------------- *
         * �����Ѿ�ȷ��path�����·���ˣ��������Ҫ    *
         * ������basedir�ϲ���                         *
         * ------------------------------------------- */
        if (basedir == null) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append(basedir.trim());

        // ��ֹ�ظ���"/"���������׺�UNC prefix����
        if ((basedir.length() > 0) && (path.length() > 0)
                    && (basedir.charAt(basedir.length() - 1) != SLASH_CHAR)) {
            buffer.append(SLASH_CHAR);
        }

        buffer.append(path);

        return normalizePath(buffer.toString(), isWindows);
    }

    /* ============================================================================ */
    /*  ȡ�������ָ��basedir���·����                                             */
    /* ============================================================================ */
    /**
     * ȡ�������ָ����Ŀ¼�����·����<p>�÷����Զ��ж�����ϵͳ�����ͣ������windowsϵͳ����֧��UNC·��������������</p>
     *
     * @param basedir ��Ŀ¼
     * @param path Ҫ�����·��
     *
     * @return ���<code>path</code>��<code>basedir</code>�Ǽ��ݵģ��򷵻������<code>basedir</code>�����·�������򷵻�<code>path</code>�������<code>basedir</code>���Ǿ���·��������·���Ƿ����򷵻�<code>null</code>
     */
    public static String getRelativePath(String basedir, String path) {
        return getRelativePath(basedir, path, isWindows());
    }

    /**
     * ȡ�������ָ����Ŀ¼�����·����
     *
     * @param basedir ��Ŀ¼
     * @param path Ҫ�����·��
     *
     * @return ���<code>path</code>��<code>basedir</code>�Ǽ��ݵģ��򷵻������<code>basedir</code>�����·�������򷵻�<code>path</code>�������<code>basedir</code>���Ǿ���·��������·���Ƿ����򷵻�<code>null</code>
     */
    public static String getWindowsRelativePath(String basedir, String path) {
        return getRelativePath(basedir, path, true);
    }

    /**
     * ȡ�������ָ����Ŀ¼�����·����
     *
     * @param basedir ��Ŀ¼
     * @param path Ҫ�����·��
     *
     * @return ���<code>path</code>��<code>basedir</code>�Ǽ��ݵģ��򷵻������<code>basedir</code>�����·�������򷵻�<code>path</code>�������<code>basedir</code>���Ǿ���·��������·���Ƿ����򷵻�<code>null</code>
     */
    public static String getUnixRelativePath(String basedir, String path) {
        return getRelativePath(basedir, path, false);
    }

    /**
     * ȡ�������ָ����Ŀ¼�����·����
     *
     * @param basedir ��Ŀ¼
     * @param path Ҫ�����·��
     * @param isWindows �Ƿ���windows·�������Ϊ<code>true</code>����֧������������UNC·��
     *
     * @return ���<code>path</code>��<code>basedir</code>�Ǽ��ݵģ��򷵻������<code>basedir</code>�����·�������򷵻�<code>path</code>�������<code>basedir</code>���Ǿ���·��������·���Ƿ����򷵻�<code>null</code>
     */
    private static String getRelativePath(String basedir, String path, boolean isWindows) {
        // ȡ�ù�񻯵�basedir��ȷ����Ϊ����·��
        basedir = normalizePath(basedir, isWindows);

        if (basedir == null) {
            return null;
        }

        String basePrefix = getSystemDependentPrefix(basedir, isWindows);

        if ((basePrefix == null) || ((basePrefix.length() == 0) && !basedir.startsWith(SLASH))) {
            return null; // basedir�����Ǿ���·��
        }

        // ȡ�ù�񻯵�path
        path = getPathBasedOn(basedir, path, isWindows);

        if (path == null) {
            return null;
        }

        String prefix = getSystemDependentPrefix(path, isWindows);

        // ���path��basedir��ǰ׺��ͬ������ת���������basedir�����·����
        // ֱ�ӷ��ع�񻯵�path���ɡ�
        if (!basePrefix.equals(prefix)) {
            return path;
        }

        // ����pathβ����"/"
        boolean endsWithSlash = path.endsWith(SLASH);

        // ��"/"�ָ�basedir��path
        String[]     baseParts = StringUtil.split(basedir.substring(basePrefix.length()), SLASH);
        String[]     parts     = StringUtil.split(path.substring(prefix.length()), SLASH);
        StringBuffer buffer    = new StringBuffer();
        int          i         = 0;

        if (isWindows) {
            while ((i < baseParts.length) && (i < parts.length)
                           && baseParts[i].equalsIgnoreCase(parts[i])) {
                i++;
            }
        } else {
            while ((i < baseParts.length) && (i < parts.length) && baseParts[i].equals(parts[i])) {
                i++;
            }
        }

        if ((i < baseParts.length) && (i < parts.length)) {
            for (int j = i; j < baseParts.length; j++) {
                buffer.append(UP_LEVEL_DIR).append(SLASH_CHAR);
            }
        }

        for (; i < parts.length; i++) {
            buffer.append(parts[i]);

            if (i < (parts.length - 1)) {
                buffer.append(SLASH_CHAR);
            }
        }

        if (buffer.length() == 0) {
            buffer.append(CURRENT_DIR);
        }

        String relpath = buffer.toString();

        if (endsWithSlash && !relpath.endsWith(SLASH)) {
            relpath += SLASH;
        }

        return relpath;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static boolean isSymbolicLink(File parent, String name)
            throws IOException {
        if (parent == null) {
            File f = new File(name);

            parent = f.getParentFile();
            name   = f.getName();
        }

        File toTest = new File(parent.getCanonicalPath(), name);

        return !toTest.getAbsolutePath().equals(toTest.getCanonicalPath());
    }
}
