package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.biz.exception.GetHotPlaceException;

/**
 * 热门地点
 * @author langben 2012-5-11
 *
 */
public interface HotPlaceService {

	String getHotPlaceContent(boolean enfource) throws  GetHotPlaceException ;
}
