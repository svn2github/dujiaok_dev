package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssnn.dujiaok.biz.service.HotCityService;
import com.ssnn.dujiaok.model.HotCityDO;
import com.ssnn.dujiaok.web.action.BasicAction;

@SuppressWarnings("serial")
public class HotCityListAction extends BasicAction {
	
	@Autowired
	private HotCityService hotCityService ;
	
	private List<HotCityDO> hotCityList ;
	
	@Override
	public String execute() throws Exception {
		
		this.hotCityList = hotCityService.getHotCitys() ;
		
		return SUCCESS ;
	}

	public List<HotCityDO> getHotCityList() {
		return hotCityList;
	}

	
}
