package com.ssnn.dujiaok.biz.dal.product.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.product.ProductDao;
import com.ssnn.dujiaok.model.product.Product;

public class ProductDaoImpl extends SqlMapClientDaoSupport implements ProductDao {

	@Override
	public Product getProductById(Integer id) {
		return (Product) getSqlMapClientTemplate().queryForObject("product.getProductById", id);
	}

}
