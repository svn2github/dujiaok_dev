package com.alibaba.antx.util;

public class Constant {

	/** 默认排除的文件。 */
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
