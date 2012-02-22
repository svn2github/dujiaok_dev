package com.ssnn.dujiaok.biz.service.product.impl;

import java.util.List;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.ProductDetailDO;

public class ProductDetailServiceImpl implements ProductDetailService {
	
	private ProductDetailDAO productDetailDAO;
	@Override
	public ProductDetailDO getProductDetail(String productId, String detailId) {
		return this.productDetailDAO.queryDetail(productId, detailId);
	}
	
	@Override
	public List<ProductDetailDO> getProductDetails(String productId) {
		return this.productDetailDAO.queryDetails(productId);
	}
	
	public void setProductDetailDAO(ProductDetailDAO productDetailDAO) {
		this.productDetailDAO = productDetailDAO;
	}
}
