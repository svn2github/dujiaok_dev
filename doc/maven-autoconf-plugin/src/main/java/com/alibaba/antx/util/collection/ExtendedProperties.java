package com.alibaba.antx.util.collection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;

import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.i18n.LocaleInfo;

/**
 * ��չ<code>Properties</code>��, ֧�ִ�<code>Reader</code>�ж�ȡunicode�ַ���
 * 
 * @author Michael Zhou
 */
public class ExtendedProperties extends Properties {
    private static final long   serialVersionUID            = 3258126960071555380L;
    private static final String KEY_VALUE_SEPARATORS        = "= \t\r\n\f";
    private static final String STRICT_KEY_VALUE_SEPARATORS = "=";
    private static final String WHITE_SPACE_CHARS           = " \t\r\n\f";

    /**
     * ��ָ����properties�ļ��У���Ĭ�ϵı����ַ�����ȡ���Ժ�ֵ��
     * 
     * @param resource properties�ļ�
     * @throws IOException ���ļ�ʧ�ܻ��ļ���ʽ����
     */
    public synchronized void load(URL resource) throws IOException {
        load(resource, null);
    }

    /**
     * ��ָ����properties�ļ��У���ָ���ı����ַ�����ȡ���Ժ�ֵ��
     * 
     * @param resource properties�ļ�
     * @param charset �����ַ���
     * @throws IOException ���ļ�ʧ�ܻ��ļ���ʽ����
     */
    public synchronized void load(URL resource, String charset) throws IOException {
        charset = getCharset(charset);

        InputStream istream = null;

        try {
            istream = resource.openStream();

            if (!(istream instanceof BufferedInputStream)) {
                istream = new BufferedInputStream(istream, 8192);
            }

            load(new InputStreamReader(istream, charset), resource.toExternalForm());
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * ��ָ�����������У���Ĭ�ϵı����ַ�����ȡ���Ժ�ֵ��
     * 
     * @param istream �����ַ���
     * @throws IOException ���ļ�ʧ�ܻ��ļ���ʽ����
     */
    public void load(InputStream istream) throws IOException {
        load(istream, null, null);
    }

    /**
     * ��ָ�����������У���ָ���ı����ַ�����ȡ���Ժ�ֵ��
     * 
     * @param istream �����ַ���
     * @throws IOException ���ļ�ʧ�ܻ��ļ���ʽ����
     */
    public synchronized void load(InputStream istream, String charset, String url) throws IOException {
        if (charset == null) {
            charset = getCharset(null);
        }

        if (!(istream instanceof BufferedInputStream)) {
            istream = new BufferedInputStream(istream, 8192);
        }

        load(new InputStreamReader(istream, charset), url);
    }

    private String getCharset(String charset) {
        if (charset == null) {
            charset = LocaleInfo.getDefault().getCharset();
        }

        return charset;
    }

    /**
     * ��ָ�����������У���Ĭ�ϵı����ַ�����ȡ���Ժ�ֵ��
     * 
     * @param reader �����ַ���
     * @throws IOException ���ļ�ʧ�ܻ��ļ���ʽ����
     */
    private synchronized void load(Reader reader, String url) throws IOException {
        BufferedReader in = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        int lineNumber = 0;

        while (true) {
            // ȡ����һ��
            String line = in.readLine();

            lineNumber++;

            if (line == null) {
                return;
            }

            // ȥ������β�Ŀհ�
            line = line.trim();

            if (line.length() > 0) {
                // ��������ԡ�\����β����������һ�еļ���
                char firstChar = line.charAt(0);

                if ((firstChar != '#') && (firstChar != '!')) {
                    while (isContinueLine(line)) {
                        String nextLine = in.readLine();

                        if (nextLine == null) {
                            nextLine = "";
                        }

                        String loppedLine = line.substring(0, line.length() - 1);

                        // ȥ�������ϵĿո�
                        int startIndex = 0;

                        for (startIndex = 0; startIndex < nextLine.length(); startIndex++) {
                            if (WHITE_SPACE_CHARS.indexOf(nextLine.charAt(startIndex)) == -1) {
                                break;
                            }
                        }

                        nextLine = nextLine.substring(startIndex, nextLine.length());
                        line = new String(loppedLine + nextLine);
                    }

                    // �ҵ�key�Ŀ�ʼ��
                    int len = line.length();
                    int keyStart;

                    for (keyStart = 0; keyStart < len; keyStart++) {
                        if (WHITE_SPACE_CHARS.indexOf(line.charAt(keyStart)) == -1) {
                            break;
                        }
                    }

                    // ���Կ���
                    if (keyStart == len) {
                        continue;
                    }

                    // ����key��value�ķֽ��
                    int separatorIndex;

                    for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
                        char currentChar = line.charAt(separatorIndex);

                        if (currentChar == '\\') {
                            separatorIndex++;
                        } else if (KEY_VALUE_SEPARATORS.indexOf(currentChar) != -1) {
                            break;
                        }
                    }

                    // ����key����Ŀհף�����еĻ���
                    int valueIndex;

                    for (valueIndex = separatorIndex; valueIndex < len; valueIndex++) {
                        if (WHITE_SPACE_CHARS.indexOf(line.charAt(valueIndex)) == -1) {
                            break;
                        }
                    }

                    // ����һ���ǿհ׵�key-value�ֽ��
                    if (valueIndex < len) {
                        if (STRICT_KEY_VALUE_SEPARATORS.indexOf(line.charAt(valueIndex)) != -1) {
                            valueIndex++;
                        }
                    }

                    // �����ֽ������Ŀհ�
                    while (valueIndex < len) {
                        if (WHITE_SPACE_CHARS.indexOf(line.charAt(valueIndex)) == -1) {
                            break;
                        }

                        valueIndex++;
                    }

                    String key = line.substring(keyStart, separatorIndex);
                    String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

                    // ת��key��value
                    key = loadConvert(key, url, lineNumber);
                    value = loadConvert(value, url, lineNumber);

                    put(key, value);
                }
            }
        }
    }

    /**
     * �жϸ����Ƿ����һ�����������С�
     * 
     * @param line ָ����
     * @return ����Ǻ���һ�������ģ��򷵻�<code>true</code>
     */
    private boolean isContinueLine(String line) {
        int slashCount = 0;
        int index = line.length() - 1;

        while ((index >= 0) && (line.charAt(index--) == '\\')) {
            slashCount++;
        }

        return ((slashCount % 2) == 1);
    }

    /**
     * ��&#92;uxxxxת����unicode�ַ������������ת������ԭ���ĸ�ʽ��
     * 
     * @param str Ҫת�����ַ���
     * @return ת������ַ���
     */
    private String loadConvert(String str, String url, int lineNumber) {
        char ch;
        int len = str.length();
        StringBuffer buffer = new StringBuffer(len);

        if (StringUtil.isEmpty(url)) {
            url = "<unknown source>";
        }

        for (int x = 0; x < len;) {
            ch = str.charAt(x++);

            if (ch == '\\') {
                ch = str.charAt(x++);

                if (ch == 'u') {
                    // Read the xxxx
                    int value = 0;

                    for (int i = 0; i < 4; i++) {
                        ch = str.charAt(x++);

                        switch (ch) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = ((value << 4) + ch) - '0';
                                break;

                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = ((value << 4) + 10 + ch) - 'a';
                                break;

                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = ((value << 4) + 10 + ch) - 'A';
                                break;

                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding at " + url + ", line "
                                        + lineNumber);
                        }
                    }

                    buffer.append((char) value);
                } else {
                    if (ch == '\\') {
                        ch = '\\';
                    } else if (ch == 't') {
                        ch = '\t';
                    } else if (ch == 'r') {
                        ch = '\r';
                    } else if (ch == 'n') {
                        ch = '\n';
                    } else if (ch == 'f') {
                        ch = '\f';
                    } else {
                        throw new IllegalArgumentException("Invalid \\" + ch + " at " + url + ", line " + lineNumber);
                    }

                    buffer.append(ch);
                }
            } else {
                buffer.append(ch);
            }
        }

        return buffer.toString();
    }
}
