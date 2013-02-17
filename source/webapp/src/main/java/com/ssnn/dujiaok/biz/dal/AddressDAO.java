package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.AreaDO;
import com.ssnn.dujiaok.model.CityDO;
import com.ssnn.dujiaok.model.ProvinceDO;

/**
 * 
 * @author langben 2012-1-19
 *
 */
public interface AddressDAO {

	List<ProvinceDO> queryProvinces() ;
	
	ProvinceDO queryProvinceByName(String name) ;
	
	List<CityDO> queryCitys(String provinceCode) ;
	
	CityDO queryCityByName(String name) ;
	
	List<AreaDO> queryAreas(String cityCode) ;
	
	AreaDO queryAreaByName(String name) ;
}
