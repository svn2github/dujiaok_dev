package com.ssnn.dujiaok.util;

import java.io.Closeable;

public final class StreamUtil {
	/**
	 * 
	 * @param closeable
	 * @return
	 */
	public static boolean close(Closeable closeable) {
		if (closeable == null) {
			return true;
		}
		try {
			closeable.close();
		} catch (Throwable e) {
			
		}
		return true;
	}
}
