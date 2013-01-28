package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.dal.HotCityDAO;
import com.ssnn.dujiaok.biz.service.HotCityService;
import com.ssnn.dujiaok.biz.service.cache.HotCityCache;
import com.ssnn.dujiaok.model.HotCityDO;

public class HotCityServiceImpl implements HotCityService {

	
	private HotCityDAO hotCityDAO ;
	
	
	private HotCityCache hotCityCache ;

	@Override
	public Long insertHotCity(HotCityDO hotCity) {
		Long hotCityId = hotCityDAO.insertHotCity(hotCity) ;
		triggerCacheModified() ;
		return hotCityId ;
	}

	@Override
	public List<HotCityDO> getHotCitys() {
		List<HotCityDO> hotCityList = hotCityCache.getHotCitys() ;
		if(hotCityList == null){
			hotCityList = hotCityDAO.queryHotCitys() ;
			hotCityCache.setHotCitys(hotCityList) ;
		}
		return hotCityList ;
	}
	
	@Override
	public void deleteHotCityById(Long id) {
		hotCityDAO.deleteHotCityById(id) ;
		triggerCacheModified() ;
	}
	
	
	private void triggerCacheModified(){
		hotCityCache.clear() ;
	}

	public void setHotCityDAO(HotCityDAO hotCityDAO) {
		this.hotCityDAO = hotCityDAO;
	}

	public void setHotCityCache(HotCityCache hotCityCache) {
		this.hotCityCache = hotCityCache;
	}

	
}
