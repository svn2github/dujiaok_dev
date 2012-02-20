/**
 * Project: maven-autoconfig-plugin
 * 
 * File Created at 2009-2-16
 * $Id: ConfigJarMojo.java 31483 2009-12-22 09:57:06Z william.liangf $
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

import java.io.File;

import org.apache.maven.artifact.Artifact;


/**
 * Config Jar Mojo
 * 
 * @goal configjar 
 * @phase package
 * @requiresProject
 * @requiresDependencyResolution
 * 
 * @author shawn.qianx
 */
public class ConfigJarMojo extends ConfigMojo {
    
	public String getFullGoalName() {
		return "autoconf:configjar";
	}

    protected File[] getDestFiles() {
        Artifact artifact = project.getArtifact();
        File destFile = null;
        if (artifact.getFile() != null) {
            destFile = artifact.getFile();
        } else {
            destFile = new File(project.getBuild().getDirectory(), finalName + getExtention(artifact));
        }
        return new File[]{destFile};
    }

}
