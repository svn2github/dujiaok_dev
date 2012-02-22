package com.ssnn.dujiaok.biz.dal.tour.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.tour.TourDao;
import com.ssnn.dujiaok.model.Tour;

public class TourDaoImpl extends SqlMapClientDaoSupport implements TourDao {
	@Override
	public List<Tour> listTourByProductId(Integer productId) {
        return (List<Tour>) getSqlMapClientTemplate().queryForList("tour.listTourByProductId", productId);
    }
	
	@Override
    public List<Tour> listTourByProductIdAndDate(Integer productId, String inday, String outday) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("productId", productId);
        condition.put("inday", inday);
        condition.put("outday", outday);

        return (List<Tour>) getSqlMapClientTemplate().queryForList(
        		"tour.listTourByProductIdAndDate", condition);
    }
	
	@Override
    public Tour queryTourById(Integer id) {
        return (Tour) getSqlMapClientTemplate().queryForObject("tour.queryTourById", id);
    }
	
	@Override
    public int addTour(Tour tour) {
        return (Integer) getSqlMapClientTemplate().insert("tour.addTour", tour);
    }
	
	@Override
    public Tour queryTourByProductIdAndStartDate(Integer productId, String startDate) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("productId", productId);
        condition.put("startDate", startDate);

        return (Tour) getSqlMapClientTemplate().queryForObject("tour.queryTourByProductIdAndStartDate", condition);
    }
    
    @Override
    public int updateTourPrice(Integer productId, String startDate, Double singlePrice,
                               Double doublePrice, Double childPrice, Double comboPrice,
                               Double hotelPrice) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("childPrice", childPrice);
        condition.put("comboPrice", comboPrice);
        condition.put("doublePrice", doublePrice);
        condition.put("hotelPrice", hotelPrice);
        condition.put("singlePrice", singlePrice);
        condition.put("startDate", startDate);
        condition.put("productId", productId);

        return (Integer) getSqlMapClientTemplate().update("tour.updateTourPrice", condition);
    }
    
    @Override
    public int updateTour(Tour tour) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("id", tour.getId());

        return (Integer) getSqlMapClientTemplate().update("tour.updateTour", tour);
    }
}
