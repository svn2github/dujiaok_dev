package com.ssnn.dujiaok.biz.service.alipay;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Map;

import org.dom4j.DocumentException;

public interface AlipayService {

	public String create_direct_pay_by_user(Map<String, String> sParaTemp , BigDecimal amount) ;
	
	 public String query_timestamp() throws MalformedURLException,
     DocumentException, IOException  ;
}
