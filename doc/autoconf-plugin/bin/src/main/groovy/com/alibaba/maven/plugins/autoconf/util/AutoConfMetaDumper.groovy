/**
 * Project: a2m-converter-1.0.0-SNAPSHOT
 * 
 * File Created at 2010-3-29
 * $Id: AutoConfMetaDumper.groovy 39828 2010-03-29 15:14:42Z shawn.qianx $
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
package com.alibaba.maven.plugins.autoconf.util;

import groovy.util.XmlSlurper;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * TODO Comment of Dump
 * @author shawn.qianx
 *
 */
public class AutoConfMetaDumper {   
    public static void main(String[] args){
        String file = "d:/tmp/test/lib/test-config.jar";
        JarFile jar = new JarFile(file);
        ZipEntry entry = jar.getJarEntry("META-INF/autoconf/auto-config.xml");
        InputStream is = jar.getInputStream(entry);

        //InputStream is = new FileInputStream("d:/tmp/test/auto-config.xml");
        def autoConfEntries = parseAutoConfigXML(is);

        autoConfEntries.each{ ace ->
            println("----------")
            println(ace.name);
            println(ace.defaultValue);
            println(ace.desc);
        }
    }

    /**
     * Return array contains: key, default-value,desc
     */
    public static Collection<AutoConfEntry> parseAutoConfigXML(InputStream input){
        List entries = new ArrayList<AutoConfEntry>();
        def xml = new XmlSlurper().parse(input);
        xml.group.each{ group ->
            group.property.each{prop->
                entries << new AutoConfEntry(name:prop.@name,defaultValue:prop.@defaultValue,desc:prop.@description);
            }
            
        }
        return entries;
    }
}
