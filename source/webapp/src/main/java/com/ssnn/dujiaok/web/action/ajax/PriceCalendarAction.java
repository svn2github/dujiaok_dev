package com.ssnn.dujiaok.web.action.ajax;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ssnn.dujiaok.biz.dal.ProductDetailDAO;
import com.ssnn.dujiaok.model.PriceCalendarDO;
import com.ssnn.dujiaok.model.ProductDetailDO;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 
 * @author langben 2012-2-12
 * 
 */
@SuppressWarnings("serial")
public class PriceCalendarAction extends BasicAction {

	private Log logger = LogFactory.getLog(PriceCalendarAction.class);

	private ProductDetailDAO productDetailDAO ;

	private String productId;

	private List<PriceCalendarDO> calendar ;
	
	
	
	
	@Override
	public String execute() throws Exception {
		
		List<ProductDetailDO> details = null ;
		if(StringUtils.isNotBlank(productId)){
			details = productDetailDAO.queryValidDetails(productId, ProductUtils.getDetailValidEnd()) ;
		}
		
// [{severtime:"2012-01",data:[{id:10,name:"250元"},{id:15,name:"250"},{id:16,name:"250"}]},{severtime:"2012-02",data:[{id:12,name:"250元"},{id:14,name:"250"},{id:16,name:"250"}]}];
//[{"servertime":"2011-02","data":[{"id":11,"name":"214"},{"id":10,"name":"222"},{"id":9,"name":"333"},{"id":1,"name":"22.33"}]}]
		if(details != null){
			calendar = ProductUtils.getPriceCalendar(details);
		}
		
		return SUCCESS;
	}
	
	/**- ---------------------------------------------------------------------------------------------------**/

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public void setProductDetailDAO(ProductDetailDAO productDetailDAO) {
		this.productDetailDAO = productDetailDAO;
	}

	public  List<PriceCalendarDO> getCalendar() {
		return calendar;
	}


}
