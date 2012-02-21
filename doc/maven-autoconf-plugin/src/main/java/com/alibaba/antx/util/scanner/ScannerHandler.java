package com.alibaba.antx.util.scanner;

/**
 * ɨ�����Ļص�������
 *
 * @author Michael Zhou
 *
 */
public interface ScannerHandler {
    /**
     * ����scanner���˷���һ�������ȱ����õġ�
     *
     * @param scanner ��ǰ����ɨ���scanner
     */
    void setScanner(Scanner scanner);

    /**
     * ��ʼɨ�衣
     */
    void startScanning();

    /**
     * ����ɨ�衣
     */
    void endScanning();

    /**
     * ɨ��Ŀ¼��
     */
    void directory();

    /**
     * ɨ���ļ���
     */
    void file();

    /**
     * �Ƿ����ָ��Ŀ¼���ļ����÷������������ɨ���ٶȡ�
     *
     * @return ����ǣ��򷵻�<code>true</code>
     */
    boolean followUp();
}
