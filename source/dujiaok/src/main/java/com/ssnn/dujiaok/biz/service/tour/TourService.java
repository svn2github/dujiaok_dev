package com.ssnn.dujiaok.biz.service.tour;

import java.util.List;

import com.ssnn.dujiaok.model.Tour;

public interface TourService {
	/**
     * 根据产品ID查询行程
     * 
     * @param productId
     * @return
     */
    public List<Tour> listTourByProductId(Integer productId);
    
    /**
     * 根据产品ID和时间查询酒店行程
     * 
     * @param productId
     * @return
     */
    public List<Tour> listTourByProductIdAndDate(Integer productId, String inday, String outday);

    /**
     * 根据行程ID查询行程
     * 
     * @param tourId
     * @return
     */
    public Tour getTourById(int tourId);

    /**
     * 新增行程，如果已存在，则不新增
     * 
     * @param tour
     * @return
     */
    public int addTour(Tour tour);
    
    /**
     * 根据产品ID和日期查询行程
     * 
     * @param productId
     * @param date
     * @return
     */
    public Tour getTourByIdAndDate(Integer productId, String date);
    
    /**
     * 新增行程，如果已存在，则覆盖
     * @param tour
     * @return
     */
    public int addTourByCoverage(Tour tour);
}
