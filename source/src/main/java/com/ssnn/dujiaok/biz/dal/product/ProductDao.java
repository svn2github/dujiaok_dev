package com.ssnn.dujiaok.biz.dal.product;

import com.ssnn.dujiaok.model.product.Product;

public interface ProductDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductById(Integer id);
}
