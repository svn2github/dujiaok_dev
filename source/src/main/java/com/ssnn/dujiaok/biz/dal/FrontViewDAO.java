package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类FrontViewDAO.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-12 下午11:56:22
 */
public interface FrontViewDAO {

    public List<FrontViewDO> queryFrontViews(String moduleKey, int limit);
    
}
