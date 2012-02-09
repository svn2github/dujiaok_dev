package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.ProductDetailDO;

public interface ProductDetailDAO {

	/**
	 * 查询 Detail
	 * @param roomId
	 * @return
	 */
	List<ProductDetailDO> queryDetails(String productId) ;
	
	/**
	 * 插入 Detail
	 * @param roomDetail
	 */
	void insertDetail(ProductDetailDO detail) ;
	
	/**
	 * 删除 Detail
	 * @param roomId
	 */
	void deleteDetails(String productId) ;
}
