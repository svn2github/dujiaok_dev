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

    private static final String QUERY_FRONT_VIEWS  = "frontView.queryFrontViews";
    private static final String INSERT_FRONT_VIEWS = "frontView.insertFrontView";
    private static final String UPDATE_FRONT_VIEWS = "frontView.updateFrontView";

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

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontViewDAO#insertFrontView(com.ssnn.dujiaok.model.FrontViewDO)
     */
    @Override
    public void insertFrontView(FrontViewDO frontViewDO) {
        if (frontViewDO != null) {
            getSqlMapClientTemplate().insert(INSERT_FRONT_VIEWS, frontViewDO);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontViewDAO#updateFrontView(com.ssnn.dujiaok.model.FrontViewDO)
     */
    @Override
    public boolean updateFrontView(FrontViewDO frontViewDO) {
        if (frontViewDO != null) {
            return getSqlMapClientTemplate().update(UPDATE_FRONT_VIEWS, frontViewDO) > 0;
        }
        return false;
    }

}
