package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.HotCityDAO;
import com.ssnn.dujiaok.model.HotCityDO;

public class IBatisHotCityDAO extends SqlMapClientDaoSupport implements HotCityDAO {

	@Override
	public Long insertHotCity(HotCityDO hotCity) {
		return (Long)getSqlMapClientTemplate().insert("hotCity.insertHotCity" , hotCity) ;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<HotCityDO> queryHotCitys() {
		return getSqlMapClientTemplate().queryForList("hotCity.queryHotCitys") ;
	}



	@Override
	public void deleteHotCityById(Long id) {
		getSqlMapClientTemplate().delete("hotCity.deleteHotCityById" , id) ;
	}

}
