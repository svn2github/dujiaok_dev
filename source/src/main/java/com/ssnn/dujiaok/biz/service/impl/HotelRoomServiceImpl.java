package com.ssnn.dujiaok.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class HotelRoomServiceImpl implements HotelRoomService {

	private HotelRoomDAO hotelRoomDAO;

	private ProductDetailDAO productDetailDAO;

	public void setHotelRoomDAO(HotelRoomDAO hotelRoomDAO) {
		this.hotelRoomDAO = hotelRoomDAO;
	}

	public void setProductDetailDAO(ProductDetailDAO productDetailDAO) {
		this.productDetailDAO = productDetailDAO;
	}

	@Override
	public HotelRoomDO getRoom(String roomId) {
		return hotelRoomDAO.queryRoom(roomId);
	}

	@Override
	public HotelRoomDO getRoomWithDetails(String roomId) {
		HotelRoomDO room = hotelRoomDAO.queryRoom(roomId);
		if (room != null) {
			List<ProductDetailDO> details = productDetailDAO.queryDetails(roomId);
			room.setDetails(details);
		}
		return room;
	}

	@Override
	public HotelRoomDO createRoomAndDetails(HotelRoomDO room) {
		List<ProductDetailDO> details = room.getDetails();
		if (details != null) {
			Date gmtExpire = getExpireDate(details);
			room.setGmtExpire(gmtExpire);
			room.setProductId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL_ROOM));
			hotelRoomDAO.insertRoom(room);
			for (ProductDetailDO detail : details) {
				detail.setProductId(room.getProductId());
				productDetailDAO.insertDetail(detail);
			}
		}

		return room;
	}

	@Override
	public HotelRoomDO updateRoomAndDetails(HotelRoomDO room) {
		List<ProductDetailDO> details = room.getDetails();
		String roomId = room.getProductId();
		if (details != null) {
			// 删除之前的detail，重新插入
			productDetailDAO.deleteDetails(roomId);
			Date gmtExpire = getExpireDate(details);
			room.setGmtExpire(gmtExpire);
			hotelRoomDAO.updateRoom(room);
			for (ProductDetailDO detail : details) {
				detail.setProductId(roomId);
				productDetailDAO.insertDetail(detail);
			}
		}
		return room;
	}

	@Override
	public QueryResult<HotelRoomDO> getRooms(Map<String, Object> condition,
			Pagination pagination) {

		pagination.setTotalCount(hotelRoomDAO.countRooms(condition));
		List<HotelRoomDO> items = hotelRoomDAO
				.queryRooms(condition, pagination);
		QueryResult<HotelRoomDO> result = new QueryResult<HotelRoomDO>(items,
				pagination);
		return result;
	}

	@Override
	public List<HotelRoomDO> getRooms(String hotelId) {
		return hotelRoomDAO.queryRooms(hotelId);
	}

	private Date getExpireDate(List<ProductDetailDO> details) {
		Date gmtExpire = null;
		for (ProductDetailDO detail : details) {
			Date gmtEnd = detail.getGmtEnd();
			if (gmtExpire == null) {
				gmtExpire = gmtEnd;
			} else {
				if (gmtExpire.before(gmtEnd)) {
					gmtExpire = gmtEnd;
				}
			}
		}
		return gmtExpire;
	}

	@Override
	public void deleteHotelRoom(String roomId) {
		hotelRoomDAO.deleteHotelRoom(roomId);
		productDetailDAO.deleteDetails(roomId);
	}

	@Override
	public void deleteHotelRooms(String hotelId) {
		List<HotelRoomDO> list = hotelRoomDAO.queryRooms(hotelId);
		for (HotelRoomDO room : list) {
			deleteHotelRoom(room.getProductId());
		}

	}
}
