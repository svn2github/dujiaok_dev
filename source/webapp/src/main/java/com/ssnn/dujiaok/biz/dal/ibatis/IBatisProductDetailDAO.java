package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.model.ProductDetailDO;

public class IBatisProductDetailDAO extends SqlMapClientDaoSupport implements ProductDetailDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDetailDO> queryDetails(String productId) {
		return getSqlMapClientTemplate().queryForList("product.queryProductDetails" , productId) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductDetailDO queryDetail(String productId, String detailID) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("productId", productId);
		param.put("productDetailId", detailID);
		return (ProductDetailDO) getSqlMapClientTemplate().queryForObject("product.queryProductDetail", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDetailDO> queryValidDetails(String productId , Date gmtEnd) {
		Map<String,Object> condition = new HashMap<String,Object>() ;
		condition.put("productId", productId) ;
		condition.put("gmtEnd", gmtEnd) ;
		return getSqlMapClientTemplate().queryForList("product.queryValidDetails" , productId);
	}

	@Override
	public void insertDetail(ProductDetailDO detail) {
		getSqlMapClientTemplate().insert("product.insertProductDetail" , detail ) ;
	}

	@Override
	public void deleteDetails(String productId) {
		getSqlMapClientTemplate().delete("product.deleteProductDetails" , productId) ;
	}

	@Override
	public ProductDetailDO queryTodayDetailByProduct(String productId) {
		return (ProductDetailDO)getSqlMapClientTemplate().queryForObject("product.queryTodayDetailByProduct" , productId) ;
	}

}
