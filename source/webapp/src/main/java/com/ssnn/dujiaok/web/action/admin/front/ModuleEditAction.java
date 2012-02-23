package com.ssnn.dujiaok.web.action.admin.front;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.BO.FrontViewBO;
import com.ssnn.dujiaok.constant.IndexChannelConstants;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class ModuleEditAction extends BasicAction {

	private FrontViewBO frontViewManageService;
	private List<FrontViewDO> frontViews;

	// 参数
	private String moduleKey;

	@Override
	public String execute() throws Exception {
		// frontViewMap = indexFrontViewManageService.getFrontViewsMap();
		return SUCCESS;
	}

	public List<FrontViewDO> getFrontViews() {
		return frontViews;
	}

	public void setFrontViews(List<FrontViewDO> frontViews) {
		this.frontViews = frontViews;
	}

	public void setFrontViewManageService(
			FrontViewBO frontViewManageService) {
		this.frontViewManageService = frontViewManageService;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

}
