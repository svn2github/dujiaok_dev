package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.FrontViewDAO;
import com.ssnn.dujiaok.model.FrontViewDO;

/**
 * 类IBatisFrontViewDAO.java的实现描述：TODO 类实现描述
 * 
 * @author ib 2012-2-12 下午11:57:11
 */
public class IBatisFrontViewDAO extends SqlMapClientDaoSupport implements FrontViewDAO {

    private static final String QUERY_FRONT_VIEWS = "frontView.queryFrontViews";

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontViewDAO#queryFrontViews(java.lang.String, int)
     */
    @Override
    public List<FrontViewDO> queryFrontViews(String moduleKey, int displayNum) {
        Map map = new HashMap();
        map.put("moduleKey", moduleKey);
        map.put("displayNum", displayNum);
        return getSqlMapClientTemplate().queryForList(QUERY_FRONT_VIEWS, map);
    }

}
