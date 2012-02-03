package com.ssnn.dujiaok.biz.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.dal.product.ProductDao;
import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.model.product.detail.HotelDetail;
import com.ssnn.dujiaok.model.product.detail.HotelRoomDetail;
import com.ssnn.dujiaok.model.product.detail.SelfDriveDetail;
import com.ssnn.dujiaok.model.product.detail.TicketDetail;

public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SelfDriveDAO selfDriveDAO;
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
	public Product2 getSelfDriveProductDetail(Product2 product) {
		product.setType(1);
		Product2 product2 = this.getProduct(product);
		List<SelfDriveDetail> selfDrives = this.selfDriveDAO.getSelfDriveWithProducts(product);
		product2.setDetails(selfDrives);
		return product2;
	}
	
	@Override
	public Product2 getHotelProductDetail(Product2 product) {
		product.setType(3);
		Product2 product2 = this.getProduct(product);
		List<HotelDetail> hotels = this.hotelDAO.getHotelByProducts(product);
		product2.setDetails(hotels);
		return product2;
	}

	@Override
	public Product2 getHotelRoomProductDetail(Product2 product) {
		product.setType(2);
		Product2 product2 = this.getProduct(product);
		List<HotelRoomDetail> rooms = this.hotelRoomDAO.getHotelRoomWithProducts(product);
		product2.setDetails(rooms);
		return product2;
	}

	@Override
	public Product2 getTicketProductDetail(Product2 product) {
		product.setType(4);
		Product2 product2 = this.getProduct(product);
		List<TicketDetail> tickets = this.ticketDAO.getTicketWithProducts(product);
		product2.setDetails(tickets);
		return product2;
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
}
