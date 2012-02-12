package com.ssnn.dujiaok.web.action.product;

import java.util.List;

import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.biz.service.tour.TourService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.PriceCalendarDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.Tour;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.web.action.BasicAction;

public class ProductAction extends BasicAction {
	
	private Integer productId;
	
	private ProductService productService;
	
	private TourService tourService;

	@Override
	public String execute() throws Exception {
		try {
			Product product = this.productService.getProductById(productId);
			this.getHttpSession().setAttribute("product", product);
			List<Tour> tours = tourService.listTourByProductId(productId);
			this.getHttpSession().setAttribute("dateList", tours);
			double tehuiMin = 0;
	        for (int i = 0; i < tours.size(); i++) {
	            Tour tour = tours.get(i);
	            if(tour==null){
	                continue;
	            }
	            
	            double tehuiDay = 0;
	            if (product.getTypeId() == 2) {
	                tehuiDay = tour.getHotelPrice();
	            } else if (product.getTypeId() == 1) {
	            	tehuiDay = tour.getComboPrice();
	            } else {
	                tehuiDay = tour.getDoublePrice()/2;
	            }
	            
	            if(i == 0){
	                tehuiMin = tehuiDay;
	            }else{
	                if(tehuiDay < tehuiMin){
	                    tehuiMin = tehuiDay;
	                }
	            }
	        }
	        this.getHttpSession().setAttribute("tehuiMin", tehuiMin);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String getSelfDriveProductDetail() {
		try {
			SelfDriveDO param = new SelfDriveDO();
			//param.setProductId(this.productId.toString());
			param.setProductId("ZJ1201201447352717");
			SelfDriveDO product = this.productService.getSelfDriveProductDetail(param);
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String getHotelProductDetail() {
		try {
			HotelDO param = new HotelDO();
//			param.setProductId(this.productId.toString());
			param.setProductId("JD1201171609535427");
			HotelDO product = this.productService.getHotelProductDetail(param);
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	public String getHotelRoomProductDetail() {
		try {
			HotelRoomDO param = new HotelRoomDO();
			//param.setProductId(this.productId.toString());
			param.setProductId("FJ1201181530466890");
			HotelRoomDO product = this.productService.getHotelRoomProductDetail(param);
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	public String getTicketProductDetail() {
		try {
			TicketDO param = new TicketDO();
			//param.setProductId(this.productId.toString());
			param.setProductId("MP1201171339498931");
			TicketDO product = this.productService.getTicketProductDetail(param);
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	public static List<PriceCalendarDO> getProductPriceCalendar(Product2 product) {
		
		return null;
	}
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public TourService getTourService() {
		return tourService;
	}

	public void setTourService(TourService tourService) {
		this.tourService = tourService;
	}

	public String getProductId() {
		return productId.toString();
	}

	public void setProductId(String productId) {
		try {
			this.productId = Integer.valueOf(productId);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid product id");
		}
	}
}