package com.ssnn.dujiaok.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类FrontConfigContainer.java的实现描述：前台配置容器
 * 
 * @author ib 2012-2-12 下午05:32:33
 */
public class FrontContainer {

    public static Boolean                           needToLoadIndexConfig = Boolean.TRUE;
    private static Map<String, List<FrontConfigDO>> frontConfigs          = new ConcurrentHashMap<String, List<FrontConfigDO>>();
    private static Map<String, List<FrontViewDO>>   frontViewLists        = new ConcurrentHashMap<String, List<FrontViewDO>>();

    public static void putFrontConfig(String channelKey, List<FrontConfigDO> configs) {
        frontConfigs.put(channelKey, configs);
    }

    public static List<FrontConfigDO> getFrontConfig(String channelKey) {
        if (!frontConfigs.containsKey(channelKey)) {
            return null;
        }
        return frontConfigs.get(channelKey);
    }

    public static void putFrontViewList(String moduleKey, List<FrontViewDO> frontViewDOs) {
        frontViewLists.put(moduleKey, frontViewDOs);
    }

    public static List<FrontViewDO> getFrontViews(String moduleKey) {
        if (!frontViewLists.containsKey(moduleKey)) {
            return null;
        }
        return frontViewLists.get(moduleKey);
    }

}
