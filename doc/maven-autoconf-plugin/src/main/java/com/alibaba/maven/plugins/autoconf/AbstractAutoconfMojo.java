/**
 * File Created at 2011-07-20
 * $Id$
 *
 * Copyright 2008 Alibaba.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.maven.plugins.autoconf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigRuntimeImpl;
import com.alibaba.antx.util.i18n.LocaleInfo;
import com.alibaba.antx.util.i18n.UnsupportedLocaleException;

/**
 * @author gang.lvg
 */
public abstract class AbstractAutoconfMojo extends AbstractMojo {

    public static final String KEY_MINAS_URI = "minas.uri";
    public static final String FILE_ANTX_PROPERTIES = "antx.properties";

    /**
     * @parameter expression="${encoding}"
     */
    protected String encoding;

    /**
     * Configure locale.See <code>antx -h</codde> locale option.
     *  linux   zh_CN.utf-8 - zh_CN:utf-8
     *  windows zh_CN.GBK   - zh_CN:GBK (default)
     *
     * @parameter expression="${locale}"
     */
    protected String locale;

    /**
     * User properties file used by autoconf to generate the file configuration files.<br/>
     * default is ${user.home}\antx.properties
     * @parameter expression="${userProp}"
     */
    protected String userProp;

    /**
     * User settings use to check the interactiveMode.
     *
     * @parameter default-value="${settings.interactiveMode}"
     */
    protected Boolean interactiveMode;

    /**
     * @parameter expression="${autoconf.interactive}" default-value="auto"
     */
    protected String interactive;

    protected ConfigRuntimeImpl getRuntimeImpl() throws MojoExecutionException {

        // config file encoding
        if ( StringUtils.isEmpty( encoding ) ) {
            encoding = System.getProperty( "file.encoding" );
        }

        getLog().info( String.format( "Using file encoding %s", encoding ) );

        // config locale
        if ( StringUtils.isEmpty( locale ) ) {
            locale = Locale.getDefault().toString() + ":" + encoding;
        }

        getLog().info( String.format( "Using locale info %s", locale ) );

        try {
            LocaleInfo.setDefault( locale );
        } catch ( UnsupportedLocaleException e ) {
            throw new MojoExecutionException( e.getMessage(), e );
        }

        ConfigRuntimeImpl runtime = new WarConfigRuntimeImpl( System.in, System.out, System.err, encoding );
        if ( userProp != null && userProp.trim().length() > 0 ) {
            getLog().info( "set user properties file:" + userProp + " using encoding:" + encoding );
            runtime.setUserPropertiesFile( userProp, encoding );
        }

        if ( BooleanUtils.isTrue(interactiveMode) ) {
            if ( ConfigConstant.INTERACTIVE_ON.equalsIgnoreCase( interactive ) ) {
                runtime.setInteractiveMode( ConfigConstant.INTERACTIVE_ON );
            } else {
                runtime.setInteractiveMode( ConfigConstant.INTERACTIVE_AUTO );
            }
        } else {
            runtime.setInteractiveMode( ConfigConstant.INTERACTIVE_OFF );
        }

        return runtime;
    }

    protected Properties getAntxProperties( File basedir ) {

        Properties result = new Properties();

        File antx = null;

        if ( basedir != null ) {
            antx = new File( basedir, FILE_ANTX_PROPERTIES );
        }

        if ( antx == null ) {
            antx = new File( System.getProperty( "user.dir" ), FILE_ANTX_PROPERTIES );
        }

        if (antx.isFile()) {
            InputStream is = null;
            try {
                is = new FileInputStream( antx );
                result.load( is );
            } catch ( FileNotFoundException e ) {
                e.printStackTrace();
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( is != null ) {
                    try {
                        is.close();
                    } catch ( IOException e ) {
                        // ignore
                    }
                }
            }
        }

        return result;
    }

   

}
