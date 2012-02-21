package com.alibaba.antx.toolkit.util.collection;

import java.util.Map;

/**
 * <p>
 * <code>Map.Entry</code>��Ĭ��ʵ��. ������������:
 * </p>
 *
 * <ul>
 * <li>
 * ֧��ֵΪ<code>null</code>��key
 * </li>
 * <li>
 * ���Ժ�����<code>Map.Entry</code>��ʵ�ֽ���<code>equals</code>�Ƚ�
 * </li>
 * <li>
 * �������<code>Map.Entry</code>��ͬ(<code>e1.equals(e2) == true</code>), �����ǵ�<code>hashCode()</code>Ҳ���
 * </li>
 * </ul>
 *
 *
 * @version $Id: DefaultMapEntry.java 509 2004-02-16 05:42:07Z baobao $
 * @author Michael Zhou
 */
public class DefaultMapEntry implements Map.Entry {
    private final Object key;
    private Object       value;

    /**
     * ����һ��<code>Map.Entry</code>.
     *
     * @param key <code>Map.Entry</code>��key
     * @param value <code>Map.Entry</code>��value
     */
    public DefaultMapEntry(Object key, Object value) {
        this.key   = key;
        this.value = value;
    }

    /**
     * ȡ��key.
     *
     * @return <code>Map.Entry</code>��key
     */
    public Object getKey() {
        return key;
    }

    /**
     * ȡ��value.
     *
     * @return <code>Map.Entry</code>��value
     */
    public Object getValue() {
        return value;
    }

    /**
     * ����value��ֵ.
     *
     * @param value �µ�valueֵ
     *
     * @return �ϵ�valueֵ
     */
    public Object setValue(Object value) {
        Object oldValue = this.value;

        this.value = value;

        return oldValue;
    }

    /**
     * �ж����������Ƿ���ͬ.
     *
     * @param o Ҫ�ȽϵĶ���
     *
     * @return �����ͬ, �򷵻�<code>true</code>
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof Map.Entry)) {
            return false;
        }

        Map.Entry e  = (Map.Entry) o;
        Object    k1 = getKey();
        Object    k2 = e.getKey();

        if ((k1 == k2) || ((k1 != null) && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();

            if ((v1 == v2) || ((v1 != null) && v1.equals(v2))) {
                return true;
            }
        }

        return false;
    }

    /**
     * ȡ��<code>Map.Entry</code>��hashֵ. �������<code>Map.Entry</code>��ͬ, �����ǵ�hashֵҲ��ͬ.
     *
     * @return hashֵ
     */
    public int hashCode() {
        return ((key == null) ? 0
                              : key.hashCode())
               ^ ((value == null) ? 0
                                  : value.hashCode());
    }

    /**
     * ��<code>Map.Entry</code>ת�����ַ���.
     *
     * @return �ַ�����ʽ��<code>Map.Entry</code>
     */
    public String toString() {
        return getKey() + "=" + getValue();
    }
}