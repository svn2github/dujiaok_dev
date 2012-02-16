package com.ssnn.dujiaok.constant;

public class Constant {

	public static final String PROTOCOL = "http" ;
	
	/**
     * 支付宝交易未付款
     */
    public static final String ALIPAY_INIT = "init";
    /**
     * 支付宝交易成功状态
     */
    public static final String ALIPAY_SUCCESS      = "TRADE_SUCCESS";
	public static final String ENCODING = "UTF-8" ;
	
	public static final String REDIRECT_KEY = "Done" ;
	
	
	public static final String PREFIX_HOTEL = "JD" ;
	public static final String PREFIX_SELFDRIVE = "ZJ" ;
	public static final String PREFIX_HOTELROOM = "FJ" ;
	public static final String PREFIX_TICKET = "MP" ;
	
	/**
     * 已付款
     */
    public static final String ORDER_PAY_STATUS_1 = "已付款";

    /**
     * 未付款
     */
    public static final String ORDER_PAY_STATUS_2 = "未付款";
    
    public static final String STATUS_1           = "待付款";
    public static final String STATUS_2           = "等待预定确认";
    public static final String STATUS_3           = "预订成功";
    public static final String STATUS_4           = "订单关闭";
    public static final String STATUS_5           = "订单关闭，申请退款";
    public static final String STATUS_6           = "已退款";

    public static final String STATUS_MANAGER_1   = "待付款";
    public static final String STATUS_MANAGER_2   = "等待预定确认";
    public static final String STATUS_MANAGER_3   = "预订成功";
    public static final String STATUS_MANAGER_4   = "订单关闭（用户关闭）";
    public static final String STATUS_MANAGER_5   = "订单关闭（超时未付款）";
    public static final String STATUS_MANAGER_6   = "订单关闭（预定不成功）";
    public static final String STATUS_MANAGER_7   = "订单关闭（客户需退款）";
    public static final String STATUS_MANAGER_8   = "已退款";
}
