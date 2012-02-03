package com.ssnn.dujiaok.biz.dal.product.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.product.ProductDao;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;

public class ProductDaoImpl extends SqlMapClientDaoSupport implements ProductDao {

	@Override
	public Product getProductById(Integer id) {
		return (Product) getSqlMapClientTemplate().queryForObject("product.getProductById", id);
	}
	
	@Override
	public Product2 getProduct(Product2 product) {
		return (Product2) getSqlMapClientTemplate().queryForObject("product.getProduct", product);
	}
}
