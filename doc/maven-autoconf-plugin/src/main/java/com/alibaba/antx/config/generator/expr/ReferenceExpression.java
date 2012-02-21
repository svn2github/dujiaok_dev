package com.alibaba.antx.config.generator.expr;

import com.alibaba.antx.util.StringUtil;

/**
 * 代表一个引用表达式，该表达式的值引用context中的其它表达式。
 * 
 * @author Michael Zhou
 */
public class ReferenceExpression implements Expression {
    private String ref;

    public ReferenceExpression(String ref) {
        this.ref = ref;
    }

    public String getExpressionText() {
        return "${" + ref + "}";
    }

    public Object evaluate(final ExpressionContext context) {
        if (StringUtil.isBlank(ref)) {
            return null;
        }

        Object value = context.get(ref);

        if (value == null) {
            return null;
        } else if (value instanceof Expression) {
            return ((Expression) value).evaluate(new ExpressionContext() {
                public Object get(String key) {
                    // 避免无限递归
                    if (ref.equals(key)
                            || StringUtil.getValidIdentifier(ref).equals(StringUtil.getValidIdentifier(key))) {
                        return null;
                    } else {
                        return context.get(key);
                    }
                }

                public void put(String key, Object value) {
                    context.put(key, value);
                }
            });
        } else {
            return value;
        }
    }

    public String toString() {
        return getExpressionText();
    }
}
