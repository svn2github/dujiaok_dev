package com.ssnn.dujiaok.biz.service.product;

import java.util.List;

import com.ssnn.dujiaok.model.ProductDetailDO;

public interface ProductDetailService {
	/**
	 * 获取某个产品明细.
	 * @param productId .
	 * @param productDetailId
	 * @return
	 */
	ProductDetailDO getProductDetail(String productId, String productDetailId);
	/**
	 * 获取产品明细列表.
	 * @param productId .
	 * @param detailId .
	 * @return
	 */
	List<ProductDetailDO> getProductDetails(String productId);
}
