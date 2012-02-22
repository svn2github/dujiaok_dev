package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.AddressDAO;
import com.ssnn.dujiaok.model.AreaDO;
import com.ssnn.dujiaok.model.CityDO;
import com.ssnn.dujiaok.model.ProvinceDO;

public class IBatisAddressDAO extends SqlMapClientDaoSupport implements AddressDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<ProvinceDO> queryProvinces() {
		return getSqlMapClientTemplate().queryForList("address.queryProvinces");
	}

	@Override
	public ProvinceDO queryProvinceByName(String name) {
		return (ProvinceDO)getSqlMapClientTemplate().queryForObject("address.queryProvinceByName",name) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> queryCitys(String provinceCode) {
		return getSqlMapClientTemplate().queryForList("address.queryCitys" , provinceCode);
	}

	@Override
	public CityDO queryCityByName(String name) {
		return (CityDO)getSqlMapClientTemplate().queryForObject("address.queryCityByName",name) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaDO> queryAreas(String cityCode) {
		return getSqlMapClientTemplate().queryForList("address.queryAreas" , cityCode);
	}

	@Override
	public AreaDO queryAreaByName(String name) {
		return (AreaDO)getSqlMapClientTemplate().queryForObject("address.queryAreaByName",name) ;
	}

	}
