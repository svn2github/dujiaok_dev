package com.ssnn.dujiaok.biz.service.tour.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.dal.tour.TourDao;
import com.ssnn.dujiaok.biz.service.tour.TourService;
import com.ssnn.dujiaok.model.Tour;

public class TourServiceImpl implements TourService {
	@Autowired
	private TourDao tourDao;
	
	@Override
    public List<Tour> listTourByProductId(Integer productId) {
        return tourDao.listTourByProductId(productId);
    }
    
	@Override
    public List<Tour> listTourByProductIdAndDate(Integer productId, String inday, String outday) {
        return tourDao.listTourByProductIdAndDate(productId, inday, outday);
    }
	
	@Override
    public Tour getTourById(int id) {
        return tourDao.queryTourById(id);
    }
	
    public TourDao getTourDao() {
        return tourDao;
    }

    public void setTourDao(TourDao tourDao) {
        this.tourDao = tourDao;
    }
    
    @Override
    public int addTour(Tour tour) {
        Tour checkTour = tourDao.queryTourByProductIdAndStartDate(tour.getProductId(),
                tour.getStartDate());
        if (null != checkTour) {
            return 0;
        } else {
            return tourDao.addTour(tour);
        }
    }
    
    @Override
    public Tour getTourByIdAndDate(Integer productId, String date) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int addTourByCoverage(Tour tour) {
        Tour checkTour = tourDao.queryTourByProductIdAndStartDate(tour.getProductId(),
                tour.getStartDate());
        if (null != checkTour) {
            return tourDao.updateTourPrice(tour.getProductId(), tour.getStartDate(),
                    tour.getSinglePrice(), tour.getDoublePrice(), tour.getChildPrice(),
                    tour.getComboPrice(), tour.getHotelPrice());
        } else {
            return tourDao.addTour(tour);
        }
    }
}
