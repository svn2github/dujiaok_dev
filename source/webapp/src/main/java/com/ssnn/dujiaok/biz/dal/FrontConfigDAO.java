package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类FrontConfigDAO.java的实现描述：前台展示配置DAO
 * 
 * @author ib 2012-2-12 上午04:50:42
 */
public interface FrontConfigDAO {

	public List<FrontConfigDO> queryFrontConfigs(String channelKey);

	public FrontConfigDO queryOneFrontConfig(String module_key);
}
