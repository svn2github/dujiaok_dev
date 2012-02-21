package com.alibaba.antx.util;

/**
 * ��һ������йص�С����.
 *
 * @author Michael Zhou
 *
 */
public class ObjectUtil {
    /**
     * �Ƚ����������Ƿ���ͬ��
     *
     * @param o1 ����1
     * @param o2 ����2
     *
     * @return �����ͬ���򷵻�<code>true</code>
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }

        return o1.equals(o2);
    }
}
