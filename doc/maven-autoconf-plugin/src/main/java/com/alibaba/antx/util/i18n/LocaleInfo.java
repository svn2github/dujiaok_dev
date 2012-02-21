package com.alibaba.antx.util.i18n;

import com.alibaba.antx.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ����һ��locale��Ϣ���ࡣ
 *
 * @author Michael Zhou
 */
public class LocaleInfo {
    private static final String DEFAULT_LOCALE    = "china";
    private static final Map    LOCALE_MAP        = new HashMap();
    private static LocaleInfo   defaultLocaleInfo;

    static {
        LOCALE_MAP.put("us", new LocaleInfo("US", Locale.US, "ISO-8859-1"));
        LOCALE_MAP.put("china", new LocaleInfo("China", Locale.CHINA, "GBK"));

        try {
            setDefault(DEFAULT_LOCALE);
        } catch (UnsupportedLocaleException e) {
            throw new InternalError(); // ���ᷢ����
        }
    }

    private String name;
    private Locale locale;
    private String charset;

/**
     * ����һ��locale��Ϣ�
     *
     * @param name ������
     * @param locale ����
     * @param charset �ַ���
     */
    public LocaleInfo(String name, Locale locale, String charset) {
        this.name                                 = name;
        this.locale                               = locale;
        this.charset                              = charset;
    }

    /**
     * ȡ���ַ������ơ�
     *
     * @return �����ַ�������
     */
    public String getCharset() {
        return charset;
    }

    /**
     * ȡ������
     *
     * @return ����
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * ȡ�õ�������
     *
     * @return ������
     */
    public String getName() {
        return name;
    }

    /**
     * ȡ��ָ�����Ƶ�<code>ResourceBundle</code>��
     *
     * @param bundleName bundle����
     *
     * @return ָ�����Ƶ�<code>ResourceBundle</code>��
     */
    public ResourceBundle getBundle(String bundleName) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, getLocale());

        if (!getLocale().toString().startsWith(bundle.getLocale().toString())) {
            bundle = ResourceBundle.getBundle(bundleName, new Locale("", "", ""));
        }

        return bundle;
    }

    /**
     * ����Ĭ�ϵ�locale��
     *
     * @param name locale����
     *
     * @throws UnsupportedLocaleException ���ָ�����Ƶ�locale������
     */
    public static void setDefault(String name) throws UnsupportedLocaleException {
        LocaleInfo locale = get(name);

        if (locale == null) {
            throw new UnsupportedLocaleException("Unsupported locale \"" + name + "\"");
        }

        defaultLocaleInfo = locale;
    }

    /**
     * ȡ��Ĭ�ϵ�locale��
     *
     * @return Ĭ�ϵ�locale
     */
    public static LocaleInfo getDefault() {
        return defaultLocaleInfo;
    }

    /**
     * ȡ��ָ�����Ƶ�locale��
     *
     * @param name locale����
     *
     * @return ָ�����Ƶ�locale����������ڣ��򷵻�<code>null</code>��
     */
    public static LocaleInfo get(String name) {
        if (name == null) {
            return null;
        }

        LocaleInfo localeInfo = (LocaleInfo) LOCALE_MAP.get(name.toLowerCase());

        if (localeInfo == null) {
            int    index       = name.indexOf(":");
            String localeName  = null;
            String charsetName = null;

            if (index >= 0) {
                localeName  = name.substring(0, index).trim();
                charsetName = name.substring(index + 1).trim();
            } else {
                localeName = name.trim();
            }

            Locale locale = null;

            if (localeName != null) {
                String[] localeParts = StringUtil.split(localeName, "_");

                if (localeParts.length == 1) {
                    locale = new Locale(localeParts[0]);
                }

                if (localeParts.length == 2) {
                    locale = new Locale(localeParts[0], localeParts[1]);
                }

                if (localeParts.length == 3) {
                    locale = new Locale(localeParts[0], localeParts[1], localeParts[2]);
                }

                if (locale == null) {
                    return null;
                }
            }

            localeInfo = new LocaleInfo(name, locale, charsetName);
        }

        return localeInfo;
    }
}
