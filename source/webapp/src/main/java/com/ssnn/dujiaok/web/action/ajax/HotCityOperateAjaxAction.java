package com.ssnn.dujiaok.web.action.ajax;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.ssnn.dujiaok.biz.service.HotCityService;
import com.ssnn.dujiaok.model.HotCityDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.action.model.JsonModel;

@SuppressWarnings("serial")
public class HotCityOperateAjaxAction extends BasicAction  {

	@Autowired
	private HotCityService hotCityService ;
	
	private JsonModel<Boolean> json = new JsonModel<Boolean>() ;
	
	private String cityName ;
	
	private Long cityId ;
	
	public String add() throws Exception {
		
		HotCityDO hotCity = new HotCityDO() ;
		cityName = URLDecoder.decode(cityName,"UTF-8") ;
		hotCity.setCityName(cityName) ;
		try {
			hotCityService.insertHotCity(hotCity) ;
			json.setCode(JsonModel.CODE_SUCCESS) ;
			
		} catch (DuplicateKeyException e){
			json.setCode(JsonModel.CODE_ILL_ARGS) ;
			json.setDetail("dujiaok.hotCity.name.duplicate") ;
		}
		
		return SUCCESS ;
	}
	
	public String delete() throws Exception {
		
		try {
			
			hotCityService.deleteHotCityById(cityId) ;
			json.setCode(JsonModel.CODE_SUCCESS) ;
			
		} catch (Exception e){
			json.setCode(JsonModel.CODE_FAIL) ;
			json.setDetail(e.getMessage()) ;
		}
		
		return SUCCESS ;
	}


	public JsonModel<Boolean> getJson() {
		return json;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
}
