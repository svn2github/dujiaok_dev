package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.util.IntegerUtils;

public class IBatisHotelRoomDAO extends SqlMapClientDaoSupport implements HotelRoomDAO{

	@Override
	public HotelRoomDO queryRoom(String roomId) {
		return (HotelRoomDO)getSqlMapClientTemplate().queryForObject("hotel.queryRoom",roomId) ;
	}

	@Override
	public void insertRoom(HotelRoomDO room) {
		getSqlMapClientTemplate().insert("hotel.insertRoom",room) ;
	}

	@Override
	public void updateRoom(HotelRoomDO room) {
		getSqlMapClientTemplate().update("hotel.updateRoom",room) ;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HotelRoomDO> queryRooms(Map<String, Object> condition, Pagination pagination) {
		condition.put("start", pagination.getStart()-1) ;
		condition.put("size", pagination.getSize()) ;
		return getSqlMapClientTemplate().queryForList("hotel.queryRooms" , condition) ;
	}

	@Override
	public int countRooms(Map<String, Object> condition) {
		return IntegerUtils.objectToInt(getSqlMapClientTemplate().queryForObject("hotel.countRooms",condition)) ;
	}

}
