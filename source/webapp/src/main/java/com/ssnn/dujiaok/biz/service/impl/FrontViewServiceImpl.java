package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.dal.FrontViewDAO;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.constant.FrontConfigConstants;
import com.ssnn.dujiaok.model.FrontViewDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;

/**
 * 类FrontViewServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-13 上午12:17:11
 */
public class FrontViewServiceImpl implements FrontViewService {

    private FrontViewDAO frontViewDAO;

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.service.FrontViewService#getFrontViewDOs(java.lang.String, int)
     */
    @Override
    public List<FrontViewDO> getFrontViewDOs(String moduleKey, int limit) {
        if (moduleKey == null || limit < 0) {
            return null;
        }
        return frontViewDAO.queryFrontViews(moduleKey, limit);
    }

    public boolean saveFrontViews(List<FrontViewDO> frontViews, String moduleKey) {
        long orderKey = System.currentTimeMillis();
        boolean result = true;
        for (FrontViewDO frontViewDO : frontViews) {
            frontViewDO.setModuleKey(moduleKey);
            frontViewDO.setOrderKey(orderKey--);
            if (!insertOrUpdate(frontViewDO)) {
                result = false;
            }
        }
        return result;
    }

    public boolean insertOrUpdate(FrontViewDO frontViewDO) {
        if (frontViewDO == null) {
            return false;
        }

        if (StringUtils.isNotBlank(frontViewDO.getFrontViewId())) {
            return frontViewDAO.updateFrontView(frontViewDO);
        } else {
            frontViewDO.setFrontViewId(UniqueIDUtil.buildFrontViewId(FrontConfigConstants.FRONT_VIEW_ID_PREFIX));
            frontViewDAO.insertFrontView(frontViewDO);
            return true;
        }
    }

    public void setFrontViewDAO(FrontViewDAO frontViewDAO) {
        this.frontViewDAO = frontViewDAO;
    }
}
