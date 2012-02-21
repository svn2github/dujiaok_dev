/**
 * Project: maven-autoconfig-plugin
 * 
 * File Created at 2009-2-16
 * $Id: ConfigMojo.java 107946 2011-08-25 03:20:26Z gang.lvg $
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

import com.alibaba.antx.config.ConfigRuntimeImpl;
import com.alibaba.antx.util.PatternSet;
import com.alibaba.maven.plugins.autoconf.util.AntPathMatcher;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Config Mojo
 * 
 * @goal config
 * @phase process-resources
 * @requiresProject
 * @requiresDependencyResolution
 * 
 * @author shawn.qianx
 * @author gang.lvg
 */
public class ConfigMojo extends AbstractAutoconfMojo {

    /**
     * @parameter expression="${project}" 
     * @required
     * @readonly
     */
    MavenProject project;

    /**
     * Destination file to read autoconf meta file and generate result to.
     * @deprecated - Please use destFiles instead
     * @parameter
     */
    String destFile;
    
    /**
     * Destination files to read autoconf meta files and generate results to.
     * @parameter
     */
    String[] destFiles;
    
    /**
     * Include patterns used by autoconf for include descriptor files
     * @parameter
     */
    String[] includeDescriptorPatterns;
    
    /**
     * Exclude patterns used by autoconf for exclude descriptor files
     * @parameter
     */
    String[] excludeDescriptorPatterns;
    
    /**
     * Include package patterns.
     * 
     * @parameter expression="${includePackagePatterns}"
     */
    private String[] includePackagePatterns;

    /**
     * Exclude package patterns.
     * 
     * @parameter expression="${excludePackagePatterns}"
     */
    private String[] excludePackagePatterns;
    
    /**
     * @parameter expression="${project.build.finalName}"
     */
    String finalName;

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
		String goalName = getFullGoalName();
		getLog().info("start " + goalName + " mojo!");

		// Skip if under non-interactive mode.
		if (!interactiveMode || "true".equalsIgnoreCase(System.getProperty("autoconf.skip"))) {
			getLog().info("Skipping autoconf in non-interactive mode");
			return;
		}

		if ("pom".equalsIgnoreCase(this.project.getPackaging())) {
			getLog().info("Skipping autoconf because project is a POM");
			return;
		}

		File[] files = getDestFiles();
		// getLog().debug("Output Dirs: " + );		
//		for (File destFile : destFiles) {
//			getLog().debug("Output Dir: " + destFile);
//			if (!destFile.exists()) {
//				destFile.mkdirs();
//			}
//		}
	    doConfigure(files);
	}
	
	private void doConfigure(File[] targetFiles) throws MojoExecutionException{
	    ConfigRuntimeImpl runtimeImpl = getRuntimeImpl();

        if ( StringUtils.isEmpty( minasUri ) ) {
            minasUri = getMinasUriFromAntxProperties( runtimeImpl, basedir );
        }

        // convert to string[], again.
        String[] targetFileNames = new String[targetFiles.length];
        for (int i = 0; i < targetFiles.length; i++) {
            targetFileNames[i] = targetFiles[i].getPath();
        }
        runtimeImpl.setDests(targetFileNames);

        String[] includes = includeDescriptorPatterns;
        String[] excludes = excludeDescriptorPatterns;
        if (includes != null || excludes != null) {

            PatternSet descriptorPatterns = runtimeImpl.getDescriptorPatterns();
            if (includes == null) {
                try {
                    includes = descriptorPatterns.getIncludes();
                } catch (NullPointerException npe) {
                    includes = new String[0];
                }
            }
            if (excludes == null) {
                try {
                    excludes = descriptorPatterns.getExcludes();
                } catch (NullPointerException npe) {
                    excludes = new String[0];
                }
            }
            getLog().debug("Customized include desc pattern: " + includes);
            getLog().debug("Customized exclude desc pattern: " + excludes);
            runtimeImpl.setDescriptorPatterns(includes, excludes);
        }
        includes = includePackagePatterns;
        excludes = excludePackagePatterns;
        if (includes != null || excludes != null) {
            PatternSet packagePatterns = runtimeImpl.getPackagePatterns();
            if (includes == null) {
                try {
                    includes = packagePatterns.getIncludes();
                } catch (NullPointerException npe) {
                    includes = new String[0];
                }
            }
            if (excludes == null) {
                try {
                    excludes = packagePatterns.getExcludes();
                } catch (NullPointerException npe) {
                    excludes = new String[0];
                }
            }
            getLog().debug("Customized include desc pattern: " + includes);
            getLog().debug("Customized exclude desc pattern: " + excludes);
            runtimeImpl.setPackagePatterns(includes, excludes);
        }
        try {
            runtimeImpl.start();
        } catch (Exception e) {
            runtimeImpl.error(e);
            if(project != null){
                Artifact artifact = project.getArtifact();
                throw new MojoExecutionException("[" + artifact.getArtifactId()
                        + "] autoconf execution error");                
            }else{
                throw new RuntimeException(e);
            }
        }
	}

	protected String getFullGoalName() {
        return "autoconf:config";
    }
    
    protected String getExtention(Artifact artifact) {
        String extention = ".jar";
        if(artifact.getType().equals("car")){
            extention = ".car";
        }else if (artifact.getType().equals("tb-war") || artifact.getType().equals("ali-war")){
            extention = ".war";
        }else if (artifact.getType().equals("tb-ear") || artifact.getType().equals("ali-war")){
            extention = ".ear";
        }else if(artifact.getType().equals("tb-deploy") || artifact.getType().equals("ali-deploy")){
            extention = "";
        }else{
            extention = "." + artifact.getArtifactHandler().getExtension();
        }
        return extention;
    }
    
    /**
     * Input: [foo,d:/foo,foo/*.jar,foo\\**\\*.jar] 
     * Output: [files...]
     * @return
     */
	protected File[] getDestFiles() {
	    String[] fileNames = destFiles;
	    
	    // if nothing configured, just use target/class as config target
	    if(fileNames == null && destFile == null){
	        return new File[] { new File(this.project.getBuild()
                    .getOutputDirectory()) };
	    }
	    
		List<File> files = new ArrayList<File>();
		if(destFile != null){
		    if(fileNames == null){
		        fileNames = new String[] { destFile };
		    }else{
		        // destFile is overridden by destFiles
	            getLog().warn("plugin configuration 'destFile' is overridden by 'destFiles'"); 
		    }
		}
		
		for(String fn : fileNames){
		    File aFile = getFileFromPath(fn);
		    if(!aFile.exists()){
		        // all pattern path separate char should be '/'
		        String p = fn.replace('\\', '/');
		        if(antPathMatcher.isPattern(p)){
	                // this is a ant path pattern;
	                files.addAll(getFilesFromPathPattern(p));		            
		        }
		    }else{
		        files.add(aFile);
		    }
        }
		
		if(files.size() > 0){
		     return files.toArray(new File[files.size()]);   
		}else{
		    return new File[0];
		}
	}
	
	private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	/**
	 * We assume path pattern is always relative path
	 * @param antPathPattern
	 * @return
	 */
	private List<File> getFilesFromPathPattern(String antPathPattern){
	    // get the start folder
	    // possible be:
	    // target/lib/*.jar <-- start from $basedir/target/lib
	    // **/*.jar         <-- start from project base dir
	    // d:/**/*.jar      <-- start from d:
	    
	    // FIXME assume * always appears before ? in an ant path pattern
	    int p = antPathPattern.indexOf("*");
	    String startPath = antPathPattern.substring(0,p);
	    File startFile;
	    if(startPath.length() > 0){
	        startFile = getFileFromPath(startPath);
	    }else{
	        startFile = this.project.getBasedir();
	    }
	     
	    List<File> result = new ArrayList<File>();
	    collectFilesByPatternRecusively(startFile,antPathPattern,result);
	    return result;
	}
	
	private void collectFilesByPatternRecusively(File file,String pattern, List<File> result){
	    if(!file.exists()){
	        return;
	    }
	    
	    if(antPathMatcher.match(pattern, /*file.toURI().getPath()*/file.getAbsolutePath().replace('\\', '/'))){
	        result.add(file);
	    }
	    
	    if(file.isDirectory()){
	        File[] children = file.listFiles();
	        for(File child:children){
	            collectFilesByPatternRecusively(child, pattern, result);
	        }
	    }
	}
	
	
	private File getFileFromPath(String filePath){
	 // construct the File object
        File aFile = new File(filePath);
        if (!aFile.isAbsolute()) {
            aFile = new File(this.project.getBasedir(), filePath);
        }
        return aFile;
	}
	
	public static void main(String[] args){
	    ConfigMojo mojo = new ConfigMojo();
	    mojo.destFiles = new String[]{
	            "D:\\tmp\\test",
	            //"d:/tmp/test/lib/*.jar",
	            //"d:/tmp/test/classes"
	    };
	    mojo.includeDescriptorPatterns = new String[]{
	            "**/META-INF/autoconf/auto-config.xml"
	    };
	    File[] files = mojo.getDestFiles();
	    
	    
	    System.out.println(Arrays.asList(files));
	    
	    try {
            mojo.doConfigure(files);
        } catch (MojoExecutionException e) {
            e.printStackTrace();
        }
	}
}
