package com.alibaba.antx.util.scanner;

/**
 * 扫描器的回调函数。
 *
 * @author Michael Zhou
 *
 */
public interface ScannerHandler {
    /**
     * 设置scanner，此方法一定是首先被调用的。
     *
     * @param scanner 当前正在扫描的scanner
     */
    void setScanner(Scanner scanner);

    /**
     * 开始扫描。
     */
    void startScanning();

    /**
     * 结束扫描。
     */
    void endScanning();

    /**
     * 扫描目录。
     */
    void directory();

    /**
     * 扫描文件。
     */
    void file();

    /**
     * 是否跟进指定目录或文件。该方法有助于提高扫描速度。
     *
     * @return 如果是，则返回<code>true</code>
     */
    boolean followUp();
}
