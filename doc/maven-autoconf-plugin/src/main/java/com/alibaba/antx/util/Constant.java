package com.alibaba.antx.util;

public class Constant {

	/** Ĭ���ų����ļ��� */
    public static final String[] DEFAULT_EXCLUDES = {
                                                        // Miscellaneous typical temporary files
    "**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*",
                                                        
    // CVS
    "**/CVS", "**/CVS/**", "**/.cvsignore",
                                                        
    // SCCS
    "**/SCCS", "**/SCCS/**",
                                                        
    // Visual SourceSafe
    "**/vssver.scc",
                                                        
    // Subversion
    "**/.svn", "**/.svn/**",
                                                        
    // Mac
    "**/.DS_Store"
                                                    };
}
