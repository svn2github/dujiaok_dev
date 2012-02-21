package com.alibaba.antx.util.scanner;

import java.io.InputStream;

import java.net.URL;

/**
 * 扫描器。
 *
 * @author Michael Zhou
 */
public interface Scanner {
    /**
     * 取得当前扫描的base URL。
     *
     * @return 当前扫描的base URL
     */
    URL getBaseURL();

    /**
     * 取得当前正在扫描的文件路径。
     *
     * @return 文件路径
     */
    String getPath();

    /**
     * 取得当前正在扫描的文件的URL。
     *
     * @return URL
     */
    URL getURL();

    /**
     * 取得当前正在扫描的文件的输入流。
     *
     * @return 输入流
     */
    InputStream getInputStream();

    /**
     * 执行扫描。
     */
    void scan();
}
