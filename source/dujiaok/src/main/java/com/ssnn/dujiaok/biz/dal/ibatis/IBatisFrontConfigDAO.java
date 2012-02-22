package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.FrontConfigDAO;
import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类IBatisFrontConfigDAO.java的实现描述：前台展示配置DAO逻辑
 * 
 * @author ib 2012-2-12 上午05:01:55
 */
public class IBatisFrontConfigDAO extends SqlMapClientDaoSupport implements FrontConfigDAO {

    private final static String QUERY_FRONT_CONFIGS = "frontConfig.queryFrontConfigs";

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontConfigDAO#queryFrontConfigs(java.lang.String)
     */
    @Override
    public List<FrontConfigDO> queryFrontConfigs(String channelKey) {
        return getSqlMapClientTemplate().queryForList(QUERY_FRONT_CONFIGS, channelKey);
    }

}
