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
import java.util.List;

import com.yahoo.platform.yui.compressor.Bootstrap;

public class Minor {

	public static void compress(File file, String encoding) {
		if (!FileUtil.isWhatFile(file, ".js")
				&& !FileUtil.isWhatFile(file, ".css"))
			throw new IllegalArgumentException(
					"Compress file-type not js or css!");

		String srcPath = file.getPath();
		String type = (FileUtil.isWhatFile(file, ".js") ? "js" : "css");
		String newPath = srcPath.substring(0, srcPath.lastIndexOf("."))
				+ "-min." + type;
		String[] paras = ("--type " + type + " --charset " + encoding + " "
				+ srcPath + " -o " + newPath).split(" ");
		try {
			Bootstrap.main(paras);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void compress(List<File> files, String encoding) {
		if(files == null || files.isEmpty()) return;
		for (File path : files) {
			compress(path, encoding);
		}
	}

	public static void main(String[] args) throws Exception {
		// Minor.compress("d:\\gzip\\xui-ajax.js","GBK");
	}

}
