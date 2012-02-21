package com.alibaba.antx.config.generator.expr;

import com.alibaba.antx.util.StringUtil;

/**
 * ����һ�����ñ��ʽ���ñ��ʽ��ֵ����context�е��������ʽ��
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
                    // �������޵ݹ�
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
