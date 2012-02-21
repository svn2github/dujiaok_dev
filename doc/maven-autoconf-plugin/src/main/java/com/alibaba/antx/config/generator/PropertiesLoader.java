package com.alibaba.antx.config.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.generator.expr.CompositeExpression;
import com.alibaba.antx.config.generator.expr.Expression;
import com.alibaba.antx.config.generator.expr.ExpressionContext;
import com.alibaba.antx.config.generator.expr.ReferenceExpression;
import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.collection.ExtendedProperties;

public abstract class PropertiesLoader {
    /**
     * װ�������ļ���
     * 
     * @param propsFile �����ļ�
     * @param propsCharset װ�������ļ�ʱʹ�õı����ַ���
     * @return �����ļ�������
     */
    public static Map loadPropertiesFile(InputStream istream, String propsCharset, String url) {
        return loadPropertiesFile(istream, propsCharset, url, true);
    }

    /**
     * װ�������ļ���
     * 
     * @param propsFile �����ļ�
     * @param propsCharset װ�������ļ�ʱʹ�õı����ַ���
     * @return �����ļ�������
     */
    public static Map loadPropertiesFile(InputStream istream, String propsCharset, String url, boolean closeOnExit) {
        ExtendedProperties props = new ExtendedProperties();

        try {
            props.load(istream, propsCharset, url);
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                }
            }
        }

        return props;
    }

    /**
     * װ�������ļ���
     * 
     * @param propsFile �����ļ�
     * @param propsCharset װ�������ļ�ʱʹ�õı����ַ���
     * @return �����ļ�������
     */
    public static Map loadPropertiesFile(File propsFile, String propsCharset) {
        ExtendedProperties props = new ExtendedProperties();

        if (propsFile.exists()) {
            try {
                props.load(propsFile.toURI().toURL(), propsCharset);
            } catch (IOException e) {
                throw new ConfigException(e);
            }
        }

        return props;
    }

    /**
     * �����Դ�Դ���Ա��кϲ���Ŀ�����Ա�����������ģ��򸲸�֮��
     * 
     * @param dest Ŀ�����Ա�
     * @param src Դ���Ա�
     */
    public static void mergeProperties(Map dest, Map src) {
        for (Iterator i = src.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                Expression expr = CompositeExpression.parse((String) value);

                if (expr != null) {
                    value = expr;
                }
            }

            dest.put(name, value);
            dest.put(StringUtil.getValidIdentifier(name), value);
        }
    }

    /**
     * ȡֵ�����㡣
     */
    public static Object evaluate(String name, final Map props) {
        return new ReferenceExpression(name).evaluate(new ExpressionContext() {
            public Object get(String key) {
                return props.get(key);
            }

            public void put(String key, Object value) {
                props.put(key, value);
            }
        });
    }
}
