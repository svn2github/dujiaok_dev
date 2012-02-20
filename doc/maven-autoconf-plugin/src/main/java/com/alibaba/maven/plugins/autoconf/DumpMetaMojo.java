/**
 * Project: maven-autoconf-plugin
 * 
 * File Created at 2010-3-29
 * $Id: DumpMetaMojo.java 39830 2010-03-29 15:18:19Z shawn.qianx $
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.alibaba.maven.plugins.autoconf.util.AntPathMatcher;
import com.alibaba.maven.plugins.autoconf.util.AutoConfEntry;
import com.alibaba.maven.plugins.autoconf.util.AutoConfMetaDumper;
import com.alibaba.maven.plugins.autoconf.util.FileWalker;

/**
 * Dump AutoConfig Data Mojo
 * 
 * @goal dump-meta
 * @requiresProject false
 * @author shawn.qianx
 */
public class DumpMetaMojo extends AbstractMojo {
    
    /**
     * Destination file to read autoconf meta files from.
     * 
     * @parameter
     */
    String           workingDirectory;
    
    /**
     * Comma separated ant style path patterns. For including file to scan
     * auto-config.xml with
     * 
     * @parameter
     */
    String           includeFilePatterns;
    private String[] _includeFilePatterns       = new String[]{"**/*.jar"};
    
    /**
     * Include patterns for autoconf descriptor files
     * 
     * @parameter
     */
    String           includeDescriptorPatterns;
    private String[] _includeDescriptorPatterns = new String[]{"**/META-INF/autoconf/auto-config.xml"};

    //FIXME more groovier
    private void readParams(){
        String[] p = splitParams(includeDescriptorPatterns);
        if(p != null){
            _includeDescriptorPatterns = p;
            p = null;
        }
        p = splitParams(includeFilePatterns);
        if(p != null){
            _includeFilePatterns = p;
        }
    }
    
    private String[] splitParams(String input) {
        String[] result = null;
        // get include file patterns
        if (input != null && input.length() > 0) {
            String[] s = input.split(",");
            List<String> patterns = new ArrayList<String>();
            for (String p : s) {
                p = p.trim();
                if (p.length() > 0) {
                    patterns.add(p);
                }
            }
            if (!patterns.isEmpty()) {
                result = patterns.toArray(new String[patterns.size()]);
            }
        }
        return result;
    }
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().debug("start autoconf:dump mojo!");
        
        readParams();
        
        File startFile;
        if (workingDirectory != null) {
            startFile = new File(workingDirectory);
        } else {
            startFile = new File("");
        }
        
        List<String> xmls = collectAutoConfigXMLs(startFile);
    }
    
    private List<String> collectAutoConfigXMLs(File startFile) {
        final List<String> xmls = new ArrayList<String>();
        FileWalker.Handler handler = new FileWalker.Handler() {
                    public boolean process(FileObject file, int level) {
                        try {
                            //String path = file.getURL().getPath();
                            String filePath = file.getName().getPath();
                            if (filePath.startsWith("/") || filePath.startsWith("\\")) {
                                filePath = filePath.substring(1);
                            }
                            FileType type = file.getType();
                            if (type == FileType.FOLDER) {
                                // always check folder
                                return false;
                            }
                            
                            // checking include files
                            for (String p : _includeFilePatterns) {
                                if (antPathMatcher.match(p, filePath)) {
                                    if(getLog().isDebugEnabled()){
                                        getLog().debug("Searching " + file.getURL() + "...");
                                    }
                                    // matches, should go on!
                                    return false;
                                }
                            }
                            
                            // checking auto-config.xml
                            for (String p : _includeDescriptorPatterns) {
                                if (antPathMatcher.match(p, filePath)) {
                                    if(getLog().isDebugEnabled()){
                                        getLog().debug(">Found " + file.getURL());
                                    }
                                    handleAutoConfigFile(file);
                                    return true;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        return true;
                    }
                };
        
        FileWalker.walk(startFile.getAbsolutePath(), handler);
        return null;
    }
    
    /**
     * @param file
     */
    protected void handleAutoConfigFile(FileObject file) {
        InputStream is = null;
        try {
            is = file.getContent().getInputStream();
            
//            ByteArrayOutputStream buff = new ByteArrayOutputStream();
//            IOUtils.copy(is, buff);
//            System.out.println(buff);
            Collection<AutoConfEntry> result = AutoConfMetaDumper.parseAutoConfigXML(is);
            for(AutoConfEntry ace : result){
                displayAutoConfEntry(ace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void displayAutoConfEntry(AutoConfEntry ace){
        StringBuilder sb = new StringBuilder();
        sb.append(ace.getName())
        .append(" = ");
        if(ace.getDefaultValue() != null && ace.getDefaultValue().length() > 0){
            sb.append(ace.getDefaultValue());
        }else{
            sb.append("<no default value>");
        }
        if(ace.getDesc() != null && ace.getDesc().length() > 0){
            sb.append("\t//").append(ace.getDesc());
        }
        System.out.println(sb.toString());
    }

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    public static void main(String[] args) {
        DumpMetaMojo mojo = new DumpMetaMojo();
        
        //d:/tmp/test/lib/test-config.jar
        mojo.workingDirectory = "d:/tmp/test";
        mojo.includeFilePatterns = "**/*.jar,**/*.zip";
        mojo.includeDescriptorPatterns = "**/auto-config.xml";
        
        long start = System.currentTimeMillis();
        try {
            mojo.execute();
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("elapsed " + elapsed + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}