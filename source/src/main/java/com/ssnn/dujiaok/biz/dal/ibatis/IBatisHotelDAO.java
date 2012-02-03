package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.util.IntegerUtils;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public class IBatisHotelDAO extends SqlMapClientDaoSupport implements HotelDAO {

	@Override
	public HotelDO queryHotel(String hotelId) {
		return (HotelDO)getSqlMapClientTemplate().queryForObject("hotel.queryHotel",hotelId);
	}

	@Override
	public void insertHotel(HotelDO hotel) {
		getSqlMapClientTemplate().insert("hotel.insertHotel", hotel) ;
	}

	@Override
	public void updateHotel(HotelDO hotel) {
		getSqlMapClientTemplate().update("hotel.updateHotel", hotel) ;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HotelDO> queryHotel(Map<String, Object> condition,Pagination pagination) {
		condition.put("start", pagination.getStart()-1) ;
		condition.put("size", pagination.getSize()) ;
		return getSqlMapClientTemplate().queryForList("hotel.queryHotels", condition);
	}

	@Override
	public int countHotels(Map<String, Object> condition) {
		return IntegerUtils.objectToInt(getSqlMapClientTemplate().queryForObject("hotel.countHotels")) ;
	}

	@Override
	public void deleteHotel(String hotelId) {
		getSqlMapClientTemplate().delete("hotel.deleteHotel",hotelId) ;
	}

}
