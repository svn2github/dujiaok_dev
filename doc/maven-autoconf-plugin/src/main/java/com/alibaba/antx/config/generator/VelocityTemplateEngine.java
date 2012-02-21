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
 * Velocity引擎。
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
     * 初始化velocity.
     */
    public VelocityTemplateEngine() {
        // parser的数量.
        engine.setProperty(RuntimeConstants.PARSER_POOL_SIZE, new Integer(ConfigConstant.VELOCITY_NUMBER_OF_PARSERS));

        // 设置log系统.
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new LogSystem());

        // 允许递归
        engine.setProperty("velocimacro.context.localscope", "true");

        // 设置resource loader系统.
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        // 设置velocimacro.
        engine.setProperty(RuntimeConstants.VM_LIBRARY, ConfigConstant.VELOCITY_MACRO_FILE);

        // 执行初始化.
        try {
            engine.init();
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    /**
     * 渲染模板.
     * 
     * @param context 上下文信息
     * @param reader 模板源
     * @return 被渲染后的字符数组
     * @throws Exception 渲染出错
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
     * 渲染模板.
     * 
     * @param context 上下文信息
     * @param reader 模板源
     * @param writer 输出流
     * @return 被渲染后的字符数组
     * @throws Exception 渲染出错
     */
    public void render(Context context, Reader reader, Writer writer) throws Exception {
        engine.evaluate(context, writer, "config", reader);
    }

    /**
     * Velocity Logger
     */
    private class LogSystem implements org.apache.velocity.runtime.log.LogSystem {
        /**
         * 实现<code>LogSystem</code>接口, 初始化.
         * 
         * @param runtimeServices velocity服务
         */
        public void init(RuntimeServices runtimeServices) {
        }

        /**
         * 实现<code>LogSystem</code>接口, 记录velocity产生的信息.
         * 
         * @param level 信息级别
         * @param message 信息内容
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
         * 除去message中的exception前缀，使之更美观。
         */
        private String processMessage(String message) {
            if (message != null) {
                message = message.replaceFirst("^[\\w\\.\\$]+Exception: ", "");
            }

            return message;
        }
    }
}
