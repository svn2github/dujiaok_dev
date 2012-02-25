package com.ssnn.dujiaok.biz.service;

import java.util.List;

import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类FrontViewService.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-13 上午12:16:40
 */
public interface FrontViewService {

    public List<FrontViewDO> getFrontViewDOs(String moduleKey, int limit);
    public boolean saveFrontViews(List<FrontViewDO> frontViews, String moduleKey);
}
