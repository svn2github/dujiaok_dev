package com.ssnn.dujiaok.web.velocity.toolbox;

import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.util.enums.PayStatusEnums;
import com.ssnn.dujiaok.util.enums.PayTypeEnums;
import com.ssnn.dujiaok.util.enums.ProductEnums;

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
	
	
}
