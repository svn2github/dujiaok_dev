package com.ssnn.dujiaok.biz.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DujiaokLogger {
	
	private Logger logger;
	
	public DujiaokLogger(String name) {
		this.logger = Logger.getLogger(name);
	}
	
	public DujiaokLogger(Class<?> clazz) {
		this.logger = Logger.getLogger(clazz);
	}
	
	public static DujiaokLogger getLogger(String name) {
		return new DujiaokLogger(name);
	}
	
	public static DujiaokLogger getLogger(Class<?> clazz) {
		return new DujiaokLogger(clazz);
	}
	
	public void error(String info) {
		logger.error(info);
	}
	
	public void error(Throwable e) {
		logger.error(e);
	}
	
	/**
	 * log error info.
	 * @param info
	 * @param e
	 */
	public void error(Object info, Throwable e) {
		if (logger.isEnabledFor(Level.ERROR)) {
			if (info == null) {
				if (e != null) {
					logger.error(e);
				} else {
					logger.error("Log Nothing");
				}
			} else {
				if (e != null) {
					logger.error(info, e);
				} else {
					logger.error(info);
				}
			}
		}
	}
}
