package com.alibaba.antx.config;

import com.alibaba.antx.config.generator.VelocityTemplateEngine;

/**
 * Config的常量。
 * 
 * @author Michael Zhou
 */
public interface ConfigConstant {
    /** 默认的编码字符集。 */
    String DEFAULT_CHARSET            = "GBK";

    /** GUI交互模式。 */
    String MODE_GUI                   = "gui";

    /** 文本交互模式。 */
    String MODE_TEXT                  = "text";

    /** 交互模式：开 */
    String INTERACTIVE_ON             = "on";

    /** 交互模式：关 */
    String INTERACTIVE_OFF            = "off";

    /** 交互模式：自动 */
    String INTERACTIVE_AUTO           = "auto";

    /** Velocity设置：缓冲池中创建的parser数. */
    int    VELOCITY_NUMBER_OF_PARSERS = 1;

    /** Velocity设置：默认的macro文件, 从classpath中装入 */
    String VELOCITY_MACRO_FILE        = VelocityTemplateEngine.class.getPackage().getName().replace('.', '/')
                                              + "/macro.vm";
}
