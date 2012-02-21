package com.alibaba.antx.util.scanner;

/**
 * ����scanner�Ļ��ࡣ
 *
 * @author Michael Zhou
 */
public abstract class AbstractScanner implements Scanner {
    private ScannerHandler handler;
    private String         path;

/**
     * ����һ��scanner��
     *
     * @param �ص�����
     */
    public AbstractScanner(ScannerHandler handler) {
        this.handler = handler;
    }

    /**
     * ȡ��scanner handler��
     *
     * @return scanner handler
     */
    public ScannerHandler getScannerHandler() {
        return handler;
    }

    /**
     * ȡ�õ�ǰ����ɨ����ļ�·����
     *
     * @return �ļ�·��
     */
    public String getPath() {
        return (path == null) ? ""
                              : path;
    }

    /**
     * ���õ�ǰ����ɨ����ļ�·����
     *
     * @param path �ļ�·��
     *
     * @return ԭ·��
     */
    protected String setPath(String path) {
        String old = getPath();

        if (path != null) {
            path = path.replace('\\', '/');
        }

        this.path = path;

        return old;
    }

    /**
     * ת�����ַ�����
     *
     * @return �ַ�����ʾ
     */
    public String toString() {
        return "Scanner[URL=" + getBaseURL() + "]";
    }
}
