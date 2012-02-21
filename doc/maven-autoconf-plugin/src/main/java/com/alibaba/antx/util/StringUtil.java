package com.alibaba.antx.util;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ���ַ����йص�С����.
 *
 * @author Michael Zhou
 */
public class StringUtil {
    /**
     * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���.
     *
     * @param str Ҫ�����ַ���
     *
     * @return ���Ϊ��, �򷵻�<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isEmpty(Object str) {
        return isEmpty(toString(str));
    }

    /**
     * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���.
     *
     * @param str Ҫ�����ַ���
     *
     * @return ���Ϊ��, �򷵻�<code>true</code>
     */
    public static boolean isBlank(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    public static boolean isBlank(Object str) {
        return isBlank(toString(str));
    }

    /**
     * ȡ��������������package����
     *
     * @param clazz Ҫ�鿴����
     *
     * @return ������
     */
    public static String getShortClassName(Class clazz) {
        return getShortClassName(clazz.getName());
    }

    /**
     * ȡ��������������package����
     *
     * @param className Ҫ�鿴������
     *
     * @return ������
     */
    public static String getShortClassName(String className) {
        int index = className.lastIndexOf('.');

        return className.substring(index + 1);
    }

    /**
     * ���ַ������ո�Ͷ��ŷֽ�.
     *
     * @param str Ҫ�ֽ���ַ���
     *
     * @return �ַ�������
     */
    public static String[] split(String str) {
        return split(str, ",");
    }

    public static String join(Object[] array) {
        return join(array, ",");
    }

    /**
     * �������е�Ԫ�����ӳ�һ���ַ�����<pre>StringUtil.join(null, *)                = null
     * StringUtil.join([], *)                  = ""StringUtil.join([null], *)              = ""
     * StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtil.join(["a", "b", "c"], null)  = "abc"
     * StringUtil.join(["a", "b", "c"], "")    = "abc"
     * StringUtil.join([null, "", "a"], ',')   = ",,a"</pre>
     *
     * @param array Ҫ���ӵ�����
     * @param separator �ָ���
     *
     * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }

        if (separator == null) {
            separator = "";
        }

        int arraySize = array.length;

        // ArraySize ==  0: Len = 0
        // ArraySize > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (���ƴ�Լ���е��ַ�����һ����)
        int bufSize = (arraySize == 0) ? 0
                                       : (arraySize * (((array[0] == null) ? 16
                                                                           : array[0].toString()
                                                                                     .length())
                                                      + ((separator != null) ? separator.length()
                                                                             : 0)));

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if ((separator != null) && (i > 0)) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }

    /**
     * ���ַ������ո�Ͷ��ŷֽ�.
     *
     * @param str Ҫ�ֽ���ַ���
     *
     * @return �ַ�������
     */
    public static String[] splitPath(String str) {
        return split(str, "," + File.pathSeparator);
    }

    /**
     * ���ַ�����ָ���ָ����ֽ�.
     *
     * @param str Ҫ�ֽ���ַ���
     * @param delimiters �ָ���
     *
     * @return �ַ�������
     */
    public static String[] split(String str, String delimiters) {
        if ((str == null) || (str.trim().length() == 0)) {
            return new String[0];
        }

        List            tokens    = new ArrayList();
        StringTokenizer tokenizer = new StringTokenizer(str, delimiters);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.length() > 0) {
                tokens.add(token);
            }
        }

        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    /**
     * ɾ�����˿հס�
     *
     * @param str Ҫ������ַ���
     *
     * @return ��ȥ���˿հ׵��ַ���������ַ���Ϊ<code>null</code>���򷵻ؿ��ַ���
     */
    public static String trimWhitespace(String str) {
        if (str == null) {
            return "";
        }

        return str.trim();
    }

    /**
     * ɾ�����пհס�
     *
     * @param str Ҫ������ַ���
     *
     * @return ��ȥ�հ׵��ַ���������ַ���Ϊ<code>null</code>���򷵻ؿ��ַ���
     */
    public static String deleteWhitespace(String str) {
        if (str == null) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (!Character.isWhitespace(ch)) {
                buffer.append(ch);
            }
        }

        return buffer.toString();
    }

    /**
     * ͨ�������Ϸ����ַ��滻��"_", �����Ϸ���Java Identifier�ַ�ת���ɺϷ���ID.
     *
     * @param id Ҫת�����ַ���
     *
     * @return �Ϸ���ID
     */
    public static String getValidIdentifier(String id) {
        return getValidIdentifier(id, null);
    }

    /**
     * ͨ���滻���Ϸ����ַ�, �����Ϸ���Java Identifier�ַ�ת���ɺϷ���ID.
     *
     * @param id Ҫת�����ַ���
     * @param replaceInvalid �����滻���Ϸ��ַ����ַ���, �����ָ��, ��ʹ��Ĭ���ַ���"_"
     *
     * @return �Ϸ���ID
     */
    public static String getValidIdentifier(String id, String replaceInvalid) {
        if (replaceInvalid == null) {
            replaceInvalid = "_";
        }

        if ((id == null) || (id.length() == 0)) {
            return replaceInvalid;
        }

        boolean      replaced = false;
        StringBuffer buffer   = new StringBuffer(id.length());
        char         c        = id.charAt(0);

        if (Character.isJavaIdentifierStart(c)) {
            buffer.append(c);
        } else {
            buffer.append(replaceInvalid);
            replaced = true;
        }

        for (int i = 1; i < id.length(); i++) {
            c = id.charAt(i);

            if (Character.isJavaIdentifierPart(c)) {
                buffer.append(c);
                replaced = false;
            } else {
                if (!replaced) {
                    replaced = true;
                    buffer.append(replaceInvalid);
                }
            }
        }

        return buffer.toString();
    }

    /**
     * ��չ��������ַ������ÿո�<code>' '</code>����ұߡ�<pre>StringUtil.alignLeft(null, *)   = null
     * StringUtil.alignLeft("", 3)     = "   "StringUtil.alignLeft("bat", 3)  = "bat"
     * StringUtil.alignLeft("bat", 5)  = "bat  "StringUtil.alignLeft("bat", 1)  = "bat"
     * StringUtil.alignLeft("bat", -1) = "bat"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignLeft(String str, int size) {
        return alignLeft(str, size, ' ');
    }

    /**
     * ��չ��������ַ�������ָ���ַ�����ұߡ�<pre>StringUtil.alignLeft(null, *, *)     = null
     * StringUtil.alignLeft("", 3, 'z')     = "zzz"StringUtil.alignLeft("bat", 3, 'z')  = "bat"
     * StringUtil.alignLeft("bat", 5, 'z')  = "batzz"StringUtil.alignLeft("bat", 1, 'z')  = "bat"
     * StringUtil.alignLeft("bat", -1, 'z') = "bat"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padChar ����ַ�
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignLeft(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignLeft(str, size, String.valueOf(padChar));
    }

    /**
     * ��չ��������ַ�������ָ���ַ�������ұߡ�<pre>StringUtil.alignLeft(null, *, *)      = null
     * StringUtil.alignLeft("", 3, "z")      = "zzz"StringUtil.alignLeft("bat", 3, "yz")  = "bat"
     * StringUtil.alignLeft("bat", 5, "yz")  = "batyz"
     * StringUtil.alignLeft("bat", 8, "yz")  = "batyzyzy"
     * StringUtil.alignLeft("bat", 1, "yz")  = "bat"StringUtil.alignLeft("bat", -1, "yz") = "bat"
     * StringUtil.alignLeft("bat", 5, null)  = "bat  "
     * StringUtil.alignLeft("bat", 5, "")    = "bat  "</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padStr ����ַ���
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignLeft(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding  = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return str.concat(new String(padding));
        }
    }

    /**
     * ��չ���Ҷ����ַ������ÿո�<code>' '</code>�����ߡ�<pre>StringUtil.alignRight(null, *)   = null
     * StringUtil.alignRight("", 3)     = "   "StringUtil.alignRight("bat", 3)  = "bat"
     * StringUtil.alignRight("bat", 5)  = "  bat"StringUtil.alignRight("bat", 1)  = "bat"
     * StringUtil.alignRight("bat", -1) = "bat"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignRight(String str, int size) {
        return alignRight(str, size, ' ');
    }

    /**
     * ��չ���Ҷ����ַ�������ָ���ַ������ߡ�<pre>StringUtil.alignRight(null, *, *)     = null
     * StringUtil.alignRight("", 3, 'z')     = "zzz"StringUtil.alignRight("bat", 3, 'z')  = "bat"
     * StringUtil.alignRight("bat", 5, 'z')  = "zzbat"StringUtil.alignRight("bat", 1, 'z')  = "bat"
     * StringUtil.alignRight("bat", -1, 'z') = "bat"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padChar ����ַ�
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignRight(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignRight(str, size, String.valueOf(padChar));
    }

    /**
     * ��չ���Ҷ����ַ�������ָ���ַ��������ߡ�<pre>StringUtil.alignRight(null, *, *)      = null
     * StringUtil.alignRight("", 3, "z")      = "zzz"StringUtil.alignRight("bat", 3, "yz")  = "bat"
     * StringUtil.alignRight("bat", 5, "yz")  = "yzbat"
     * StringUtil.alignRight("bat", 8, "yz")  = "yzyzybat"
     * StringUtil.alignRight("bat", 1, "yz")  = "bat"StringUtil.alignRight("bat", -1, "yz") = "bat"
     * StringUtil.alignRight("bat", 5, null)  = "  bat"
     * StringUtil.alignRight("bat", 5, "")    = "  bat"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padStr ����ַ���
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String alignRight(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding  = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return new String(padding).concat(str);
        }
    }

    /**
     * ��չ�������ַ������ÿո�<code>' '</code>������ߡ�<pre>StringUtil.center(null, *)   = null
     * StringUtil.center("", 4)     = "    "StringUtil.center("ab", -1)  = "ab"
     * StringUtil.center("ab", 4)   = " ab "StringUtil.center("abcd", 2) = "abcd"
     * StringUtil.center("a", 4)    = " a  "</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    /**
     * ��չ�������ַ�������ָ���ַ�������ߡ�<pre>StringUtil.center(null, *, *)     = null
     * StringUtil.center("", 4, ' ')     = "    "StringUtil.center("ab", -1, ' ')  = "ab"
     * StringUtil.center("ab", 4, ' ')   = " ab "StringUtil.center("abcd", 2, ' ') = "abcd"
     * StringUtil.center("a", 4, ' ')    = " a  "StringUtil.center("a", 4, 'y')    = "yayy"</pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padChar ����ַ�
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String center(String str, int size, char padChar) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + (pads / 2), padChar);
        str = alignLeft(str, size, padChar);
        return str;
    }

    /**
     * ��չ�������ַ�������ָ���ַ���������ߡ�<pre>StringUtil.center(null, *, *)     = null
     * StringUtil.center("", 4, " ")     = "    "StringUtil.center("ab", -1, " ")  = "ab"
     * StringUtil.center("ab", 4, " ")   = " ab "StringUtil.center("abcd", 2, " ") = "abcd"
     * StringUtil.center("a", 4, " ")    = " a  "StringUtil.center("a", 4, "yz")   = "yayz"
     * StringUtil.center("abc", 7, null) = "  abc  "StringUtil.center("abc", 7, "")   = "  abc  "
     * </pre>
     *
     * @param str Ҫ������ַ���
     * @param size ��չ�ַ�����ָ�����
     * @param padStr ����ַ���
     *
     * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String center(String str, int size, String padStr) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + (pads / 2), padStr);
        str = alignLeft(str, size, padStr);
        return str;
    }

    /**
     * ȥ�������еĿ�ֵ��
     *
     * @param strs �ַ�������
     *
     * @return ����������
     */
    public static String[] trimStringArray(String[] strs) {
        if (strs == null) {
            return new String[0];
        }

        List strList = new ArrayList(strs.length);

        for (int i = 0; i < strs.length; i++) {
            String str = StringUtil.trimWhitespace(strs[i]);

            if (str.length() > 0) {
                strList.add(str);
            }
        }

        return (String[]) strList.toArray(new String[strList.size()]);
    }

    public static String toString(Object obj) {
        return (obj == null) ? ""
                             : obj.toString();
    }
}
