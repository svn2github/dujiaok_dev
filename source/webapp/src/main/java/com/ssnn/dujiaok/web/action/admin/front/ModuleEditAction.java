package com.ssnn.dujiaok.web.action.admin.front;

import java.util.List;
import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.web.action.BasicAction;

public class ModuleEditAction extends BasicAction {

    private FrontViewService   frontViewService;
    private FrontConfigService frontConfigService;
    private List<FrontViewDO>  frontViews;
    private FrontConfigDO      frontconfig;

    // 参数name = frontViews[0].frontViewId
    private String             moduleKey;

    // 返回
    private String             message;

    @Override
    public String execute() throws Exception {
        frontconfig = frontConfigService.getOneFrontConfig(moduleKey);
        if (frontconfig != null) {
            frontViews = frontViewService.getFrontViewDOs(moduleKey, frontconfig.getDispalyNum());
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        if (frontViews != null && moduleKey != null && frontViewService.saveFrontViews(frontViews, moduleKey)) {
            message = "saveSuccess";
        } else {
            message = "saveFail";
        }
        return execute();
    }

    public List<FrontViewDO> getFrontViews() {
        return frontViews;
    }

    public void setFrontViews(List<FrontViewDO> frontViews) {
        this.frontViews = frontViews;
    }

    public FrontConfigDO getFrontconfig() {
        return frontconfig;
    }

    public void setFrontconfig(FrontConfigDO frontconfig) {
        this.frontconfig = frontconfig;
    }

    public void setFrontViewService(FrontViewService frontViewService) {
        this.frontViewService = frontViewService;
    }

    public void setFrontConfigService(FrontConfigService frontConfigService) {
        this.frontConfigService = frontConfigService;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
