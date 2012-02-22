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

	private String code;

	private String name;
	
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

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = getRequest() ;
		String n = request.getParameter("name") ;
		
		if (!StringUtils.equals(type, "province") && (StringUtils.isBlank(code)&&StringUtils.isBlank(name))) {
			return SUCCESS;
		}
		if(StringUtils.equals("province", type)){
			result = buildResult(addressDAO.queryProvinces()) ;
		}else if(StringUtils.equals("city", type)){
			if(StringUtils.isNotBlank(name)){
				ProvinceDO province = addressDAO.queryProvinceByName(name) ;
				if(province != null){
					code = province.getCode() ;
				}
			}
			result = buildResult(addressDAO.queryCitys(code)) ;
		}else if(StringUtils.equals("area", type)){
			if(StringUtils.isNotBlank(name)){
				CityDO city = addressDAO.queryCityByName(name) ;
				if(city != null){
					code = city.getCode() ;
				}
			}
			result = buildResult(addressDAO.queryAreas(code)) ;
		}
		return SUCCESS;
	}
	
	private List buildResult(List list){
		return list == null ? new ArrayList() : list ;
	}
}
