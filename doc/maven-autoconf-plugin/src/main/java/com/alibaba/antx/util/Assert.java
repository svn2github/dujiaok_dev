package com.alibaba.antx.util;

public final class Assert {
    public static Object assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("[Assertion failed] - the argument is required; it must not be null");
        }

        return object;
    }

    public static Object assertNull(Object object) {
        if (object != null) {
            throw new IllegalArgumentException("[Assertion failed] - the object argument must be null");
        }

        return object;
    }

    public static void assertTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("[Assertion failed] - the expression must be true");
        }
    }
}
