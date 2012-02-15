package com.ssnn.dujiaok.biz.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.product.ProductDao;
import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.util.ProductUtils;

public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SelfDriveDAO selfDriveDAO;
	@Autowired
	private ProductDetailDAO productDetailDAO;
	@Autowired
	private HotelDAO hotelDAO;
	@Autowired
	private HotelRoomDAO hotelRoomDAO;
	@Autowired
	private TicketDAO ticketDAO;
	
	@Override
	public Product getProductById(Integer id) {
		return productDao.getProductById(id);
	}
	
	@Override
	public Product2 getProduct(Product2 product) {
		return this.productDao.getProduct(product);
	}
	
	@Override
	public SelfDriveDO getSelfDriveProductDetail(SelfDriveDO product) {
		SelfDriveDO selfDriveDO = this.selfDriveDAO.querySelfDrive(product.getProductId());
		if (selfDriveDO == null) {
			return null;
		}
		List<ProductDetailDO> details = this.productDetailDAO.queryValidDetails(product.getProductId() , ProductUtils.getDetailValidEnd());
		selfDriveDO.setDetails(details);
		return selfDriveDO;
	}
	
	@Override
	public HotelDO getHotelProductDetail(HotelDO product) {
		return this.hotelDAO.queryHotel(product.getProductId());
	}

	@Override
	public HotelRoomDO getHotelRoomProductDetail(HotelRoomDO product) {
		HotelRoomDO hotelRoomDO = this.hotelRoomDAO.queryRoom(product.getProductId());
		if (hotelRoomDO == null) {
			return null;
		}
		List<ProductDetailDO> details = this.productDetailDAO.queryValidDetails(product.getProductId() ,  ProductUtils.getDetailValidEnd());
		hotelRoomDO.setDetails(details);
		return hotelRoomDO;
	}

	@Override
	public TicketDO getTicketProductDetail(TicketDO product) {
		TicketDO ticketDO = this.ticketDAO.queryTicket(product.getProductId());
		if (ticketDO == null) {
			return null;
		}
		List<ProductDetailDO> details = this.productDetailDAO.queryValidDetails(product.getProductId() ,  ProductUtils.getDetailValidEnd());
		ticketDO.setDetails(details);
		return ticketDO;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setSelfDriveDAO(SelfDriveDAO selfDriveDAO) {
		this.selfDriveDAO = selfDriveDAO;
	}

	public void setHotelDAO(HotelDAO hotelDAO) {
		this.hotelDAO = hotelDAO;
	}

	public void setHotelRoomDAO(HotelRoomDAO hotelRoomDAO) {
		this.hotelRoomDAO = hotelRoomDAO;
	}

	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public void setProductDetailDAO(ProductDetailDAO productDetailDAO) {
		this.productDetailDAO = productDetailDAO;
	}
}
