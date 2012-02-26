package com.ssnn.dujiaok.web.action.admin.front;

import com.ssnn.dujiaok.service.cache.impl.JvmCacheClient;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 类CacheAction.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-27 上午02:23:00
 */
public class CacheAction extends BasicAction {

    @Override
    public String execute() throws Exception {
        JvmCacheClient cacheClient = new JvmCacheClient();
        cacheClient.clearCache();
        return SUCCESS;
    }
}
