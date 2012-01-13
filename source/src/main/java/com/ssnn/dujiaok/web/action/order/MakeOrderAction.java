package com.ssnn.dujiaok.web.action.order;

import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.biz.service.tour.TourService;
import com.ssnn.dujiaok.model.Tour;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.util.string.StringUtil;
import com.ssnn.dujiaok.web.action.BasicAction;

public class MakeOrderAction extends BasicAction {
	private String productId;
	private Integer countPerson;
	private Integer countChild;
	private String tourId;
	
	private TourService tourService;
	private ProductService productService;
	
	@Override
	public String execute() throws Exception  {
        int countTotal = this.countPerson + this.countChild;

        Tour tour = tourService.getTourById(new Integer(tourId));

        if (tour == null || tour.getStartDate() == null) {
            return SUCCESS;
        }

        if (tour.getChildPrice() == null) {
        	getHttpSession().setAttribute("childPrice", 0);
        } else {
        	getHttpSession().setAttribute("childPrice", tour.getChildPrice());
        }

        if (tour.getDoublePrice() == null) {
        	getHttpSession().setAttribute("doublePrice", 0);
        } else {
        	getHttpSession().setAttribute("doublePrice", tour.getDoublePrice());
        }

        if (tour.getSinglePrice() == null) {
        	getHttpSession().setAttribute("singlePrice", 0);
        } else {
        	getHttpSession().setAttribute("singlePrice", tour.getSinglePrice());
        }
        
        if (tour.getComboPrice() == null) {
            getHttpSession().setAttribute("comboPrice", 0);
        } else {
        	getHttpSession().setAttribute("comboPrice", tour.getComboPrice());
        }
        
        Product product = productService.getProductById(new Integer(productId));
        if(product != null){
        	getHttpSession().setAttribute("product", product);
        }

        getHttpSession().setAttribute("day", tour.getStartDate());
        getHttpSession().setAttribute("dest", product.getDest());
        getHttpSession().setAttribute("countPerson", this.countPerson);
        getHttpSession().setAttribute("countChild", this.countChild);
        getHttpSession().setAttribute("countTotal", countTotal);
        getHttpSession().setAttribute("productId", productId);
        return SUCCESS;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getCountPerson() {
		return countPerson;
	}
	public void setCountPerson(String countPerson) {
		if (countPerson == null) {
			this.countPerson = 0;
		}
		try {
			this.countPerson = Integer.valueOf(countPerson);
		} catch (Exception e) {
			this.countPerson = 0;
		}
	}
	public Integer getCountChild() {
		return countChild;
	}
	public void setCountChild(String countChild) {
		if (countChild == null) {
			this.countChild = 0;
		}
		try {
			this.countChild = Integer.valueOf(countChild);
		} catch (Exception e) {
			this.countChild = 0;
		}
	}
	public String getTourId() {
		return tourId;
	}
	public void setTourId(String tourId) {
		this.tourId = tourId;
	}
	public TourService getTourService() {
		return tourService;
	}
	public void setTourService(TourService tourService) {
		this.tourService = tourService;
	}
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
