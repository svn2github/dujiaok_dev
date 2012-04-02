package com.ssnn.dujiaok.constant;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.util.enums.PayStatusEnums;

public enum AlipayStatus {

	
		TRADE_UNPAY("TRADE_UNPAY",PayStatusEnums.UNPAID) ,
		TRADE_INIT("TRADE_INIT",PayStatusEnums.UNPAID),
		TRADE_SUCCESS("TRADE_SUCCESS" , PayStatusEnums.PAID) ,
		TRADE_FINISHED("TRADE_FINISHED" , PayStatusEnums.PAID),
		TRADE_OTHER("TRADE_OTHER"  , PayStatusEnums.UNKNOWN) ;
		
		private AlipayStatus(String value , PayStatusEnums payStatus){
			this.payStatus = payStatus ;
			this.value = value ;
		}
		
		private PayStatusEnums payStatus ;
		private String value ;
		
		public PayStatusEnums getPayStatus() {
			return payStatus;
		}

		public String getValue() {
			return value;
		}

		public static PayStatusEnums toPayStatus(String alipayStatus) {
			String value = StringUtils.upperCase(alipayStatus) ;
			for(AlipayStatus e : AlipayStatus.values()){
				if(StringUtils.equals(e.getValue() , value)){
					return e.getPayStatus() ;
				}
			}
			return PayStatusEnums.UNKNOWN ;
		}

}
