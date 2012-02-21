package com.alibaba.maven.plugins.autoconf.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;

public class FileWalker {
	public static interface Handler {
	    /**
	     * Process the file that traversed.
	     * @param file file to be processed
	     * @param level file recursive level
	     * @return boolean true - done, don't process again, false - not done, process later.  
	     */
		boolean process(FileObject file, int level);
	}

	/**
	 * Walk through the file
	 * 
	 * @param file
	 * @param callback
	 */
	public static void walk(String pathOrUrl, Handler ... handlers) {
		String uri = pathOrUrl;
		FileObject file;
		try {
			file = VFS.getManager().resolveFile(uri);
			walkRecursive(file, handlers, 0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean walkRecursive(FileObject file, Handler[] handlers, int level) {
		if(isFileIgnored(file)){
			//System.out.println("<file: " + file.getName().getBaseName() + " is ignored...>");
			return true;
		}

		for(Handler handler : handlers){
		    if (handler.process(file, level)) {
                // return true here means break the process chain and go on processing next sibling file
                return true;
            }   
		}
		
		// recursive children
		try {
			FileSystemManager vfs = VFS.getManager();			
			FileObject fo = null;
			if (file.getType().hasChildren()) {
				fo = file;
			} else {
				String detectedScheme = detectScheme(file);
				if (detectedScheme != null) {  // This is a file, but mountable.
					if ("zip".equalsIgnoreCase(detectedScheme) || "jar".equalsIgnoreCase(detectedScheme)) { // zip file
						fo = vfs.createFileSystem(file);
					} else {
						// append the scheme and resolve again.
						FileName name = file.getName();
						String uri = detectedScheme + ":" + name.getURI();
						if ("tgz".equalsIgnoreCase(detectedScheme) || "tbz2".equalsIgnoreCase(detectedScheme)){
							// tgz special case.
							if(!uri.endsWith("!/")){
								uri = uri + "!/";
							}
						}
						fo = vfs.resolveFile(uri);
					}
				}else{
					// no children to walk
					return true;
				}
			}

			if (fo != null) {
				if (level == maxRecurseLevel) { 
					// Stop walking children if the recurse-level is too deep;
					System.out.println("<...children ignored...>" );
					return true;
				}				
				
				// Walk though the children
				FileObject[] ff = fo.getChildren();
				if (ff != null && ff.length > 0) {
					++level;
					for (FileObject child : ff) {
						// !!!Recursive Call!!!
						if (!walkRecursive(child, handlers, level)) {
							// bail out if something wrong. 
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			// bail out if got exception.
			System.err.println("Error walking file " + file + ", " + e.getMessage());
			System.err.println("Recursing Aborted.");
			return false;
		}		
		return true;
	}

	private static String getSuffix(String name) {
		String suffix = null;
		String f = name.toLowerCase();
		int i = f.lastIndexOf('.');
		if (i > 0 && i < f.length()) {
			// Specal case for tar.gz or tar.bz2
			int j = f.lastIndexOf(".tar.");
			if (j > 0) {
				suffix = f.substring(j + 1);
			} else {
				suffix = f.substring(i + 1);
			}
		}
		return suffix;
	}

	public static boolean isFileIgnored(FileObject file){
		//FIXME - support more ignore file patterns
		return file.getName().getBaseName().endsWith(".svn");
	}
	
	public static String detectScheme(FileObject file) {
		String suffix = getSuffix(file.getName().getBaseName());
		if (suffix != null) {
			return suffixToSchemaMap.get(suffix);
		}
		return null;
	}

	
	private static int maxRecurseLevel = 1000;
	private static String tmpDir = null;
	private static final Map<String, String> suffixToSchemaMap = new HashMap<String, String>();
	private static final HashSet<String> ignoredFileSuffixSet = new HashSet<String>();
	static {
		suffixToSchemaMap.put("zip", "zip");

		suffixToSchemaMap.put("jar", "jar");
		suffixToSchemaMap.put("war", "jar");
		suffixToSchemaMap.put("car", "jar");

		suffixToSchemaMap.put("tgz", "tgz");
		suffixToSchemaMap.put("tbz2", "tbz2");
		suffixToSchemaMap.put("tar.gz", "tgz");
		suffixToSchemaMap.put("tar.bz2", "tbz2");

		suffixToSchemaMap.put("tar", "tar");
		suffixToSchemaMap.put("gz", "gz");
		suffixToSchemaMap.put("gzip", "gz");
		suffixToSchemaMap.put("bz2", "bz2");

		ignoredFileSuffixSet.add("svn");
		
        tmpDir = System.getProperty("a2mdiff.tmpdir", ".");
		//tmpDir = "D:/works/maven/reference-code/a2m-diff/tmp";

		// Initialize the VFS
		String orig = System.getProperty("java.io.tmpdir");
		System.setProperty("java.io.tmpdir", tmpDir);
		try {
			VFS.getManager();
			// Add additional extension to schema mapping
			((DefaultFileSystemManager)VFS.getManager()).addExtensionMap("war","jar");
			((DefaultFileSystemManager)VFS.getManager()).addExtensionMap("car","jar");
		} catch (FileSystemException e) {
			throw new RuntimeException("FATAL - initialize VFS failed!");
		}
		System.setProperty("java.io.tmpdir", orig);
	}
}
