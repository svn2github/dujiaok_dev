package com.ssnn.dujiaok.web.action.admin.front;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.constant.IndexChannelConstants;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.service.FrontViewManageService;
import com.ssnn.dujiaok.web.action.BasicAction;

public class IndexAction extends BasicAction {

    private FrontViewManageService         indexFrontViewManageService;
    private Map<String, List<FrontViewDO>> frontViewMap;
    private IndexChannelConstants          constants = new IndexChannelConstants();
    private String                         isEdit = "true";

    @Override
    public String execute() throws Exception {
        frontViewMap = indexFrontViewManageService.getFrontViewsMap();
        return SUCCESS;
    }

    public Map<String, List<FrontViewDO>> getFrontViewMap() {
        return frontViewMap;
    }

    public void setFrontViewMap(Map<String, List<FrontViewDO>> frontViewMap) {
        this.frontViewMap = frontViewMap;
    }

    public void setIndexFrontViewManageService(FrontViewManageService indexFrontViewManageService) {
        this.indexFrontViewManageService = indexFrontViewManageService;
    }

    public IndexChannelConstants getConstants() {
        return constants;
    }

    public void setConstants(IndexChannelConstants constants) {
        this.constants = constants;
    }
    
    public String getIsEdit() {
        return isEdit;
    }
    
    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

}
