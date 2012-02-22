package com.ssnn.dujiaok.biz.dal.tour;

import java.util.List;

import com.ssnn.dujiaok.model.Tour;

public interface TourDao {

    public List<Tour> listTourByProductId(Integer productId);

    public List<Tour> listTourByProductIdAndDate(Integer productId, String inday, String outday);

    public Tour queryTourById(Integer id);

    public int addTour(Tour tour);

    public Tour queryTourByProductIdAndStartDate(Integer productId, String startDate);

    public int updateTourPrice(Integer productId, String startDate, Double singlePrice,
                               Double doublePrice, Double childPrice, Double comboPrice,
                               Double hotelPrice);

    public int updateTour(Tour tour);

}