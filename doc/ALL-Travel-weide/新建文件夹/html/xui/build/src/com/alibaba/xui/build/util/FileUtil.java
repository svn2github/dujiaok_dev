/**
 * Copyright(c) 2005 Dragonfly - created by FengChun
 * All Rights Reserved.
 * 
 * @license: Dragonfly Common License
 * @date 2005-5-16
 */
package com.alibaba.xui.build.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * File Util Class.
 * 
 * @version 1.0
 * @author Frank.fengc
 * @email f15_nsm@hotmail.com
 */
public class FileUtil {
	private FileUtil() {

	}

	public static String PATH_CHAR = "" + File.separatorChar;

	public static void mergeTextFiles(List<File> filePaths, String dir,
			String fileName, String encoding) {
		try {
			FileUtil.createTextFile(dir, fileName, "", encoding);

			for (File path : filePaths) {
				StringBuffer sb = FileUtil.readTextFile(path, encoding);
				FileUtil.writeTextFile(dir, fileName, sb.toString(), encoding,
						true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StringBuffer readTextFile(File path, String encoding) {
		StringBuffer sb = new StringBuffer("");

		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			read = new InputStreamReader(new FileInputStream(path), encoding);
			reader = new BufferedReader(read);

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (read != null)
				try {
					read.close();
				} catch (IOException e) {
				}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}

		return sb;
	}

	public static void mkDir(String destDir, String dirName) {
		File target = new File(destDir + PATH_CHAR + dirName);
		if (!target.exists()) {
			if (!target.mkdir())
				throw new RuntimeException("The Directory can't create:"
						+ target.getPath());
		}
	}

	public static void copyFile(File file, String destDir) throws IOException {
		if (!isCanReadFile(file))
			throw new RuntimeException("The File can't read:" + file.getPath());
		if (!isCanWriteDirectory(destDir))
			throw new RuntimeException("The Directory can't write:" + destDir);

		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		try {
			// Create channel on the source
			srcChannel = new FileInputStream(file).getChannel();

			// Create channel on the destination
			dstChannel = new FileOutputStream(destDir + "/" + file.getName())
					.getChannel();

			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		} catch (IOException e) {
			throw e;
		} finally {
			if (srcChannel != null)
				srcChannel.close();
			if (dstChannel != null)
				dstChannel.close();
		}
	}

	public static void copyFile(String destFile, String destDir)
			throws IOException {
		copyFile(new File(destFile), destDir);
	}

	public static void copySubFiles(String srcDir, String destDir)
			throws IOException {
		if (!isCanReadDirectory(srcDir))
			throw new RuntimeException("The Directory can't read:" + srcDir);
		if (!isCanWriteDirectory(destDir))
			throw new RuntimeException("The Directory can't write:" + destDir);

		File dir = new File(srcDir);
		File[] files = dir.listFiles();

		FileUtil.mkDir(destDir, dir.getName());
		String target = destDir + PATH_CHAR + dir.getName();

		if (files != null) {
			for (File file : files) {
				copyFile(file, target);
			}
		}
	}

	public static void delete(String path) {
		File f = new File(path);
		if (!f.exists())
			return;
		if (f.isDirectory()) {
			if (f.listFiles().length == 0) {
				f.delete();
			} else {
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						delete(delFile[j].getAbsolutePath());
					}
					delFile[j].delete();
				}
			}
		} else {
			f.delete();
		}
	}

	public static boolean isCanReadFile(File file) {
		return file.exists() && file.isFile() && file.canRead();
	}

	public static boolean isCanReadDirectory(String destDir) {
		File dir = new File(destDir);
		return dir.exists() && dir.isDirectory() && dir.canRead();
	}

	public static boolean isCanWriteDirectory(String destDir) {
		File dir = new File(destDir);
		return dir.exists() && dir.isDirectory() && dir.canWrite();
	}

	/**
	 * Create an encoding text-file.
	 * 
	 * @param destDir
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws Exception
	 */
	public static void writeTextFile(String destDir, String fileName,
			String content, String encoding, boolean append) throws IOException {
		if (!isCanWriteDirectory(destDir))
			throw new RuntimeException("The Directory can't write:" + destDir);

		FileWriter writer = new FileWriter(new File(destDir + PATH_CHAR
				+ fileName), append);
		try {
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
			writer = null;
		}
	}

	/**
	 * Create an encoding text-file.
	 * 
	 * @param destDir
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws Exception
	 */
	public static void createTextFile(String destDir, String fileName,
			String content, String encoding) throws IOException {
		writeTextFile(destDir, fileName, content, encoding, false);
	}

	/**
	 * List all files and directories of the directories.
	 * ȡ��Ŀ¼�µ������ļ�����
	 * 
	 * @param directory
	 * @return Array of Files
	 */
	public static File[] listFilesAndDirs(File directory) {
		return listFilesAndDirs(directory, null, null);
	}

	/**
	 * List all files and directories of the directories with file suffix.
	 * ȡ��Ŀ¼�µģ�
	 * 
	 * @param directory
	 * @param suffix
	 * @return Array of Files
	 */
	public static File[] listFilesAndDirs(File dir, String suffix, String ignore) {
		if (!dir.isDirectory())
			return null;

		File[] files = null;
		if (suffix == null || suffix.length() <= 0) {
			files = dir.listFiles();
		} else {
			FilenameFilter filter = new SuffixFileFilter(suffix.toLowerCase());
			files = dir.listFiles(filter);
		}

		if (files != null && ignore != null && ignore.length() > 0) {
			List list = new ArrayList();
			for (File file : files) {
				if (!file.getName().endsWith(ignore))
					list.add(file);
			}
			return (File[]) list.toArray(new File[] {});
		}

		return files;
	}

	/**
	 * List only files of the directories. ȡ��Ŀ¼�µ��ļ�����
	 * 
	 * @param directory
	 * @return Array of Files
	 */
	public static List listFilesOnly(File directory) {
		return listFilesOnly(directory, null, null);
	}

	/**
	 * List all files of the directories with file suffix.
	 * 
	 * @param directory
	 * @param suffix
	 * @return Array of Files
	 */
	public static List<File> listFilesOnly(File directory, String suffix,
			String ignore) {
		File[] listFiles = listFilesAndDirs(directory, suffix, ignore);
		if (listFiles == null)
			return null;

		List<File> files = new ArrayList<File>();
		for (int i = 0; i < listFiles.length; i++) {
			if (listFiles[i].isFile())
				files.add(listFiles[i]);
		}

		return files;
	}

	/**
	 * List all directories of the directories.
	 * 
	 * @param directory
	 * @return Array of Files
	 */
	public static List<File> listDirsOnly(File directory) {
		File[] listFiles = listFilesAndDirs(directory);
		if (listFiles == null)
			return null;

		List<File> dirs = new ArrayList<File>();
		for (int i = 0; i < listFiles.length; i++) {
			if (listFiles[i].isDirectory())
				dirs.add(listFiles[i]);
		}

		return dirs;
	}

	/**
	 * List all files under the directories.
	 * 
	 * @param directory
	 * @return Array of Files
	 */
	public static List<File> treeFilesOnly(File directory) {
		return treeFilesOnly(directory, null);
	}

	/**
	 * List all files under the directories with file suffix.
	 * 
	 * @param directory
	 * @param suffix
	 * @return Array of Files
	 */
	public static List<File> treeFilesOnly(File directory, String suffix) {
		return iterTreeFiles(directory, suffix, null);
	}

	/**
	 * Get all files under the directories by self.
	 * 
	 * @param directory
	 * @param files
	 * @return Array of Files
	 */
	private static List<File> iterTreeFiles(File directory, String suffix,
			List<File> files) {
		if (files == null)
			files = new ArrayList<File>();

		List<File> subFiles = listFilesOnly(directory, suffix, null);
		List<File> subDirs = listDirsOnly(directory);

		for (int i = 0; i < subFiles.size(); i++) {
			files.add((File) subFiles.get(i));
		}

		for (int i = 0; i < subDirs.size(); i++) {
			iterTreeFiles(((File) subDirs.get(i)), suffix, files);
		}

		return files;
	}

	/**
	 * Judge the file type.
	 * 
	 * @param file
	 * @param fileType
	 * @return Yes or No
	 */
	public static boolean isWhatFile(File file, String fileType) {
		return file.getPath().toLowerCase().endsWith(fileType.toLowerCase());
	}

	public static String getFileType(String path) {
		if (path == null || path.length() <= 0)
			return path;
		return path.substring(path.lastIndexOf("."));
	}

	public static void main(String[] args) {

		// try {
		// FileUtil.mergeTextFiles(new String[] { "d:\\gzip\\xui-ajax.js",
		// "d:\\gzip\\xui-cookie.js", "d:\\gzip\\xui-date-util.js" },
		// "d:\\gzip\\", "merge.txt", "GBK");
		//			
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

}