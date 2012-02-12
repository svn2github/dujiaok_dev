package com.ssnn.dujiaok.service.impl;

import java.util.List;
import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.service.FrontContainer;
import com.ssnn.dujiaok.service.FrontConfigLoadService;

/**
 * 类JvmFrontConfigService.java的实现描述：基于JVM静态存储的前台配置服务
 * 
 * @author ib 2012-2-12 下午04:36:31
 */
public class JvmFrontConfigLoadService implements FrontConfigLoadService {

    private String             channelKey;
    private boolean            neetToLoadConfig;
    private FrontConfigService frontConfigService;

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.FrontConfigService#getIndexChannelConfigs()
     */
    @Override
    public List<FrontConfigDO> getChannelConfigs() {
        if (neetToLoadConfig) {
            loadChannelConfigs();
        }
        return FrontContainer.getFrontConfig(channelKey);
    }

    /**
     * 加载首页配置
     */
    public synchronized void loadChannelConfigs() {
        if (neetToLoadConfig) {
            loadConfigs(channelKey);
            neetToLoadConfig = false;
        }
    }

    private void loadConfigs(String channelKey) {
        List<FrontConfigDO> configs = frontConfigService.getFrontConfigs(channelKey);
        FrontContainer.putFrontConfig(channelKey, configs);
    }

    public void setFrontConfigService(FrontConfigService frontConfigService) {
        this.frontConfigService = frontConfigService;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public void setNeetToLoadConfig(boolean neetToLoadConfig) {
        this.neetToLoadConfig = neetToLoadConfig;
    }
    
}
