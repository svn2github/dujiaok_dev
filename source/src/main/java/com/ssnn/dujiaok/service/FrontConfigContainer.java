package com.ssnn.dujiaok.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类FrontConfigContainer.java的实现描述：前台配置容器
 * 
 * @author ib 2012-2-12 下午05:32:33
 */
public class FrontConfigContainer {

    public static Boolean                           needToLoadIndexConfig = Boolean.TRUE;
    private static Map<String, List<FrontConfigDO>> frontConfigs = new ConcurrentHashMap<String, List<FrontConfigDO>>();

    public static void pubFrontConfig(String channelKey, List<FrontConfigDO> configs) {
        frontConfigs.put(channelKey, configs);
    }

    public static List<FrontConfigDO> getFrontConfig(String channelKey) {
        return frontConfigs.get(channelKey);
    }

    public static Map<String, List<FrontConfigDO>> getFrontConfigs() {
        return frontConfigs;
    }

    public static void setFrontConfigs(Map<String, List<FrontConfigDO>> frontConfigs) {
        FrontConfigContainer.frontConfigs = frontConfigs;
    }

}
