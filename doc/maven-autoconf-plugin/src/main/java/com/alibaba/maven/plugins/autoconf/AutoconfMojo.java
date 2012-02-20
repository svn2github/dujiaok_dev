/**
 * Project: maven-autoconfig-plugin
 * 
 * File Created at 2009-2-16
 * $Id: AutoconfMojo.java 113444 2011-09-22 03:32:26Z gang.lvg $
 * 
 * Copyright 2008 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.maven.plugins.autoconf;

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigRuntimeImpl;
import com.alibaba.antx.util.PatternSet;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;

/**
 * Autoconf Mojo, eg: mvn autoconf:autoconf
 * 
 * @goal autoconf
 * @requiresProject false
 * 
 * @author shawn.qianx
 * @author hongyuan.zhouhy
 * @author william.liangf
 */
public class AutoconfMojo extends AbstractAutoconfMojo {

    /**
     * directory or jar/war/zip archive path, separate by comma, default is current directory.
     * 
     * @parameter expression="${path}"
     */
    private String path;
    
    /**
     * user antx.properties path. default is ${user.home}/antx.properties
     * 
     * @parameter expression="${properties}"
     */
    private String properties;

    /**
     * shared antx.properties path.
     * 
     * @parameter expression="${sharedProperties}"
     */
    private String sharedProperties;

    /**
     * shared antx.properties name.
     * 
     * @parameter expression="${sharedPropertiesName}"
     */
    private String sharedPropertiesName;

    /**
     * include auto-config.xml descriptors path.
     * 
     * @parameter expression="${descriptors}"
     */
    private String descriptors;

    /**
     * exclude auto-config.xml descriptors path.
     * 
     * @parameter expression="${excludeDescriptors}"
     */
    private String excludeDescriptors;
    
    /**
     * include packages path.
     * 
     * @parameter expression="${packages}" default-value = "lib/*.jar"
     */
    private String packages;

    /**
     * exclude packages path.
     * 
     * @parameter expression="${excludePackages}"
     */
    private String excludePackages;

    /**
     * verbose, show more information.
     * 
     * @parameter expression="${verbose}"
     */
    private boolean verbose;

    /**
     * @parameter expression="${minasUri}"
     */
    private String minasUri;

    /**
     * @parameter expression="${minas.username}"
     */
    private String minasUsername;

    /**
     * @parameter expression="${minas.version}"
     */
    private String minasVersion;

    /**
     * @parameter default-value="${basedir}"
     */
    private File basedir;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Execute autoconf:autoconf.");

        ConfigRuntimeImpl runtime = getRuntimeImpl();

        if(properties != null
                && properties.trim().length() > 0
                && runtime.getPropertiesSet().getUserPropertiesFile() == null ){
            getLog().info("Customized user properties:" + properties + " using encoding:" + encoding);
            runtime.setUserPropertiesFile(properties, encoding);
        }

        if ( StringUtils.isEmpty( minasUri ) ) {
            minasUri = getMinasUriFromAntxProperties( runtime, basedir );
        }

        if ( StringUtils.isNotEmpty( minasUri ) ) {

            runtime.setMinasProperties( minasUri );

            if ( StringUtils.isNotEmpty( minasVersion ) ) {
                runtime.getPropertiesSet().getMinasProperties().setRevision( minasVersion );
            }

            if ( StringUtils.isNotEmpty( minasUsername ) ) {
                runtime.getPropertiesSet().getMinasProperties().setMinasUsername( minasUsername );
            }
        }


        if(path != null && path.trim().length() > 0){
            runtime.setDests(path.split(","));
        } else {
            runtime.setDests(new String[] {"."});
        }

        if (verbose) {
            runtime.setVerbose();
        }

        if (sharedProperties != null && sharedProperties.length() > 0) {
            getLog().info("Customized shared properties: " + descriptors);
            runtime.setSharedPropertiesFiles(sharedProperties.split(","), sharedPropertiesName, encoding);
        }

        if ((descriptors != null && descriptors.length() > 0) 
                || (excludeDescriptors != null && excludeDescriptors.length() > 0)) {
            String[] includes;
            String[] excludes;
            PatternSet patterns = runtime.getDescriptorPatterns();
            if (descriptors == null || descriptors.length() == 0) {
                try {
                    includes = patterns.getIncludes();
                } catch (NullPointerException npe) {
                    includes = new String[0];
                }
            } else {
                getLog().info("Customized include descriptors: " + descriptors);
                includes = descriptors.split(",");
            }
            if (excludeDescriptors == null || excludeDescriptors.length() == 0) {
                try {
                    excludes = patterns.getExcludes();
                } catch (NullPointerException npe) {
                    excludes = new String[0];
                }
            } else {
                getLog().info("Customized exclude descriptors: " + excludeDescriptors);
                excludes = excludeDescriptors.split(",");
            }
            runtime.setDescriptorPatterns(includes, excludes);
        }
        if ((packages != null && packages.length() > 0) 
                || (excludePackages != null && excludePackages.length() > 0)) {
            String[] includes;
            String[] excludes;
            PatternSet patterns = runtime.getPackagePatterns();
            if (packages == null || packages.length() == 0) {
                try {
                    includes = patterns.getIncludes();
                } catch (NullPointerException npe) {
                    includes = new String[0];
                }
            } else {
                getLog().info("Customized include packages: " + descriptors);
                includes = packages.split(",");
            }
            if (excludePackages == null || excludePackages.length() == 0) {
                try {
                    excludes = patterns.getExcludes();
                } catch (NullPointerException npe) {
                    excludes = new String[0];
                }
            } else {
                getLog().info("Customized exclude packages: " + excludeDescriptors);
                excludes = excludePackages.split(",");
            }
            runtime.setPackagePatterns(includes, excludes);
        }
        try {
            runtime.start();
        } catch (Exception e) {
            runtime.error(e);
            throw new MojoExecutionException("autoconf execution error", e);
        }
    }

}
