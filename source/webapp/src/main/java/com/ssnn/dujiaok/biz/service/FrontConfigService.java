package com.ssnn.dujiaok.biz.service;

import java.util.List;

import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类FrontConfigService.java的实现描述：TODO 类实现描述 
 * @author ib 2012-2-12 下午05:38:27
 */
public interface FrontConfigService {
    public List<FrontConfigDO> getFrontConfigs(String channelKey);
    public FrontConfigDO getOneFrontConfig(String moduleKey);
}
