package com.alibaba.antx.util.regex;

import org.apache.oro.text.regex.MalformedPatternException;

/**
 * ����ཫһ������ͨ������ļ�·��, �����Perl5��������ʽ.  ��ʽ��������:
 * 
 * <ul>
 * <li>
 * �Ϸ���<em>�ļ����ַ�</em>����: ��ĸ/����/�»���/С����/�̺���;
 * </li>
 * <li>
 * �Ϸ���<em>·���ָ���</em>Ϊб��"/";
 * </li>
 * <li>
 * "��"����0������<em>�ļ����ַ�</em>;
 * </li>
 * <li>
 * "��"����1��<em>�ļ����ַ�</em>;
 * </li>
 * <li>
 * "����"����0������<em>�ļ����ַ�</em>��<em>·���ָ���</em>;
 * </li>
 * <li>
 * ������������3��"��";
 * </li>
 * <li>
 * ������������2��<em>·���ָ���</em>;
 * </li>
 * <li>
 * "����"��ǰ��ֻ����<em>·���ָ���</em>.
 * </li>
 * </ul>
 * 
 * ת�����������ʽ, ��ÿһ��ͨ�������<em>���ñ���</em>, ����Ϊ<code>$1</code>, <code>$2</code>, ...
 *
 * @author Michael Zhou
 * @version $Id: PathNameCompiler.java 509 2004-02-16 05:42:07Z baobao $
 */
public class PathNameCompiler extends Perl5CompilerWrapper {
    /** ǿ��ʹ�þ���·�� */
    public static final int FORCE_ABSOLUTE_PATH = 0x1;

    /** ǿ��ʹ�����·�� */
    public static final int FORCE_RELATIVE_PATH = 0x2;

    /** ��ͷƥ�� */
    public static final int FORCE_MATCH_PREFIX = 0x4;

    // ˽�г���
    private static final char   SLASH                       = '/';
    private static final char   UNDERSCORE                  = '_';
    private static final char   DASH                        = '-';
    private static final char   DOT                         = '.';
    private static final char   STAR                        = '*';
    private static final char   QUESTION                    = '?';
    private static final String REGEX_MATCH_PREFIX          = "^";
    private static final String REGEX_WORD_BOUNDARY         = "\\b";
    private static final String REGEX_SLASH                 = "\\/";
    private static final String REGEX_SLASH_NO_DUP          = "\\/(?!\\/)";
    private static final String REGEX_FILE_NAME_CHAR        = "[\\w\\-\\.]";
    private static final String REGEX_FILE_NAME_SINGLE_CHAR = "(" + REGEX_FILE_NAME_CHAR + ")";
    private static final String REGEX_FILE_NAME             = "(" + REGEX_FILE_NAME_CHAR + "*)";
    private static final String REGEX_FILE_PATH             = "(" + REGEX_FILE_NAME_CHAR + "+(?:"
        + REGEX_SLASH_NO_DUP + REGEX_FILE_NAME_CHAR + "*)*(?=" + REGEX_SLASH + "|$)|)"
        + REGEX_SLASH + "?";

    // ��һ��token��״̬
    private static final int LAST_TOKEN_START       = 0;
    private static final int LAST_TOKEN_SLASH       = 1;
    private static final int LAST_TOKEN_FILE_NAME   = 2;
    private static final int LAST_TOKEN_STAR        = 3;
    private static final int LAST_TOKEN_DOUBLE_STAR = 4;
    private static final int LAST_TOKEN_QUESTION    = 5;

    /**
     * ������ͨ�����·�����ʽ, �����perl5������ʽ.
     *
     * @param pattern Ҫ�����·��
     * @param options λ��־
     *
     * @return Perl5������ʽ�ַ���
     *
     * @throws MalformedPatternException ���·���ַ�����ʽ����ȷ
     */
    public String toPerl5Regex(char[] pattern, int options)
            throws MalformedPatternException {
        int          lastToken = LAST_TOKEN_START;
        StringBuffer buffer = new StringBuffer(pattern.length * 2);

        boolean      forceMatchPrefix  = (options & FORCE_MATCH_PREFIX) != 0;
        boolean      forceAbsolutePath = (options & FORCE_ABSOLUTE_PATH) != 0;
        boolean      forceRelativePath = (options & FORCE_RELATIVE_PATH) != 0;

        // �����һ���ַ�Ϊslash, �������Ҫ��forceMatchPrefix, ���ͷƥ��
        if (forceMatchPrefix || ((pattern.length > 0) && (pattern[0] == SLASH))) {
            buffer.append(REGEX_MATCH_PREFIX);
        }

        for (int i = 0; i < pattern.length; i++) {
            char ch = pattern[i];

            if (forceAbsolutePath && (lastToken == LAST_TOKEN_START) && (ch != SLASH)) {
                throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
            }

            switch (ch) {
                case SLASH:

                    // slash���治����slash, slash����λ�����ַ�(���ָ����force relative path�Ļ�)
                    if (lastToken == LAST_TOKEN_SLASH) {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    } else if (forceRelativePath && (lastToken == LAST_TOKEN_START)) {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    // ��Ϊ**�Ѿ�������slash, ���Բ���Ҫ�����ƥ��slash
                    if (lastToken != LAST_TOKEN_DOUBLE_STAR) {
                        buffer.append(REGEX_SLASH_NO_DUP);
                    }

                    lastToken = LAST_TOKEN_SLASH;
                    break;

                case STAR:

                    int j = i + 1;

                    if ((j < pattern.length) && (pattern[j] == STAR)) {
                        i = j;

                        // **ǰ��ֻ����slash
                        if ((lastToken != LAST_TOKEN_START) && (lastToken != LAST_TOKEN_SLASH)) {
                            throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                        }

                        lastToken = LAST_TOKEN_DOUBLE_STAR;
                        buffer.append(REGEX_FILE_PATH);
                    } else {
                        // *ǰ�治����*��**
                        if ((lastToken == LAST_TOKEN_STAR) || (lastToken == LAST_TOKEN_DOUBLE_STAR)) {
                            throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                        }

                        lastToken = LAST_TOKEN_STAR;
                        buffer.append(REGEX_FILE_NAME);
                    }

                    break;

                case QUESTION:
                    lastToken = LAST_TOKEN_QUESTION;
                    buffer.append(REGEX_FILE_NAME_SINGLE_CHAR);
                    break;

                default:

                    // **��ֻ����slash
                    if (lastToken == LAST_TOKEN_DOUBLE_STAR) {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    if (Character.isLetterOrDigit(ch) || (ch == UNDERSCORE) || (ch == DASH)) {
                        // ����word�߽�, ��������ƥ��
                        if (lastToken == LAST_TOKEN_START) {
                            buffer.append(REGEX_WORD_BOUNDARY).append(ch); // ǰ�߽�
                        } else if ((i + 1) == pattern.length) {
                            buffer.append(ch).append(REGEX_WORD_BOUNDARY); // ��߽�
                        } else {
                            buffer.append(ch);
                        }
                    } else if (ch == DOT) {
                        buffer.append(ESCAPE_CHAR).append(DOT);
                    } else {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    lastToken = LAST_TOKEN_FILE_NAME;
            }
        }

        return buffer.toString();
    }
}
