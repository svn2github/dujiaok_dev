package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.model.ProductDetailDO;

public class IBatisProductDetailDAO extends SqlMapClientDaoSupport implements ProductDetailDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDetailDO> queryDetails(String productId) {
		return getSqlMapClientTemplate().queryForList("product.queryProductDetails" , productId) ;
	}
	
	@Override
	public List<ProductDetailDO> queryValidDetails(String productId) {
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

}
