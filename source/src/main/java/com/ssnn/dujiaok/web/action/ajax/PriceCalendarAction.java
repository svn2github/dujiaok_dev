package com.ssnn.dujiaok.web.action.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.biz.service.TicketService;
import com.ssnn.dujiaok.constant.Constant;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.PriceCalendarDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.JacksonHelper;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author shenjia.caosj 2012-2-12
 * 
 */
@SuppressWarnings("serial")
public class PriceCalendarAction extends BasicAction {

	private Log logger = LogFactory.getLog(PriceCalendarAction.class);

	private SelfDriveService selfDriveSerive;

	private TicketService ticketService;

	private HotelRoomService hotelRoomService;

	private String productId;

	private List<PriceCalendarDO> calendar ;
	
	
	@Override
	public String execute() throws Exception {
		List<PriceCalendarDO> calendar1 = null ;
		List<ProductDetailDO> details = null;
		try {
			if (StringUtils.startsWithIgnoreCase(productId,
					Constant.PREFIX_TICKET)) {
				TicketDO item = ticketService.getTicketWithDetails(productId);
				details = item.getDetails();
			} else if (StringUtils.startsWithIgnoreCase(productId,
					Constant.PREFIX_HOTELROOM)) {
				HotelRoomDO item = hotelRoomService
						.getRoomWithDetails(productId);
				details = item.getDetails();
			} else if (StringUtils.startsWithIgnoreCase(productId,
					Constant.PREFIX_SELFDRIVE)) {
				SelfDriveDO item = selfDriveSerive
						.getSelfDriveWithDetails(productId);
				details = item.getDetails();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
// [{severtime:"2012-01",data:[{id:10,name:"250元"},{id:15,name:"250"},{id:16,name:"250"}]},{severtime:"2012-02",data:[{id:12,name:"250元"},{id:14,name:"250"},{id:16,name:"250"}]}];
//[{"servertime":"2011-02","data":[{"id":11,"name":"214"},{"id":10,"name":"222"},{"id":9,"name":"333"},{"id":1,"name":"22.33"}]}]
		calendar = ProductUtils.getPriceCalendar(details);
		
		return SUCCESS;
	}
	
	/**- ---------------------------------------------------------------------------------------------------**/

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setSelfDriveSerive(SelfDriveService selfDriveSerive) {
		this.selfDriveSerive = selfDriveSerive;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	public  List<PriceCalendarDO> getCalendar() {
		return calendar;
	}


}
