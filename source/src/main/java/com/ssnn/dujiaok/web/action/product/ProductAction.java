package com.ssnn.dujiaok.web.action.product;

import java.util.List;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.model.Tour;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

public class ProductAction extends BasicAction {
	
	private String productId;
	
	private SelfDriveService selfDriveService;
	private HotelService hotelService;
	private HotelRoomService hotelRoomService;
	private TicketService ticketService;

	@Override
	public String execute() throws Exception {
		try {
			Product product = null;//this.productService.getProductById(productId);
			this.getHttpSession().setAttribute("product", product);
			List<Tour> tours = null;//tourService.listTourByProductId(productId);
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
			//param.setProductId(this.productId);
			param.setProductId("ZJ1201201447352717");
			SelfDriveDO product = this.selfDriveService.getSelfDriveWithDetails(param.getProductId());
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			//e.printStackTrace();
			//TODO LOG EXCEPTION STACK
			return ERROR;
		}
	}
	
	public String getHotelProductDetail() {
		try {
			HotelDO param = new HotelDO();
//			param.setProductId(this.productId);
			param.setProductId("JD1201171609535427");
			HotelDO product = this.hotelService.getHotel(param.getProductId());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			//TODO LOG EXCEPTION STACK
			return ERROR;
		}
	}
	
	public String getHotelRoomProductDetail() {
		try {
			HotelRoomDO param = new HotelRoomDO();
			//param.setProductId(this.productId);
			param.setProductId("FJ1201181530466890");
			HotelRoomDO product = this.hotelRoomService.getRoomWithDetails(param.getProductId());
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			//TODO LOG EXCEPTION STACK
			return ERROR;
		}
	}
	
	public String getTicketProductDetail() {
		try {
			TicketDO param = new TicketDO();
			//param.setProductId(this.productId);
			param.setProductId("MP120117105857939");
			TicketDO product = this.ticketService.getTicketWithDetails(param.getProductId());
			ProductUtils.filteInvalideProductDetail(product.getDetails());
			this.getHttpSession().setAttribute("product", product);
			return SUCCESS;
		} catch (Exception e) {
			//TODO LOG EXCEPTION STACK
			return ERROR;
		}
	}

	public String getProductId() {
		return productId.toString();
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setSelfDriveService(SelfDriveService selfDriveService) {
		this.selfDriveService = selfDriveService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
}