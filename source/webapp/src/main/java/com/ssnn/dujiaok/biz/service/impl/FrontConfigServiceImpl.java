package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.dal.FrontConfigDAO;
import com.ssnn.dujiaok.biz.service.FrontConfigService;
import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类FrontConfigServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-12 下午05:39:01
 */
public class FrontConfigServiceImpl implements FrontConfigService {

    private FrontConfigDAO frontConfigDAO;

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.service.FrontConfigService#getFrontConfigs(java. lang.String)
     */
    @Override
    public List<FrontConfigDO> getFrontConfigs(String channelKey) {
        if (channelKey == null) return null;
        return frontConfigDAO.queryFrontConfigs(channelKey);
    }

    public FrontConfigDO getOneFrontConfig(String moduleKey) {
        if (moduleKey == null) return null;
        return frontConfigDAO.queryOneFrontConfig(moduleKey);
    }

    public void setFrontConfigDAO(FrontConfigDAO frontConfigDAO) {
        this.frontConfigDAO = frontConfigDAO;
    }

    public void updateModuleName(String moduleKey, String moduleName) {
        if (StringUtils.isNotBlank(moduleKey)) {
            frontConfigDAO.updateModuleName(moduleKey, moduleName);
        }
    }

}
