package com.ssnn.dujiaok.constant;

/**
 * 类CommonChannelConstants.java的实现描述：通用频道常量
 * 
 * @author ib 2012-4-14 上午01:06:10
 */
public class CommonChannelConstants {

    /**
     * E1.1度假ok动态top
     */
    public static final String M_DUJIAOK_DYNAMIC_TOP = "common.dujiaok.dynamic.top.module";
    /**
     * E1.2度假ok动态
     */
    public static final String M_DUJIAOK_DYNAMIC     = "common.dujiaok.dynamic.module";
    /**
     * E2驴友热议
     */
    public static final String M_TRAVEL_MATE_DYNAMIC = "common.travel.mate.module";

    public static String getmDujiaokDynamicTop() {
        return M_DUJIAOK_DYNAMIC_TOP;
    }

    public static String getmDujiaokDynamic() {
        return M_DUJIAOK_DYNAMIC;
    }

    public static String getmTravelMateDynamic() {
        return M_TRAVEL_MATE_DYNAMIC;
    }
}
