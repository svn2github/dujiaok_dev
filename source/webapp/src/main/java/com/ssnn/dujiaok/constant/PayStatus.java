package com.ssnn.dujiaok.constant;

public enum PayStatus {
	TRADE_UNPAY {
		@Override
		public OrderStatus getOrderStatus() {
			return OrderStatus.UNPAY;
		}
	},
	TRADE_INIT {
		@Override
		public OrderStatus getOrderStatus() {
			return OrderStatus.UNPAY;
		}
	},
	TRADE_SUCCESS {
		@Override
		public OrderStatus getOrderStatus() {
			return OrderStatus.SUCCESS;
		}
	},
	TRADE_FINISHED {
		@Override
		public OrderStatus getOrderStatus() {
			return OrderStatus.SUCCESS;
		}
	},
	TRADE_OTHER {
		@Override
		public OrderStatus getOrderStatus() {
			return OrderStatus.FAILED;
		}
	};
	
	public abstract OrderStatus getOrderStatus();
	
	public static PayStatus getStatus(String name) {
		try {
			return Enum.valueOf(PayStatus.class, name.toUpperCase());
		} catch (Exception e) {
			return PayStatus.TRADE_OTHER;
		}
	}
}
