package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class HotelServiceImpl implements HotelService{

	private HotelDAO hotelDAO ;
	
	public void setHotelDAO(HotelDAO hotelDAO) {
		this.hotelDAO = hotelDAO;
	}

	@Override
	public HotelDO getHotel(String hotelId) {
		return hotelDAO.queryHotel(hotelId) ;
	}

	@Override
	public HotelDO createHotel(HotelDO hotel) {
		hotel.setProductId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL)) ;
		hotelDAO.insertHotel(hotel) ;
		return hotel ; 
	}

	@Override
	public HotelDO updateHotel(HotelDO hotel) {
		hotelDAO.updateHotel(hotel) ;
		return hotel ;
	}
	
	@Override
	public QueryResult<HotelDO> getHotels(Map<String,Object> condition , Pagination pagination) {
		int count = hotelDAO.countHotels(condition) ;
		pagination.setTotalCount(count) ;
		
		List<HotelDO> items = hotelDAO.queryHotel(condition, pagination) ;
		
		return new QueryResult<HotelDO>(items, pagination) ;
	}

}
