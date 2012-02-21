package com.alibaba.antx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.antx.toolkit.util.collection.ArrayHashSet;

/**
 * ����includes��excludes��pattern���ϡ�
 *
 * @author Michael Zhou
 */
public class PatternSet {
    private String[] includes;
    private String[] excludes;

    public PatternSet() {
        this(new String[0], new String[0]);
    }

    public PatternSet(String includes) {
        this(StringUtils.split(includes), new String[0]);
    }

    public PatternSet(String includes, String excludes) {
        this(StringUtils.split(includes), StringUtil.split(excludes));
    }

    public PatternSet(String[] includes, String[] excludes) {
        this.includes = normalizePatterns(includes);
        this.excludes = normalizePatterns(excludes);
    }

    public PatternSet(PatternSet patterns, PatternSet defaultPatterns) {
        if ((patterns == null) || patterns.isEmpty()) {
            patterns = defaultPatterns;

            if (patterns == null) {
                patterns = new PatternSet();
            }
        }

        this.includes = patterns.includes;
        this.excludes = patterns.excludes;
    }

    /**
     * ������pattern��񻯳ɣ���/ǰ׺/��׺����/�ָ���
     */
    private static String[] normalizePatterns(String[] patterns) {
        if (patterns == null) {
            return new String[0];
        }

        List patternList = new ArrayList(patterns.length);

        for (int i = 0; i < patterns.length; i++) {
            String pattern = patterns[i];

            if (pattern == null) {
                continue;
            } else {
                pattern = pattern.trim().replace('\\', '/');
            }

            int startIndex = 0;
            int endIndex   = pattern.length();

            while ((startIndex < pattern.length()) && (pattern.charAt(startIndex) == '/')) {
                startIndex++;
            }

            while ((endIndex > 0) && (pattern.charAt(endIndex - 1) == '/')) {
                endIndex--;
            }

            if ((startIndex > 0) || (endIndex < pattern.length())) {
                pattern = pattern.substring(startIndex, endIndex);
            }

            if (pattern.length() > 0) {
                patternList.add(pattern);
            }
        }

        return (String[]) patternList.toArray(new String[patternList.size()]);
    }

    /**
     * �ų�ָ�����ļ���
     */
    public PatternSet addExcludes(String[] addedExcludes) {
        Set excludeSet = new ArrayHashSet();

        for (int i = 0; i < excludes.length; i++) {
            excludeSet.add(excludes[i]);
        }

        for (int i = 0; i < addedExcludes.length; i++) {
            excludeSet.add(addedExcludes[i]);
        }

        excludes = (String[]) excludeSet.toArray(new String[excludeSet.size()]);

        return this;
    }

    /**
     * �ų�Ĭ�ϵ��ļ���
     */
    public PatternSet addDefaultExcludes() {
        return addExcludes(Constant.DEFAULT_EXCLUDES);
    }

    /**
     * ȡ��include patterns
     */
    public String[] getIncludes() {
        return includes;
    }

    /**
     * ȡ��exclude patterns
     */
    public String[] getExcludes() {
        return excludes;
    }

    /**
     * �Ƿ�Ϊ�ա�
     */
    public boolean isEmpty() {
        return (includes.length == 0) && (excludes.length == 0);
    }

    /**
     * �Ƿ�������С�
     */
    public boolean isIncludeAll() {
        if (isExcludeAll()) {
            return false;
        }

        return (includes.length == 1) && "**".equals(includes[0]) && (excludes.length == 0);
    }

    /**
     * �Ƿ��ų����С�
     */
    public boolean isExcludeAll() {
        return (excludes.length == 1) && "**".equals(excludes[0]);
    }

    /**
     * ת�����ַ�����ʽ��
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        // includes
        buffer.append("includes[");

        for (int i = 0; i < includes.length; i++) {
            String include = includes[i];

            if (i > 0) {
                buffer.append(", ");
            }

            buffer.append(include);
        }

        buffer.append("]");

        // excludes
        buffer.append(", excludes[");

        for (int i = 0; i < excludes.length; i++) {
            String exclude = excludes[i];

            if (i > 0) {
                buffer.append(", ");
            }

            buffer.append(exclude);
        }

        buffer.append("]");

        return buffer.toString();
    }
}
