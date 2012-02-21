package com.alibaba.antx.util.scanner;

/**
 * 默认的scanner handler实现。
 *
 * @author Michael Zhou
 */
public class DefaultScannerHandler implements ScannerHandler {
    private Scanner scanner;

    /**
     * 取得scanner。
     *
     * @return 当前正在扫描的scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * 设置scanner，此方法一定是首先被调用的。
     *
     * @param scanner 当前正在扫描的scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * 开始扫描。
     */
    public void startScanning() {
    }

    /**
     * 结束扫描。
     */
    public void endScanning() {
    }

    /**
     * 扫描目录。
     */
    public void directory() {
    }

    /**
     * 扫描文件。
     */
    public void file() {
    }

    /**
     * 是否跟进指定目录或文件。该方法有助于提高扫描速度。
     *
     * @return 如果是，则返回<code>true</code>
     */
    public boolean followUp() {
        return true;
    }
}
