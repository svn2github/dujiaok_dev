package com.ssnn.dujiaok.web.action.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.service.HotCityService;
import com.ssnn.dujiaok.model.HotCityDO;
import com.ssnn.dujiaok.web.action.BasicAction;
import com.ssnn.dujiaok.web.action.model.JsonModel;

@SuppressWarnings("serial")
public class HotCityAjaxAction  extends BasicAction {

	private JsonModel<List<HotCityDO>> json = new JsonModel<List<HotCityDO>>() ;
	
	private HotCityService hotCityService ;
	
	@Override
	public String execute() throws Exception {
		List<HotCityDO> hotCityList = hotCityService.getHotCitys() ;
		json.setCode(JsonModel.CODE_SUCCESS) ;
		json.setData(hotCityList) ;
		return SUCCESS ;
	}

	public JsonModel<List<HotCityDO>> getJson() {
		return json;
	}

	public void setHotCityService(HotCityService hotCityService) {
		this.hotCityService = hotCityService;
	}
	
	
}
