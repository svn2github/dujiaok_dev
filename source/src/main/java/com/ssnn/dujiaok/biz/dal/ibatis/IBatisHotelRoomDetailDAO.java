package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.HotelRoomDetailDAO;
import com.ssnn.dujiaok.model.HotelRoomDetailDO;

public class IBatisHotelRoomDetailDAO extends SqlMapClientDaoSupport implements HotelRoomDetailDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<HotelRoomDetailDO> queryRoomDetails(String roomId) {
		return getSqlMapClientTemplate().queryForList("hotel.queryRoomDetails",roomId) ;
	}

	@Override
	public void insertRoomDetail(HotelRoomDetailDO roomDetail) {
		getSqlMapClientTemplate().insert("hotel.insertRoomDetail" , roomDetail) ;
	}

	@Override
	public void deleteRoomDetails(String roomId) {
		getSqlMapClientTemplate().delete("hotel.deleteRoomDetails",roomId) ;
	}

}
