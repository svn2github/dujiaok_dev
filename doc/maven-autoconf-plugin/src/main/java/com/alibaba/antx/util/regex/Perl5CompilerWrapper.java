package com.alibaba.antx.util.regex;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;

import java.text.MessageFormat;

/**
 * ��һ�ָ�ʽ��patternת����Perl5��׼��������ʽ�ı�����.
 *
 * @author Michael Zhou
 * @version $Id: Perl5CompilerWrapper.java 509 2004-02-16 05:42:07Z baobao $
 */
public abstract class Perl5CompilerWrapper implements PatternCompiler {
    /** Ĭ�ϵ�λ��־ */
    public static final int DEFAULT_MASK = 0;

    /** ��Сд�����б�־ */
    public static final int CASE_INSENSITIVE_MASK = 0x1000;

    /** ����ֻ����pattern��־ */
    public static final int READ_ONLY_MASK = 0x2000;

    /** Escape�ַ� */
    protected static final char ESCAPE_CHAR = '\\';

    /** Perl5������ʽ�ı����ַ� */
    private static final String PERL5_META_CHARS = "*?+[]()|^$.{}\\";

    // ������Ϣ
    private static final String ERROR_UNEXPECTED_CHAR = "Unexpected \"{0}\" near \"{1}\"";

    /** �ڲ���perl5������ */
    protected final Perl5Compiler compiler = new Perl5Compiler();

    /**
     * ��pattern�����perl5������ʽ.
     *
     * @param pattern Ҫ�����pattern
     *
     * @return Perl5������ʽ
     *
     * @throws MalformedPatternException ���pattern��ʽ����ȷ
     */
    public Pattern compile(String pattern) throws MalformedPatternException {
        return compile(pattern.toCharArray(), DEFAULT_MASK);
    }

    /**
     * ��pattern�����perl5������ʽ.
     *
     * @param pattern Ҫ�����pattern
     * @param options λ��־
     *
     * @return Perl5������ʽ
     *
     * @throws MalformedPatternException ���pattern��ʽ����ȷ
     */
    public Pattern compile(String pattern, int options) throws MalformedPatternException {
        return compile(pattern.toCharArray(), options);
    }

    /**
     * ��pattern�����perl5������ʽ.
     *
     * @param pattern Ҫ�����pattern
     *
     * @return Perl5������ʽ
     *
     * @throws MalformedPatternException ���pattern��ʽ����ȷ
     */
    public Pattern compile(char[] pattern) throws MalformedPatternException {
        return compile(pattern, DEFAULT_MASK);
    }

    /**
     * ��pattern�����perl5������ʽ.
     *
     * @param pattern Ҫ�����pattern
     * @param options λ��־
     *
     * @return Perl5������ʽ
     *
     * @throws MalformedPatternException ���pattern��ʽ����ȷ
     */
    public Pattern compile(char[] pattern, int options) throws MalformedPatternException {
        int perlOptions = 0;

        if ((options & CASE_INSENSITIVE_MASK) != 0) {
            perlOptions |= Perl5Compiler.CASE_INSENSITIVE_MASK;
        }

        if ((options & READ_ONLY_MASK) != 0) {
            perlOptions |= Perl5Compiler.READ_ONLY_MASK;
        }

        return compiler.compile(toPerl5Regex(pattern, options), perlOptions);
    }

    /**
     * ��pattern�����perl5������ʽ�ַ���.
     *
     * @param pattern Ҫ�����pattern
     * @param options λ��־
     *
     * @return Perl5������ʽ
     *
     * @throws MalformedPatternException ���pattern��ʽ����ȷ
     */
    protected abstract String toPerl5Regex(char[] pattern, int options)
            throws MalformedPatternException;

    /**
     * �ж�ָ���ַ��Ƿ���perl5������ʽ�������ַ�
     *
     * @param ch �ַ�
     *
     * @return ����Ǳ����ַ�, �򷵻�<code>true</code>
     */
    protected boolean isPerl5MetaChar(char ch) {
        return PERL5_META_CHARS.indexOf(ch) != -1;
    }

    /**
     * ȡ�ô�����Ϣ.
     *
     * @param pattern ��ǰ�����pattern
     * @param index ��ǰ�����pattern��index
     *
     * @return ������Ϣ
     */
    protected String getDefaultErrorMessage(char[] pattern, int index) {
        return MessageFormat.format(ERROR_UNEXPECTED_CHAR,
            new Object[] { new Character(pattern[index]), new String(pattern, 0, index) });
    }
}
