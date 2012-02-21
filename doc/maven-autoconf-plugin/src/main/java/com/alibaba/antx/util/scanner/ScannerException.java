package com.alibaba.antx.util.scanner;


/**
 * ����һ��ɨ�������쳣
 *
 * @author Michael Zhou
 */
public class ScannerException extends RuntimeException {
    private static final long serialVersionUID = 5765730179081284697L;

/**
     * ����һ���쳣��
     */
    public ScannerException() {
        super();
    }

/**
     * ����һ���쳣��
     *
     * @param message �쳣��Ϣ
     */
    public ScannerException(String message) {
        super(message);
    }

/**
     * ����һ���쳣��
     *
     * @param message �쳣��Ϣ
     * @param cause �쳣ԭ��
     */
    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }

/**
     * ����һ���쳣��
     *
     * @param cause �쳣ԭ��
     */
    public ScannerException(Throwable cause) {
        super(cause);
    }
}
