/**
 * Copyright 2005-2009 Alisoft.com
 * All rights reserved.
 *
 * @project xui
 * @author chun.fengch
 * @version 1.0
 * @date 2009-1-6
 */
package com.alibaba.xui.build.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class GZip {

	public static void merge(List<File> files, String dir,
			String mergeName, String encoding){
		FileUtil.mergeTextFiles(files, dir, mergeName, encoding);
	}
	
	public static void compress(String dir,
			String srcName, String destName, String encoding) {		
		
		FileInputStream fin = null;
		FileOutputStream fout = null;
		GZIPOutputStream gzout = null;
		try {
			// ����ѹ���ļ���Ϊ�ļ�������
			fin = new FileInputStream(dir+FileUtil.PATH_CHAR+srcName);
			// ����ѹ���ļ������
			fout = new FileOutputStream(dir+FileUtil.PATH_CHAR+destName);
			// ����gzipѹ�������
			gzout = new GZIPOutputStream(fout);
			byte[] buf = new byte[1024];// �趨���뻺�����ߴ�
			int num;

			while ((num = fin.read(buf)) != -1) {
				gzout.write(buf, 0, num);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if(gzout!=null)
				try {
					gzout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fout!=null)
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fin!=null)
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}			
		}

	}
		
	public static void main(String[] args) {
//		GZip.compress(new String[]{"D:\\gzip\\xui-cookie.js"},"D:\\gzip\\","xui-util.gzjs","GBK");
	}

}
