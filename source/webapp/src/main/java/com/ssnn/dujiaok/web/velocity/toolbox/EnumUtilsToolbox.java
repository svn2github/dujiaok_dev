package com.ssnn.dujiaok.web.velocity.toolbox;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.util.enums.AddProductEnums;
import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.util.enums.PayStatusEnums;
import com.ssnn.dujiaok.util.enums.PayTypeEnums;
import com.ssnn.dujiaok.util.enums.ProductEnums;
import com.ssnn.dujiaok.util.enums.StarRateEnums;

public class EnumUtilsToolbox {

	public OrderStatusEnums[] listOrderStatus(){
		return OrderStatusEnums.values() ;
	}
	
	public OrderStatusEnums orderStatus(String name){
		return OrderStatusEnums.fromValue(name) ;
	}
	
	public PayStatusEnums[] listPayStatus() {
		return PayStatusEnums.values() ;
	}
	
	public PayStatusEnums payStatus(String name) {
		return PayStatusEnums.fromValue(name) ;
	}
	
	public PayTypeEnums[] listPayType() {
		return PayTypeEnums.values() ;
	}
	
	public PayTypeEnums payType(String name) {
		return PayTypeEnums.fromValue(name) ;
	}
	
	public ProductEnums[] listProduct(){
		return ProductEnums.values() ;
	}
	
	public ProductEnums product(String name){
		return ProductEnums.fromValue(name) ;
	}
	
	public AddProductEnums addProduct(String name){
		return AddProductEnums.fromValue(name);
	}
	
	public String toPayTypeEnumDescs(String nameListString) {
		List<String> payTypeEnums = ArrayStringUtils.toList(nameListString) ;
		String desc = "" ;
		if(payTypeEnums==null || payTypeEnums.isEmpty()){
			return "" ;
		}
		for(String name : payTypeEnums){
			desc += PayTypeEnums.fromValue(name).getDesc() + "," ;
		}
		if(desc.length() > 0){
			desc = StringUtils.removeEnd(desc, ",") ;
		}
		return desc ;
	}
	
	public String toAddProductEnumDescs(String nameListString) {
		List<String> enums = ArrayStringUtils.toList(nameListString) ;
		String desc = "" ;
		if(enums==null || enums.isEmpty()){
			return "" ;
		}
		for(String name : enums){
			desc += AddProductEnums.fromValue(name).getDesc() + "," ;
		}
		if(desc.length() > 0){
			desc = StringUtils.removeEnd(desc, ",") ;
		}
		return desc ;
	}
	
	public StarRateEnums toStarRate(int value){
		return StarRateEnums.fromValue(value) ;
	}
	
	public StarRateEnums[] startRates(){
		return StarRateEnums.values() ;
	}
}
