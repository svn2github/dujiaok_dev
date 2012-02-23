package com.ssnn.dujiaok.biz.dal.cache;

import java.util.List;
import com.ssnn.dujiaok.biz.dal.ibatis.IBatisFrontViewDAO;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.service.impl.AbstractCacheSupport;

public class CacheFrontViewDAO extends IBatisFrontViewDAO {
	private AbstractCacheSupport frontViewCache;

	@Override
	public List<FrontViewDO> queryFrontViews(String moduleKey, int limit) {
		List<FrontViewDO> frontConfigDOs = frontViewCache.getValue(moduleKey
				+ limit);
		if (frontConfigDOs != null)
			return frontConfigDOs;

		frontConfigDOs = super.queryFrontViews(moduleKey, limit);
		frontViewCache.putValue(moduleKey + limit, frontConfigDOs);
		return frontConfigDOs;
	}

	public void setFrontViewCache(AbstractCacheSupport frontViewCache) {
		this.frontViewCache = frontViewCache;
	}

}
