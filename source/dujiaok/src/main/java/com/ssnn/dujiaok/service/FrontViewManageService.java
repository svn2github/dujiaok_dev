package com.ssnn.dujiaok.service;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类FrontViewManageService.java的实现描述：TODO 类实现描述 
 * @author ib 2012-2-12 下午11:47:51
 */
public interface FrontViewManageService {
    public Map<String, List<FrontViewDO>> getFrontViewsMap();
}
