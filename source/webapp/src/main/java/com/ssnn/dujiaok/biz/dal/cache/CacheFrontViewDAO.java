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
		if (frontConfigDOs != null)
			return frontConfigDOs;

		frontConfigDOs = super.queryFrontViews(moduleKey, limit);
		frontViewCache.putValue(moduleKey, frontConfigDOs);
		return frontConfigDOs;
	}

	/**
	 * 非缓存方式
	 * 
	 * @param moduleKey
	 * @param limit
	 * @return
	 */
	public List<FrontViewDO> queryFrontViewsNoCache(String moduleKey, int limit) {
		return super.queryFrontViews(moduleKey, limit);
	}

	public void setFrontViewCache(AbstractCacheSupport frontViewCache) {
		this.frontViewCache = frontViewCache;
	}

}
