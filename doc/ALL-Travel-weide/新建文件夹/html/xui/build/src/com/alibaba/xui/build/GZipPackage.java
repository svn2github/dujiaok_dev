/**
 * Copyright 2005-2009 Alisoft.com
 * All rights reserved.
 *
 * @project xui
 * @author chun.fengch
 * @version 1.0
 * @date 2009-1-7
 */
package com.alibaba.xui.build;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.xui.build.util.FileUtil;
import com.alibaba.xui.build.util.StringUtil;

public class GZipPackage {
	private String name;

	private String dir;

	private String[] fileNames;

	private String type;

	private String suffix;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public GZipPackage(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	private String appendFileName(String oldPath, String suffix) {
		if (suffix == null)
			return oldPath;
		return oldPath.substring(0, oldPath.lastIndexOf(".")) + suffix
				+ oldPath.substring(oldPath.lastIndexOf("."));
	}

	private List<File> deals;

	public List<File> getDeals() {
		return deals;
	}

	public List<File> getFiles(String fileType, String ignore) {
		if (this.fileNames == null)
			return null;

		List<File> arr;
		if (fileNames[0].startsWith("*")) {
			arr = FileUtil.listFilesOnly(new File(this.dir), fileType
					+ this.type, ignore);
		} else {
			arr = new ArrayList<File>();
			for (int i = 0; i < this.fileNames.length; i++) {
				arr.add(new File(this.dir + FileUtil.PATH_CHAR
						+ appendFileName(this.fileNames[i], fileType)));
			}
		}

		if (StringUtil.isEmpty(fileType))
			this.deals = arr;
		return arr;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
		if (this.fileNames != null && this.fileNames.length > 0) {
			this.type = FileUtil.getFileType(this.fileNames[0]).toLowerCase();
			if (!".js".equalsIgnoreCase(this.type)
					&& !".css".equalsIgnoreCase(this.type))
				throw new IllegalArgumentException("The package<" + this.name
						+ "> has error-config about files!");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
