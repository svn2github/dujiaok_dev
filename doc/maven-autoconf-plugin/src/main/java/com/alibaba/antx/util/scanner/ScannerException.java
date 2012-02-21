package com.alibaba.antx.util.scanner;


/**
 * 代表一个扫描器的异常
 *
 * @author Michael Zhou
 */
public class ScannerException extends RuntimeException {
    private static final long serialVersionUID = 5765730179081284697L;

/**
     * 创建一个异常。
     */
    public ScannerException() {
        super();
    }

/**
     * 创建一个异常。
     *
     * @param message 异常信息
     */
    public ScannerException(String message) {
        super(message);
    }

/**
     * 创建一个异常。
     *
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }

/**
     * 创建一个异常。
     *
     * @param cause 异常原因
     */
    public ScannerException(Throwable cause) {
        super(cause);
    }
}
