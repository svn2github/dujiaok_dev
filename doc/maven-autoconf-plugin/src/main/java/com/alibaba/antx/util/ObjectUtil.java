package com.alibaba.antx.util;

/**
 * 和一般对象有关的小工具.
 *
 * @author Michael Zhou
 *
 */
public class ObjectUtil {
    /**
     * 比较两个对象是否相同。
     *
     * @param o1 对象1
     * @param o2 对象2
     *
     * @return 如果相同，则返回<code>true</code>
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }

        return o1.equals(o2);
    }
}
