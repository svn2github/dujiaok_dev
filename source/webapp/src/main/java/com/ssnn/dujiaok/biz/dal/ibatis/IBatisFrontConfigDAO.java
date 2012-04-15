package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.FrontConfigDAO;
import com.ssnn.dujiaok.model.FrontConfigDO;

/**
 * 类IBatisFrontConfigDAO.java的实现描述：前台展示配置DAO逻辑
 * 
 * @author ib 2012-2-12 上午05:01:55
 */
public class IBatisFrontConfigDAO extends SqlMapClientDaoSupport implements FrontConfigDAO {

    private final static String QUERY_FRONT_CONFIGS    = "frontConfig.queryFrontConfigs";
    private final static String QUERY_ONE_FRONT_CONFIG = "frontConfig.queryOneFrontConfig";
    private final static String UPDATE_MODULE_NAME     = "frontConfig.updateModuleName";

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontConfigDAO#queryFrontConfigs(java.lang.String )
     */
    @Override
    public List<FrontConfigDO> queryFrontConfigs(String channelKey) {
        return getSqlMapClientTemplate().queryForList(QUERY_FRONT_CONFIGS, channelKey);
    }

    @Override
    public FrontConfigDO queryOneFrontConfig(String moduleKey) {
        return (FrontConfigDO) getSqlMapClientTemplate().queryForObject(QUERY_ONE_FRONT_CONFIG, moduleKey);
    }

    /*
     * (non-Javadoc)
     * @see com.ssnn.dujiaok.biz.dal.FrontConfigDAO#updateModuleName(java.lang.String, java.lang.String)
     */
    @Override
    public void updateModuleName(String moduleKey, String moduleName) {
        Map map = new HashMap<String, String>();
        map.put("moduleKey", moduleKey);
        map.put("moduleName", moduleName);
        getSqlMapClientTemplate().update(UPDATE_MODULE_NAME, map);
    }

}
