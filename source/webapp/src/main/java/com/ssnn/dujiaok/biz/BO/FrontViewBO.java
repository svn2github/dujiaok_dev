package com.ssnn.dujiaok.biz.BO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类DefaultFrontViewManageService.java的实现描述：模块管理service
 * 
 * @author ib 2012-2-13 上午02:00:01
 */
public class FrontViewBO {

	private FrontViewService frontViewService;
	private FrontConfigService frontConfigService;

	public Map<String, List<FrontViewDO>> getFrontViewsMap(String channelKey) {
		List<FrontConfigDO> configs = (List<FrontConfigDO>) frontConfigService
				.getFrontConfigs(channelKey);
		if (configs == null) {
			return null;
		}

		Map<String, List<FrontViewDO>> resultMap = new HashMap<String, List<FrontViewDO>>();
		String moduleKey;
		// 获取所有的moduleKey
		for (FrontConfigDO frontConfigDO : configs) {
			moduleKey = frontConfigDO.getModuleKey();
			if (moduleKey != null) {
				List<FrontViewDO> frontViews = (List<FrontViewDO>) frontViewService
						.getFrontViewDOs(moduleKey, frontConfigDO
								.getDispalyNum());
				resultMap.put(moduleKey, frontViews);
			}
		}
		return resultMap;
	}

	public void setFrontViewService(FrontViewService frontViewService) {
		this.frontViewService = frontViewService;
	}

	public void setFrontConfigService(FrontConfigService frontConfigService) {
		this.frontConfigService = frontConfigService;
	}

}
