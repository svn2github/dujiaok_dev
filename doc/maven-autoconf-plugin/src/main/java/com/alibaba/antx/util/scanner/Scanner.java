package com.alibaba.antx.util.scanner;

import java.io.InputStream;

import java.net.URL;

/**
 * ɨ������
 *
 * @author Michael Zhou
 */
public interface Scanner {
    /**
     * ȡ�õ�ǰɨ���base URL��
     *
     * @return ��ǰɨ���base URL
     */
    URL getBaseURL();

    /**
     * ȡ�õ�ǰ����ɨ����ļ�·����
     *
     * @return �ļ�·��
     */
    String getPath();

    /**
     * ȡ�õ�ǰ����ɨ����ļ���URL��
     *
     * @return URL
     */
    URL getURL();

    /**
     * ȡ�õ�ǰ����ɨ����ļ�����������
     *
     * @return ������
     */
    InputStream getInputStream();

    /**
     * ִ��ɨ�衣
     */
    void scan();
}
