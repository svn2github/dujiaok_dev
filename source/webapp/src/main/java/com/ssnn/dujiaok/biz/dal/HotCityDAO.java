package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.HotCityDO;

/**
 * 
 * @author langben 2013-1-28
 *
 */
public interface HotCityDAO {

	Long insertHotCity(HotCityDO hotCity) ;
	
	List<HotCityDO> queryHotCitys() ;
	
	void deleteHotCityById(Long id) ;
}
