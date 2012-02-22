/**
 * Project: a2m-converter-1.0.0-SNAPSHOT
 * 
 * File Created at 2010-3-29
 * $Id: AutoConfEntry.groovy 39828 2010-03-29 15:14:42Z shawn.qianx $
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

/**
 * TODO Comment of AutoConfEntry
 * @author shawn.qianx
 *
 */
public class AutoConfEntry {
	
	String name;
	String defaultValue;
	String desc;


	public String toString(){
	    return name + "\n" + defaultValue + "\n" + desc;
	}
}
