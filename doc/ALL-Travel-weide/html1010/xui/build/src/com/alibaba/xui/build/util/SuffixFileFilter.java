/**
 * Copyright(c) 2005 Dragonfly - created by FengChun
 * All Rights Reserved.
 * 
 * @license: Dragonfly Common License
 * @date 2006-3-15
 */
package com.alibaba.xui.build.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.List;

/**
 * File Suffix Filter.
 * 
 * @version 1.0
 * @author Frank.fengc
 * @email f15_nsm@hotmail.com
 */
public class SuffixFileFilter implements FileFilter, FilenameFilter {
	/** The filename suffixes to search for */
	private String[] suffixes;

	/**
	 * Constructs a new Suffix file filter for a single extension.
	 * 
	 * @param suffix
	 *            the suffix to allow, must not be null
	 * @throws IllegalArgumentException
	 *             if the suffix is null
	 */
	public SuffixFileFilter(String suffix) {
		if (suffix == null) {
			throw new IllegalArgumentException("The suffix must not be null");
		}
		this.suffixes = new String[] { suffix };
	}

	/**
	 * Constructs a new Suffix file filter for an array of suffixs.
	 * <p>
	 * The array is not cloned, so could be changed after constructing the
	 * instance. This would be inadvisable however.
	 * 
	 * @param suffixes
	 *            the suffixes to allow, must not be null
	 * @throws IllegalArgumentException
	 *             if the suffix array is null
	 */
	public SuffixFileFilter(String[] suffixes) {
		if (suffixes == null) {
			throw new IllegalArgumentException(
					"The array of suffixes must not be null");
		}
		this.suffixes = suffixes;
	}

	/**
	 * Constructs a new Suffix file filter for a list of suffixes.
	 * 
	 * @param suffixes
	 *            the suffixes to allow, must not be null
	 * @throws IllegalArgumentException
	 *             if the suffix list is null
	 * @throws ClassCastException
	 *             if the list does not contain Strings
	 */
	public SuffixFileFilter(List suffixes) {
		if (suffixes == null) {
			throw new IllegalArgumentException(
					"The list of suffixes must not be null");
		}
		this.suffixes = (String[]) suffixes
				.toArray(new String[suffixes.size()]);
	}

	public boolean accept(File file) {
		String name = file.getName();
		for (int i = 0; i < this.suffixes.length; i++) {
			if (name.endsWith(this.suffixes[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean accept(File file, String name) {
		for (int i = 0; i < this.suffixes.length; i++) {
			if (name.endsWith(this.suffixes[i])) {
				return true;
			}
		}
		return false;
	}

}
