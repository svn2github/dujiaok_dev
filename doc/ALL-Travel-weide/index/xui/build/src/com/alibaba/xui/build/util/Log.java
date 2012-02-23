/**
 * Copyright 2005-2009 Alisoft.com
 * All rights reserved.
 *
 * @project xui
 * @author chun.fengch
 * @version 1.0
 * @date 2009-1-7
 */
package com.alibaba.xui.build.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.alibaba.xui.build.GZipPackage;

public class Log {
	private String version;

	private String buildId;

	private String logDir;

	private String logSuffix;

	public Log(String version) {
		this.version = version;
		this.buildId = this.newBuildId();

		PropertiesReader config = new PropertiesReader("xui-build");
		this.logDir = config.get("xui.log.dir");
		this.logSuffix = config.get("xui.log.suffix");
		if (StringUtil.isEmpty(this.logDir))
			this.logDir = "build";
		if (StringUtil.isEmpty(this.logSuffix))
			this.logSuffix = ".log";
	}

	private String toTimeString(int i) {
		String s = "" + i;
		return s.length() < 2 ? "0" + s : s;
	}

	private String newBuildId() {
		Date date = new Date();
		String year = toTimeString(1900 + date.getYear());
		String month = toTimeString(date.getMonth() + 1);
		String day = toTimeString(date.getDate());

		String hour = toTimeString(date.getHours());
		String min = toTimeString(date.getMinutes());
		String sec = toTimeString(date.getSeconds());

		return "M" + year + month + day + "-" + hour + min + sec;
	}

	public void write(String xuiDir, List<GZipPackage> pkgs, String encoding) {
		StringBuffer content = new StringBuffer("XUI " + this.version
				+ " Build:" + this.buildId + "\n");
		content.append("==================================\n");

		for (GZipPackage pkg : pkgs) {
			appendLog(pkg, content);
		}

		try {
			FileUtil.writeTextFile(xuiDir + FileUtil.PATH_CHAR + this.logDir,
					"release_" + this.version + "." + this.buildId
							+ this.logSuffix, content.toString(), encoding,
					false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendLog(GZipPackage pkg, StringBuffer content) {
		content.append(pkg.getName()
				+ "\n---------------------------\n");
		List<File> files = pkg.getDeals();

		if (files == null || files.isEmpty())
			return;
		for (File file : files) {
			content.append(file.getPath() + "\n");
		}
		content.append("\n");
	}

	public static void main(String[] args) {
		String s = new Log("").newBuildId();
		System.out.println(s);
	}

}
