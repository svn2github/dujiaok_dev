package com.ssnn.dujiaok.web.action.admin.front;

import java.util.Map;
import com.ssnn.dujiaok.biz.BO.FrontViewBO;
import com.ssnn.dujiaok.constant.FrontConfigConstants;
import com.ssnn.dujiaok.model.VO.FrontViewVO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class HotelAction extends BasicAction {

    private FrontViewBO              frontViewBO;
    private Map<String, FrontViewVO> frontViewMap;
    private String                   isEdit = "true";

    @Override
    public String execute() throws Exception {
        frontViewMap = frontViewBO.getFrontViewsMap(FrontConfigConstants.CHANNEL_HOTEL_PAGE);
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

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }
}
