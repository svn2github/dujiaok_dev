package com.alibaba.antx.util.scanner;

/**
 * Ĭ�ϵ�scanner handlerʵ�֡�
 *
 * @author Michael Zhou
 */
public class DefaultScannerHandler implements ScannerHandler {
    private Scanner scanner;

    /**
     * ȡ��scanner��
     *
     * @return ��ǰ����ɨ���scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * ����scanner���˷���һ�������ȱ����õġ�
     *
     * @param scanner ��ǰ����ɨ���scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * ��ʼɨ�衣
     */
    public void startScanning() {
    }

    /**
     * ����ɨ�衣
     */
    public void endScanning() {
    }

    /**
     * ɨ��Ŀ¼��
     */
    public void directory() {
    }

    /**
     * ɨ���ļ���
     */
    public void file() {
    }

    /**
     * �Ƿ����ָ��Ŀ¼���ļ����÷������������ɨ���ٶȡ�
     *
     * @return ����ǣ��򷵻�<code>true</code>
     */
    public boolean followUp() {
        return true;
    }
}
