package com.ssnn.dujiaok.biz.dal.cache;

import java.util.List;
import com.ssnn.dujiaok.biz.dal.ibatis.IBatisFrontConfigDAO;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.service.impl.AbstractCacheSupport;

public class CacheFrontConfigDAO extends IBatisFrontConfigDAO {
	private AbstractCacheSupport frontConfigCache;

	public List<FrontConfigDO> queryFrontConfigs(String channelKey) {
		List<FrontConfigDO> frontConfigDOs = frontConfigCache
				.getValue(channelKey);
		if (frontConfigDOs != null)
			return frontConfigDOs;

		frontConfigDOs = super.queryFrontConfigs(channelKey);
		frontConfigCache.putValue(channelKey, frontConfigDOs);
		return frontConfigDOs;

	}

	public void setFrontConfigCache(AbstractCacheSupport frontConfigCache) {
		this.frontConfigCache = frontConfigCache;
	}

}
