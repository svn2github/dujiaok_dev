package com.ssnn.dujiaok.web.velocity.toolbox;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * @author shenjia.caosj 2012-1-13
 *
 */
public class DateUtilToolbox {

	public String format(Date date , String format){
		return new SimpleDateFormat(format).format(date) ;
	}
}
