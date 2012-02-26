package com.ssnn.dujiaok.model.VO;

import java.util.List;

import com.ssnn.dujiaok.model.FrontConfigDO;
import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类FrontViewVO.java的实现描述：前台展示模型
 * 
 * @author ib 2012-2-26 下午09:06:34
 */
public class FrontViewVO {

    private List<FrontViewDO> frontViews;
    private FrontConfigDO     config;

    public List<FrontViewDO> getFrontViews() {
        return frontViews;
    }

    public void setFrontViews(List<FrontViewDO> frontViews) {
        this.frontViews = frontViews;
    }

    public FrontConfigDO getConfig() {
        return config;
    }

    public void setConfig(FrontConfigDO config) {
        this.config = config;
    }

}
