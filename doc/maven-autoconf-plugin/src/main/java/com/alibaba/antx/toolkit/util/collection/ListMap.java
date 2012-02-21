package com.alibaba.antx.toolkit.util.collection;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * �����<code>java.util.Map</code>.
 * </p>
 *
 * <p>
 * ����ӵ��<code>java.util.Map</code>��������������, <code>ListMap</code>�е� ��(<code>Map.Entry</code>)�������.
 * Ҳ����˵, �������Լ�ֵ(key)������, Ҳ����������(index)������. ����,
 * </p>
 *
 * <p>
 * ͨ��key����:
 * </p>
 * <pre>
 * Object value1 = listMap.get("key1");
 * </pre>
 *
 * <p>
 * ͨ������index, ȡ��key��value:
 * </p>
 * <pre>
 * Object value2 = listMap.get(2);
 * Object key2   = listMap.getKey(2);
 * </pre>
 *
 * <p>
 * ͨ������index, ɾ��һ��, �����ر�ɾ������:
 * </p>
 * <pre>
 * Map.Entry removedEntry = listMap.remove(3);
 * </pre>
 *
 * <p>
 * ����, �����ṩ����������: <code>keyList()</code>, <code>valueList()</code>��<code>entryList()</code>,
 * ����ȡ��key, value��entry��<code>List</code>. �����<code>Map.keySet()</code>, <code>Map.values()</code>
 * �Լ�<code>Map.entrySet()</code>, ����ֻ�ṩ��ȡ�������key��entry��<code>Set</code>,
 * �Լ�ȡ��value��<code>Collection</code>�ķ���.
 * </p>
 *
 * @version $Id: ListMap.java 509 2004-02-16 05:42:07Z baobao $
 * @author Michael Zhou
 */
public interface ListMap extends Map {
    /**
     * ����ָ��index����value.  ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫ���ص�value������ֵ.
     *
     * @return ָ��index����value����
     */
    Object get(int index);

    /**
     * ����ָ��index����key. ���index������Χ, �򷵻�<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫ���ص�key������ֵ.
     *
     * @return ָ��index����key����
     */
    Object getKey(int index);

    /**
     * ɾ��ָ��index������. ���index������Χ, �򷵻�<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫɾ�����������ֵ.
     *
     * @return ��ɾ����<code>Map.Entry</code>��.
     */
    Map.Entry remove(int index);

    /**
     * ��������key��<code>List</code>.
     *
     * @return ����key��<code>List</code>.
     */
    List keyList();

    /**
     * ��������value��<code>List</code>.
     *
     * @return ����value��<code>List</code>.
     */
    List valueList();

    /**
     * ��������entry��<code>List</code>.
     *
     * @return ����entry��<code>List</code>.
     */
    List entryList();
}