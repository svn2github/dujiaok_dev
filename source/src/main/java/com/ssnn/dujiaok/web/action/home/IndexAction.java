package com.ssnn.dujiaok.web.action.home;

import com.ssnn.dujiaok.service.FrontConfigLoadService;
import com.ssnn.dujiaok.web.action.BasicAction;

public class IndexAction extends BasicAction {

    private FrontConfigLoadService frontConfigLoadService;

    @Override
    public String execute() throws Exception {
        frontConfigLoadService.getIndexChannelConfigs();
        return SUCCESS;
    }

    public void setFrontConfigLoadService(FrontConfigLoadService frontConfigLoadService) {
        this.frontConfigLoadService = frontConfigLoadService;
    }

}
