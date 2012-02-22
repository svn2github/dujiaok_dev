package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;
import com.ssnn.dujiaok.biz.dal.FrontViewDAO;
import com.ssnn.dujiaok.biz.service.FrontViewService;
import com.ssnn.dujiaok.model.FrontViewDO;

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

    public void setFrontViewDAO(FrontViewDAO frontViewDAO) {
        this.frontViewDAO = frontViewDAO;
    }

}
