package com.ssnn.dujiaok.biz.dal.product;

import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;

public interface ProductDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductById(Integer id);
	/**
	 * 获取产品.
	 * @param product .
	 * @return .
	 */
	public Product2 getProduct(Product2 product);
}