/**
 * File Created at 2011-07-13
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

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigRuntimeImpl;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.StringUtils;

/**
 * @author gang.lvg
 *
 * @goal minas-commit
 * @requiresProject false
 */
public class MinasCommitMojo extends AbstractAutoconfMojo {

    /**
     * @parameter expression="${minas.commit.uri}"
     * @required
     */
    private String minasCommitUri;

    /**
     * @parameter expression="${minas.commit.comment}" default-value="committed by maven plugin"
     */
    private String minasCommitComment;

    /**
     * @parameter expression="${minas.username}"
     */
    private String minasUsername;

    /**
     * @parameter expression="${commit.structure}" default-value="true"
     */
    private boolean onlyStructure;


    public void execute()
            throws MojoExecutionException, MojoFailureException {

        getLog().info( "Start minas commit" );

        ConfigRuntimeImpl runtime = getRuntimeImpl();

        runtime.setMinasProperties( minasCommitUri );

        runtime.getPropertiesSet().getMinasProperties().setCommitComment( minasCommitComment );

        runtime.getPropertiesSet().getMinasProperties().setOnlyStructure( onlyStructure );

        if (interactiveMode) {
            runtime.getPropertiesSet().getMinasProperties().setInteractiveMode( ConfigConstant.INTERACTIVE_ON );
        } else {
            runtime.getPropertiesSet().getMinasProperties().setInteractiveMode( ConfigConstant.INTERACTIVE_OFF );
        }

        if ( StringUtils.isNotEmpty( minasUsername ) ) {
            runtime.getPropertiesSet().getMinasProperties().setMinasUsername( minasUsername );
        }

        getLog().info( "Start minas commit" );
        runtime.getPropertiesSet().getMinasProperties().commitProperties();
        getLog().info( "Minas commit successfully" );
    }

}
