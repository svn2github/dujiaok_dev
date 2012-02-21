package com.alibaba.antx.config;

import com.alibaba.antx.config.generator.VelocityTemplateEngine;

/**
 * Config�ĳ�����
 * 
 * @author Michael Zhou
 */
public interface ConfigConstant {
    /** Ĭ�ϵı����ַ����� */
    String DEFAULT_CHARSET            = "GBK";

    /** GUI����ģʽ�� */
    String MODE_GUI                   = "gui";

    /** �ı�����ģʽ�� */
    String MODE_TEXT                  = "text";

    /** ����ģʽ���� */
    String INTERACTIVE_ON             = "on";

    /** ����ģʽ���� */
    String INTERACTIVE_OFF            = "off";

    /** ����ģʽ���Զ� */
    String INTERACTIVE_AUTO           = "auto";

    /** Velocity���ã�������д�����parser��. */
    int    VELOCITY_NUMBER_OF_PARSERS = 1;

    /** Velocity���ã�Ĭ�ϵ�macro�ļ�, ��classpath��װ�� */
    String VELOCITY_MACRO_FILE        = VelocityTemplateEngine.class.getPackage().getName().replace('.', '/')
                                              + "/macro.vm";
}
