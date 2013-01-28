package com.ssnn.dujiaok.biz.service.cache;

import java.util.List;

import com.ssnn.dujiaok.model.HotCityDO;

public interface HotCityCache {

	List<HotCityDO> getHotCitys() ;
	
	void setHotCitys(List<HotCityDO> list) ;
	
	void clear() ;
}
