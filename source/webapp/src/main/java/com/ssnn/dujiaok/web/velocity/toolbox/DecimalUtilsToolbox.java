package com.ssnn.dujiaok.web.velocity.toolbox;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class DecimalUtilsToolbox {

    public String format(BigDecimal decimal, String pattern) {
        if (decimal == null) {
            return "";
        }
        DecimalFormat f = new DecimalFormat(pattern);
        return f.format(decimal);
    }

    public String formatPrice(BigDecimal decimal) {
        return format(decimal, "0.00");
    }

    /**
     * ֻ���С��
     * 
     * @param decimal
     * @param pattern
     * @return
     */
    public String fractionFormat(BigDecimal decimal, String pattern) {
        if (decimal == null) {
            return "";
        }
        DecimalFormat f = new DecimalFormat(pattern);
        f.setMaximumIntegerDigits(0);
        return f.format(decimal);
    }

    public BigDecimal divide(BigDecimal d1, String decimalStr) {
        return d1.divide(new BigDecimal(decimalStr));
    }

    /**
     * createInt
     * 
     * @param str
     * @return
     */
    public int createInt(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        return NumberUtils.createInteger(str);
    }

    public int createInt(Integer i) {
        if (i == null) {
            return 0;
        }
        return i;
    }

    public int createInt(Integer i, int defaultVal) {
        if (i == null) {
            return defaultVal;
        }
        return createInt(i);
    }

    /**
     * createInt
     * 
     * @param str
     * @return
     */
    public int createInt(String str, int defaultVal) {
        if (StringUtils.isBlank(str)) {
            return defaultVal;
        }
        return createInt(str);
    }

    public boolean isGreaterThan(Object d , int i){
    	if(d == null){
    		return false ;
    	}
    	//return d.compareTo(new BigDecimal(i)) == 1 ;
    	return true ;
    }
}
