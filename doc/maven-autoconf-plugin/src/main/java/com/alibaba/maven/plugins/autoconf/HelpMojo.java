/**
 * Project: maven-autoconf-plugin
 * 
 * File Created at 2009-12-22
 * $Id: HelpMojo.java 31483 2009-12-22 09:57:06Z william.liangf $
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Help Mojo, eg: mvn autoconf:help
 * 
 * @goal help
 * @requiresProject false
 * 
 * @author william.liangf
 */
public class HelpMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Example:");
        getLog().info("mvn autoconf:autoconf");
        getLog().info("mvn autoconf:autoconf -Dpath=xxx.jar,xxx.war");
        getLog().info("mvn autoconf:autoconf -Dinteractive");
        getLog().info("Parameter:");
        getLog().info("-Dpath                 directory or jar/war/zip archive path, separate by comma, default is current directory.");
        getLog().info("-Dinteractive          turn on interactive mode.");
        getLog().info("-Dcharset              input and ouput charset.");
        getLog().info("-Dproperties           user antx.properties path. default is ${user.home}/antx.properties");
        getLog().info("-DsharedProperties     shared antx.properties path.");
        getLog().info("-DsharedPropertiesName shared antx.properties name.");
        getLog().info("-Ddescriptors          include auto-config.xml descriptors path.");
        getLog().info("-DexcludeDescriptors   exclude auto-config.xml descriptors path.");
        getLog().info("-Dpackages             include packages path.");
        getLog().info("-DexcludePackages      exclude packages path.");
        getLog().info("-Dverbose              show more information.");
        getLog().info("-Dgui                  turn on gui mode.");
    }

}
