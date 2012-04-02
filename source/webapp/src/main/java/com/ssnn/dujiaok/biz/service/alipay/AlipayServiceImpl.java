package com.ssnn.dujiaok.biz.service.alipay;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ssnn.dujiaok.biz.service.order.util.AlipaySubmit;

/* *
 *类名：AlipayService
 *功能：支付宝各接口构造类
 *详细：构造支付宝各接口请求参数
 *版本：3.2
 *修改日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayServiceImpl implements AlipayService {

	private String partner;
	private String key;
	private String seller_email;
	private String notify_url;
	private String return_url;
	private String input_charset;
	private String sign_type;
	private String transport;
	private String gateway;
	private boolean production ;
	

	/**
	 * 构造即时到帐接口
	 * 
	 * @param sParaTemp
	 *            请求参数集合
	 * @return 表单提交HTML信息
	 */
	@Override
	public String create_direct_pay_by_user(Map<String, String> sParaTemp , BigDecimal amount) {

		// 增加基本配置
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", partner);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("_input_charset", input_charset);
		if(production){
			sParaTemp.put("total_fee", amount.toString()) ;
		}else{
			sParaTemp.put("total_fee", "0.01") ;
		}

		String strButtonName = "确认";

		return AlipaySubmit.buildForm(sParaTemp, gateway, "get", strButtonName);
	}

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	@Override
	public String query_timestamp() throws MalformedURLException,
			DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = gateway + "service=query_timestamp&partner=" + partner;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());

		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success")
					&& node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc
						.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}

		return result.toString();
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}
	
}
