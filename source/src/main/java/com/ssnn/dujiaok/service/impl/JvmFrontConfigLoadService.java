package com.ssnn.dujiaok.service.impl;

import java.util.List;
import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.constant.FrontConfigConstants;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.service.FrontConfigContainer;
import com.ssnn.dujiaok.service.FrontConfigLoadService;

/**
 * 类JvmFrontConfigService.java的实现描述：基于JVM静态存储的前台配置服务
 * 
 * @author ib 2012-2-12 下午04:36:31
 */
public class JvmFrontConfigLoadService implements FrontConfigLoadService {

    private FrontConfigService frontConfigService;

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.FrontConfigService#getIndexChannelConfigs()
     */
    @Override
    public List<FrontConfigDO> getIndexChannelConfigs() {
        if (FrontConfigContainer.needToLoadIndexConfig) {
            loadIndexChannelConfigs();
        }
        return FrontConfigContainer.getFrontConfig(FrontConfigConstants.CHANNEL_INDEX_PAGE);
    }

    /**
     * 加载首页配置
     */
    public synchronized void loadIndexChannelConfigs() {
        if (FrontConfigContainer.needToLoadIndexConfig) {
            loadConfigs(FrontConfigConstants.CHANNEL_INDEX_PAGE);
            //FrontConfigContainer.needToLoadIndexConfig = Boolean.FALSE;
        }
    }

    private void loadConfigs(String channelKey) {
        List<FrontConfigDO> configs = frontConfigService.getFrontConfigs(channelKey);
        FrontConfigContainer.pubFrontConfig(channelKey, configs);
    }

    public void setFrontConfigService(FrontConfigService frontConfigService) {
        this.frontConfigService = frontConfigService;
    }

}
