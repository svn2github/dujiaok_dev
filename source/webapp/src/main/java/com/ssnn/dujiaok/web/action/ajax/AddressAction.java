package com.ssnn.dujiaok.web.action.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.dal.AddressDAO;
import com.ssnn.dujiaok.model.CityDO;
import com.ssnn.dujiaok.model.ProvinceDO;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class AddressAction extends BasicAction {

	private List result;

	private String parentcode;

	private String parentname ;
	
	/**
	 * province city area
	 */
	private String type;
	
	
	private AddressDAO addressDAO ;
	
	

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	public List<String> getResult() {
		return result;
	}

	

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = getRequest() ;
				
		if (!StringUtils.equals(type, "province") && (StringUtils.isBlank(parentcode)&&StringUtils.isBlank(parentname))) {
			return SUCCESS;
		}
		if(StringUtils.equals("province", type)){
			result = buildResult(addressDAO.queryProvinces()) ;
		}else if(StringUtils.equals("city", type)){
			if(StringUtils.isBlank(parentcode)){
				ProvinceDO province = addressDAO.queryProvinceByName(parentname) ;
				if(province != null){
					parentcode = province.getCode() ;
				}
			}
			result = buildResult(addressDAO.queryCitys(parentcode)) ;
		}else if(StringUtils.equals("area", type)){
			if(StringUtils.isBlank(parentcode)){
				CityDO city = addressDAO.queryCityByName(parentname) ;
				if(city != null){
					parentcode = city.getCode() ;
				}
			}
			result = buildResult(addressDAO.queryAreas(parentcode)) ;
		}
		return SUCCESS;
	}
	
	private List buildResult(List list){
		return list == null ? new ArrayList() : list ;
	}
}
