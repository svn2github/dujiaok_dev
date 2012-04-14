package com.ssnn.dujiaok.biz.BO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.model.VO.FrontViewVO;

/**
 * 类DefaultFrontViewManageService.java的实现描述：模块管理service
 * 
 * @author ib 2012-2-13 上午02:00:01
 */
public class FrontViewBO {

    private FrontViewService   frontViewService;
    private FrontConfigService frontConfigService;

    public Map<String, FrontViewVO> getFrontViewsMap(String channelKey) {
        List<FrontConfigDO> configs = (List<FrontConfigDO>) frontConfigService.getFrontConfigs(channelKey);
        if (configs == null) {
            return null;
        }

        Map<String, FrontViewVO> resultMap = new HashMap<String, FrontViewVO>();
        String moduleKey;
        // 获取所有的moduleKey
        for (FrontConfigDO frontConfigDO : configs) {
            moduleKey = frontConfigDO.getModuleKey();
            if (moduleKey != null) {
                List<FrontViewDO> frontViews = (List<FrontViewDO>) frontViewService.getFrontViewDOs(
                                                                                                    moduleKey,
                                                                                                    frontConfigDO.getDispalyNum());
                FrontViewVO viewVO = new FrontViewVO();
                viewVO.setConfig(frontConfigDO);
                viewVO.setFrontViews(frontViews);
                resultMap.put(moduleKey, viewVO);
            }
        }
        return resultMap;
    }

    public FrontViewVO getDefaultFrontViewsByModuleKey(String moduleKey) {
        FrontConfigDO config = frontConfigService.getOneFrontConfig(moduleKey);
        if (config != null) {
            FrontViewVO viewVO = new FrontViewVO();
            viewVO.setConfig(config);
            List<FrontViewDO> frontViews = frontViewService.getFrontViewDOs(moduleKey, config.getDispalyNum());
            viewVO.setFrontViews(frontViews);
            return viewVO;
        }

        return null;
    }

    public void setFrontViewService(FrontViewService frontViewService) {
        this.frontViewService = frontViewService;
    }

    public void setFrontConfigService(FrontConfigService frontConfigService) {
        this.frontConfigService = frontConfigService;
    }

}
