package com.ssnn.dujiaok.biz.service.product;

import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;

public interface ProductService {
	
	public Product getProductById(Integer id);
	
	public Product2 getProduct(Product2 product);
	/**
	 * 获取自驾产品详细信息.
	 * @param product
	 * @return
	 */
	public SelfDriveDO getSelfDriveProductDetail(SelfDriveDO product);
	/**
	 * 获取酒店产品详细信息.
	 * @param product
	 * @return
	 */
	public HotelDO getHotelProductDetail(HotelDO product);
	/**
	 * 获取酒店房间产品详细信息.
	 * @param product
	 * @return
	 * 
	 */
	public HotelRoomDO getHotelRoomProductDetail(HotelRoomDO product);
	/**
	 * 获取门票产品详细信息.
	 * @param product
	 * @return
	 */
	public TicketDO getTicketProductDetail(TicketDO product);
}