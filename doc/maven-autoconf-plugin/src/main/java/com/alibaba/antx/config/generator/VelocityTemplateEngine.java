package com.alibaba.antx.config.generator;

import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigException;

/**
 * Velocity���档
 * 
 * @author Michael Zhou
 */
public class VelocityTemplateEngine {
    private static Log                 log    = LogFactory.getLog(VelocityTemplateEngine.class);
    private static VelocityTemplateEngine instance;
    private VelocityEngine                engine = new VelocityEngine();

    public static VelocityTemplateEngine getInstance() {
        if (instance == null) {
            instance = new VelocityTemplateEngine();
        }

        return instance;
    }

    /**
     * ��ʼ��velocity.
     */
    public VelocityTemplateEngine() {
        // parser������.
        engine.setProperty(RuntimeConstants.PARSER_POOL_SIZE, new Integer(ConfigConstant.VELOCITY_NUMBER_OF_PARSERS));

        // ����logϵͳ.
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new LogSystem());

        // ����ݹ�
        engine.setProperty("velocimacro.context.localscope", "true");

        // ����resource loaderϵͳ.
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        // ����velocimacro.
        engine.setProperty(RuntimeConstants.VM_LIBRARY, ConfigConstant.VELOCITY_MACRO_FILE);

        // ִ�г�ʼ��.
        try {
            engine.init();
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    /**
     * ��Ⱦģ��.
     * 
     * @param context ��������Ϣ
     * @param reader ģ��Դ
     * @return ����Ⱦ����ַ�����
     * @throws Exception ��Ⱦ����
     */
    public char[] render(Context context, Reader reader) throws Exception {
        CharArrayWriter writer = new CharArrayWriter();

        try {
            render(context, reader, writer);
        } finally {
            writer.flush();
            writer.close();
        }

        return writer.toCharArray();
    }

    /**
     * ��Ⱦģ��.
     * 
     * @param context ��������Ϣ
     * @param reader ģ��Դ
     * @param writer �����
     * @return ����Ⱦ����ַ�����
     * @throws Exception ��Ⱦ����
     */
    public void render(Context context, Reader reader, Writer writer) throws Exception {
        engine.evaluate(context, writer, "config", reader);
    }

    /**
     * Velocity Logger
     */
    private class LogSystem implements org.apache.velocity.runtime.log.LogSystem {
        /**
         * ʵ��<code>LogSystem</code>�ӿ�, ��ʼ��.
         * 
         * @param runtimeServices velocity����
         */
        public void init(RuntimeServices runtimeServices) {
        }

        /**
         * ʵ��<code>LogSystem</code>�ӿ�, ��¼velocity��������Ϣ.
         * 
         * @param level ��Ϣ����
         * @param message ��Ϣ����
         */
        public void logVelocityMessage(int level, String message) {
            switch (level) {
                case LogSystem.DEBUG_ID:
                    log.debug(processMessage(message));
                    break;

                case LogSystem.INFO_ID:
                    log.info(processMessage(message));
                    break;

                case LogSystem.WARN_ID:
                    log.warn(processMessage(message));
                    break;

                case LogSystem.ERROR_ID:
                    log.error(processMessage(message));
                    break;
            }
        }

        /**
         * ��ȥmessage�е�exceptionǰ׺��ʹ֮�����ۡ�
         */
        private String processMessage(String message) {
            if (message != null) {
                message = message.replaceFirst("^[\\w\\.\\$]+Exception: ", "");
            }

            return message;
        }
    }
}
