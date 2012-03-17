package com.ssnn.dujiaok.biz.service.product.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.biz.service.product.ProductDetailService;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.util.ProductUtils;

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

	@Override
	public BigDecimal getTodayBottomPrice(String productId) {
		ProductDetailDO detail = productDetailDAO.queryTodayDetailByProduct(productId) ;
		if(detail == null){
			return null ;
		}
		BigDecimal price = ProductUtils.getBottomPrice(detail) ;
		return price ;
	}
}
