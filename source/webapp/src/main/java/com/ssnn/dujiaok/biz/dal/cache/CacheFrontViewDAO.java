package com.ssnn.dujiaok.biz.dal.cache;

import java.util.List;
import com.ssnn.dujiaok.biz.dal.ibatis.IBatisFrontViewDAO;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.service.impl.AbstractCacheSupport;

public class CacheFrontViewDAO extends IBatisFrontViewDAO {

    private AbstractCacheSupport frontViewCache;

    /**
     * 基于moduleKey做缓存，如果不是按照模块显示数量获取，不要走缓存的方式
     */
    public List<FrontViewDO> queryFrontViews(String moduleKey, int limit) {
        List<FrontViewDO> frontConfigDOs = frontViewCache.getValue(moduleKey);
        if (frontConfigDOs != null) return frontConfigDOs;

        frontConfigDOs = super.queryFrontViews(moduleKey, limit);
        frontViewCache.putValue(moduleKey, frontConfigDOs);
        return frontConfigDOs;
    }

    public void insertFrontView(FrontViewDO frontViewDO) {
        super.insertFrontView(frontViewDO);
        frontViewCache.clearKey(frontViewDO.getModuleKey());
    }

    public boolean updateFrontView(FrontViewDO frontViewDO) {
        boolean result = super.updateFrontView(frontViewDO);
        if (result) {
            frontViewCache.clearKey(frontViewDO.getModuleKey());
            frontViewCache.clearKey(frontViewDO.getFrontViewId());
        }
        return result;
    }

    public void setFrontViewCache(AbstractCacheSupport frontViewCache) {
        this.frontViewCache = frontViewCache;
    }

}
