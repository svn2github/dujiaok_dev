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
	 * 获取产品明细.
	 * @param productId
	 * @param detailID
	 * @return
	 */
	ProductDetailDO queryDetail(String productId, String detailID);
	/**
	 * 获取当前时间有效的产品detail信息
	 * @param productId
	 * @return
	 */
	List<ProductDetailDO> queryValidDetails(String productId);
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
