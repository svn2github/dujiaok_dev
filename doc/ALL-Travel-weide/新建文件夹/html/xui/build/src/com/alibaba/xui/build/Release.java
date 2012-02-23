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
import java.util.Date;
import java.util.List;

import com.alibaba.xui.build.util.FileUtil;
import com.alibaba.xui.build.util.GZip;
import com.alibaba.xui.build.util.Log;
import com.alibaba.xui.build.util.Minor;
import com.alibaba.xui.build.util.PropertiesReader;
import com.alibaba.xui.build.util.StringUtil;

public class Release {
	private String version;

	private String encoding = "GBK";

	private String xuiDir;

	private String minSuffix = "-min";

	private String cssSuffix = ".gzcss";

	private String jsSuffix = ".gzjs";

	private List<GZipPackage> pkgs;

	public Release() {
		PropertiesReader config = new PropertiesReader("xui-build");
		this.version = config.get("xui.version");
		this.encoding = config.get("xui.encoding");
		if (StringUtil.isEmpty(config.get("xui.dir"))) {
			this.xuiDir = this.getProjectDir() + FileUtil.PATH_CHAR;
		} else {
			this.xuiDir = config.get("xui.dir");
		}

		this.minSuffix = config.get("xui.min.suffix");
		this.jsSuffix = config.get("xui.gzip.suffix.js");
		this.cssSuffix = config.get("xui.gzip.suffix.css");
		FileUtil.PATH_CHAR = config.get("xui.pathchar");

		String files = config.get("xui.gzip.packages");
		if (files == null || files.length() <= 0)
			return;

		pkgs = new ArrayList<GZipPackage>();
		String[] pkgNames = files.split(",");
		for (String name : pkgNames) {
			GZipPackage gzp = buildPackage(name, config);
			if (gzp != null)
				pkgs.add(gzp);
		}
	}

	private GZipPackage buildPackage(String name, PropertiesReader config) {
		String pkgDir = config.get("xui.gzip.dir." + name);
		String pkgFiles = config.get("xui.gzip.files." + name);

		if (StringUtil.isEmpty(pkgDir))
			pkgDir = FileUtil.PATH_CHAR + name;
		if (StringUtil.isEmpty(pkgFiles))
			pkgFiles = "*.js";

		GZipPackage pkg = new GZipPackage(name);
		pkg.setDir(this.xuiDir + "/source" + pkgDir);
		pkg.setFileNames(pkgFiles.split(","));

		return pkg;
	}

	private void gzip(GZipPackage pkg) {
		pkg.setSuffix(pkg.getType().equals(".js") ? this.jsSuffix
				: this.cssSuffix);
		List<File> files = pkg.getFiles(this.minSuffix, null);
		if(files == null || files.isEmpty()) return;
		
		if(files.size()==1){
			String name = files.get(0).getName();
			name = name.substring(0,name.indexOf("."));
			GZip.compress(pkg.getDir(), name + pkg.getType(), name + pkg.getSuffix(),
					this.encoding);
		}else{
			GZip.merge(files, pkg.getDir(), pkg
					.getName()
					+ this.minSuffix + pkg.getType(),
					this.encoding);
			GZip.compress(pkg.getDir(), pkg
					.getName()
					+ this.minSuffix + pkg.getType(), pkg.getName()+ this.minSuffix + pkg.getSuffix(),
					this.encoding);
		}
		
		
	}

	private void min(GZipPackage pkg) {
		Minor.compress(pkg.getFiles("", this.minSuffix + pkg.getType()),
				this.encoding);
	}

	public void run() {
		if (this.pkgs == null || this.pkgs.isEmpty())
			return;

		System.out.println(new Date().toLocaleString() + " XUI-AUTO-RELEASE start......");

		Log log = new Log(this.version);

		for (GZipPackage pkg : pkgs) {
			System.out.println(new Date().toLocaleString() + " [Package:"
					+ pkg.getName() + "]processing...");
			min(pkg);
			gzip(pkg);
		}
		log.write(this.xuiDir, pkgs, encoding);
		System.out.println(new Date().toLocaleString() + " XUI-AUTO-RELEASE finished. The sum total of packages is "
				+ pkgs.size() + ".");
	}

	private String getProjectDir() {
		String path = Release.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();

		return new File(path).getParent();
	}

	public static void main(String[] args) {
		new Release().run();
	}

}
