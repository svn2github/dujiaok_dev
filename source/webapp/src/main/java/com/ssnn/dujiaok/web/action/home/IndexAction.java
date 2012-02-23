package com.ssnn.dujiaok.web.action.home;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.BO.FrontViewBO;
import com.ssnn.dujiaok.constant.FrontConfigConstants;
import com.ssnn.dujiaok.constant.IndexChannelConstants;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class IndexAction extends BasicAction {

	private FrontViewBO frontViewBO;
	private Map<String, List<FrontViewDO>> frontViewMap;

	@Override
	public String execute() throws Exception {
		frontViewMap = frontViewBO
				.getFrontViewsMap(FrontConfigConstants.CHANNEL_INDEX_PAGE);
		return SUCCESS;
	}

	public Map<String, List<FrontViewDO>> getFrontViewMap() {
		return frontViewMap;
	}

	public void setFrontViewMap(Map<String, List<FrontViewDO>> frontViewMap) {
		this.frontViewMap = frontViewMap;
	}

	public void setFrontViewBO(FrontViewBO frontViewBO) {
		this.frontViewBO = frontViewBO;
	}

}
