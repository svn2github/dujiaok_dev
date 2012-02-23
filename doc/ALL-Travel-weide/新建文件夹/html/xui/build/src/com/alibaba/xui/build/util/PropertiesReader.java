/**
 * Copyright 1999-2007 Alisoft.com
 * All rights reserved.
 *
 * @project 阿里软件外贸版
 * @date 2007-4-20
 */

package com.alibaba.xui.build.util;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Properties文件读取类
 * 
 * @author chun.fengch
 * @version 1.0
 * @date 2007-4-20
 */
public final class PropertiesReader {
	private ResourceBundle res = null;

	public PropertiesReader(String bundleName) {
		try {
			res = (PropertyResourceBundle) ResourceBundle.getBundle(bundleName);
		} catch (Exception e) {
			res = null;
		}
		if (res == null)
			throw new RuntimeException("The properties-file<" + bundleName
					+ "> maybe not exist!");
	}

	public String get(String key) {
		try {
			return res.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public PropertyResourceBundle getBundle() {
		return (PropertyResourceBundle) res;
	}
}
