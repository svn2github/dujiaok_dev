package com.ssnn.dujiaok.service;

import java.util.List;

import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类FrontConfigService.java的实现描述：前台展示配置服务 
 * @author ib 2012-2-12 下午04:35:40
 */
public interface FrontConfigLoadService {
    /**
     * 获取首页频道配置信息
     * @return
     */
    public List<FrontConfigDO> getIndexChannelConfigs();
}
