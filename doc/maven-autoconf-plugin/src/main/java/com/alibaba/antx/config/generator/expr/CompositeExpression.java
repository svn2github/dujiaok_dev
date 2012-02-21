package com.alibaba.antx.config.generator.expr;

import java.util.ArrayList;
import java.util.List;

/**
 * ����һ����ϵı��ʽ��
 * 
 * @author Michael Zhou
 */
public class CompositeExpression implements Expression {
    private String       expr;
    private Expression[] expressions;

    /**
     * ����һ����ϵı��ʽ��
     * 
     * @param expr ���ʽ�ַ���
     * @param expressions ���ʽ�б�
     */
    public CompositeExpression(String expr, List expressions) {
        this.expr = expr;
        this.expressions = (Expression[]) expressions.toArray(new Expression[expressions.size()]);
    }

    /**
     * ȡ�ñ��ʽ�ַ�����ʾ��
     * 
     * @return ���ʽ�ַ�����ʾ
     */
    public String getExpressionText() {
        return expr;
    }

    /**
     * ��ָ�����������м�����ʽ��
     * 
     * @param context ������
     * @return ���ʽ�ļ�����
     */
    public Object evaluate(ExpressionContext context) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            Object value = expression.evaluate(context);

            if (value != null) {
                buffer.append(value);
            }
        }

        return buffer.toString();
    }

    /**
     * �������ʽ��
     * <ul>
     * <li>������ʽ�в�������<code>${...}</code>�����򴴽�<code>ConstantExpression</code>��</li>
     * <li>������ʽ�ԡ�<code>${</code>����ʼ�����ԡ�<code>}</code>����β���򴴽����ñ��ʽ��</li>
     * <li>������ʽ������<code>${...}</code>�������ڴ�֮�⻹�б���ַ����򴴽�
     * <code>CompositeExpression</code>��</li>
     * </ul>
     * 
     * @param expr ���ʽ�ַ���
     * @return ���ʽ
     */
    public static Expression parse(String expr) {
        int length = expr.length();
        int startIndex = expr.indexOf("${");

        // ������ʽ������${}���򴴽�constant expression��
        if (startIndex < 0) {
            return new ConstantExpression(expr);
        }

        int endIndex = expr.indexOf("}", startIndex + 2);

        if (endIndex < 0) {
            throw new ExpressionException("Missing '}' character at the end of expression: " + expr);
        }

        // ������ʽ��${��ͷ����}��β����ֱ�ӵ���factory���������ʽ��
        if ((startIndex == 0) && (endIndex == (length - 1))) {
            return new ReferenceExpression(expr.substring(2, endIndex));
        }

        // �������ϵı��ʽ��
        List expressions = new ArrayList();
        char ch = 0;
        int i = 0;

        StringBuffer chars = new StringBuffer();
        StringBuffer exprBuff = new StringBuffer();

        MAIN: while (i < length) {
            ch = expr.charAt(i);

            switch (ch) {
                case ('$'): {
                    if ((i + 1) < length) {
                        ++i;
                        ch = expr.charAt(i);

                        switch (ch) {
                            case ('$'): {
                                chars.append(ch);
                                break;
                            }

                            case ('{'): {
                                if (chars.length() > 0) {
                                    expressions.add(new ConstantExpression(chars.toString()));
                                    chars.delete(0, chars.length());
                                }

                                if ((i + 1) < length) {
                                    ++i;

                                    while (i < length) {
                                        ch = expr.charAt(i);

                                        {
                                            switch (ch) {
                                                case ('"'): {
                                                    exprBuff.append(ch);
                                                    ++i;

                                                    DOUBLE_QUOTE: while (i < length) {
                                                        ch = expr.charAt(i);

                                                        boolean escape = false;

                                                        switch (ch) {
                                                            case ('\\'): {
                                                                escape = true;
                                                                ++i;
                                                                exprBuff.append(ch);
                                                                break;
                                                            }

                                                            case ('"'): {
                                                                ++i;
                                                                exprBuff.append(ch);
                                                                break DOUBLE_QUOTE;
                                                            }

                                                            default: {
                                                                escape = false;
                                                                ++i;
                                                                exprBuff.append(ch);
                                                            }
                                                        }
                                                    }

                                                    break;
                                                }

                                                case ('\''): {
                                                    exprBuff.append(ch);
                                                    ++i;

                                                    SINGLE_QUOTE: while (i < length) {
                                                        ch = expr.charAt(i);

                                                        boolean escape = false;

                                                        switch (ch) {
                                                            case ('\\'): {
                                                                escape = true;
                                                                ++i;
                                                                exprBuff.append(ch);
                                                                break;
                                                            }

                                                            case ('\''): {
                                                                ++i;
                                                                exprBuff.append(ch);
                                                                break SINGLE_QUOTE;
                                                            }

                                                            default: {
                                                                escape = false;
                                                                ++i;
                                                                exprBuff.append(ch);
                                                            }
                                                        }
                                                    }

                                                    break;
                                                }

                                                case ('}'): {
                                                    expressions.add(new ReferenceExpression(exprBuff.toString()));

                                                    exprBuff.delete(0, exprBuff.length());
                                                    ++i;
                                                    continue MAIN;
                                                }

                                                default: {
                                                    exprBuff.append(ch);
                                                    ++i;
                                                }
                                            }
                                        }
                                    }
                                }

                                break;
                            }

                            default:
                                chars.append(ch);
                        }
                    } else {
                        chars.append(ch);
                    }

                    break;
                }

                default:
                    chars.append(ch);
            }

            ++i;
        }

        if (chars.length() > 0) {
            expressions.add(new ConstantExpression(chars.toString()));
        }

        return new CompositeExpression(expr, expressions);
    }
}
