package com.ssnn.dujiaok.web.action.home;

import java.util.Map;
import com.ssnn.dujiaok.biz.BO.FrontViewBO;
import com.ssnn.dujiaok.constant.FrontConfigConstants;
import com.ssnn.dujiaok.model.VO.FrontViewVO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class SelfDriveAction extends BasicAction {

    private FrontViewBO              frontViewBO;
    private Map<String, FrontViewVO> frontViewMap;

    @Override
    public String execute() throws Exception {
        frontViewMap = frontViewBO.getFrontViewsMap(FrontConfigConstants.CHANNEL_SELF_DRIVE_PAGE);
        Map<String, FrontViewVO> commonViewMap = frontViewBO.getFrontViewsMap(FrontConfigConstants.CHANNEL_COMMON_PAGE);
        frontViewMap.putAll(commonViewMap);
        return SUCCESS;
    }

    public Map<String, FrontViewVO> getFrontViewMap() {
        return frontViewMap;
    }

    public void setFrontViewBO(FrontViewBO frontViewBO) {
        this.frontViewBO = frontViewBO;
    }

}
