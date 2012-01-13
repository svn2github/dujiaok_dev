package com.ssnn.dujiaok.biz.service.product.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.dal.product.ProductDao;
import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.model.product.Product;

public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Product getProductById(Integer id) {
		return productDao.getProductById(id);
	}
	
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
