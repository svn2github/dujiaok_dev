package com.alibaba.maven.plugins.autoconf.util;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * TODO Comment of Dump
 * @author shawn.qianx
 *
 */
public class AutoConfMetaDumper {   
    public static void main(String[] args) throws Exception{
        String file = "d:/test.jar";
        JarFile jar = new JarFile(file);
        ZipEntry entry = jar.getJarEntry("META-INF/autoconf/auto-config.xml");
        InputStream is = jar.getInputStream(entry);

        //InputStream is = new FileInputStream("d:/tmp/test/auto-config.xml");
        Collection<AutoConfEntry> autoConfEntries = parseAutoConfigXML(is);

        for(AutoConfEntry ace : autoConfEntries ){
            System.out.println("----------") ;
            System.out.println(ace.name);
            System.out.println(ace.defaultValue);
            System.out.println(ace.desc);
        }
        
    }

    /**
     * Return array contains: key, default-value,desc
     */
    public static Collection<AutoConfEntry> parseAutoConfigXML(InputStream input) throws Exception{
        List<AutoConfEntry> entries = new ArrayList<AutoConfEntry>();
        
        SAXReader reader = new SAXReader();  
        Document document = reader.read(input);  
        List<Element> list = document.selectNodes("//config/group/property") ;
        for(Element e : list){
        	
        	AutoConfEntry entry = new AutoConfEntry() ;
        	String defaultVal = e.attributeValue("defaultValue") ; 
        	String name = e.attributeValue("name") ; 
        	String desc = e.attributeValue("description") ; 
        	entry.setDefaultValue(defaultVal) ;
        	entry.setDesc(desc) ;
        	entry.setName(name) ;
        	entries.add(entry) ;
        }
       
        
        return entries;
    }
}
