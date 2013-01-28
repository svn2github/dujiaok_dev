package com.ssnn.dujiaok.biz.service;

import java.util.List;

import com.ssnn.dujiaok.model.HotCityDO;

public interface HotCityService {

	/**
	 * 
	 * @param hotCity
	 * @return
	 */
	Long insertHotCity(HotCityDO hotCity) ;
	
	/**
	 * 
	 * @return
	 */
	List<HotCityDO> getHotCitys() ;
	
	void deleteHotCityById(Long id) ;
}
