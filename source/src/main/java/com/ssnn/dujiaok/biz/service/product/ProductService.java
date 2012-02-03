package com.ssnn.dujiaok.biz.service.product;

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
	public Product2 getSelfDriveProductDetail(Product2 product);
	/**
	 * 获取酒店产品详细信息.
	 * @param product
	 * @return
	 */
	public Product2 getHotelProductDetail(Product2 product);
	/**
	 * 获取酒店房间产品详细信息.
	 * @param product
	 * @return
	 * 
	 */
	public Product2 getHotelRoomProductDetail(Product2 product);
	/**
	 * 获取门票产品详细信息.
	 * @param product
	 * @return
	 */
	public Product2 getTicketProductDetail(Product2 product);
}