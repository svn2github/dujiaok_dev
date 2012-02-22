package com.ssnn.dujiaok.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.service.FrontConfigLoadService;
import com.ssnn.dujiaok.service.FrontViewManageService;

/**
 * 类JvmFrontViewManageService.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-13 上午02:00:01
 */
public class DefaultFrontViewManageService extends AbstractCacheSupport implements FrontViewManageService {

    private FrontConfigLoadService frontConfigLoadService;
    private FrontViewService       frontViewService;

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.service.FrontViewManageService#getFrontViewsMap(java.lang.String)
     */
    @Override
    public Map<String, List<FrontViewDO>> getFrontViewsMap() {
        List<FrontConfigDO> configs = frontConfigLoadService.getChannelConfigs();
        if (configs == null) {
            return null;
        }
        Map<String, List<FrontViewDO>> resultMap = new HashMap<String, List<FrontViewDO>>();
        String moduleKey;
        // 获取所有的moduleKey
        for (FrontConfigDO frontConfigDO : configs) {
            moduleKey = frontConfigDO.getModuleKey();
            List<FrontViewDO> frontViews =getValue(moduleKey);

            // 缓存中没有，重新获取
            if (frontViews == null) {
                frontViews = (List<FrontViewDO>) frontViewService.getFrontViewDOs(moduleKey,
                                                                                       frontConfigDO.getDispalyNum());
                // 若获取到，放入缓存中
                if (frontViews != null) {
                    putValue(moduleKey, frontViews);
                }
            }
            resultMap.put(moduleKey, frontViews);
        }
        return resultMap;
    }

    public void setFrontConfigLoadService(FrontConfigLoadService frontConfigLoadService) {
        this.frontConfigLoadService = frontConfigLoadService;
    }

    public void setFrontViewService(FrontViewService frontViewService) {
        this.frontViewService = frontViewService;
    }
}
