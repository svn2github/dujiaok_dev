package com.alibaba.maven.plugins.autoconf.util;

public class AutoConfEntry {

	String name;
	String defaultValue;
	String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return name + "\n" + defaultValue + "\n" + desc;
	}
}